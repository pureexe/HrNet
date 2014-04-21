package com.pureexe.hrnet;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.pureexe.hrnet.Dialog.DialogUtil;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;

public class UpdateCheckerTask extends AsyncTask{

	public Context context;
	public DataManager dm;
	public String servermsg;
	public UpdateCheckerTask(Context context2){
		context = context2;
		dm = new DataManager(context);
	}
	@Override
	protected Object doInBackground(Object... arg0) {
		servermsg = uploadDevice();
		return null;
	}

	@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);
		String[] msg = servermsg.split("#");

		dm.setInt("ServerVersionCode", Integer.parseInt(msg[0]));
		dm.setString("ServerVersionName",msg[1]);
		dm.setString("ServerVersionURL",msg[2]);
		PackageInfo pm = null;
		try {
			pm = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		if(Integer.parseInt(msg[0])!=pm.versionCode){
			DialogUtil.newUpdateNotify(context);
		}
		dm.setLong("LastCheckUpdate",new Date().getTime());
		
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}
	public static String getHttpPost(String url,List<NameValuePair> params){
		StringBuilder str = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		
		try {
			if(params!=null)
			httpPost.setEntity(new UrlEncodedFormEntity(params));			
			HttpResponse response = client.execute(httpPost); //เจ้าตัวปัญหา
			
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) { // Status OK
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					
					str.append(line);
				}
			} else {
				str.append(""+statusCode);
			}
			}
			catch(Exception e)
			{				
				return e.toString();
			}
		return str.toString();
	}
	
	public String uploadDevice(){
		PackageInfo pInfo = null;
		try {
			pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("AppBuildVer",""+pInfo.versionCode));
		params.add(new BasicNameValuePair("UniqueID",NetworkUtil.getUniqueID(context)));
		params.add(new BasicNameValuePair("Model",Build.MODEL));
		params.add(new BasicNameValuePair("Board",Build.BOARD));
		params.add(new BasicNameValuePair("Device",Build.DEVICE));
		params.add(new BasicNameValuePair("Brand",Build.BRAND));
		params.add(new BasicNameValuePair("Manufacturer",Build.MANUFACTURER));
		params.add(new BasicNameValuePair("BuildFingerprint",Build.FINGERPRINT));
		params.add(new BasicNameValuePair("AndroidVersion",""+Build.VERSION.RELEASE));
		params.add(new BasicNameValuePair("Carrier",NetworkUtil.getCarrier(context)));
		params.add(new BasicNameValuePair("TimeStamp",DateUtil.getCurrentTimeStamp()));
		return getHttpPost("http://exam-engine.urll.us/hrnet/checkupdate.php",params);
	}

}
