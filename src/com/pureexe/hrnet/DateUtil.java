package com.pureexe.hrnet;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static String getPastDate(String dateString,int past){
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date myDate = null;
		try {
			myDate = dateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		 Calendar calendar = Calendar.getInstance();
	        calendar.setTime(myDate);
	        calendar.add(Calendar.DAY_OF_YEAR, -past);

	        Date previousDate = calendar.getTime();
	        String result = dateFormat.format(previousDate);

		return result;
	}
	public static String getThisDate(){
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
	        Date previousDate = calendar.getTime();
	        String result = dateFormat.format(previousDate);

		return result;
	}
}
