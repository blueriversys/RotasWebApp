package com.javacodegeeks.dao;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class StatDaoImpl implements StatDao {
	private static final String DB_DRIVER = "org.postgresql.Driver";
    private final static Logger logger = Logger.getLogger(StatDaoImpl.class);
    private final static String PROPERTIES_FILE = "application.properties";
	
    // to be used for Heroku deployment
	private Connection getConnection() throws URISyntaxException, SQLException {
	    URI dbUri = new URI(System.getenv("DATABASE_URL"));

	    String username = dbUri.getUserInfo().split(":")[0];
	    String password = dbUri.getUserInfo().split(":")[1];
	    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

	    return DriverManager.getConnection(dbUrl, username, password);
	}
	
/*    
    // to be used for local deployment
	private static Connection getConnection() throws SQLException {
		// for local test
		try {
			Class.forName(DB_DRIVER);
		} 
		catch (ClassNotFoundException e) {
			logger.info("Connection error: {}", e);
		}

		Properties props = new Properties();
		try {
			props.load(StatDaoImpl.class.getClassLoader().getResourceAsStream("/"+PROPERTIES_FILE));
		}
		catch (Exception e) {
			logger.info("Error reading properties: {}", e);
		}
		
	    String dbUrl = props.getProperty("jdbc.url");
	    String user = props.getProperty("jdbc.username");
	    String password = props.getProperty("jdbc.password");
	    return DriverManager.getConnection(dbUrl, user, password);
	}
*/	
	public int read(int year, int month) throws SQLException, URISyntaxException {
		String selectTableSQL = "select count from USAGE "
				+ "where year = ? and month = ?";
		Connection conn = getConnection();
		PreparedStatement preparedStatement = conn.prepareStatement(selectTableSQL);
		preparedStatement.setInt(1,  year);
		preparedStatement.setInt(2,  month);
		ResultSet rs = preparedStatement.executeQuery();

		int rowCount = 0;
		
		while (rs.next()) {
			rowCount = rs.getInt("COUNT");
			break;
		}
		
		preparedStatement.close();
		conn.close();
		return rowCount;
	}
	
	public void insert(int year, int month) throws SQLException, URISyntaxException {
		String insertTableSQL = "INSERT INTO USAGE"
				+ "(year, month, day, count) VALUES"
				+ "(?,?,?,?)";
		Connection conn = getConnection();
		PreparedStatement preparedStatement = conn.prepareStatement(insertTableSQL);
		preparedStatement.setInt(1, year);
		preparedStatement.setInt(2, month);
		preparedStatement.setInt(3, 0);
		preparedStatement.setInt(4, 1);
		
		// execute insert SQL stetement
		preparedStatement.executeUpdate();
		preparedStatement.close();
		conn.close();
	}
	
	public void update(int year, int month, int count) throws SQLException, URISyntaxException {
		String updateTableSQL = "UPDATE USAGE "
				+ "set count = ? "
				+ "where year = ? and month = ?";
		Connection conn = getConnection();
		PreparedStatement preparedStatement = conn.prepareStatement(updateTableSQL);
		preparedStatement.setInt(1, count);
		preparedStatement.setInt(2, year);
		preparedStatement.setInt(3, month);
		preparedStatement.executeUpdate();
		preparedStatement.close();
		conn.close();
	}
	
	
}
