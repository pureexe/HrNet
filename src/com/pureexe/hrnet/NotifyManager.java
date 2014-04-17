package com.pureexe.hrnet;

import android.app.NotificationManager;
import android.content.Context;

public class NotifyManager {
	public static void cancleAll(Context context){
		NotificationManager notiMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notiMgr.cancelAll();
	}
}
