package com.androidexample.delivery;

import java.util.ArrayList;

public class Menu {
	String name;
	ArrayList<String> foodName;

	public void setName(String n) {name = n;}
	public String getName() {return name;}
	
	
	public void setFoodName(ArrayList<String> f) {
		foodName = new ArrayList<String>();
		for (String name : f)
			foodName.add(name);
	}
	public ArrayList<String> getFoodName() {return foodName;}
//
//	public void setPrice(double p) {price = p;}
//	public String getPrice() {return "$" + price;}
//	
	public Menu(String n, ArrayList<String> f) {
		name = n;
		foodName = new ArrayList<String>();
		for (String name : f)
			foodName.add(name);
//		price = p;
	}
	
}
