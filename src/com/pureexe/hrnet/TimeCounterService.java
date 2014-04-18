package com.pureexe.hrnet;

import java.util.Calendar;






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
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

public class TimeCounterService extends Service {

	public DataManager dm;
	
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
                	noti.push_timecounter(dm.getInt("UsageTime"));
                	}
                	else {
                		NotifyManager.cancle(getApplicationContext(), NotifyManager.TIME_COUNTER);
                	}
			}
		};
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
