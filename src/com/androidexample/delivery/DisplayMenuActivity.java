package com.androidexample.delivery;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.androidexample.delivery.MerchantCustomAdapter.MenuData;;

public class DisplayMenuActivity extends BaseActivity {

	
	// arraylist of merchants
	private ArrayList<Menu> menuArray = new ArrayList<Menu>();
	
	private JSONObject menu = MenuData.getResult();
	@Override
	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_menu);

		 // Create the text view
	    TextView textView = (TextView) findViewById(R.id.textView1);
	    textView.setTextSize(12);
	    textView.setText(menu.toString());

	    // Set the text view as the activity layout
	    // setContentView(textView);
	}

}
