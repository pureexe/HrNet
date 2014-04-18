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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.*;
import com.jjoe64.graphview.GraphView.GraphViewData;

public class UsageHistoryFragment extends Fragment {


	public ActionBarActivity activity;
	private DataManager dm;

	public UsageHistoryFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_usagehistory, container,
				false);
		GraphView graphView = new LineGraphView(getActivity().getApplicationContext() , "");
		
		GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
			    new GraphViewData(1, 2.0d)
			    , new GraphViewData(2, 9.0d)
			    , new GraphViewData(3, 4.0d)
			    , new GraphViewData(4, 3.0d)
			});
			 
			
			graphView.addSeries(exampleSeries); // data
			graphView.getGraphViewStyle().setVerticalLabelsColor(Color.BLACK);
			graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.BLACK);
			LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.box);
			layout.addView(graphView);
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