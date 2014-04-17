package com.pureexe.hrnet;

import com.pureexe.hrnet.R.color;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class MainFragment extends Fragment {

	public ActionBarActivity activity;
	private DataManager dm;

	public MainFragment() {

	}

	public MainFragment(ActionBarActivity a) {
		activity = a;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		dm = new DataManager(activity.getApplicationContext());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		
		final TextView time = (TextView) rootView.findViewById(R.id.textView1);
		final TextView txtwarn = (TextView) rootView.findViewById(R.id.textView3);
		Thread refreshUIThread = new Thread() {
			@SuppressLint("ResourceAsColor")
			public void run() {
				try {
					while (true) {
						activity.runOnUiThread(new Runnable() {
							public void run() {
								int dataplan = dm.getInt("DataPlan");
								int datause = dm.getInt("UsageTime");
								if (dataplan - datause < 0) {
									time.setTextColor(activity.getResources().getColor(R.color.red));
									return;
								}
								txtwarn.setVisibility(View.GONE);
								//Toast.makeText(activity, "OK RUNING", Toast.LENGTH_LONG).show();
								dataplan = dataplan - datause;
								String txt = "";
								if(dataplan / 60<10){
									txt ="0"+(dataplan / 60)+":";
								} else {
									txt = "" + (dataplan / 60) + ":";
								}
								if(dataplan%60<10) {
									txt+="0"+(dataplan % 60);
								} else {
									txt+=(dataplan % 60);
								}
								if((dataplan / 60)==0&&(dataplan % 60)<=5){
									time.setTextColor(activity.getResources().getColor(R.color.red));
								} else if((dataplan / 60)==0&&(dataplan % 60)<=20){
									time.setTextColor(activity.getResources().getColor(R.color.orange));
								} 
								else {
									time.setTextColor(Color.BLACK);
								}
								time.setText(txt);
							}
						});
						sleep(15000);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		refreshUIThread.start();
		return rootView;
	}

}