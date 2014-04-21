package com.pureexe.hrnet;

import java.util.ArrayList;

import com.pureexe.hrnet.R.color;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
		dm = new DataManager(getActivity().getApplicationContext());
		Spinner spinerDate = (Spinner)rootView.findViewById(R.id.spinner1);
		ArrayList<String> options=new ArrayList<String>();
		options.add(getActivity().getString(R.string.last7day));
		options.add(getActivity().getString(R.string.last30day));
//		options.add(DateUtil.getMonthLableFull(getActivity(), DateUtil.getThisDate()));
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,options);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinerDate.setAdapter(adapter);
			final LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.box);
		    spinerDate.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
					if(position == 0){
						layout.removeAllViews();
						layout.addView(init7DayGraph());
					} else if(position == 1) {
						layout.removeAllViews();
						layout.addView(init30DayGraph());
					}
					 /*else if(position == 2) {
							layout.removeAllViews();
							layout.addView(initThisMonthGraph());
					}*/
				}

				@Override
				public void onNothingSelected(AdapterView<?> parentView) {
					
				}

		    });
		    		
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
	
	private GraphView init7DayGraph(){
	    GraphView graphView = new BarGraphView(getActivity().getApplicationContext() , getActivity().getString(R.string.graph_header));
		GraphViewSeries Series = new GraphViewSeries(new GraphViewData[] {
			    new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 6))),dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 6)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 5))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 5)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 4))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 4)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 3))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 3)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 2))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 2)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 1))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 1)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 0))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 0)))
			});
			String[] HorizonLable= new String[7];
			int i,maxValue = 0,u;
			for(i=0;i<7;i++){
			HorizonLable[i] = DateUtil.getDayLableInWeek(getActivity(), DateUtil.getPastDate(DateUtil.getThisDate(),7-i-1));
			}
			graphView.addSeries(Series);
			graphView.getGraphViewStyle().setVerticalLabelsColor(Color.BLACK);
			graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.BLACK);
			graphView.setHorizontalLabels(HorizonLable);
			graphView.getGraphViewStyle().setTextSize(graphView.getGraphViewStyle().getTextSize()*2/3);
			for(i=0;i<7;i++){
				u=dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), i));
				if(u>maxValue)
					maxValue=u;
			}
			int interval;
					  if (maxValue <= 55) {
					      interval = 5; 
					  } else if (maxValue <= 110) {
					      interval = 10; 
					  } else {
					      interval = 20;
					  }
					  int maxLabel = maxValue;
					  while (maxLabel % interval != 0) {
					      maxLabel++;
					  }
					  graphView.setManualYAxisBounds(maxLabel, 0);
					  graphView.getGraphViewStyle().setNumVerticalLabels(maxLabel / interval + 1);
			return graphView;
	}
	private GraphView init30DayGraph(){
	    GraphView graphView = new BarGraphView(getActivity().getApplicationContext() , getActivity().getString(R.string.graph_header));
		GraphViewSeries Series = new GraphViewSeries(new GraphViewData[] {
			    new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 29))),dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 29)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 28))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 28)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 27))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 27)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 26))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 26)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 25))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 25)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 24))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 24)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 23))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 23)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 22))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 22)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 21))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 21)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 20))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 20)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 19))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 19)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 18))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 18)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 17))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 17)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 16))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 16)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 15))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 15)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 14))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 14)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 13))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 13)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 12))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 12)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 11))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 11)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 10))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 10)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 9))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 9)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 8))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 8)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 7))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 7)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 6))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 6)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 5))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 5)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 4))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 4)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 3))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 3)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 2))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 2)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 1))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 1)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), 0))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), 0)))
			});
			String[] HorizonLable= new String[30];
			int i;
			for(i=0;i<30;i++){
				if(i%3==2)
					HorizonLable[i] = DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(),30-i-1));
				else
					HorizonLable[i]="";
			}
					int u,maxValue=0;
					for(i=0;i<30;i++){
						u=dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), i));
						if(u>maxValue)
							maxValue=u;
					}
					  int interval;
					  if (maxValue <= 55) {
					      interval = 5; 
					  } else if (maxValue <= 110) {
					      interval = 10; 
					  } else {
					      interval = 20;
					  }
					  int maxLabel = maxValue;
					  while (maxLabel % interval != 0) {
					      maxLabel++;
					  }
					  graphView.setManualYAxisBounds(maxLabel, 0);
					  graphView.getGraphViewStyle().setNumVerticalLabels(maxLabel / interval + 1);
					  
			graphView.addSeries(Series);
			graphView.getGraphViewStyle().setVerticalLabelsColor(Color.BLACK);
			graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.BLACK);
			graphView.setHorizontalLabels(HorizonLable);
			graphView.getGraphViewStyle().setTextSize(graphView.getGraphViewStyle().getTextSize()*2/3);
			return graphView;
	}
	private GraphView initThisMonthGraph(){
	    GraphView graphView = new BarGraphView(getActivity().getApplicationContext() , getActivity().getString(R.string.graph_header));
	    int toFirstDate = DateUtil.pastToFisrtDateofMonth();
		GraphViewSeries Series = new GraphViewSeries(new GraphViewData[] {
			    new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(),toFirstDate))),dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate +1))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate -1)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate +2))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate -2)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate +3))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate -3)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate +4))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate -4)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate +5))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate -5)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate +6))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate -6)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate +7))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate -7)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate +8))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate -8)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate +9))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate -9)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate +10))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate -10)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate +11))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate -11)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate +12))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate -12)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate +13))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate -13)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate +14))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate -14)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate +15))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate -15)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate +16))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate -16)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate +17))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate -17)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate +18))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate -18)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate +19))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate -19)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate +20))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate -20)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate +21))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate -21)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate +22))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate -22)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate +23))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate -23)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate +24))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate -24)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate +25))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate -25)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate +26))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate -26)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate +27))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate -27)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate +28))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate -28)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate +29))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate -29)))
			    , new GraphViewData(Integer.parseInt(DateUtil.getDateOnly(DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate +30))), dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate -30)))
		});

		
		String[] HorizonLable= new String[DateUtil.getMonthAvaliable(getActivity(), DateUtil.getThisDate())];
			int i;
			for(i=0;i<DateUtil.getMonthAvaliable(getActivity(), DateUtil.getThisDate());i++){
				if(i%3==0)
					HorizonLable[i] = ""+(i+1);
				else
					HorizonLable[i]="";
			}
					int u,maxValue=0;
					for(i=0;i<DateUtil.getMonthAvaliable(getActivity(), DateUtil.getThisDate());i++){
						u=dm.getInt("Date_"+DateUtil.getPastDate(DateUtil.getThisDate(), toFirstDate-i));
						if(u>maxValue)
							maxValue=u;
					}
					  int interval;
					  if (maxValue <= 55) {
					      interval = 5; 
					  } else if (maxValue <= 110) {
					      interval = 10; 
					  } else {
					      interval = 20;
					  }
					  int maxLabel = maxValue;
					  while (maxLabel % interval != 0) {
					      maxLabel++;
					  }
					  graphView.setManualYAxisBounds(maxLabel, 0);
					  graphView.getGraphViewStyle().setNumVerticalLabels(maxLabel / interval + 1);
					  
			graphView.addSeries(Series);
			graphView.getGraphViewStyle().setVerticalLabelsColor(Color.BLACK);
			graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.BLACK);
			graphView.setHorizontalLabels(HorizonLable);
			graphView.getGraphViewStyle().setTextSize(20);

			return graphView;
	}
	
}