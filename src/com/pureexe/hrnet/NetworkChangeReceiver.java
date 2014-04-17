package com.pureexe.hrnet;


import java.util.List;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {

	private static final String NOTIFICATION_SERVICE = null;

	@Override
	public void onReceive(final Context context, final Intent intent) {
		NotificationManager notiMgr = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		DataManager dm = new DataManager(context);
		if (NetworkUtil.getConnectivityStatus(context) == NetworkUtil.TYPE_MOBILE) {
				//context.stopService(new Intent(context, TimeCounterService.class));
				//context.startService(new Intent(context, TimeCounterService.class));
			Intent resultIntent = new Intent(context,MainActivity.class);
			TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
			stackBuilder.addParentStack(MainActivity.class);
			stackBuilder.addNextIntent(resultIntent);
			PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

	    	Builder mBuilder = new NotificationCompat.Builder(context)
	    		    .setSmallIcon(R.drawable.ic_action_time)
	    		    .setContentText(context.getString(R.string.internet_used))
	    		    .setContentIntent(resultPendingIntent);
	    	int mNotificationId = 001;
        	mBuilder.setContentTitle(dm.getInt("UsageTime")+" "+context.getString(R.string.minute));
        	Notification noti = mBuilder.build();
        	noti.flags |= Notification.FLAG_ONGOING_EVENT;
        	notiMgr.notify(mNotificationId,noti);
			dm.setBool("NotifyRUNNED", true);
		} else {
			//context.stopService(new Intent(context, TimeCounterService.class));
	    	if(dm.getBool("NotifyRUNNED")){
	    		dm.setInt("UsageTime", dm.getInt("UsageTime")+1);
	    		notiMgr.cancel(001);
	    		dm.setBool("NotifyRUNNED", false);
	    	}
			
		}
	}
}