package com.chaitanya.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Convertor {
	
	
	public static final String dateFormat="dd-MMMM-yyyy";
	
	public static final String dateFormatWithTime="dd-MMMM-yyyy HH:mm:ss";
	
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
	
	public static String calendartoString(Calendar date, String dateformat){
		SimpleDateFormat format=new SimpleDateFormat(dateformat);
		return format.format(date.getTime());
	}
	
	public static Calendar stringToCalendar(String date, String dateformat){
		Calendar foramttedDate= Calendar.getInstance();
		try {
			SimpleDateFormat sdf=new SimpleDateFormat(dateformat);
			sdf.parse(date);
			foramttedDate = (Calendar)sdf.getCalendar().clone();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return foramttedDate;
		
	}
	
}
