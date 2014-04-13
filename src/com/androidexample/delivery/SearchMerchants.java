package com.androidexample.delivery;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * This class receives input address, creates http call to 
 * the delivery server and returns the merchants' data 
 * @author brian & lam
 *
 */

public class SearchMerchants {
	
	// temp
	private static final String TAG_ID = "id";
	private static final String TAG_ORDER = "ordering";
	private static final String TAG_SUMMARY = "summary";
	private static final String TAG_LOCATION = "location";
	
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
    
    // The searchAddress input
	private String searchAddress;
	
	/**
	 * The constructor of the class to initilize 
	 * the searchAddress
	 * @param address The user's input from FirstScreen
	 */
	public SearchMerchants(String address) {
		searchAddress = address;
	}
	
	/**
	 * The getList method call the search function, converts
	 * the data into JSONObject, and returns the data
	 * @return merchantsList The list of available merchants
	 */
	public ArrayList<HashMap<String, String>> getList() {;
		//String list = search(searchAddress);
		String list = search(SEARCH_ADDRESS); // for faster search

		// Hashmap for ListView
		ArrayList<HashMap<String, String>> merchantsList = new ArrayList<HashMap<String, 
																	String>>();
		
		// Store the array of JSONObjects 
		JSONArray merchants = null;
		
		Log.d("Response: ", "> " + list);
		if (list != null) {
			try {					
				// get string of Jsonobj
				JSONObject searchResult = new JSONObject(list);
				// Getting JSON Array node
				merchants = searchResult.getJSONArray("merchants");
				
				// looping through all merchants
				for (int i = 0; i < merchants.length(); i++) {
					JSONObject m = merchants.getJSONObject(i);
					
					
					String id = m.getString(TAG_ID);
					//String oder = m.getString(TAG_ORDER);
					//String location = m.getString(TAG_LOCATION);
					
					// get summary
					JSONObject summary = m.getJSONObject(TAG_SUMMARY);
					String name = summary.getString("name");
					
					// odering node is JSON Object (sub-category)
					JSONObject odering = m.getJSONObject(TAG_ORDER);
					boolean acceptCardBool = odering.getBoolean("delivery_processes_card");
					String acceptCard = String.valueOf(acceptCardBool);
					
					// location node is JSON Object (sub-category)
					JSONObject location = m.getJSONObject(TAG_LOCATION);
					String distance = location.getString("distance");
					String street = location.getString("street");						
			
					
					// tmp hashmap for single row
					HashMap<String, String> tmp = new HashMap<String, String>();

					// adding each child node to HashMap key => value
					tmp.put(TAG_ID, id);
					tmp.put(TAG_ORDER, acceptCard);
					tmp.put("distance", distance);
					tmp.put("street", street);
					tmp.put("name", name);

					// adding contact to contact list
					merchantsList.add(tmp);
				}					
			}
			catch (JSONException e) {
					e.printStackTrace();
			}

		} else {
			Log.e("SearchRestaurant", "Couldn't get any data from the url");
			return null;
		}
		
		return merchantsList;
	}
	
	/**
	 * The search method create a http call to delivery server
	 * and returns the string output
	 * @param address The input address
	 * @return buffer The string output from server
	 */
	private String search(String address) {
//	    String url = host + SEARCH_URL + "?client_id="
//	    			+ CLIENT_ID + "&address=" + address;
		// The url to connect
		String url = "https://api.delivery.com/merchant/search/delivery?client_id=ZjkxODFiNWRkMTYzOWNhMzEzZTk4ZTZjNTU4MDM2ZjJj&address=1330%201st%20Ave,%2010021";
	    
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
}