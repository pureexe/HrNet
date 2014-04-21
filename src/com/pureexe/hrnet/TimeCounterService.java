package com.pureexe.hrnet;

import java.util.Calendar;







import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

public class TimeCounterService extends Service {

	public DataManager dm;
	public PowerManager pm;
	private Context context;
	private Runnable mUpdateResults;
	private NotifyManager noti;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		context = getApplicationContext();
		dm = new DataManager(context);
		noti = new NotifyManager(context);
		 pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		final Handler mHandler = new Handler();
		 mUpdateResults = new Runnable(){
			public void run(){
				if(NetworkUtil.getConnectivityStatus(context)==NetworkUtil.TYPE_MOBILE){
                	dm.setInt("UsageTime", dm.getInt("UsageTime")+1);
                	String date = DateUtil.getThisDate();
                	dm.setInt("Date_"+date, dm.getInt("Date_"+date)+1);
                	if(dm.getSettingString(SettingUI.KEY_NOTI_ALWAYS_TYPE).equals("1")){
                		noti.push_timecounter(dm.getInt("UsageTime"));
                	} else if(dm.getSettingString(SettingUI.KEY_NOTI_ALWAYS_TYPE).equals("2")){
                		noti.push_timecountdown(dm.getInt("DataPlan")-dm.getInt("UsageTime"));
                	}
                	else {
                		NotifyManager.cancle(getApplicationContext(), NotifyManager.TIME_COUNTER);
                	}
        			if(dm.getInt("DataPlan")-dm.getInt("UsageTime")<0){
        				if(dm.getSettingBoolean(SettingUI.KEY_DISCONNECT_OUTOFPACK)){
        					PhoneUtil.setMobileDataEnabled(getApplicationContext(), false);
        					noti.push_netcut_alert();
        				}
        			} else if(dm.getInt("DataPlan")-dm.getInt("UsageTime")<=5){
        				if(dm.getSettingBoolean(SettingUI.KEY_NOTI_TIMEOUT_ALERT)){
        					noti.push_lownet_alert();
        				}
        			}
        			if(!pm.isScreenOn()){
        				if(dm.getSettingBoolean(SettingUI.KEY_DISCONNECT_DONTUSE)){
        					if(dm.getBool("ScreenCheck")){
        						if(new Date().getTime()>=dm.getLong("ScreenCheckTime")+600000){
        							PhoneUtil.setMobileDataEnabled(getApplicationContext(), false);
        						}
        					}
        					
        				}
        			}
			}

		}};
		new Thread(){
			public void run(){
				try {
					while(true){
					mHandler.post(mUpdateResults);
					sleep(60000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
}
