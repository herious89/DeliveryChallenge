package com.androidexample.delivery;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.androidexample.delivery.DeliveryMainActivity.MerchantData;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * This class displays the list of restaurants
 * based on the search address input
 * @author brian & lam
 *
 */
public class DisplayMerchantsActivity extends BaseActivity {
	
	Button back;
	
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
		
		// top bar
		ActionBar actionbar = getActionBar();
	    actionbar.setCustomView(R.layout.actionbar_top_display_merchants_activity);
	    actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
	    
	    // buttons in this activity
	    back = (Button) findViewById(R.id.btnBack);
	    
	    // handle events for buttons
	    back.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		
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
				int merchantID = merchantArray.get(position).getID();
				Intent in = new Intent(getApplicationContext(), SingleMerchantActivity.class);
				try {
					JSONArray merchantArray = MerchantData.getResult().getJSONArray("merchants");
					String merchantInfo = "";
					boolean found = false;
					if (!(merchantArray.length() == 0))
					{
						for (int i = 0; i < merchantArray.length(); i++)
				        {
							int temp = merchantArray.getJSONObject(i).getInt("id");
							if (merchantID == temp) {
								merchantInfo = merchantArray.getJSONObject(i).toString();
								found = true;
							}
				        }
						if (found == false) {
							AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());
							alert.setTitle("Error");
							alert.setMessage("Could not find more information about that merchat!");
							alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
									dialog.cancel();
								}      
							});
							alert.show();
						}
						else {
							in.putExtra("merchantInfo", merchantInfo);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				startActivity(in);
			}
		});
	}		
}