package com.androidexample.delivery;

public class Merchant {

	String name;
	int id;
	String street;
	String cuisine;
	double distance;

	public void setName(String n) {name = n;}
	public String getName() {return name;}
	
	public void setID(int i) {id = i;}
	public int getID() {return id;}
	
	public void setStreet(String n) {street = n;}
	public String getStreet() {return street;}


	public void setCuisine(String n) {cuisine = n;}
	public String getCuisine() {return cuisine;}

	public void setDistance(double d) {distance = d;}
	public String getDistance() {return distance + " miles";}
	
	public Merchant(String n, int i, String a, String c, double d) {
		name = n;
		id = i;
		street = a;
	    cuisine = c;
	    distance = d;
	}
}
