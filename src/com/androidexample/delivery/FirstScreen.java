package com.androidexample.delivery;


import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FirstScreen extends BaseActivity {
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    
    // Load dialog
    private ProgressDialog pDialog;
    
	// JSON Node names
	private static final String TAG_CONTACTS = "merchants";
	private static final String TAG_STREET = "street";
	private static final String TAG_ZIP = "zip_code";
	private static final String TAG_STATE = "state";
	
	// temp
	private static final String TAG_ID = "id";
	private static final String TAG_ORDER = "ordering";
	private static final String TAG_SUMMARY = "summary";
	private static final String TAG_LOCATION = "location";
	
	JSONArray merchants = null;

	// Hashmap for ListView
	ArrayList<HashMap<String, String>> merchantsList;
    
    private Button btnSearch;	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.firstscreen); 
		
		merchantsList = new ArrayList<HashMap<String, String>>();

		
		
		// buttons
		btnSearch = (Button)findViewById(R.id.search_btn);
		
		btnSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			    
				// Calling async task to get json
				new GetContacts().execute();
			}
		});
		
	} 
	
	// open new activity
	public void viewResult(ArrayList<HashMap<String, String>> file) {
		if (file.isEmpty() || file.equals("null")) {
			return;
		}
		Intent i = new Intent(this, DisplayRestaurant.class);
		i.putExtra("hashmap",file);
		startActivity(i, true);
	}
	
	@Override
    protected void onDestroy() {
        super.onDestroy();
    }
	
	/**
	 * Async task class to get json by making HTTP call
	 * */
	private class GetContacts extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(FirstScreen.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			
			SearchRestaurant searchRes = new SearchRestaurant("input here");
		    String jsonStr = searchRes.getList();

			Log.d("Response: ", "> " + jsonStr);
			if (jsonStr != null) {
				try {
					
//					// get string of Jsonobj
//					JSONObject searchResult = new JSONObject(jsonStr);
//					JSONObject geoCodedLocation = searchResult.getJSONObject("search_address");
//					
//					// set the data for listview
//					String street = geoCodedLocation.getString(TAG_STREET);
//					String zip = geoCodedLocation.getString(TAG_ZIP);
//					String state = geoCodedLocation.getString(TAG_STATE);
//					
//					// tmp hashmap for single row on listview
//					HashMap<String, String> hashmap = new HashMap<String, String>();
//	//System.out.println("ID HERE     " + street);
//					
//					// adding each child node to HashMap key => value
//					hashmap.put(TAG_STREET, street);
//					hashmap.put(TAG_ZIP, zip);
//					hashmap.put(TAG_STATE, state);
//					
//					// adding tags to one row on listview
//					merchantsList.add(hashmap);
//	//System.out.println("ID HERE     " + hashmap);
					
					
					// get string of Jsonobj
					JSONObject searchResult = new JSONObject(jsonStr);
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
			}
			
//			if (jsonStr != null) {
//				try {
//					JSONObject jsonObj = new JSONObject(jsonStr);
//					
//					// Getting JSON Array node
//					contacts = jsonObj.getJSONArray(TAG_CONTACTS);
//
//					// looping through All Contacts
//					for (int i = 0; i < contacts.length(); i++) {
//						JSONObject c = contacts.getJSONObject(i);
//						
//						String id = c.getString(TAG_ID);
//						String name = c.getString(TAG_NAME);
////						String email = c.getString(TAG_EMAIL);
////						String address = c.getString(TAG_ADDRESS);
////						String gender = c.getString(TAG_GENDER);
////
////						// Phone node is JSON Object
////						JSONObject phone = c.getJSONObject(TAG_PHONE);
////						String mobile = phone.getString(TAG_PHONE_MOBILE);
////						String home = phone.getString(TAG_PHONE_HOME);
////						String office = phone.getString(TAG_PHONE_OFFICE);
//
//						// tmp hashmap for single contact
//						HashMap<String, String> contact = new HashMap<String, String>();
//
//						// adding each child node to HashMap key => value
//						contact.put(TAG_ID, id);
//						contact.put(TAG_NAME, name);
////						contact.put(TAG_EMAIL, email);
////						contact.put(TAG_PHONE_MOBILE, mobile);
//
//						// adding contact to contact list
//						merchantsList.add(contact);
//					}
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			} else {
//				Log.e("ServiceHandler", "Couldn't get any data from the url");
//			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			viewResult(merchantsList);
		}

	}
	

}
