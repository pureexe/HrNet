package com.pureexe.hrnet;

import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	public DataManager dm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);
		  
		    Tab tab = actionBar.newTab()
		                       .setText("Main")
		                       .setTabListener(new TabListener<MainFragment>(
		                               this, "main", MainFragment.class));
		    actionBar.addTab(tab);

		    tab = actionBar.newTab()
		                   .setText("Usage")
		                   .setTabListener(new TabListener<UsageHistoryFragment>(
		                           this, "usage", UsageHistoryFragment.class));
		    actionBar.addTab(tab);
		    

		
		/*
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new MainFragment()).commit();
		}*/
		Context context = this;
		if(!ServiceUtil.checkServiceRunning(context, TimeCounterService.class)){
			Intent active = new Intent(context, TimeCounterService.class);
			context.startService(active);
		}
		
		
	}
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {

			Intent intent = new Intent(this,SettingUI.class);
			startActivity(intent);
			return true;
			
		}
		else if(id == R.id.action_set_dataplan){
			setDataPlan(null);
			return true;
		} else if(id == R.id.action_clear_data_usage){
			resetCounterConfirm();
			
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void setDataPlan(View v){
		AlertDialog.Builder	builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.set_data_plan_title));
		builder.setMessage(getString(R.string.set_data_plan_box_des));
		//builder.setCancelable(false);
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_TEXT);
		builder.setView(input);
		builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
		
		public void onClick(DialogInterface dialog, int whichButton) {
				try{
					String inp = input.getText().toString();
					if(inp.equals("")){
						return;
					}
					String[] inpBox= inp.split(":");
					int minute =Integer.parseInt(inpBox[0]);
					if(inpBox.length==1){
						dm.setInt("DataPlan", minute);
						Toast.makeText(getApplicationContext(), getString(R.string.applying_pleasewait), Toast.LENGTH_LONG).show();
					}
					else if(inpBox.length==2){
						minute*=60;
						minute+=Integer.parseInt(inpBox[1]);
						dm.setInt("DataPlan", minute);
						Toast.makeText(getApplicationContext(), getString(R.string.applying_pleasewait), Toast.LENGTH_LONG).show();
					}
					else {
						Toast.makeText(getApplicationContext(), getString(R.string.fail_invalid_input), Toast.LENGTH_LONG).show();
					}
					
				}
				catch(Exception e)
				{
					Toast.makeText(getApplicationContext(), getString(R.string.fail_invalid_input), Toast.LENGTH_LONG).show();
				}
				
			
			}
		});
		builder.show();
	}
	public void resetCounterConfirm(){
		AlertDialog.Builder	builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.action_clear_data_usage))
			.setMessage(getString(R.string.action_clear_data_usage_des))
			.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					Toast.makeText(getApplicationContext(), getString(R.string.applying_pleasewait), Toast.LENGTH_LONG).show();
					dm.setInt("UsageTime", 0);
					NotifyManager.cancleAll(getApplicationContext());
				}
				
			})
			.setNegativeButton(getString(R.string.no),new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface arg0, int arg1) {
				}
			});
		builder.show();
	}




	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		dm = new DataManager(getApplicationContext());

	}
}
