package com.androidexample.delivery;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainSplashScreen extends Activity {
	
	TextView welcomeText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_splash_screen);
		
		// set welcome text
		welcomeText = (TextView)findViewById(R.id.welcome_text);
		welcomeText.setText("Cal Poly Delivery App"); // set text
		
// METHOD 1		
		
         /****** Create Thread that will sleep for 5 seconds *************/  		
		Thread background = new Thread() {
			public void run() {
				
				try {
					// Time showing splash
					sleep(2*1000);
					
					// After x seconds redirect to another intent
				    Intent i=new Intent(getBaseContext(),FirstScreen.class);
					startActivity(i);
					
					//Remove activity
					finish();
					
				} catch (Exception e) {
				
				}
			}
		};
		
		// start thread
		background.start();
		
//METHOD 2	
		
		/*
		new Handler().postDelayed(new Runnable() {
			 
            // Using handler with postDelayed called runnable run method
 
            @Override
            public void run() {
                Intent i = new Intent(MainSplashScreen.this, FirstScreen.class);
                startActivity(i);
 
                // close this activity
                finish();
            }
        }, 5*1000); // wait for 5 seconds
		*/
	}
	
	@Override
    protected void onDestroy() {
		
        super.onDestroy();
        
    }
}
