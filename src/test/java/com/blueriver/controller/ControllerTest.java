package com.blueriver.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.blueriver.controller.ApplicationController;
import com.blueriver.service.StatService;

/**
 * 
 * @author Blueriver Systems, LLC
 *
 */
public class ControllerTest {
	private static Logger logger = Logger.getLogger(ControllerTest.class);
	
    private MockMvc mockMvc;

    @Mock
    private StatService statService;

    @InjectMocks
    private ApplicationController appController;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(appController)
                .build();
    }

    @Test
    public void testWebServiceMode() throws Exception {
        int mockedReturn = 200;
        int expectedReturn = 200;
        int year = 2017;
        int month = 5;
        
        when(statService.read(year,month)).thenReturn(mockedReturn);

        MvcResult result = mockMvc.perform(get("/stats/ws/{year}/{month}", year, month))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE+";charset=ISO-8859-1"))
                .andExpect(jsonPath("$.count", is(expectedReturn)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        logger.info("Result of call WS type: "+content);
        
        // verifies the service was actually invoked with exactly these parameters
        verify(statService, times(1)).read(year,month);
        verifyNoMoreInteractions(statService);
    }
    
    @Test
    public void testJspGetMode() throws Exception {
        int mockedReturn = 274;
        int expectedReturn = 274;
        int year = 2017;
        int month = 5;
        
        when(statService.read(year,month)).thenReturn(mockedReturn);

        mockMvc.perform(get("/stats/{year}/{month}", year, month))
                .andExpect(status().isOk())
                .andExpect(view().name("stats"))
                .andExpect(forwardedUrl("stats"))
                .andExpect(model().attribute("callCount", expectedReturn))
                .andDo(MockMvcResultHandlers.print());

        // verifies the service was actually invoked with exactly these parameters
        verify(statService, times(1)).read(year,month);
        verifyNoMoreInteractions(statService);
    }
    
    /**
     * Tests whether the controller returns -1 when a month/year combination
     * not found in the database.
     * 
     * @throws Exception
     */
    @Test
    public void testYearMonthNotFound() throws Exception {
        int mockedReturn = -1;
        int year = 3017;
        int month = 5;

        when(statService.read(year,month)).thenReturn(mockedReturn);

        mockMvc.perform(get("/stats/{year}/{month}", year, month))
		        .andExpect(status().isOk())
		        .andExpect(view().name("stats"))
		        .andExpect(forwardedUrl("stats"))
		        .andExpect(model().attribute("callCount", mockedReturn))
		        .andDo(MockMvcResultHandlers.print());

        // verifies the service was actually invoked with exactly these parameters
        verify(statService, times(1)).read(year,month);
        verifyNoMoreInteractions(statService);
    }
    
}
