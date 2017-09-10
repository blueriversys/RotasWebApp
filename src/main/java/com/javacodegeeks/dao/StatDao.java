package com.javacodegeeks.dao;

import java.net.URISyntaxException;
import java.sql.SQLException;

public interface StatDao {
	public int read(int year, int month) throws SQLException, URISyntaxException;
	public void insert(int year, int month) throws SQLException, URISyntaxException;
	public void update(int year, int month, int count) throws SQLException, URISyntaxException;
}
