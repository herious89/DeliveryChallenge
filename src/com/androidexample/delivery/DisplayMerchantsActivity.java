package com.androidexample.delivery;

import java.util.ArrayList;
import com.androidexample.delivery.FirstScreen.Data;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * This class displays the list of restaurants
 * based on the search address input
 * @author brian & lam
 *
 */
public class DisplayMerchantsActivity extends BaseActivity {
	
	// arraylist of merchants
	ArrayList<Merchant> merchantArray = new ArrayList<Merchant>();

	/**
	 * The onCreate method displays the list of available 
	 * merchants and calls the SingleMerchantActivity whenever
	 * a specific merchant is chosen
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_merchants);
	    // Get the search result
		merchantArray = Data.getMerchantList();
		
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
			Intent in = new Intent(getApplicationContext(),	SingleMerchantActivity.class);
			String field1 = ((TextView) view.findViewById(R.id.field1))
					.getText().toString();
			String field2 = ((TextView) view.findViewById(R.id.field2))
					.getText().toString();
			String field3 = ((TextView) view.findViewById(R.id.field3))
					.getText().toString();
			String field4 = ((TextView) view.findViewById(R.id.field4))
					.getText().toString();
			String field5 = ((TextView) view.findViewById(R.id.field5))
					.getText().toString();
			in.putExtra("name", field1);
			in.putExtra("id", field2);
			in.putExtra("address", field3);
			in.putExtra("phone", field4);
			in.putExtra("distance", field5);			
			startActivity(in);
			}
		});
	}	
	
}