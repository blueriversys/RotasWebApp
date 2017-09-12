package com.blueriver.json;

/**
 * Java bean to be returned to client as JSON in web service calls.
 * 
 * @author Blueriver Systems, LLC
 *
 */
public class Stat {
	private int count;

	public Stat(int count) {
		this.count = count;
	}
	
	// getters
	public int getCount() {
		return count;
	}

	// setters
	public void setCount(int count) {
		this.count = count;
	}
	
	
}
