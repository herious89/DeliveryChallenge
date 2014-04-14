package com.androidexample.delivery;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.androidexample.delivery.FirstScreen.MerchantData;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * This class displays the list of restaurants
 * based on the search address input
 * @author brian & lam
 *
 */
public class DisplayMerchantsActivity extends BaseActivity {
	
	// arraylist of merchants
	private ArrayList<Merchant> merchantArray = new ArrayList<Merchant>();
	private String merchantID;
	/**
	 * The onCreate method displays the list of available 
	 * merchants and calls the SingleMerchantActivity whenever
	 * a specific merchant is chosen
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_merchants);
	    // Get the search result
		merchantArray = MerchantData.getMerchantList();
		
		MerchantCustomAdapter adapter = new MerchantCustomAdapter(this, R.layout.list_item,
				 merchantArray);
		ListView mList = (ListView) findViewById(R.id.listView);
		mList.setItemsCanFocus(false);
		mList.setAdapter(adapter);	
	    
		// Listview on item click listener
		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				// Starting single contact activity
//				Intent in = new Intent(getApplicationContext(),	SingleMerchantActivity.class);
//		String field1 = ((TextView) view.findViewById(R.id.field1))
//				.getText().toString();
//		String field2 = ((TextView) view.findViewById(R.id.field2))
//				.getText().toString();
//		String field3 = ((TextView) view.findViewById(R.id.field3))
//				.getText().toString();
//		String field4 = ((TextView) view.findViewById(R.id.field4))
//				.getText().toString();
//		String field5 = ((TextView) view.findViewById(R.id.field5))
//				.getText().toString();
//				in.putExtra("name", field1);
//				in.putExtra("id", field2);
//				in.putExtra("address", field3);
//				in.putExtra("phone", field4);
//				in.putExtra("distance", field5);			
//				startActivity(in);
				merchantID = merchantArray.get(position).getID() + "";
				new GetMenu().execute();
			}
		});
	}	
	
	public static class MenuData {
		private static JSONObject result;
		private static ArrayList<Menu> menu;
		
		public static void setResult(String s) {
			try {
				result = new JSONObject(s);
			} catch (JSONException e) {e.printStackTrace();}
		}
		public static JSONObject getResult() {return result;}

		public static void setMenuList(ArrayList<Menu> m) {menu = m;}
		public static ArrayList<Menu> getMenuList() {return menu;}
	}
	
	private class GetMenu extends AsyncTask<Void, Void, Void> {
	    private ProgressDialog pDialog;
		/**
		 * The onPreExecute method display the waiting message
		 * while the program is executing the search function
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(DisplayMerchantsActivity.this);
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
			MenuData.setResult(SearchMerchants.search(merchantID, 1));
			// MenuData.setMenuList(SearchMerchants.createMenuList(MerchantData.getResult()));
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
	
	public void viewResult() {
		JSONObject searchResult;
		try {
			searchResult = MenuData.getResult();
			if (searchResult.has("menu")) {
				Intent i = new Intent(this, DisplayMenuActivity.class); 
				startActivity(i, true);								
			}
			else { // Display error message
				String msg = searchResult.getJSONObject("message").getJSONObject("user_msg").toString();
				AlertDialog.Builder adb = new AlertDialog.Builder(DisplayMerchantsActivity.this);
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
	
}