package com.androidexample.delivery;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.androidexample.delivery.FirstScreen.MerchantData;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MerchantCustomAdapter extends ArrayAdapter<Merchant> {
	Context context;
	int layoutResourceId;
	ArrayList<Merchant> data = new ArrayList<Merchant>();
	
	/**
	 * Constructor of the class with 3 arguments
	 */
	public MerchantCustomAdapter(Context cont, int layout,
			ArrayList<Merchant> d) {
		super(cont, layout, d);
		context = cont;
		layoutResourceId = layout;
		data = d;
	}
	
	/**
	 * The getView function provides the layout for the list
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		MerchantHolder holder = null;
		final int pos = position;
		
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new MerchantHolder();
			holder.textName = (TextView) row.findViewById(R.id.field1);
			holder.textCuisine = (TextView) row.findViewById(R.id.field2);
			holder.textStreet = (TextView) row.findViewById(R.id.field3);
			holder.textDistance = (TextView) row.findViewById(R.id.field4);
			
			holder.btnInfo = (Button) row.findViewById(R.id.button1);
			holder.btnRate = (Button) row.findViewById(R.id.button2);
			row.setTag(holder);
		} else {
			holder = (MerchantHolder) row.getTag();
		}
		Merchant m = data.get(position);
		holder.textName.setText(m.getName());
		holder.textCuisine.setText(m.getCuisine());
		holder.textStreet.setText(m.getStreet());
		holder.textDistance.setText(m.getDistance());
		
		holder.btnInfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int id = data.get(pos).getID();
				new GetInfo().execute(id);
			}
		});

		holder.btnRate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("Rate Button Clicked", "**********");
				Toast.makeText(context, "Rate button Clicked",
						Toast.LENGTH_LONG).show();
			}
		});
		return row;
	}
	
	static class MerchantHolder {
		TextView textName;
		TextView textCuisine;
		TextView textStreet;
		TextView textDistance;
		Button btnInfo;
		Button btnRate;
	}
	
	private class GetInfo extends AsyncTask<Integer, Void, Void> {
		
	    // Load dialog
		
	    private ProgressDialog pDialog;
		Intent in = new Intent(context, SingleMerchantActivity.class);
		
		/**
		 * The onPreExecute method display the waiting message
		 * while the program is executing the search function
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(context);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		/**
		 * The doInBackGround method creates the searchRestaurant
		 * object and gets the list of available merchants 
		 */
		@Override
		protected Void doInBackground(Integer... arg) {		
			// search merchant information
			
			try {
				JSONArray merchantArray = MerchantData.getResult().getJSONArray("merchants");
				String merchantInfo = "";
				boolean found = false;
				if (!(merchantArray.length() == 0))
				{
					for (int i = 0; i < merchantArray.length(); i++)
		        	{
						int id = merchantArray.getJSONObject(i).getInt("id");
						if (id == arg[0]) {
							merchantInfo = merchantArray.getJSONObject(i).toString();
							found = true;
						}
		        	}
					if (found == false) {
						AlertDialog.Builder alert = new AlertDialog.Builder(context);
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
			context.startActivity(in);			
		}

	}
	
}