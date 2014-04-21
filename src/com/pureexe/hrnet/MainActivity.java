package com.pureexe.hrnet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.pureexe.hrnet.Dialog.DialogUtil;

import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
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
		actionBar.setDisplayShowTitleEnabled(true);

		  
		    Tab tab = actionBar.newTab()
		                       .setText(getString(R.string.notialwaystype))
		                       .setTabListener(new TabListener<MainFragment>(
		                               this,getString(R.string.timecounter), MainFragment.class));
		    actionBar.addTab(tab);

		    tab = actionBar.newTab()
		                   .setText(getString(R.string.usage))
		                   .setTabListener(new TabListener<UsageHistoryFragment>(
		                           this,getString(R.string.usage), UsageHistoryFragment.class));
		    actionBar.addTab(tab);

		/*
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new MainFragment()).commit();
		}*/
		if(!ServiceUtil.checkServiceRunning(this, TimeCounterService.class)){
			Intent active = new Intent(this, TimeCounterService.class);
			startService(active);
		}
		dm = new DataManager(getApplicationContext());
		if (NetworkUtil.getConnectivityStatus(this) != NetworkUtil.TYPE_NOT_CONNECTED) {
				if((new Date().getTime()>=dm.getLong("LastCheckUpdate")+86400000)){
					new UpdateCheckerTask(this).execute();
			}
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
		 else if(id == R.id.action_clear_data_usage){
			resetCounterConfirm();
			
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void setDataPlan(View v){
		DialogUtil.setDataPlan(this);
	}
	public void resetCounterConfirm(){
		DialogUtil.reset_counter(this);
	}




	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}
	
}
