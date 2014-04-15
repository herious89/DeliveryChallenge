package com.androidexample.delivery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_merchant);
        
        Intent in = getIntent();
        
        // Displaying all values on the screen
        TextView lbl1 = (TextView) findViewById(R.id.a_label);
        TextView lbl2 = (TextView) findViewById(R.id.b_label);
        TextView lbl3 = (TextView) findViewById(R.id.c_label);
        TextView lbl4 = (TextView) findViewById(R.id.d_label);
        TextView lbl5 = (TextView) findViewById(R.id.e_label);
        TextView lbl6 = (TextView) findViewById(R.id.f_label);
        
        try {
        	JSONObject mer = new JSONObject(in.getStringExtra("merchantInfo"));        
        	lbl1.setText(mer.getJSONObject("summary").getString("name"));
        	lbl2.setText("Address: " + mer.getJSONObject("location").getString("street")
        			+ ", " + mer.getJSONObject("location").getString("city") + ", "
        			+ mer.getJSONObject("location").getString("state")
        			+ mer.getJSONObject("location").getString("zip"));
        	lbl3.setText("Phone: " + mer.getJSONObject("summary").getString("phone"));
        	lbl4.setText("Description: " + mer.getJSONObject("summary")
        			.getString("description") + "\n");
        	try {
        		JSONArray special = mer.getJSONObject("ordering").getJSONArray("specials");
        		String s = "";
        		for (int i = 0; i < special.length(); i++)
        			s = s + special.getString(i) + "\n";
        		lbl5.setText("Special offer: " + s);
        	} catch (JSONException e) {
        		lbl5.setText("Specials: No special offers available");
        	}
  	
        	lbl6.setText("Website: " + mer.getJSONObject("summary").getJSONObject("url")
        			.getString("complete"));
        } 
        catch (JSONException e) {
        	e.printStackTrace();
        }
    }
}
