package com.androidexample.delivery;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class FirstScreen extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.firstscreen); 
		
	} 
	
	public void getAddress(View view) {
	    Intent intent = new Intent(this, DisplayRestaurant.class);
	    EditText editText = (EditText) findViewById(R.id.search_box);
	    String message = editText.getText().toString();
	    intent.putExtra(EXTRA_MESSAGE, message);
	    startActivity(intent);
	}
	
	@Override
    protected void onDestroy() {
		
        super.onDestroy();
        
    }
}
