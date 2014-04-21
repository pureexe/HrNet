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
		NotifyManager noti = new NotifyManager(context);
		DataManager dm = new DataManager(context);
		if (NetworkUtil.getConnectivityStatus(context) == NetworkUtil.TYPE_MOBILE) {
				//context.stopService(new Intent(context, TimeCounterService.class));
				//context.startService(new Intent(context, TimeCounterService.class));
			if(dm.getSettingString(SettingUI.KEY_NOTI_ALWAYS_TYPE).equals("1")){
				noti.push_timecounter(dm.getInt("UsageTime"));
        	} else if(dm.getSettingString(SettingUI.KEY_NOTI_ALWAYS_TYPE).equals("2")){
        		noti.push_timecountdown(dm.getInt("DataPlan")-dm.getInt("UsageTime"));
        	}
			if((dm.getInt("DataPlan")-dm.getInt("UsageTime"))<=0){
				if(dm.getSettingBoolean(SettingUI.KEY_DISCONNECT_OUTOFPACK)){
				PhoneUtil.setMobileDataEnabled(context,false);
				noti.push_netcut_alert();
				}
			}
			dm.setBool("NotifyRUNNED", true);
		} else {
			//context.stopService(new Intent(context, TimeCounterService.class));
	    	if(dm.getBool("NotifyRUNNED")){
	    		dm.setInt("UsageTime", dm.getInt("UsageTime")+1);
            	String date = DateUtil.getThisDate();
            	dm.setInt("Date_"+date, dm.getInt("Date_"+date)+1);
	    		NotifyManager.cancle(context, NotifyManager.TIME_COUNTER);
	    		dm.setBool("NotifyRUNNED", false);
	    	}
			
		}
	}
}