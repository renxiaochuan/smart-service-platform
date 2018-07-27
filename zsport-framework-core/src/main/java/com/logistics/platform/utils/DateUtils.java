
package com.logistics.platform.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * 
 * @author dlfc
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};
	
	private static DateUtils date;
	public static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
	
	public static String CHINA_DATE_PATERN = "yyyy年MM月dd日 HH时MM分SS秒";

	public static String MODIFIED_PATERN = "yyyy-MM-dd HH:mm:ss";

	public static String DATE_Y_M_D_PATERN = "yyyy-MM-dd";

	public static String DATE_YMD_PATERN = "yyyy-MM-dd";

	public static String CHINA_DATE_YMD = "yyyy年MM月dd日";

	public static String DATE_MS_PATERN = "yyyyMMddHHmmssSSS";

	private static int getDateField(Date date, int field) {
		Calendar c = getCalendar();
		c.setTime(date);
		return c.get(field);
	}

	public static int getYearsBetweenDate(Date begin, Date end) {
		int bYear = getDateField(begin, Calendar.YEAR);
		int eYear = getDateField(end, Calendar.YEAR);
		return eYear - bYear;
	}

	public static int getMonthsBetweenDate(Date begin, Date end) {
		int bMonth = getDateField(begin, Calendar.MONTH);
		int eMonth = getDateField(end, Calendar.MONTH);
		return eMonth - bMonth;
	}

	public static int getWeeksBetweenDate(Date begin, Date end) {
		int bWeek = getDateField(begin, Calendar.WEEK_OF_YEAR);
		int eWeek = getDateField(end, Calendar.WEEK_OF_YEAR);
		return eWeek - bWeek;
	}

	public static int getDaysBetweenDate(Date begin, Date end) {
		return (int) ((end.getTime()-begin.getTime())/(1000 * 60 * 60 * 24));
	}

	public static void main(String args[]) {
		System.out.println(getSpecficDateStart(new Date(), 288));
	}

	/**
	 * 获取date年后的amount年的第一天的开始时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficYearStart(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, amount);
		cal.set(Calendar.DAY_OF_YEAR, 1);
		return getStartDate(cal.getTime());
	}

	/**
	 * 获取date年后的amount年的最后一天的终止时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficYearEnd(Date date, int amount) {
		Date temp = getStartDate(getSpecficYearStart(date, amount + 1));
		Calendar cal = Calendar.getInstance();
		cal.setTime(temp);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return getFinallyDate(cal.getTime());
	}

	/**
	 * 获取date月后的amount月的第一天的开始时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficMonthStart(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, amount);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return getStartDate(cal.getTime());
	}

	/**
	 * 获取当前自然月后的amount月的最后一天的终止时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficMonthEnd(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getSpecficMonthStart(date, amount + 1));
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return getFinallyDate(cal.getTime());
	}

	/**
	 * 获取date周后的第amount周的开始时间（这里星期一为一周的开始）
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficWeekStart(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(Calendar.MONDAY); /* 设置一周的第一天为星期一 */
		cal.add(Calendar.WEEK_OF_MONTH, amount);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return getStartDate(cal.getTime());
	}

	/**
	 * 获取date周后的第amount周的最后时间（这里星期日为一周的最后一天）
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficWeekEnd(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY); /* 设置一周的第一天为星期一 */
		cal.add(Calendar.WEEK_OF_MONTH, amount);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return getFinallyDate(cal.getTime());
	}

	public static Date getSpecficDateStart(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, amount);
		return getStartDate(cal.getTime());
	}

	/**
	 * 得到指定日期的一天的的最后时刻23:59:59
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFinallyDate(Date date) {
		String temp = format.format(date);
		temp += " 23:59:59";

		try {
			return format1.parse(temp);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 得到指定日期的一天的开始时刻00:00:00
	 * 
	 * @param date
	 * @return
	 */
	public static Date getStartDate(Date date) {
		String temp = format.format(date);
		temp += " 00:00:00";

		try {
			return format1.parse(temp);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * @param date
	 *            指定比较日期
	 * @param compareDate
	 * @return
	 */
	public static boolean isInDate(Date date, Date compareDate) {
		if (compareDate.after(getStartDate(date))
				&& compareDate.before(getFinallyDate(date))) {
			return true;
		} else {
			return false;
		}

	}
	
	/**
	 * 获取两个时间的差值秒
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Integer getSecondBetweenDate(Date d1,Date d2){
		Long second=(d2.getTime()-d1.getTime())/1000;
		return second.intValue();
	}

	private static Calendar getCalendar() {
		return Calendar.getInstance();
	}

	public static DateUtils getDateInstance() {
		if (date == null) {
			date = new DateUtils();
		}
		return date;
	}
	/**
	 * 获得系统时间
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getSysDateStr(String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(DateUtils.getSynchTime());
	}
	
	public static Date strToDate(String str) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			return format.parse(str);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String strParseToStr(String str ,String dateFrom ,String dateEnd) throws Exception {
		try {
			SimpleDateFormat format = new SimpleDateFormat(dateFrom);
			Date date =  format.parse(str);
			format = new SimpleDateFormat(dateEnd);
			return format.format(date);
		} catch (Exception e) {
			return "";
		}
	}
	
	public static Date strToDate(String str,String pattern) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			return format.parse(str);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Integer getMinute() {
		try {
			SimpleDateFormat format = new SimpleDateFormat("mm");
			return Integer.parseInt(format.format(new Date()));
		} catch (Exception e) {
			return null;
		}
	}
	public static Integer getSecond() {
		try {
			SimpleDateFormat format = new SimpleDateFormat("ss");
			return Integer.parseInt(format.format(new Date()));
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获得当前年份
	 * 
	 * @return
	 */
	public static String getCurrentYear() {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy");
			return format.format(new Date());
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 获得当前月份
	 * 
	 * @return
	 */
	public static String getCurrentMonth() {
		try {
			SimpleDateFormat format = new SimpleDateFormat("MM");
			return format.format(new Date());
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获得当前天
	 * 
	 * @return
	 */
	public static String getCurrentDay() {
		try {
			SimpleDateFormat format = new SimpleDateFormat("DD");
			return format.format(new Date());
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 取得指定日期的星期的中文数字
	 * @param date 指定日期
	 * @return 星期的中文数字
	 */
	public static String getWeekDayName(Date date) {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int dayInt = calendar.get(Calendar.DAY_OF_WEEK);
			String weekDayName = "";
			switch (dayInt) {
			case 1:
				weekDayName = "星期日";
				break;
			case 2:
				weekDayName = "星期一";
				break;
			case 3:
				weekDayName = "星期二";
				break;
			case 4:
				weekDayName = "星期三";
				break;
			case 5:
				weekDayName = "星期四";
				break;
			case 6:
				weekDayName = "星期五";
				break;
			case 7:
				weekDayName = "星期六";
				break;
			default:
				break;
			}
			return weekDayName;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 比较两个字符串日期的大小
	 * 
	 * @param first
	 * @param second
	 * @return
	 * @throws Exception
	 */
	public static long strDateCompare(String first,String second) throws Exception {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dfirst = format.parse(first);
		Date dsecond = format.parse(second);
		
		return dfirst.getTime() - dsecond.getTime();
	}
	
	/**
	 * 获得上周日期
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String getLastWeek(String date) throws Exception {
		
		if(date == null || "".equals(date)) {
			date = getSysDateStr(DATE_Y_M_D_PATERN);
		}
		
		SimpleDateFormat df=new SimpleDateFormat(DATE_Y_M_D_PATERN); 
		
		Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。    
		cal.setTime(strToDate(date + " 00:00:00",MODIFIED_PATERN));
		
		cal.add(Calendar.DAY_OF_MONTH, -7);//取当前日期的前一天.    

		//通过格式化输出日期    

		return df.format(cal.getTime());  

	}
	
	/**
	 * 获得上月的今天日期
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String getLastMonth(String date) throws Exception {
		
		if(date == null || "".equals(date)) {
			date = getSysDateStr(DATE_Y_M_D_PATERN);
		}
		
		SimpleDateFormat df=new SimpleDateFormat(DATE_Y_M_D_PATERN); 
		
		Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。    
		cal.setTime(strToDate(date + " 00:00:00",MODIFIED_PATERN));
		
		cal.add(Calendar.MONTH, -1);//取当前日期的前一天.    

		//通过格式化输出日期    

		return df.format(cal.getTime());  

	}
	
	/**
	 * 获得上三个月的今天日期
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String getLastThreeMonth(String date) throws Exception {
		
		if(date == null || "".equals(date)) {
			date = getSysDateStr(DATE_Y_M_D_PATERN);
		}
		
		SimpleDateFormat df=new SimpleDateFormat(DATE_Y_M_D_PATERN); 
		
		Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。    
		cal.setTime(strToDate(date + " 00:00:00",MODIFIED_PATERN));
		
		cal.add(Calendar.MONTH, -3);//取当前日期的前一天.    

		//通过格式化输出日期    

		return df.format(cal.getTime());  

	}
	/**
	 * 获得最近70年份集合
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String,String> getLastSeventyYears() throws Exception {

		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("", "年");
		
		int currYear = Integer.valueOf(getCurrentYear());
		
		for(int i=0;i<70;i++) {
			map.put(String.valueOf(currYear), String.valueOf(currYear));
			currYear--;
		}
		
		return map;
	}
	
	/**
	 * 获得12月集合
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String,String> getTwelveMonth() throws Exception {

		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("", "月");
		
		for(int i=1;i<=12;i++) {
			String month = "";
			if(i<10) {
				month = "0" + String.valueOf(i);
			} else {
				month = String.valueOf(i);
			}
			map.put(month, String.valueOf(i));
		}
		
		return map;
	}
	
	/**
	 * 获得12月集合
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String,String> getHourMap() throws Exception {

		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		
		for(int i=1;i<=24;i++) {
			String hour = "";
			if(i<10) {
				hour = "0" + String.valueOf(i);
			} else {
				hour = String.valueOf(i);
			}
			map.put(hour, hour);
		}
		
		return map;
	}
	
	/**
	 * 获得12月集合
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String,String> getMinuteMap() throws Exception {

		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		
		for(int i=1;i<=60;i++) {
			String minute = "";
			if(i<10) {
				minute = "0" + String.valueOf(i);
			} else {
				minute = String.valueOf(i);
			}
			map.put(minute, minute);
		}
		
		return map;
	}
	
	/**
	 * 获得12月集合
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String,String> getSecondMap() throws Exception {

		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		
		for(int i=1;i<=60;i++) {
			String second = "";
			if(i<10) {
				second = "0" + String.valueOf(i);
			} else {
				second = String.valueOf(i);
			}
			map.put(second, second);
		}
		
		return map;
	}
	
	public static int getDayOfWeekBySiteId(String siteId) throws ParseException {
		SimpleDateFormat formatNo = new SimpleDateFormat("yyyyMMdd");
		Date recruitDate = formatNo.parse(siteId
				.substring(0, 8));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(recruitDate);
		
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
	
	public static String addDay(String date,int day) {
		
		SimpleDateFormat df=new SimpleDateFormat(DATE_Y_M_D_PATERN); 
		
		Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。    
		cal.setTime(strToDate(date + " 00:00:00",MODIFIED_PATERN));
		
		cal.add(Calendar.DAY_OF_MONTH, day);//取当前日期的前一天.    

		//通过格式化输出日期    
		return df.format(cal.getTime());  
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}

	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}

	/**
	 * 日期型字符串转化为日期 格式 { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
	 * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy.MM.dd",
	 * "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * 
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (24 * 60 * 60 * 1000);
	}

	/**
	 * 获取过去的小时
	 * 
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (60 * 60 * 1000);
	}

	/**
	 * 获取过去的分钟
	 * 
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (60 * 1000);
	}

	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * 
	 * @param timeMillis
	 * @return
	 */
	public static String formatDateTime(long timeMillis) {
		long day = timeMillis / (24 * 60 * 60 * 1000);
		long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
		long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
		return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
	}

	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}

	public static String dateToStr(Date date, String pattern) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			return format.format(date);
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * 初始化当前时间（到天）
	 * 
	 * @return
	 */
	public static Calendar getNowCal() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getSynchTime());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}
	
	

	/**
	 * 获取服务器时间
	 * 
	 * @return
	 */
	public static Date getSynchTime() {
		Date date = new Date();
		return date;
	}
}
