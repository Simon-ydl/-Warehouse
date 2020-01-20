package com.yjs.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/*
 日期装换的工具，Date->String  String->Date
 */
public class DateUtil {

	public static final String yMd = "yyyyMMdd";
	
	public static final String y_M = "yyyy-MM";
	
	public static final String y_M_d = "yyyy-MM-dd";
	
	public static final String H_m_s = "HH:mm:ss";

	public static final String y_M_d_H_m = "yyyy-MM-dd HH:mm";

	public static final String y_M_d_H_m_s = "yyyy-MM-dd HH:mm:ss";
	
	private static final Random random = new Random();

	/**
	 * 精确到毫秒
	 */
	public static final String yMdHmsSSS = "yyyyMMddHHmmssSSS";

	/**
	 * 转成字符串(默认格式:yyyy-MM-dd)
	 * 
	 * @param d
	 * @return
	 */
	public static String toStr(Date d) {
		return toStr(d, y_M_d);
	}
	
	/**
	 * 根据当前日期转成字符串(格式：yyyy-MM-dd HH:mm:ss)
	 * 
	 * @return
	 */
	public static String toStr() {
		return toStr(new Date(), y_M_d_H_m_s);
	}
	/**
	 * 根据当前日期转成字符串
	 * 
	 * @param d
	 * @return
	 */
	public static String toStr(String pattern) {
		return toStr(new Date(), pattern);
	}
	
	/**
	 * 根据当前日期格式"yyyyMMddHHmmssSSS"生成后面2位随机数
	 * 
	 * @param d
	 * @return
	 */
	public static String toRandomStrByDate() {
		Double d = Math.random() * 100;
		return toStr(new Date(), yMdHmsSSS) + d.intValue();
	}
	/**
	 * 根据当前系统时间戳生成后面max随机数
	 * 
	 * @return
	 */
	public static String toCurrentTimeMillisAndRandom(int max) {
//		int min = 0;//(int) Math.pow(10, len -1);
//		int max = (int) Math.pow(10, len);
//		int intBounded = min + ((int) (random.nextFloat() * (max - min)));
		/*int pj = max/2;
		int first = random.nextInt(pj);
		int second = first + pj;		
		return System.currentTimeMillis() + "" + first + "" + second;*/
		int intBounded = random.nextInt(max);
		return System.currentTimeMillis() + "" + intBounded;
	}
	
	public static String toCurrentTimeMillisAndRandomFormat(int max) {
		int intBounded = random.nextInt(max);
		return toStr(yMdHmsSSS) + "" + intBounded;
	}
	/**
	 * 获取id
	 * @return
	 */
	public static String getId() {		
		return toCurrentTimeMillisAndRandom(100000000);
		//return toCurrentTimeMillisAndRandomFormat(100000000);
	}
	/**
	 * 获取id
	 * @return
	 */
	public static String getIdFormat() {
		return toCurrentTimeMillisAndRandomFormat(100000000);
		//return toCurrentTimeMillisAndRandomFormat(100000000);
	}

	/**
	 * 转成字符串
	 * 
	 * @param d
	 * @param pattern
	 * @return
	 */
	public static String toStr(Date d, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(d);
	}

