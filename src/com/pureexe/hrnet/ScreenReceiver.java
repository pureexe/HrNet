package com.pureexe.hrnet;

import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScreenReceiver extends BroadcastReceiver {
    
    public static boolean wasScreenOn = true;
 
    @Override
    public void onReceive(Context context, Intent intent) {
    	DataManager dm = new DataManager(context);
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
        	if(dm.getSettingBoolean(SettingUI.KEY_DISCONNECT_DONTUSE)&&NetworkUtil.getConnectivityStatus(context)==NetworkUtil.TYPE_MOBILE){
        		dm.setBool("ScreenCheck", true);
        		dm.setLong("ScreenCheckTime", new Date().getTime());
        	}
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
        	if(dm.getSettingBoolean(SettingUI.KEY_DISCONNECT_DONTUSE)&&NetworkUtil.getConnectivityStatus(context)==NetworkUtil.TYPE_MOBILE){
        		dm.setBool("ScreenCheck", false);
        	}
        }
    }
 
}