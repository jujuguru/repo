package com.jlabs.sf.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.widget.Toast;

import com.jlabs.sf.MainActivity;

public class Util {

	public static String getTime(){		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		return formatter.format(new Date());
	}
	
	public static String getViewDate(String realTime){
		return realTime.substring(0,19);
	}
	
	public static String getDate(Calendar cal){		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		return formatter.format(cal.getTime()).substring(0,10);
	}
	
	public static String getThatTime(String thatDate){		
		SimpleDateFormat formatter = new SimpleDateFormat(" HH:mm:ss:SSS");
		return thatDate + "" + formatter.format(new Date()); 
	}

}
