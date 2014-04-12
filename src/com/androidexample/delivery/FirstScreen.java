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

/**
 * This class display the search screen where the app
 * receives users' input and login information
 * @author brian & lam
 *
 */
public class FirstScreen extends BaseActivity {
	
	// Hold the user's input 
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    
    // Load dialog
    private ProgressDialog pDialog;

	// Hashmap for ListView
	ArrayList<HashMap<String, String>> merchantsList;
    
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
		
		// Initialize the merchantList 
		merchantsList = new ArrayList<HashMap<String, String>>();
		
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
	 * The viewResult methods call the DisplayRestaurant activity 
	 * to display the list of merchants in the proper format 
	 * @param list The ArrayList received from Async task
	 */
	public void viewResult(ArrayList<HashMap<String, String>> list) {
		if (list.isEmpty() || list.equals("null")) {
			return;
		}
		Intent i = new Intent(this, DisplayMerchantsActivity.class);
		i.putExtra("hashmap", list);
		startActivity(i, true);
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
			
			SearchMerchants searchRes = new SearchMerchants("input here");
			if (searchRes.getList() != null)
				merchantsList = searchRes.getList();
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
			viewResult(merchantsList);
		}

	}
	

}
