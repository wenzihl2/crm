package com.wzbuaa.crm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateHelper {

	private static String year;
	private static String month;
	private static String day;
	private static String hour;
	private static String minute;
	private static String second;
	@SuppressWarnings("unused")
	private static String millisecond;

	public static Date string2Date(String dateStr, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将给定的时间字符串转换为普通的时间对象
	 * "yyyy-MM-dd HH:mm:ss" to Date
	 * @param dateStr
	 * @return
	 */
	public static Date string2DateCommon(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将给定的日期对象转换成普通的时间字符串
	 * date to "yyyy-MM-dd HH:mm:ss"
	 * @param date
	 * @return
	 */
	public static String date2StringCommon(Date date) {
		return date2String(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将给定的日期对象转换为给定的日期格式
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2String(Date date, String format) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
    /**
     * 将给定的日期对象转换为给定的日期格式,如果给定的日期为空，则默认为当前日期
     * 
     * @param date
     * @param format
     * @return
     */
    public static String date2StringNow(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (date == null) {
            return sdf.format(new Date());
        }
        return sdf.format(date);
    }
	
	/**
	 * 获取给定时间的开始时间
	 * @param date
	 * @return
	 */
	public static Date toDayStart(Date date) {
		if(date == null){
			return null;
		}
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            return null;
        }
    }
	
	/**
	 * 获取给定时间的最后一秒
	 * @param date
	 * @return
	 */
	public static Date toDayEnd(Date date) {
		if(date == null){
			return null;
		}
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date tmp = null;
        try {
            tmp = sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            return null;
        }
        return new Date(tmp.getTime() + 24 * 60 * 60 * 1000 - 1);
    }

	public static void setCurrentDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		year = String.valueOf(cal.get(Calendar.YEAR));
		month = String.valueOf(cal.get(Calendar.MONTH) + 1);
		if (month.length() == 1) {
			month = new StringBuilder("0").append(month).toString();
		}
		day = String.valueOf(cal.get(Calendar.DATE));
		if (day.length() == 1) {
			day = new StringBuilder("0").append(day).toString();
		}
		hour = String.valueOf(cal.get(Calendar.HOUR));
		if (hour.length() == 1) {
			hour = new StringBuilder("0").append(hour).toString();
		}
		minute = String.valueOf(cal.get(Calendar.MINUTE));
		if (minute.length() == 1) {
			minute = new StringBuilder("0").append(minute).toString();
		}
		second = String.valueOf(cal.get(Calendar.SECOND));
		if (second.length() == 1) {
			second = new StringBuilder("0").append(second).toString();
		}
		millisecond = String.valueOf(cal.get(14));
	}

	/**
	 * 获取当前的年份
	 * 
	 * @return
	 */
	public static final synchronized String getCurrentYear() {
		setCurrentDate();
		return year;
	}

	/**
	 * 获取当前的月日
	 * 
	 * @return
	 */
	public static final synchronized String getCurrentMonDay() {
		setCurrentDate();
		return (new StringBuilder(String.valueOf(month))).append(day)
				.toString();
	}

	/**
	 * 获取当前的年月日(2位的年)
	 * 
	 * @return
	 */
	public static final synchronized String getCurrentYearMonDay() {
		setCurrentDate();
		return (new StringBuilder(String.valueOf(year.substring(2))))
				.append(month).append(day).toString();
	}

	/**
	 * 获取当前的年后上一个月的月份
	 * @return
	 */
	public static final synchronized String getLastYearMon() {
		setCurrentDate();
		if (Integer.parseInt(month) == 1)
			return (new StringBuilder(String.valueOf(getLastYear()))).append(
					"12").toString();
		String lastMonth = String.valueOf(Integer.parseInt(month) - 1);
		if (lastMonth.length() == 1)
			lastMonth = (new StringBuilder("0")).append(lastMonth).toString();
		return (new StringBuilder(String.valueOf(year))).append(lastMonth) .toString();
	}

	/**
	 * 去年
	 * 
	 * @return
	 */
	public static final synchronized String getLastYear() {
		setCurrentDate();
		int lastYear = Integer.parseInt(year) - 1;
		return String.valueOf(lastYear);
	}

	/**
	 * 获取当前的月份
	 * 
	 * @return
	 */
	public static final synchronized String getCurrentMonth() {
		setCurrentDate();
		return month;
	}

	/**
	 * 获取年月
	 * 
	 * @return
	 */
	public static final synchronized String getCurrentYearMon() {
		setCurrentDate();
		return (new StringBuilder(String.valueOf(year))).append(month)
				.toString();
	}

	/**
	 * 获取当前日期并用"."号分隔
	 * 
	 * @return
	 */
	public static final synchronized String getCurrentDate() {
		setCurrentDate();
		return (new StringBuilder(String.valueOf(year))).append(".")
				.append(month).append(".").append(day).toString();
	}
	
	/**
	 * 获得当前时间。由于freemarker的日期必须有具体类型，所以使用timestamp。
	 * 
	 * @return
	 */
	public static java.sql.Timestamp now() {
		return new java.sql.Timestamp(System.currentTimeMillis());
	}

    /**
     * 获取给定时间之后几天的日期
     * @param date
     * @param dateSize
     * @return
     */
    public static Date addDate(Date date, int dateSize){
        Calendar cal = Calendar.getInstance();
        if(date == null){
        	date = new Date();
        }
        cal.setTime(date);
        int curDate = cal.get(Calendar.DATE) + dateSize;
        cal.set(Calendar.DATE, curDate);
        return cal.getTime();
    }
    
	public static void main(String[] args) {
		// Date date = DateHelper.string2Date("2010-12-12 17:26:42",
		// "yyyy-MM-dd");
		// System.out.println(date.toString());
		// System.out.println(DateHelper.date2StringCommon(new Date()));
		// Calendar cal = Calendar.getInstance();
		// System.out.println(cal.get(Calendar.MONTH) + 1);
		// System.out.println(cal.get(Calendar.YEAR));
//		Date date = DateHelper.toDayEnd(new Date());
//		Date date = DateHelper.now();
//		System.out.println(DateHelper.date2StringCommon(date));
	}
}
