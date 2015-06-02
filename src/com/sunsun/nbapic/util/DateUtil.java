package com.sunsun.nbapic.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *  时间格式处理类
 * @author sunsun
 * @date 2015年6月2日 下午12:55:48
 */
public class DateUtil {

	private static final SimpleDateFormat mDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");

	private static final SimpleDateFormat mDateFormatThisYear = new SimpleDateFormat(
			"MM-dd HH:mm");

	/**
	 * 把时间戳格式化为指定的时间格式
	 * 
	 * @param timestamp
	 * @param format
	 * @return
	 */
	public static String timestampToFormatDate(long timestamp, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
		Date date = new Date(timestamp);
		return sdf.format(date);
	}

	/**
	 * 
	 * @param time
	 *            单位秒
	 * @return
	 */
	public static String formatDurationToTime(int time) {
		int hour = 0;
		int minute = 0;
		int second = 0;
		second = time;
		if (second > 60) {
			minute = second / 60;
			second = second % 60;
		}

		if (minute > 60) {
			hour = minute / 60;
			minute = minute % 60;
		}

		String strtime = "";
		if (hour != 0) {
			strtime = (hour >= 10 ? hour : "0" + hour) + ":"
					+ (minute >= 10 ? minute : "0" + minute) + ":"
					+ (second >= 10 ? second : "0" + second);
		} else {
			strtime = (minute >= 10 ? minute : "0" + minute) + ":"
					+ (second >= 10 ? second : "0" + second);
		}
		return strtime;
	}

	/**
	 * 把时间戳转成 字符串
	 * 
	 * @param unixtime
	 * @return
	 */
	public static String timeDistanceString(long unixtime) {
		
		final long MILLISEC = 1000;
		final long MILLISEC_MIN = 60 * MILLISEC;
		final long MILLISEC_HOUR = 60 * MILLISEC_MIN;
		// final long MILLISEC_DAY = 24 * MILLISEC_HOUR;
		// final long MILLISEC_MONTH = 30 * MILLISEC_DAY;
		// final long MILLISEC_YEAR = 12 * MILLISEC_MONTH;
		long currentTime = System.currentTimeMillis();
		long timeDifference = Math.abs(currentTime - unixtime);

		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), 0, 1, 0, 0, 0);
		final boolean before_this_year = unixtime < cal
				.getTimeInMillis();

		if (timeDifference <= 0) {
			return "刚刚";
		}
		/*
		 * 今年之前
		 */
		else if (before_this_year) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd", Locale.CHINA);
			String dateString = dateFormat.format(new Date(unixtime));
			return dateString;
		} else {
			// cal = Calendar.getInstance();
			// cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
			// cal.get(Calendar.DAY_OF_MONTH) - 1, 0, 0, 0);
			// long startTime = cal.getTimeInMillis();
			cal = Calendar.getInstance();
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
					cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
			long endTime = cal.getTimeInMillis();
			// final boolean yesterday = unixtime * 1000 >= startTime &&
			// unixtime * 1000 < endTime;

			cal = Calendar.getInstance();
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
					cal.get(Calendar.DAY_OF_MONTH) + 1, 0, 0, 0);
			final boolean today = unixtime >= endTime
					&& unixtime < cal.getTimeInMillis();

			/*
			 * 1天内
			 */
			if (today) {
				if (timeDifference / MILLISEC_MIN < 1) {
					return "刚刚";
				} else if (timeDifference / MILLISEC_HOUR < 1) {
					/*
					 * 1小时内
					 */
					return String.format(Locale.US, "%d分钟前", timeDifference
							/ MILLISEC_MIN);
				} else {
					/*
					 * 1天内
					 */
					return String.format(Locale.US, "%d小时前", timeDifference
							/ MILLISEC_HOUR);
				}
			}
			// else if(yesterday){
			// return "昨天";
			// }
			else {
				/*
				 * 今年
				 */
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"MM-dd HH:mm", Locale.CHINA);
				String dateString = dateFormat
						.format(new Date(unixtime));
				return dateString;
			}
		}
	}

	public static String parseTime(long createTime) {
		Date createDate = new Date((long) createTime );
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(new Date());
		c2.setTime(createDate);
		if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) {
			return mDateFormatThisYear.format(createDate);
		}
		return mDateFormat.format(createDate);
	}
}

