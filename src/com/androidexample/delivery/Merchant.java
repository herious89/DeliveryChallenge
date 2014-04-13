package com.androidexample.delivery;

public class Merchant {

	String name;
	int id;
	String address;
	String phone;
	double distance;

	public void setName(String n) {name = n;}
	public String getName() {return name;}
	
	public void setID(int i) {id = i;}
	public int getID() {return id;}
	
	public void setAddress(String a) {address = a;}
	public String getAddress() {return address;}

	
	public void setPhone(String p) {phone = p;}
	public String getPhone() {return phone;}

	public void setDistance(double d) {distance = d;}
	public String getDistance() {return distance + " miles";}
	
	public Merchant(String n, int i, String a, String p, double d) {
		name = n;
		id = i;
		address = a;
	    phone = p;
	    distance = d;
	}
}
