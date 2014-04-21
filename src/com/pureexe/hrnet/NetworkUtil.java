package com.pureexe.hrnet;

import java.util.UUID;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class NetworkUtil {

	public static int TYPE_WIFI = 1;
	public static int TYPE_MOBILE = 2;
	public static int TYPE_NOT_CONNECTED = 0;
   
   
  public static int getConnectivityStatus(Context context) {
      ConnectivityManager cm = (ConnectivityManager) context
              .getSystemService(Context.CONNECTIVITY_SERVICE);
      
      NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
      
      if (null != activeNetwork) {
          if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
              return TYPE_WIFI;
           
          if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
              return TYPE_MOBILE;
      } 
      return TYPE_NOT_CONNECTED;
  }
  

public static boolean isConnected(Context context){
	ConnectivityManager conMgr = (ConnectivityManager) context
            .getSystemService(Context.CONNECTIVITY_SERVICE);
	  NetworkInfo i = conMgr.getActiveNetworkInfo();
	  if (i == null)
	    return false;
	  if (!i.isConnected())
	    return false;
	  if (!i.isAvailable())
	    return false;
	  return true;
}
  public static String getConnectivityStatusString(Context context) {
      int conn = NetworkUtil.getConnectivityStatus(context);
      String status = null;
      if (conn == NetworkUtil.TYPE_WIFI) {
          status = "WIFI";
      } else if (conn == NetworkUtil.TYPE_MOBILE) {
          status = "MOBILE";
      } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
          status = "NO";
      }
      return status;
  }
  public static String getCarrier(Context context){
	  TelephonyManager manager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
	  return manager.getNetworkOperatorName();
	  
  }
  public static String getUniqueID(Context context){
	  final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	    final String tmDevice, tmSerial, androidId;
	    tmDevice = "" + tm.getDeviceId();
	    tmSerial = "" + tm.getSimSerialNumber();
	    androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

	    UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
	    String deviceId = deviceUuid.toString();
		return deviceId;
  }
}