	/**
	 * 获取精确到日的日期
	 * 
	 * @param d
	 * @return
	 */
	public static Date getForDay(Date d) {
		if(null == d){
			d = new Date();
		}
		Calendar c = Calendar.getInstance();
		c.clear();
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d);
		c.set(Calendar.YEAR, c1.get(Calendar.YEAR));
		c.set(Calendar.MONTH, c1.get(Calendar.MONTH));
		c.set(Calendar.DAY_OF_MONTH, c1.get(Calendar.DAY_OF_MONTH));
		c1 = null;
		return c.getTime();
	}
	/**
	 * 获取精确到日的日期(当前日期)
	 * @return
	 */
	public static Date getForDay() {
		return getForDay(null);
	}	
	/**
	 * 获取当前日期所在月的第一天
	 * 
	 * @param d
	 * @return
	 */
	public static Date getForMonthFirstDay(Date d) {
		if(null == d){
			d = new Date();
		}
		Calendar c = Calendar.getInstance();
		c.clear();
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d);
		c.set(Calendar.YEAR, c1.get(Calendar.YEAR));
		c.set(Calendar.MONTH, c1.get(Calendar.MONTH));
		c.set(Calendar.DAY_OF_MONTH, 1);
		c1 = null;
		return c.getTime();
	}
	/**
	 * 获取当前日期所在月的第一天(当前日期)
	 * @return
	 */
	public static Date getForMonthFirstDay() {
		return getForMonthFirstDay(null);
	}
	/**
	 * 获取当前日期所在月的最后一天
	 * 
	 * @param d
	 * @return
	 */
	public static Date getForMonthLastDay(Date d) {
		if(null == d){
			d = new Date();
		}
		Calendar c = Calendar.getInstance();
		c.clear();
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d);
		c.set(Calendar.YEAR, c1.get(Calendar.YEAR));
		c.set(Calendar.MONTH, c1.get(Calendar.MONTH)+1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c1 = null;
		return offset(c.getTime(), Calendar.DAY_OF_MONTH, -1);
	}
	/**
	 * 获取当前日期所在月的最后一天(当前日期)
	 * @return
	 */
	public static Date getForMonthLastDay() {
		return getForMonthLastDay(null);
	}
	
	/**
	 * 将给定日期的字段设定成给定数字
	 * @param d 日期
	 * @param model 日、月、年、小时、分钟(如:Calendar.YEAR)
	 * @param value 要设置的数字
	 * @return
	 */
	public static Date getForModel(Date d,int model, int value) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.clear(model);
		c.set(model, value);
		return c.getTime();
	}

	/**
	 * 给给定日期添加偏移量
	 * 
	 * @param d
	 * @param model
	 *            在哪里偏移(年/月/日 等 如:Calendar.YEAR)
	 * @param offset
	 *            可以是正、负数
	 * @return
	 */
	public static Date offset(Date d, int model, int offset) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(model, offset);
		return c.getTime();
	}
	/**
	 * 给当前日期添加偏移量
	 * 
	 * @param model
	 *            在哪里偏移(年/月/日 等 如:Calendar.YEAR)
	 * @param offset
	 *            可以是正、负数
	 * @return
	 */
	public static Date offset(int model, int offset) {
		return offset(new Date(), model, offset);
	}
	/**
	 * 给当前日期添加偏移量
	 * 
	 * @param model
	 *            在哪里偏移(年/月/日 等 如:Calendar.YEAR)
	 * @param offset
	 *            可以是正、负数
	 * @return
	 */
	public static Date offsetForDay(int offset) {
		return offset(new Date(), Calendar.DATE, offset);
	}
	/**
	 * 给当前日期添加偏移量
	 * 
	 * @param model
	 *            在哪里偏移(年/月/日 等 如:Calendar.YEAR)
	 * @param offset
	 *            可以是正、负数
	 * @return
	 */
	public static Date offsetForDay(Date day , int offset) {
		return offset(day, Calendar.DATE, offset);
	}

	/**
	 * 获取当前时间从零点开始的分钟数
	 * 
	 * @return
	 */
	public static int getMinuteNum() {
		return getMinuteNum(new Date());
	}

	/**
	 * 根据给定时间获取从零点开始的分钟数
	 * 
	 * @return
	 */
	public static int getMinuteNum(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int h = c.get(Calendar.HOUR_OF_DAY);
		int m = c.get(Calendar.MINUTE);
		return (h * 60) + m;
	}

	/**
	 * 根据给定小时和分钟字符串转换成从零点开始的分钟数
	 * 
	 * @param d
	 *            格式: 12:30/8:20
	 * @return
	 */
	public static int formatMinuteNum(String d) {
		String[] ds = d.split(":");
		if (ds.length != 2) {
			return 0;
		}
		int h = Integer.parseInt(ds[0]);
		int m = Integer.parseInt(ds[1]);
		return (h * 60) + m;
	}

	/**
	 * 将从零点开始分钟数转换成字符串
	 * 
	 * @param m
	 * @return 00:00/12:00/08:02
	 */
	public static String convertToStr(int m) {
		String str = "00:00";
		if (m > 0) {
			String s = String.valueOf(m / 60);
			if (s.length() == 1) {
				s = "0" + s;
			}
			String mStr = String.valueOf(m % 60);
			if (mStr.length() == 1) {
				mStr = "0" + mStr;
			}
			str = s + ":" + mStr;
		}
		return str;
	}

	/**
	 * 验证当前时间是否在开始时间和结束时间之间
	 * 
	 * @param startTime
	 *            格式：2015-03-12 12:00
	 * @param endTime
	 *            格式：2015-03-12 12:00
	 *            
	 * @param pattern
	 * @return
	 */
	public static boolean betweenCurrentTime(String startTime, String endTime,String pattern) {
		Date start = convertDate(startTime, pattern);
		Date end = convertDate(endTime, pattern);
		if(null != start && null != end){
			Date current = new Date();
			return (start.getTime() <= current.getTime() && end.getTime() >= current.getTime());
		}
		return false;
	}

	/**
	 * 字符窜转换成Date
	 * @param strTime
	 * @param pattern
	 * @return
	 */
	public static Date convertDate(String strTime, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = format.parse(strTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static int getDay(Date d){
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c.get(Calendar.DATE);
	}
}
