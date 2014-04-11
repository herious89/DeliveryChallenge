package com.androidexample.delivery;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class DisplayRestaurant extends ListActivity {

    
	// JSON Node names
	private static final String TAG_CONTACTS = "merchants";
	private static final String TAG_STREET = "street";
	private static final String TAG_ZIP = "zip_code";
	private static final String TAG_STATE = "state";
	
	
	// temp
	private static final String TAG_ID = "id";
	private static final String TAG_ORDER = "ordering";
	private static final String TAG_SUMMARY = "summary";

	@Override
	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_restaurant);
	    // Get the message from the intent
	    Intent intent = getIntent();

	    // pass hashmap to this intent
		final ArrayList<HashMap<String, String>> merchantsList =
			    (ArrayList<HashMap<String,String>>)intent.getSerializableExtra("hashmap");

	    
	    // handle touch events (to-do)
	    ListView lv = getListView();
	    
		// Listview on item click listener
		lv.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			// getting values from selected ListItem
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

			// Starting single contact activity
			Intent in = new Intent(getApplicationContext(),	SingleMerchantActivity.class);
			in.putExtra("name", field5);
			in.putExtra(TAG_ID, field1);
			in.putExtra(TAG_ORDER, field2);
			in.putExtra("street", field3);
			in.putExtra("distance", field4);			
			startActivity(in);

		}
	});
	    
		/**
		 * Updating parsed JSON data into ListView
		 * */
		ListAdapter adapter = new SimpleAdapter(
				DisplayRestaurant.this, merchantsList,
//				R.layout.list_item, new String[] { TAG_STREET, TAG_ZIP,
//						TAG_STATE }, new int[] { R.id.name,
//						R.id.email, R.id.mobile });
				
				R.layout.list_item, new String[] { TAG_ID, TAG_ORDER,
						"street", "distance" , "name"}, new int[] { R.id.field1,
				R.id.field2, R.id.field3, R.id.field4, R.id.field5 });

		setListAdapter(adapter);

	}
	
	
}