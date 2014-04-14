package com.androidexample.delivery;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class receives input address, creates http call to 
 * the delivery server and returns the merchants' data 
 * @author brian & lam & quang
 *
 */

public class SearchMerchants {
		
	// For server authentication 
	final static String host = "http://sandbox.delivery.com/";	 
    final static String GUEST_TOKEN = "Guest-Token";
    final static String AUTH_TOKEN = "Authorization";
    final static String GUEST_TOKEN_URL = "customer/auth/guest";
    final static String CUSTOMER_CART_URL = "customer/cart";
    final static String CHECKOUT_URL = "customer/cart";
    final static String CC_URL = "customer/cc";
    final static String AUTH_URL = "customer/auth";
    final static String LOCATION_URL = "customer/location";
    final static String ORDER_URL = "customer/orders/recent";
    final static String SEARCH_URL = "merchant/search/delivery";    
    final static String SEARCH_ADDRESS = "1330 1st Ave, 10021";
    final static String ADDRESS_APT = "Apt 123";    
    final static String CLIENT_ID = "MzMxMjA4N2FjOWM0YjQ1YmIyYzgwMTI1MmIzMjA1MDYz";    
    final static String ORDER_TYPE = "delivery";
    
	/**
	 * The search method create a http call to delivery server
	 * and returns the string output
	 * @param address The input address
	 * @return buffer The string output from server
	 */
	public static String search(String input, int type) {
		String url = "";
//	    String url = host + SEARCH_URL + "?client_id="
//	    			+ CLIENT_ID + "&address=" + address;
		if (type == 0)
			// The url to connect
			url = "https://api.delivery.com/merchant/search/delivery?client_id=ZjkxODFiNWRkMTYzOWNhMzEzZTk4ZTZjNTU4MDM2ZjJj&address=1330%201st%20Ave,%2010021";
		else
			url = "https://api.delivery.com/merchant/835/menu";
		// The input stream to hold data from server
		InputStream is = null;
	    
		// To create http call
	    HttpURLConnection con = null;
	    try {
	    	// Create a httpp call by using the url
	    	con = (HttpURLConnection) (new URL(url)).openConnection();
	    	// Send request and connect to sever
	    	con.setRequestMethod("GET");
	    	con.setDoInput(true);
	    	con.connect();
	    	
	    	// Buffer to read from the input stream
	    	StringBuffer buffer = new StringBuffer();
	    	is = con.getInputStream();
	    	
	    	String line = null;
	    	BufferedReader br = new BufferedReader(new InputStreamReader(is));
	    	
	    	// Continue to read from the input stream
	    	while (  (line = br.readLine()) != null )
	    		buffer.append(line + "\r\n");
	    	
	    	// Close the input stream and disconnect from server
	    	is.close();
	    	con.disconnect();
	    	
	    	// Return the buffer
	    	return buffer.toString();
	    }
	    catch (Throwable t) {
	    	t.printStackTrace();
	    }
	    finally {
	    	try { is.close(); } catch (Throwable t) {}
	    	try { is.close(); } catch (Throwable t) {}
	    }
	    
	    return null;
    }
	
	public static ArrayList<Merchant> createMerList(JSONObject result) {

		ArrayList<Merchant> m = new ArrayList<Merchant>();
		try {
			JSONObject searchResult = result;
			JSONArray mArray = searchResult.getJSONArray("merchants");

		
			// variables that hold infor. for a merchant
			String name, address, cuisine;
			int id;
			double distance;
			// create array of merchants
			for (int i = 0; i < mArray.length(); i++)
			{
				cuisine = "";
				name = mArray.getJSONObject(i).getJSONObject("summary").getString("name");
				id = mArray.getJSONObject(i).getInt("id");
				address = mArray.getJSONObject(i).getJSONObject("location").getString("street");
				try {
					JSONArray cui = mArray.getJSONObject(i).getJSONObject("summary")
							.getJSONArray("cuisines");
					int size = cui.length();
					for (int j = 0; j < size; j++)
						if (j < size - 1)
							cuisine = cuisine + cui.getString(j) + ", ";
						else
							cuisine = cuisine + cui.getString(j);
				} catch (JSONException e) {
					cuisine = "No tags";
				}
				distance = mArray.getJSONObject(i).getJSONObject("location").getDouble("distance");
				m.add(new Merchant(name, id, address, cuisine, distance));		
			}	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return m;
	}
	
	public static ArrayList<Menu> createMenuList(JSONObject result) {

		ArrayList<Menu> m = new ArrayList<Menu>();
		try {
			JSONObject searchResult = result;
			JSONArray mArray = searchResult.getJSONArray("menu");

		
			// variables that hold info for a merchant
			String name; 
			ArrayList<String> fName = new ArrayList<String>();
			double price;
			// create array of merchants
			for (int i = 0; i < mArray.length(); i++)
			{
				name = mArray.getJSONObject(i).getString("name");
				JSONArray fNameArray = mArray.getJSONObject(i).getJSONArray("children");
				for (int j = 0; j < fNameArray.length(); j++) 
					fName.add(fNameArray.getJSONObject(i).getString("name"));

				m.add(new Menu(name, fName));		
			}	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return m;
	}
}