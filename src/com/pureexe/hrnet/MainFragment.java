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
	private Thread refreshUIThread;

	public MainFragment() {

	}

	public MainFragment(ActionBarActivity a) {
		activity = a;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		dm = new DataManager(getActivity().getApplicationContext());
		
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
	
}