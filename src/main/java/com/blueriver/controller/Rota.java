package com.blueriver.controller;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType (propOrder={"name","origin","dest","distance"})
public class Rota {
	private String name;
	private String origin;
	private String dest;
	private String distance;
	
	public Rota() {
		
	}
	
	public String getDistance() {
		return distance;
	}
	
	public String getName() {
		return name;
	}
	
	public String getOrigin() {
		return origin;
	}
	
	public String getDest() {
		return dest;
	}
	
	@XmlElement (name="via")
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlElement (name="origem")
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	@XmlElement (name="destino")
	public void setDest(String dest) {
		this.dest = dest;
	}
	
	@XmlElement (name="distancia")
	public void setDistance(String distance) {
		this.distance = distance;
	}
	
	
}
