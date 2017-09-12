package com.blueriver.controller;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.blueriver.controller.Rota;

@XmlRootElement (name = "rotas")
@XmlType (propOrder={"status","rotas"})
public class RotasSet {
	private String status;
	private Rota[] rotas;
	
	public RotasSet() {
		
	}
	
	public String getStatus() {
		return status;
	}

	@XmlElement (name="status")
	public void setStatus(String status) {
		this.status = status;
	}

	public Rota[] getRotas() {
		return rotas;
	}
	
	@XmlElement (name="rota")
	public void setRotas(Rota[] rotas) {
		this.rotas = rotas;
	}
}
