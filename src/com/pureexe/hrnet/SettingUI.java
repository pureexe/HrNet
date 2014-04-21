package com.pureexe.hrnet;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class SettingUI extends PreferenceActivity implements OnSharedPreferenceChangeListener{

	private ListPreference NotificationAlwaysType;
	public static final String KEY_NOTI_ALWAYS_TYPE = "keyNotiAlwaysType";
	public static final String KEY_NOTI_TIMEOUT_ALERT = "keyNotiTimeoutAlert";
	public static final String KEY_DISCONNECT_OUTOFPACK = "keyDisconnectOutofpack";
	public static final String KEY_DISCONNECT_DONTUSE = "keyDisconnectDontUse";
	
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting_ui);
        context = getApplicationContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.registerOnSharedPreferenceChangeListener(this);
        NotificationAlwaysType = (ListPreference)findPreference(KEY_NOTI_ALWAYS_TYPE);
        setNotiAlwayTypeSummary();


}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences pref, String prefKey) {
		if(prefKey.equals(KEY_NOTI_ALWAYS_TYPE)){
			setNotiAlwayTypeSummary();
		}
	}
	public void setNotiAlwayTypeSummary(){
		String data = PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_NOTI_ALWAYS_TYPE,"");
		if(data.equals("1")){
			NotificationAlwaysType.setSummary(getString(R.string.notification_counter_increase));
		}else if(data.equals("2")){
			NotificationAlwaysType.setSummary(getString(R.string.notification_counter_decrease));
		} else {
			NotificationAlwaysType.setSummary(getString(R.string.notification_counter_none));
		}
	}

}
