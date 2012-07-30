package com.survey.tools;

import java.util.concurrent.TimeUnit;

public class TimeUtils {
	
	public static long currentTimeMillis(){
		return System.currentTimeMillis();
	}
	
	public static long currentSeconds(){
		return TimeUnit.MILLISECONDS.toSeconds(TimeUtils.currentTimeMillis());
	}
	
	public static long currentMinutes(){
		return TimeUnit.MILLISECONDS.toMinutes(TimeUtils.currentTimeMillis());
	}
	
	public static long currentHours(){
		return TimeUnit.MILLISECONDS.toHours(TimeUtils.currentTimeMillis());
	}
	
	public static long currentDays(){
		return TimeUnit.MILLISECONDS.toDays(TimeUtils.currentTimeMillis());
	}
	
	public static long convertSeconds(long duration){
		return TimeUnit.MILLISECONDS.toSeconds(duration);
	}
	
	public static long convertMinutes(long duration){
		return TimeUnit.MILLISECONDS.toMinutes(duration);
	}
	
	public static long convertHours(long duration){
		return TimeUnit.MILLISECONDS.toHours(duration);
	}
	
	public static long convertDays(long duration){
		return TimeUnit.MILLISECONDS.toDays(duration);
	}
	
	/**
	 * 传入时间与当前时间相差多少秒
	 * @param duration
	 * @return
	 */
	public static long diffCurrentSeconds(long duration){
		return TimeUtils.currentSeconds()-TimeUtils.convertSeconds(duration);
	}
	
	/**
	 * 传入时间与当前时间相差多少分
	 * @param duration
	 * @return
	 */
	public static long diffCurrentMinutes(long duration){
		return TimeUtils.currentMinutes()-TimeUtils.convertMinutes(duration);
	}
	
	/**
	 * 传入时间与当前时间相差多少小时
	 * @param duration
	 * @return
	 */
	public static long diffCurrentHours(long duration){
		return TimeUtils.currentHours()-TimeUtils.convertHours(duration);
	}
	
	/**
	 * 传入时间与当前时间相差多少天
	 * @param duration
	 * @return
	 */
	public static long diffCurrentDays(long duration){
		return TimeUtils.currentDays()-TimeUtils.convertDays(duration);
	}
	
	public static void main(String args[]) throws Exception{
		long duration=TimeUtils.currentTimeMillis();
		Thread.sleep(1000);
		System.out.println(TimeUtils.diffCurrentSeconds(duration));
	}
	

}
