package com.chaitanya.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Convertor {
	public static Boolean convetStatusToBool(Character status){
		Boolean result;
		if(status=='Y')
			result= Boolean.TRUE;
		else
			result= Boolean.FALSE;
		return result;
	}
	
	public static Character convertStatusToChar(Boolean status){
		Character result;
		if(status==Boolean.TRUE){
			result='Y';
		}
		else{
			result='N';
		}
		return result;
	}
	public static String calendartoString(Calendar date){
		SimpleDateFormat format=new SimpleDateFormat("dd-MMMM-YYYY");
		return format.format(date.getTime());
	}
	public static Calendar stringToCalendar(String date){
		Calendar foramttedDate= Calendar.getInstance();
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("dd-MMMM-YYYY HH:mm:ss");
			sdf.parse(date);
			foramttedDate = (Calendar)sdf.getCalendar().clone();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return foramttedDate;
		
	}
	
	public static Calendar stringToCalendar(String date, String format){
		Calendar foramttedDate= Calendar.getInstance();
		try {
			SimpleDateFormat sdf=new SimpleDateFormat(format);
			sdf.parse(date);
			foramttedDate = (Calendar)sdf.getCalendar().clone();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return foramttedDate;
		
	}
	
}
