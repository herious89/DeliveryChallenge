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
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * This class display the search screen where the app
 * receives users' input and login information
 * @author brian & lam
 *
 */
public class FirstScreen extends BaseActivity {
	
	// Hold the user's input 
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";	   
    final static String SEARCH_ADDRESS = "1330 1st Ave, 10021";
    
    // Load dialog
    private ProgressDialog pDialog;
    
	// Search button
    private Button btnSearch;	
	
    /**
     * The onCreate method displays the search screen,
     * sets the search button to listen to the click event
     * which will then trigger the Async task
     */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.firstscreen); 
		
		// buttons
		btnSearch = (Button)findViewById(R.id.search_btn);
		
		// Set the button the listen the click event
		btnSearch.setOnClickListener(new OnClickListener() {
			// Calling the event
			@Override
			public void onClick(View v) {			    
				// Calling async task 
				new GetContacts().execute();
			}
		});
		
	}
	
	/**
	 * The Data methods holds the search result as a string
	 * for easy access from other activity
	 * @param 
	 */
	public static class Data {
		private static String strResult;
		private static ArrayList<Merchant> merchants;
		
		public static void setResult(String s) {strResult = s;}
		public static String getResult() {return strResult;}

		public static void setMerchantList(ArrayList<Merchant> m) {merchants = m;}
		public static ArrayList<Merchant> getMerchantList() {return merchants;}
	}
	
	/**
	 * The viewResult methods call the DisplayRestaurant activity 
	 * to display the list of merchants in the proper format 
	 * @param
	 */
	public void viewResult() {
		JSONObject searchResult;
		try {
			searchResult = new JSONObject(Data.getResult());
			if (searchResult.has("merchants")) {
				Intent i = new Intent(this, DisplayMerchantsActivity.class); 
				startActivity(i, true);								
			}
			else { // Display error message
				String msg = searchResult.getJSONObject("message").getJSONObject("user_msg").toString();
				AlertDialog.Builder adb = new AlertDialog.Builder(FirstScreen.this);
				adb.setTitle("Error");
				adb.setMessage(msg);
				adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}      
				});
				adb.show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
    protected void onDestroy() {
        super.onDestroy();
    }
	
	/**
	 * The getContacts method is a Async task which will 
	 * create searchRestaurant object to get data from
	 * delivery server
	 * @author brian & lam
	 *
	 */
	private class GetContacts extends AsyncTask<Void, Void, Void> {
		
		/**
		 * The onPreExecute method display the waiting message
		 * while the program is executing the search function
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(FirstScreen.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		/**
		 * The doInBackGround method creates the searchRestaurant
		 * object and gets the list of available merchants 
		 */
		@Override
		protected Void doInBackground(Void... arg0) {
			
			// SearchMerchants search = new SearchMerchants("input here");
			Data.setResult(SearchMerchants.search(SEARCH_ADDRESS));
			Data.setMerchantList(SearchMerchants.createList(Data.getResult()));
			return null;
		}

		/**
		 * The onPostExecute views the list of merchants found
		 * after the searching method had successfully executed
		 */
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			viewResult();
		}

	}
	

}
