package com.androidexample.delivery;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * This class creates the splash green (welcome screen) of the app
 * @author brian & lam
 *
 */

public class MainSplashScreen extends Activity {
	
	// Store the welcome text
	TextView welcomeText;

	/**
	 * The onCreate method displays the main splash screen
	 * and the welcome text by creating a thread which
	 * will sleep after 5 seconds
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_splash_screen);
		
		// set welcome text
		welcomeText = (TextView)findViewById(R.id.welcome_text);
		welcomeText.setText("Cal Poly Delivery App"); // set text
		
         /****** Create Thread that will sleep after 5 seconds *************/  		
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
	}
	
	@Override
    protected void onDestroy() {		
        super.onDestroy();      
    }
}
