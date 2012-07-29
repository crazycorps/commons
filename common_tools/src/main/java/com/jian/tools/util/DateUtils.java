package com.jian.tools.util;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.jian.tools.TimeUtils;
public class DateUtils {
	
	public static final String DEFAULT_DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
	
	public static final String DEFAULT_DAY_FORMAT="yyyy-MM-dd";
	
	public static final  String DEFAULT_HOUR_FORMAT="HH:mm:ss";
	
	public static final String HOUR_NO_SECOND_FORMAT="HH:mm";
	
	public static final String formatToDay(Date date){
		return format(date, DEFAULT_DAY_FORMAT);
	}
	
	public static String formatToDay(long millis){
		return format(millis, DEFAULT_DAY_FORMAT);
	}
	
	public static String formatToHour(Date date){
		return format(date, DEFAULT_HOUR_FORMAT);
	}
	
	public static String formatToHour(long millis){
		return format(millis, DEFAULT_HOUR_FORMAT);
	}
	
	public static String format(Date date){
		return format(date, DEFAULT_DATE_FORMAT);
	}
	
	public static String format(long millis){
		return format(millis, DEFAULT_DATE_FORMAT);
	}

	public static String format(Date date,String pattern){
		return DateFormatUtils.format(date, pattern);
	}
	
	public static String format(long millis,String pattern){
		return DateFormatUtils.format(millis, pattern);
	}
	
	public static long getDiffDayThanToday(long millis){
		return TimeUtils.currentDays()-TimeUtils.convertDays(millis);
	}
	
	
}
