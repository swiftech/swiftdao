package org.swiftdao.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Wang Yuxing
 * 
 */
public class TimeUtils {

	/**
	 * 根据提供的开始时间和有效期间，检查是否当前时间已经超过有效时间。
	 * 
	 * @param startTime
	 * @param offset
	 * @return
	 */
	public static boolean checkTimeExpired(Calendar startTime, int offset) {
		Calendar cutoffTime = (Calendar) startTime.clone();
		cutoffTime.add(Calendar.SECOND, offset);
		if (cutoffTime.after(Calendar.getInstance())) {
			return false;
		}
		return true;
	}

	/**
	 * 根据提供的开始时间和有效期间，检查是否当前时间已经超过有效时间。
	 * 
	 * @param startTime
	 * @param offset
	 * @return
	 */
	public static boolean checkTimeExpired(Date startTime, long offset) {

		return false;
	}

	/**
	 * 根据月份数的偏移，得到新的日期。
	 * 
	 * @param source Date
	 * @param offset int
	 * @return Date
	 */
	public static Date getDateByMonthOffset(Date source, int offset) {
		Date destination = null;
		if (source != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(source);
			c.add(Calendar.MONTH, offset);
			destination = c.getTime();
		}
		return destination;
	}

	/**
	 * 根据日期的偏移，得到新的日期。
	 * 
	 * @param source Date
	 * @param offset int
	 * @return Date
	 */
	public static Date getDateByDayOffset(Date source, int offset) {
		Date destination = null;
		if (source != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(source);
			c.add(Calendar.DAY_OF_MONTH, offset);
			destination = c.getTime();
		}
		return destination;
	}

	/**
	 * 取出日期中的年份数字。
	 * 
	 * @param source Date
	 * @return int
	 */
	public static int getYear(Date source) {
		int year = Integer.MIN_VALUE;
		if (source != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(source);
			year = c.get(Calendar.YEAR);
		}
		return year;

	}

	/**
	 * 取出日期中的月份数字。
	 * 
	 * @param source Date
	 * @return int
	 */
	public static int getMonth(Date source) {
		int month = Integer.MIN_VALUE;
		if (source != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(source);
			month = c.get(Calendar.MONTH) + 1;
		}
		return month;

	}

	/**
	 * 取出日期中的天是月份中第几天。
	 * 
	 * @param source Date
	 * @return int
	 */
	public static int getDayOfMonth(Date source) {
		int day = Integer.MIN_VALUE;
		if (source != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(source);
			day = c.get(Calendar.DAY_OF_MONTH);
		}
		return day;

	}

	/**
	 * 取出日期中的天是年份中第几天。
	 * 
	 * @param source Date
	 * @return int
	 */
	public static int getDayOfYear(Date source) {
		int day = Integer.MIN_VALUE;
		if (source != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(source);
			day = c.get(Calendar.DAY_OF_YEAR) + 1;
		}
		return day;
	}

	/**
	 * 取出日期中的小时数字，24小时。
	 * 
	 * @param source Date
	 * @return int
	 */
	public static int getHourOfDay(Date source) {
		int hour24 = Integer.MIN_VALUE;
		if (source != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(source);
			hour24 = c.get(Calendar.HOUR_OF_DAY);
		}
		return hour24;
	}

	/**
	 * 取出日期中的分钟数字。
	 * 
	 * @param source Date
	 * @return int
	 */
	public static int getMinute(Date source) {
		int minute = Integer.MIN_VALUE;
		if (source != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(source);
			minute = c.get(Calendar.MINUTE);
		}
		return minute;
	}

	/**
	 * 取出日期中的秒数字。
	 * 
	 * @param source Date
	 * @return int
	 */
	public static int getSecond(Date source) {
		int second = Integer.MIN_VALUE;
		if (source != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(source);
			second = c.get(Calendar.SECOND);
		}
		return second;
	}

	/**
	 * 取得一个月的开始时间，即一号凌晨零点。
	 * 
	 * @param source Date
	 * @return Date
	 */
	public static Date getMonthBeginTime(Date source) {
		Date des = null;
		if (source != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(source);
			c.set(Calendar.DAY_OF_MONTH, 1);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			des = c.getTime();
		}
		return des;
	}

	/**
	 * 取得下一个月的开始时间，即下个月初的一号凌晨零点。
	 * 
	 * @param source Date
	 * @return Date
	 */
	public static Date getMonthEndTime(Date source) {
		Date des = null;
		if (source != null) {
			des = getDateByMonthOffset(source, 1);
			des = getMonthBeginTime(des);
			des = new Date(des.getTime() - 1);
		}
		return des;
	}

	/**
	 * 取得一天开始的时间，即凌晨零点。
	 * 
	 * @param source Date
	 * @return Date
	 */
	public static Date getDayBeginTime(Date source) {
		Date des = null;
		if (source != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(source);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			des = c.getTime();
		}
		return des;
	}

	/**
	 * 取得一天结束的时间，即午夜12点
	 * 
	 * @param source Date
	 * @return Date
	 */
	public static Date getDayEndTime(Date source) {
		Date des = null;
		if (source != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(source);
			c.set(Calendar.HOUR_OF_DAY, 24);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			des = c.getTime();
		}
		return des;
	}
	
	/**
	 * 取得向前某个月的第一天。
	 * @param monthAgo
	 * @return
	 */
	public static Date getFirstDayOfMonthAgo(int monthAgo){
		Calendar time = Calendar.getInstance();
		time.add(Calendar.MONTH, 0 - monthAgo);
		time.set(Calendar.DATE, 1);
		time.set(Calendar.HOUR_OF_DAY, 0);
		time.set(Calendar.MINUTE, 0);
		time.set(Calendar.SECOND, 0);
		return time.getTime();
	}
}
