package com.androidexample.delivery;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import net.sf.json.JSONObject;


public class SearchRestaurant {
	
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
    
	private String searchAddress;
	
	public SearchRestaurant(String address) {
		searchAddress = address;
	}
	
	public String getList() {;
		//String list = search(searchAddress);
		String list = search(SEARCH_ADDRESS); // for faster search
		return list;
	}
	
	private String search(String address) {
	    String url = host + SEARCH_URL + "?client_id="
	    			+ CLIENT_ID + "&address=" + address;
	    InputStream is = null;
	    
	    HttpURLConnection con = null;
	    try {
	    	con = (HttpURLConnection) (new URL(url)).openConnection();
	    	con.setRequestMethod("GET");
	    	con.setDoInput(true);
	    	con.connect();
	    	
	    	StringBuffer buffer = new StringBuffer();
	    	is = con.getInputStream();
	    	
	    	String line = null;
	    	BufferedReader br = new BufferedReader(new InputStreamReader(is));
	    	while (  (line = br.readLine()) != null )
	    		buffer.append(line + "\r\n");
	    	is.close();
	    	con.disconnect();
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
}