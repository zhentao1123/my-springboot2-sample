package com.example.demo.util.comm;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;

public class DateUtil extends org.apache.commons.lang3.time.DateUtils {
	public static final String FORMAT_DATE_DASH = "yyyy-MM-dd";

	public static final String FORMAT_DATE_WORD = "yyyy年MM月dd日";

	private static int val = -1;

	private static int curval = 0;

	public static String date2String(Date date) throws Exception {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	public static String date2String(Date date, String dateFormat)
			throws Exception {
		return new SimpleDateFormat(dateFormat).format(date);
	}

	public static Date string2Date(String str) throws Exception {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
	}

	public static Date string2Date(String str, String dateFormat) throws Exception {
		return new SimpleDateFormat(dateFormat).parse(str);
	}

	/**
	 * 获取当前日期格式字符串 , 格式自定义
	 * 
	 * @param formatString格式字符串 如: yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getNow(String formatString) {
		if (StringUtils.isBlank(formatString)) {
			formatString = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat formatter_f = new SimpleDateFormat(formatString);
		Date currentTime_f = new Date(); // 得到当前系统时间
		String new_date_f = formatter_f.format(currentTime_f); // 将日期时间格式化
		return new_date_f;

	}

	public static String getTimeSS(Date date) {
		SimpleDateFormat formatter_f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = formatter_f.format(date);
		return str;
	}

	public static String getTime(Date date) {
		SimpleDateFormat formatter_f = new SimpleDateFormat("yyyy-MM-dd");
		String str = formatter_f.format(date);
		return str;
	}

	/**
	 * 计算本月共多少天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getCountDayForMonth(int year, int month) {
		if (month > 12)
			return 0;
		int[] monDays = new int[] { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		if (((year) % 4 == 0 && (year) % 100 != 0) || (year) % 400 == 0)
			monDays[2]++;

		return monDays[month];
	}
	
	/**
	 * 当前日期距离某日的天数，排除假日
	 * @param dateStr
	 * @return
	 */
	public static int daysBetweenNowWithoutHoliday(String dateStr){
		Integer result = 0;
		try {
			Calendar nowCal = Calendar.getInstance();
			nowCal.setTime(getDateWithoutTime(new Date()));
			
			Calendar targetCal = Calendar.getInstance();
			targetCal.setTime(string2Date(dateStr, "yyyy-MM-dd"));
			
			Integer compare = targetCal.compareTo(nowCal);
			if(compare<1){//目标日期早于或等于当前日期，直接返回-1或0
				return compare;
			}else{//目标日期晚于当前日期，计算除去节假日的天数差
				//result=1; 当日不算
				nowCal.add(Calendar.DAY_OF_MONTH, 1);
				while(targetCal.compareTo(nowCal)==1){//目标天晚于当天
					//System.out.println(nowCal.getTime());
					if(!isHoliday(nowCal)){
						result++;
					}
					nowCal.add(Calendar.DAY_OF_MONTH, 1);
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 获取日期是否是节假日
	 * @param cal
	 * @return
	 */
	public static boolean isHoliday(Calendar cal){
		if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
			//System.out.println(cal.getTime()+" is holiday");
			return true;
		}else{
			//System.out.println(cal.getTime()+" is not holiday");
			return false;
		}
	}

	public static Date getDateWithoutTime(Date date) throws Exception{
		return string2Date(date2String(date, "yyyy-MM-dd"), "yyyy-MM-dd");
	}
	
	/**
	 * 根据生日获取年龄
	 * @param birthDay
	 * @return
	 * @throws Exception
	 */
	public static Integer getAge(Date birthDay) throws Exception {  
        Calendar cal = Calendar.getInstance();  
  
        if (cal.before(birthDay)) {  
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");  
        }  
        int yearNow = cal.get(Calendar.YEAR);  
        int monthNow = cal.get(Calendar.MONTH);  
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);  
        cal.setTime(birthDay);  
  
        int yearBirth = cal.get(Calendar.YEAR);  
        int monthBirth = cal.get(Calendar.MONTH);  
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);  
  
        int age = yearNow - yearBirth;  
  
        if (monthNow <= monthBirth) {  
            if (monthNow == monthBirth) {  
                if (dayOfMonthNow < dayOfMonthBirth) age--;  
            }else{  
                age--;  
            }  
        }  
        return age;  
    } 
	
	/**
	 * 获取当前日期的字符串格式 2010-11-12
	 * 
	 * @return
	 */
	public static String getNow() {
		return getNow("yyyy-MM-dd");
	}

	/**
	 * 返回时间的毫秒值
	 * 
	 * @return
	 */
	public static long nowTimeInMillis() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.getTimeInMillis();
	}

	/**
	 * 得到当天 零点
	 * 
	 * @param date
	 * @return
	 */
	public static Date getZero(Date date) {
		SimpleDateFormat formatter_f = new SimpleDateFormat("yyyy-MM-dd");
		String new_date_f = formatter_f.format(date); // 将日期时间格式化
		new_date_f = new_date_f + " 00:00:00";
		return parseDate(null, new_date_f);
	}

	/**
	 * 得到当天 深夜(午夜12点)
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMidnight(Date date) {
		SimpleDateFormat formatter_f = new SimpleDateFormat("yyyy-MM-dd");
		String new_date_f = formatter_f.format(date); // 将日期时间格式化
		new_date_f = new_date_f + " 23:59:59";
		return parseDate(null, new_date_f);
	}

	/**
	 * 获取指定日期月份的最后一天
	 * 
	 * @param date日期对象
	 * @return
	 */
	public static Date getLastDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, value);
		return cal.getTime();
	}

	/**
	 * 取得指定日期N天后的日期
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
	}
	
	/**
	 * 获取推移几天后的日期，可以加几天或减几天
	 * 输入和输出都为"yyyy-MM-dd"格式
	 * @param origDate
	 * @param days整数加几天，负数减几天
	 * @return
	 */
	public static String dateAddDaysFormatDateStr(String origDateStr, int days){
		try {
			Date origDate = new SimpleDateFormat("yyyy-MM-dd").parse(origDateStr);
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(origDate);
			gc.add(5, days);
			Date newDate = gc.getTime();
			return new SimpleDateFormat("yyyy-MM-dd").format(newDate);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 计算两个日期之间相差的天数,严格,计算相差秒数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int daysBetween(Date date1, Date date2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		long time1 = cal.getTimeInMillis();
		cal.setTime(date2);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 计算两个时间相差的天数
	 * 
	 * @author wangbh
	 * @param beginTime开始时间
	 * @param endTime结束时间
	 * ***/
	public static Long getDays(String beginTime, String endTime) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		long day = 0l;
		try {
			Date beginDate = df.parse(beginTime);
			Date endDate = df.parse(endTime);
			long diff = endDate.getTime() - beginDate.getTime();
			day = diff / (1000 * 60 * 60 * 24);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day;
	}
	
	/**
	 * 计算两个时间相差的天数
	 * 
	 * @author wangbh
	 * @param beginTime开始时间
	 * @param endTime结束时间
	 * ***/
	public static Long getDays(String beginTime, String endTime, String format) {
		DateFormat df = new SimpleDateFormat(format);
		long day = 0l;
		try {
			Date beginDate = df.parse(beginTime);
			Date endDate = df.parse(endTime);
			long diff = endDate.getTime() - beginDate.getTime();
			day = diff / (1000 * 60 * 60 * 24);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day;
	}

	/**
	 * 计算两个日期之间相差的天数,只关心天
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long daysOfTwo(Date fDate, Date oDate) {
		Calendar aCalendar = Calendar.getInstance();

		aCalendar.setTime(fDate);
		Calendar bCalendar = Calendar.getInstance();
		bCalendar.setTime(oDate);

		long atimeInMillis = aCalendar.getTimeInMillis();
		long btimeInMillis = bCalendar.getTimeInMillis();
		long day = (btimeInMillis - atimeInMillis) / (24 * 60 * 60 * 1000);
		return day;
	}
	
	public static long natureDaysOfTwo(final Date fDate, final Date oDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		Date fDate_ = sdf.parse(sdf.format(fDate));
		Date oDate_ = sdf.parse(sdf.format(oDate));   
        Calendar cal = Calendar.getInstance();    
        cal.setTime(fDate_);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(oDate_);    
        long time2 = cal.getTimeInMillis();         
        long between_days = (time2-time1) / (1000*3600*24);  
    	return Long.parseLong(String.valueOf(between_days));  
    }

	/**
	 * @param date1需要比较的时间 不能为空(null),需要正确的日期格式 ,如：2009-09-12
	 * @param date2被比较的时间 为空(null)则为当前时间
	 * 
	 * @return 举例：
	 * compareDate("2009-09-12", null);//比较天
	 * compareDate("2009-09-12", null);//比较月 
	 * compareDate("2009-09-12", null);//比较年
	 */
	public static int compareDate(Date startDay, Date endDay) {
		boolean issx = startDay.after(endDay);
		int n = 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		Date[] ds = exchangeDate(startDay, endDay);
		c1.setTime(ds[0]);
		c2.setTime(ds[1]);
		while (!c1.after(c2)) { // 循环对比，直到相等，n 就是所要的结果
			c1.add(Calendar.DATE, 1);
			if (issx) {
				n++;
			} else {
				n--;
			}
		}
		return n;
	}

	/**
	 * 将Timestamp类型的日期转换为系统参数定义的格式的字符串。
	 * 
	 * @param aTs_Datetime需要转换的日期。
	 * @return 转换后符合给定格式的日期字符串
	 */
	public static String format(Date aTs_Datetime) {
		return format(aTs_Datetime, "yyyy-MM-dd");
	}

	/**
	 * 将Date类型的日期转换为系统参数定义的格式的字符串。
	 * 
	 * @param aTs_Datetime
	 * @param as_Pattern
	 * @return
	 */
	public static String format(Date aTs_Datetime, String as_Pattern) {
		if (aTs_Datetime == null || as_Pattern == null)
			return null;

		SimpleDateFormat dateFromat = new SimpleDateFormat();
		dateFromat.applyPattern(as_Pattern);
		return dateFromat.format(aTs_Datetime);
	}

	/**
	 * Parse a string and return the date value in the specified format
	 * 
	 * @param strFormat
	 * @param dateValue
	 * @return
	 * @throws ParseException
	 * @throws Exception
	 */
	public static Date parseDate(String strFormat, String dateValue) {
		if (dateValue == null)
			return null;

		if (strFormat == null) {
			strFormat = "yyyy-MM-dd HH:mm:ss";
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat(strFormat);
		Date newDate = null;
		try {
			newDate = dateFormat.parse(dateValue);
		} catch (ParseException pe) {
			newDate = null;
		}
		return newDate;
	}

	/**
	 * 比较日期先后,并摆正顺序 不是引用类型?函数外又还原...
	 */
	public static Date[] exchangeDate(Date startDate, Date endDate) {
		Date datetemp;
		if (startDate.after(endDate)) {
			datetemp = endDate;
			endDate = startDate;
			startDate = datetemp;
		}
		return new Date[] { startDate, endDate };
	}

	/**
	 * 传入时间字符串,加一天后返回Date
	 * 
	 * @param date时间 格式 YYYY-MM-DD
	 * @return
	 */
	public static Date addDate(String date) {
		if (date == null) {
			return null;
		}

		Date tempDate = parseDate("yyyy-MM-dd", date);
		String year = format(tempDate, "yyyy");
		String month = format(tempDate, "MM");
		String day = format(tempDate, "dd");
		GregorianCalendar calendar = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));

		calendar.add(GregorianCalendar.DATE, 1);
		return calendar.getTime();
	}

	/**
	 * 获得昨天时间，格式自定义
	 * 
	 * @param format
	 * @return
	 */
	public static String getYesterdayDate(String format) {
		Calendar day = Calendar.getInstance();
		day.add(Calendar.DATE, -1);
		SimpleDateFormat sdf = new SimpleDateFormat(format);// "yyyy-MM-dd"
		String date = sdf.format(day.getTime());
		return date;
	}

	/**
	 * 实现给定某日期，判断是星期几 date:必须yyyy-MM-dd格式
	 */
	public static String getWeekday(String date) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdw = new SimpleDateFormat("E");
		Date d = null;
		try {
			d = sd.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			d = new Date();
		}
		return sdw.format(d);
	}

	/**
	 * 实现给定某日期，判断是星期几 date:必须yyyy-MM-dd格式
	 */
	public static String getWeekday(Date date) {
		SimpleDateFormat sdw = new SimpleDateFormat("E");
		return sdw.format(date);
	}

	/**
	 * 实现给定某日期，判断是星期几 date:必须yyyy-MM-dd格式
	 */
	public static String getWeekdayShort(Date date) {
		String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}
	
	/**
	 * 实现给定某日期，判断是星期几(英文) date:必须yyyy-MM-dd格式
	 */
	public static String getWeekdayShort_EN(Date date) {
		String[] weekDays = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}
	
	/**
	 * 实现给定某日期，判断是星期几 date:必须yyyy-MM-dd格式
	 */
	public static int getWeekdayInt(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w <= 0)
			w = 7;
		return w;
	}
	
	
	/**
	 * 实现给定某日期，判断是星期几 date:必须yyyy-MM-dd格式
	 */
	public static int getMonthdayInt(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int w = cal.get(Calendar.DAY_OF_MONTH);
		return w;
	}
	

	/**
	 * 获得当前日期与本周一相差的天数
	 */
	private static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == 1) {
			return -6;
		} else {
			return 2 - dayOfWeek;
		}
	}

	/**
	 * 获取某年某月第一天是星期几,星期天为0,星期六为6
	 * 
	 * @return
	 */
	public static int getFirstDayWeek(int year, int month) {
		String wd = getWeekday(year + "-" + month + "-1");

		if ("星期日".equals(wd) || "SUNDAY".equalsIgnoreCase(wd.toUpperCase())) {
			return 0;
		}
		if ("星期一".equals(wd) || "MONDAY".equalsIgnoreCase(wd.toUpperCase())) {
			return 1;
		}
		if ("星期二".equals(wd) || "TUESDAY".equalsIgnoreCase(wd.toUpperCase())) {
			return 2;
		}
		if ("星期三".equals(wd) || "Wednesday".equalsIgnoreCase(wd.toUpperCase())) {
			return 3;
		}
		if ("星期四".equals(wd) || "THURSDAY".equalsIgnoreCase(wd.toUpperCase())) {
			return 4;
		}
		if ("星期五".equals(wd) || "FRIDAY".equalsIgnoreCase(wd.toUpperCase())) {
			return 5;
		}
		if ("星期六".equals(wd) || "SATURDAY".equalsIgnoreCase(wd.toUpperCase())) {
			return 6;
		}
		return 0;
	}

	/**
	 * 根据当前时间,返回一个顺序的数字字符串
	 * 
	 * @return
	 */
	public static synchronized String getTransactionIdByTime() {
		String st = String.valueOf(System.currentTimeMillis());
		curval = val + 1;
		val = curval;
		if (curval < 10) {
			return st + "00" + String.valueOf(curval);
		} else if (curval < 100) {
			return st + "0" + String.valueOf(curval);
		} else {
			if (curval < 999) {
				return st + String.valueOf(curval);
			} else {
				val = -1;
				return st + String.valueOf(curval);
			}
		}
	}

	public static String timestamp() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(new Date());
	}

	/**
	 * 获得本周星期一的日期
	 */
	public static String getCurrentMonday(String format) {
		int mondayPlus = getMondayPlus();
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(Calendar.DATE, mondayPlus);
		SimpleDateFormat sdf = new SimpleDateFormat(format);// "yyyy-MM-dd"
		String date = sdf.format(currentDate.getTime());
		return date;
	}

	/**
	 * 获得上周星期一的日期
	 */
	public static String getPreviousMonday(String format) {
		int mondayPlus = getMondayPlus();
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(GregorianCalendar.DATE, mondayPlus - 7);
		SimpleDateFormat sdf = new SimpleDateFormat(format);// "yyyy-MM-dd"
		String date = sdf.format(currentDate.getTime());
		return date;
	}

	/**
	 * 获得下周星期一的日期
	 */
	public static String getNextMonday(String format) {
		int mondayPlus = getMondayPlus();
		// GregorianCalendar currentDate = new GregorianCalendar();
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
		SimpleDateFormat sdf = new SimpleDateFormat(format);// "yyyy-MM-dd"
		String date = sdf.format(currentDate.getTime());
		return date;
	}

	/**
	 * 按日加
	 * 
	 * @param value
	 * @return
	 */
	public static Date addDay(int value) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DAY_OF_YEAR, value);
		return now.getTime();
	}

	/**
	 * 按日加,指定日期
	 * 
	 * @param date
	 * @param value
	 * @return
	 */
	public static Date addDay(Date date, int value) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.DAY_OF_YEAR, value);
		return now.getTime();
	}

	/**
	 * 按月加
	 * 
	 * @param value
	 * @return
	 */
	public static Date addMonth(int value) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH, value);
		return now.getTime();
	}

	/**
	 * 按月加,指定日期
	 * 
	 * @param date
	 * @param value
	 * @return
	 */
	public static Date addMonth(Date date, int value) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.MONTH, value);
		return now.getTime();
	}

	/**
	 * 按年加
	 * 
	 * @param value
	 * @return
	 */
	public static Date addYear(int value) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.YEAR, value);
		return now.getTime();
	}

	/**
	 * 按年加,指定日期
	 * 
	 * @param date
	 * @param value
	 * @return
	 */
	public static Date addYear(Date date, int value) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.YEAR, value);
		return now.getTime();
	}

	/**
	 * 按小时加
	 * 
	 * @param value
	 * @return
	 */
	public static Date addHour(int value) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.HOUR_OF_DAY, value);
		return now.getTime();
	}

	/**
	 * 按小时加,指定日期
	 * 
	 * @param date
	 * @param value
	 * @return
	 */
	public static Date addHour(Date date, int value) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.HOUR_OF_DAY, value);
		return now.getTime();
	}

	/**
	 * 按分钟加
	 * 
	 * @param value
	 * @return
	 */
	public static Date addMinute(int value) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, value);
		return now.getTime();
	}

	/**
	 * 按分钟加,指定日期
	 * 
	 * @param date
	 * @param value
	 * @return
	 */
	public static Date addMinute(Date date, int value) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.MINUTE, value);
		return now.getTime();
	}
	
	public static int getYear(Date date)
	{
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		return now.get(Calendar.YEAR);
	}
	
	public static int getMonth(Date date)
	{
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		return now.get(Calendar.MONTH);
	}

	public static int getDayOfMonth(Date date)
	{
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		return now.get(Calendar.DAY_OF_MONTH);
	}
	
	public static int getMaxDaysOfMonth(Date date)
	{
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		return now.getActualMaximum(Calendar.DATE);
	}
	
	public static int getHour(Date date)
	{
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		return now.get(Calendar.HOUR_OF_DAY);
	}
	
	public static int getMinutes(Date date)
	{
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		return now.get(Calendar.MINUTE);
	}
	
	
	/**
	 * 年份
	 * 
	 * @return
	 */
	public static int year() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.YEAR);
	}

	/**
	 * 月份
	 * 
	 * @return
	 */
	public static int month() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.MONTH) + 1;
	}

	/**
	 * 日(号)
	 * 
	 * @return
	 */
	public static int day() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 小时(点)
	 * 
	 * @return
	 */
	public static int hour() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 分钟
	 * 
	 * @return
	 */
	public static int minute() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.MINUTE);
	}

	/**
	 * 秒
	 * 
	 * @return
	 */
	public static int second() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.SECOND);
	}

	/**
	 * 星期几(礼拜几)
	 * 
	 * @return
	 */
	public static int weekday() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.DAY_OF_WEEK) - 1;
	}
	
	/**
	 * 当月的几号
	 * @return
	 */
	public static int monthday()
	{
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 是上午吗?
	 * 
	 * @return
	 */
	public static boolean isAm() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.AM_PM) == 0;
	}

	/**
	 * 是下午吗?
	 * 
	 * @return
	 */
	public static boolean isPm() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.AM_PM) == 1;
	}

	/**
	 * 获取某年某月第一天是星期几,星期天为0,星期六为6
	 * 
	 * @return
	 */
	public static int parseWeek(String weekString) {
		if ("星期日".equals(weekString) || "SUNDAY".equalsIgnoreCase(weekString.toUpperCase())) {
			return 0;
		}
		if ("星期一".equals(weekString) || "MONDAY".equalsIgnoreCase(weekString.toUpperCase())) {
			return 1;
		}
		if ("星期二".equals(weekString) || "TUESDAY".equalsIgnoreCase(weekString.toUpperCase())) {
			return 2;
		}
		if ("星期三".equals(weekString) || "Wednesday".equalsIgnoreCase(weekString.toUpperCase())) {
			return 3;
		}
		if ("星期四".equals(weekString) || "THURSDAY".equalsIgnoreCase(weekString.toUpperCase())) {
			return 4;
		}
		if ("星期五".equals(weekString) || "FRIDAY".equalsIgnoreCase(weekString.toUpperCase())) {
			return 5;
		}
		if ("星期六".equals(weekString) || "SATURDAY".equalsIgnoreCase(weekString.toUpperCase())) {
			return 6;
		}
		return 0;
	}
	
	/**
	 * 判断时间字符串是否是合法日期格式
	 * 
	 * @param str
	 * @param dateFormat
	 * @return
	 */
	public static boolean isValidDate(String str, String dateFormat) {
		boolean convertSuccess = true;
		// 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		try {
			// 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			format.setLenient(false);
			format.parse(str);
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			convertSuccess = false;
		}
		return convertSuccess;
	}
}