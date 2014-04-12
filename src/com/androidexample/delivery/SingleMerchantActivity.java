package com.androidexample.delivery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * This class displays basic information of a specified merchant
 * @author brian & lam
 *
 */
public class SingleMerchantActivity  extends Activity {
	
	// JSON node keys
	private static final String TAG_ID = "id";
	private static final String TAG_ORDER = "ordering";
	private static final String TAG_SUMMARY = "summary";
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_merchant);
        
        // getting intent data
        Intent in = getIntent();
        
        // Get JSON values from previous intent
        String id = in.getStringExtra(TAG_ID);
        String order = in.getStringExtra(TAG_ORDER);
        String street = in.getStringExtra("street");
        String distance = in.getStringExtra("distance");
        String name = in.getStringExtra("name");
        
        // Displaying all values on the screen
        TextView lbl1 = (TextView) findViewById(R.id.a_label);
        TextView lbl2 = (TextView) findViewById(R.id.b_label);
        TextView lbl3 = (TextView) findViewById(R.id.c_label);
        TextView lbl4 = (TextView) findViewById(R.id.d_label);
        TextView lbl5 = (TextView) findViewById(R.id.e_label);
        
        lbl1.setText("ID: " + id);
        lbl2.setText("Delivery_processes_card: " + order);
        lbl3.setText("Street: " + street);
        lbl4.setText("Distance: " +distance);
        lbl5.setText(name);
    }
}
