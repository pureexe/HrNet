package com.pureexe.hrnet;

import com.pureexe.hrnet.R.color;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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


	private DataManager dm;
	private Thread refreshUIThread;
	private TextView time;
	private TextView txtwarn;
	Intent intent;
	public MainFragment() {
		
	}
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	refreshUI();      
        }
    };    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		 time = (TextView) rootView.findViewById(R.id.textView1);
		 txtwarn = (TextView) rootView.findViewById(R.id.textView3);
		 float fontface = PortableUtil.getDensity(getActivity());
/*		 if(fontface <=0.75){
			 time.setTextSize(90);
		 }*/
		 time.setTextSize(50*PortableUtil.getDensity(getActivity()));
		 //time.setTextSize(100);
		  intent = new Intent(getActivity(), RefreshUIService.class);
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		dm = new DataManager(getActivity().getApplicationContext());
		getActivity().startService(intent);
		getActivity().registerReceiver(broadcastReceiver, new IntentFilter(RefreshUIService.BROADCAST_ACTION));
		refreshUI();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		getActivity().unregisterReceiver(broadcastReceiver);
		getActivity().stopService(intent);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	public void refreshUI() {
		int dataplan = dm.getInt("DataPlan");
		int datause = dm.getInt("UsageTime");
		
		if(dataplan==0){
			txtwarn.setVisibility(View.VISIBLE);
		}
		else{
			txtwarn.setVisibility(View.GONE);
		}
		if (dataplan - datause < 0) {
			time.setText("00:00");
			time.setTextColor(getActivity().getResources()
					.getColor(R.color.red));
			
			return;
		}
		
		dataplan = dataplan - datause;
		String txt = "";
		if (dataplan / 60 < 10) {
			txt = "0" + (dataplan / 60) + ":";
		} else {
			txt = "" + (dataplan / 60) + ":";
		}
		if (dataplan % 60 < 10) {
			txt += "0" + (dataplan % 60);
		} else {
			txt += (dataplan % 60);
		}
		if ((dataplan / 60) == 0 && (dataplan % 60) <= 5) {
			time.setTextColor(getActivity().getResources()
					.getColor(R.color.red));
		} else if ((dataplan / 60) == 0 && (dataplan % 60) <= 20) {
			time.setTextColor(getActivity().getResources().getColor(
					R.color.orange));
		} else {
			time.setTextColor(Color.BLACK);
		}
		time.setText(txt);
	}

	
}