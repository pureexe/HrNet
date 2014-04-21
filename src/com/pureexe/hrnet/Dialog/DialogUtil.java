package com.pureexe.hrnet.Dialog;

import com.pureexe.hrnet.DataManager;
import com.pureexe.hrnet.NotifyManager;
import com.pureexe.hrnet.R;
import com.pureexe.hrnet.R.layout;
import com.pureexe.hrnet.R.string;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.preference.DialogPreference;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DialogUtil {
	public static void setDataPlan(final Context context){
		final DataManager dm = new DataManager(context);
		AlertDialog.Builder	builder = new AlertDialog.Builder(context);
		builder.setTitle(context.getString(R.string.set_data_plan_title));
		builder.setMessage(context.getString(R.string.set_data_plan_box_des));
		final EditText input = new EditText(context);
		input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_TEXT);
		builder.setView(input);
		builder.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
		
		public void onClick(DialogInterface dialog, int whichButton) {
				try{
					String inp = input.getText().toString();
					if(inp.equals("")){
						return;
					}
					String[] inpBox= inp.split(":");
					int minute =Integer.parseInt(inpBox[0]);
					if(inpBox.length==1){
						dm.setInt("DataPlan", minute);
						Toast.makeText(context, context.getString(R.string.applying_pleasewait), Toast.LENGTH_LONG).show();
					}
					else if(inpBox.length==2){
						minute*=60;
						minute+=Integer.parseInt(inpBox[1]);
						dm.setInt("DataPlan", minute);
						Toast.makeText(context, context.getString(R.string.applying_pleasewait), Toast.LENGTH_LONG).show();
					}
					else {
						Toast.makeText(context, context.getString(R.string.fail_invalid_input), Toast.LENGTH_LONG).show();
					}
					
				}
				catch(Exception e)
				{
					Toast.makeText(context, context.getString(R.string.fail_invalid_input), Toast.LENGTH_LONG).show();
				}
				
			
			}
		});
		builder.show();
	}
	
	public static void warn_setDataPlan(final Context context){
		final Context c = context;
		AlertDialog.Builder	builder = new AlertDialog.Builder(context);
		builder.setTitle(context.getString(R.string.warning));
		builder.setMessage(context.getString(R.string.warning_dataplan_override));
		builder.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
			DialogUtil.setDataPlan(c);
			}
		});
		builder.setNegativeButton(context.getString(R.string.cancle), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		builder.show();
	}
	
	public static void reset_counter(final Context context){
		final DataManager dm = new DataManager(context);
		AlertDialog.Builder	builder = new AlertDialog.Builder(context);
		builder.setTitle(context.getString(R.string.action_clear_data_usage))
			.setMessage(context.getString(R.string.action_clear_data_usage_des))
			.setPositiveButton(context.getString(R.string.yes), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					Toast.makeText(context, context.getString(R.string.applying_pleasewait), Toast.LENGTH_LONG).show();
					dm.setInt("UsageTime", 0);
					NotifyManager.cancleAll(context);
				}
				
			})
			.setNegativeButton(context.getString(R.string.no),new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface arg0, int arg1) {
				}
			});
		builder.show();
	}

	public static void newUpdateNotify(final Context context) {
		final DataManager dm = new DataManager(context);
		AlertDialog.Builder	builder = new AlertDialog.Builder(context);
		builder.setTitle(context.getString(R.string.new_update))
			.setMessage(context.getString(R.string.update_version)+" "+dm.getString("ServerVersionName")+"\n"+context.getString(R.string.tap_ok_to_download))
			.setPositiveButton(context.getString(R.string.yes), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse(dm.getString("ServerVersionURL")));
					context.startActivity(intent);
				}
				
			})
			.setNegativeButton(context.getString(R.string.no),new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface arg0, int arg1) {
				}
			});
		builder.show();
	}
	public static void openWeb(Context context,String Url){
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(Url));
		context.startActivity(intent);
	}
	

}

