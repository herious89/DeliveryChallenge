package com.androidexample.delivery;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
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

	public MerchantCustomAdapter(Context cont, int layout,
			ArrayList<Merchant> d) {
		super(cont, layout, d);
		context = cont;
		layoutResourceId = layout;
		data = d;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		MerchantHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new MerchantHolder();
			holder.textName = (TextView) row.findViewById(R.id.field1);
			holder.textID = (TextView) row.findViewById(R.id.field2);
			holder.textAddress = (TextView) row.findViewById(R.id.field3);
			holder.textPhone = (TextView) row.findViewById(R.id.field4);
			holder.textDistance = (TextView) row.findViewById(R.id.field5);
			
			holder.btnInfo = (Button) row.findViewById(R.id.button1);
			holder.btnRate = (Button) row.findViewById(R.id.button2);
			row.setTag(holder);
		} else {
			holder = (MerchantHolder) row.getTag();
		}
		Merchant m = data.get(position);
		holder.textName.setText(m.getName());
		holder.textID.setText(Integer.toString(m.getID()));
		holder.textAddress.setText(m.getAddress());
		holder.textPhone.setText(m.getPhone());
		holder.textDistance.setText(m.getDistance());
		
		holder.btnInfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("Info Button Clicked", "**********");
				Toast.makeText(context, "menu button Clicked",
						Toast.LENGTH_LONG).show();
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
		TextView textID;
		TextView textAddress;
		TextView textPhone;
		TextView textDistance;
		Button btnInfo;
		Button btnRate;
	}
}