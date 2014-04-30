package com.pureexe.hrnet;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

public class NotifyManager{
	private Context context;
	private NotificationCompat.Builder mBuilder;
	private Notification noti;
	private NotificationManager mNotifyMgr;
	public static int TIME_COUNTER = 001; 
	public static int EMPTY_MOBILE_DATA = 002;
	public static int AUTO_DISCONNECT_YED = 003;
	public static int AUTO_DISCONNECT_SCREENOFF = 004;
	public NotifyManager(Context c){
		context = c;
		Intent resultIntent = new Intent(context,MainActivity.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(MainActivity.class);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		
    	mBuilder = new NotificationCompat.Builder(context)
    		    .setSmallIcon(R.drawable.ic_action_time)
    		    .setContentText(context.getString(R.string.internet_used))
    		    .setContentIntent(resultPendingIntent);
    	mNotifyMgr = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
	}
	
	public static void cancleAll(Context context){
		NotificationManager notiMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notiMgr.cancelAll();
	}
	public static void cancle(Context context,int id){
		NotificationManager notiMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notiMgr.cancel(id);
	}
	public void push_timecounter(int time){
		mBuilder.setContentTitle(""+time+" "+context.getString(R.string.minute));
		noti = mBuilder.build();
    	noti.flags |= Notification.FLAG_ONGOING_EVENT;
    	mNotifyMgr.notify(TIME_COUNTER,noti);
	}
	public void push_timecountdown(int time){
		if(time>0){
		mBuilder.setContentTitle(""+time+" "+context.getString(R.string.minute)+context.getString(R.string.remaining));
		}
		else {
			mBuilder.setContentTitle(context.getString(R.string.outofdataplan));
			mBuilder.setContentText(context.getString(R.string.outofdataplan_des));
		}
		noti = mBuilder.build();
    	noti.flags |= Notification.FLAG_ONGOING_EVENT;
    	mNotifyMgr.notify(TIME_COUNTER,noti);
	}
	public void push_netcut_alert(){
		mBuilder.setContentTitle(context.getString(R.string.auto_disconnect_yet));
		mBuilder.setContentText(context.getString(R.string.outofdataplan_des));
		noti = mBuilder.build();
    	mNotifyMgr.notify(EMPTY_MOBILE_DATA,noti);
	}
	public void push_auto_screenoff(){
		mBuilder.setContentTitle(context.getString(R.string.auto_disconnect_yet));
		mBuilder.setContentText(context.getString(R.string.outofdataplan_des));
		noti = mBuilder.build(); 
    	mNotifyMgr.notify(AUTO_DISCONNECT_SCREENOFF,noti);
	}

	public void push_lownet_alert() {
		mBuilder.setContentTitle(context.getString(R.string.auto_disconnect_yet));
		mBuilder.setContentText(context.getString(R.string.outofdataplan_des));
		noti = mBuilder.build();
    	mNotifyMgr.notify(EMPTY_MOBILE_DATA,noti);
		
	}
}
