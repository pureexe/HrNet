package com.pureexe.hrnet;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;

public class DateUtil {
	public static final int DATE_MONDAY = 2;
	public static final int DATE_TUESDAY = 3;
	public static final int DATE_WEDNESDAY = 4;
	public static final int DATE_THURSDAY = 5;
	public static final int DATE_FRIDAY = 6;
	public static final int DATE_SATURDAY = 7;
	public static final int DATE_SUNDAY = 1;
	public static final int MONTH_JAN = 1;
	public static final int MONTH_FEB = 2;
	public static final int MONTH_MAR = 3;
	public static final int MONTH_ARP = 4;
	public static final int MONTH_MAY = 5;
	public static final int MONTH_JUN = 6;
	public static final int MONTH_JUL = 7;
	public static final int MONTH_AUG = 8;
	public static final int MONTH_SEP = 9;
	public static final int MONTH_OCT = 10;
	public static final int MONTH_NOV = 11;
	public static final int MONTH_DEC = 12;
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
	public static String getDateOnly(String dateString){
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date myDate = null;
		try {
			myDate = dateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		DateFormat dateOnly = new SimpleDateFormat("dd");
        String result = dateOnly.format(myDate);
		return result;
	}
	public static String getMonthOnly(String dateString){
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date myDate = null;
		try {
			myDate = dateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		DateFormat dateOnly = new SimpleDateFormat("MM");
        String result = dateOnly.format(myDate);
		return result;
	}
	public static int getDayInWeek(String dataString){
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date myDate = null;
		try {
			myDate = dateFormat.parse(dataString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(myDate);
		int day_of_week = c.get(Calendar.DAY_OF_WEEK);
		return day_of_week;
	}
	public static String getDayLableInWeek(Context context,String dataString){
		int day = getDayInWeek(dataString);
		String dayint = getDateOnly(dataString);
		if(day == DATE_MONDAY){
			return "MON"+dayint;
		}else if(day == DATE_TUESDAY){
			return "TUE"+dayint;
		}else if(day == DATE_WEDNESDAY){
			return "WED"+dayint;
		}else if(day == DATE_THURSDAY){
			return "THU"+dayint;
		}else if(day == DATE_FRIDAY){
			return "FRI"+dayint;
		}else if(day == DATE_SATURDAY){
			return "SAT"+dayint;
		}else if(day == DATE_SUNDAY){
			return "SUN"+dayint;
		}
		return dataString;
		
	}
	public static String getMonthLableHalf(Context context,String dataString){
		String m = getMonthOnly(dataString);
		int month = Integer.parseInt(m);
		if(month == MONTH_JAN){
			return "JAN";
		}else if(month == MONTH_FEB){
			return "FEB";
		}else if(month == MONTH_MAR){
			return "MAR";
		}else if(month == MONTH_ARP){
			return "ARP";
		}else if(month == MONTH_MAY){
			return "MAY";
		}else if(month == MONTH_JUN){
			return "JUN";
		}else if(month == MONTH_JUL){
			return "JUL";
		}else if(month == MONTH_AUG){
			return "AUG";
		}else if(month == MONTH_SEP){
			return "SEP";
		}else if(month == MONTH_OCT){
			return "OCT";
		}else if(month == MONTH_NOV){
			return "NOV";
		}else if(month == MONTH_DEC){
			return "DEC";
		}
		
		return "";
	}
	public static String getMonthLableFull(Context context,String dataString){
		String m = getMonthOnly(dataString);
		int month = Integer.parseInt(m);
		if(month == MONTH_JAN){
			return "JANUARY";
		}else if(month == MONTH_FEB){
			return "FEBUARY";
		}else if(month == MONTH_MAR){
			return "MARCH";
		}else if(month == MONTH_ARP){
			return "ARPIL";
		}else if(month == MONTH_MAY){
			return "MAY";
		}else if(month == MONTH_JUN){
			return "JUNE";
		}else if(month == MONTH_JUL){
			return "JULY";
		}else if(month == MONTH_AUG){
			return "AUGEST";
		}else if(month == MONTH_SEP){
			return "SEPTEMBER";
		}else if(month == MONTH_OCT){
			return "OCTOBER";
		}else if(month == MONTH_NOV){
			return "NOVEMBER";
		}else if(month == MONTH_DEC){
			return "DECEMBER";
		}
		
		return "";
	}
	public static int pastToFisrtDateofMonth(){
		Calendar c = Calendar.getInstance();
		int date = c.get(Calendar.DAY_OF_MONTH);
		return date-1;
	}
	public static int getMonthAvaliable(Context context,String dataString){
		String m = getMonthOnly(dataString);
		int month = Integer.parseInt(m);
		if(month == MONTH_JAN){
			return 31;
		}else if(month == MONTH_FEB){
			return 28;
		}else if(month == MONTH_MAR){
			return 31;
		}else if(month == MONTH_ARP){
			return 30;
		}else if(month == MONTH_MAY){
			return 31;
		}else if(month == MONTH_JUN){
			return 30;
		}else if(month == MONTH_JUL){
			return 31;
		}else if(month == MONTH_AUG){
			return 31;
		}else if(month == MONTH_SEP){
			return 30;
		}else if(month == MONTH_OCT){
			return 31;
		}else if(month == MONTH_NOV){
			return 30;
		}else if(month == MONTH_DEC){
			return 31;
		}
		return 0;	
	}
	public static String getCurrentTimeStamp(){
	    try {

	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String currentTimeStamp = dateFormat.format(new Date()); // Find todays date

	        return currentTimeStamp;
	    } catch (Exception e) {
	        e.printStackTrace();

	        return null;
	    }
	}
}
