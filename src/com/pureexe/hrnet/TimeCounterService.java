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
	private NotificationCompat.Builder mBuilder;
	private Notification noti;
	private int mNotificationId;
	private NotificationManager mNotifyMgr;
	private Context context;
	private Runnable mUpdateResults;
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
		Intent resultIntent = new Intent(context,MainActivity.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(MainActivity.class);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		
    	mBuilder = new NotificationCompat.Builder(context)
    		    .setSmallIcon(R.drawable.ic_action_time)
    		    .setContentText(context.getString(R.string.internet_used))
    		    .setContentIntent(resultPendingIntent);
    	mNotificationId = 001;
    	mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);	
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		final Handler mHandler = new Handler();
		 mUpdateResults = new Runnable(){
			public void run(){
				if(NetworkUtil.getConnectivityStatus(context)==NetworkUtil.TYPE_MOBILE){
                	dm.setInt("UsageTime", dm.getInt("UsageTime")+1);
                	mBuilder.setContentTitle(dm.getInt("UsageTime")+" "+context.getString(R.string.minute));
                	noti = mBuilder.build();
                	noti.flags |= Notification.FLAG_ONGOING_EVENT;
                	mNotifyMgr.notify(mNotificationId,noti);
                	}
                	else {
                		mNotifyMgr.cancel(mNotificationId);
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}
	
}
