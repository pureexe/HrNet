package com.pureexe.hrnet;

import java.util.Calendar;




import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class StartupService extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
			if(!ServiceUtil.checkServiceRunning(context, TimeCounterService.class)){
				Intent active = new Intent(context, TimeCounterService.class);
				context.startService(active);
				
			}
		}
	}

}
