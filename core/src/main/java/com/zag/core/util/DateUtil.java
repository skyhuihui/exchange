package com.zag.core.util;

import com.zag.core.enums.Weeks;
import org.apache.commons.lang3.StringUtils;
import org.codelogger.utils.DateUtils;
import org.codelogger.utils.exceptions.DateException;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author stone
 */
public final class DateUtil {

    public static final long SECOND = 1000;
    public static final long MINUTE = 60 * SECOND;
    public static final long HOUR = 60 * MINUTE;
    public static final long TWOHOUR = HOUR * 2;
    public static final long DAY = 24 * HOUR;
    public static final long DAY3 = DAY * 3;
    public static final long DAY7 = DAY * 7;
    public static final long DAY15 = DAY * 15;
    public static final long DAY30 = DAY * 30;

    public static final DateFormat SQL99_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

    public static final DateFormat DATE_NUMBER_FORMAT = new SimpleDateFormat("yyyyMMdd");

    private DateUtil() {
    }
    
    public static Timestamp now(){
    	return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 获取当前time
     * @return
     */
    public static Time currentTime(){
        Calendar calendar = Calendar.getInstance();
        return new Time(calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),calendar.get(Calendar.SECOND));
    }

    public static String durationFormat(long duration) {
        if (duration >= DAY)
            return duration / DAY + "天";
        else if (duration >= HOUR)
            return duration / HOUR + "小时";
        else if (duration >= MINUTE)
            return duration / MINUTE + "分钟";
        else
            return duration / SECOND + "秒";
    }

    public static java.util.Date today() {
        return new java.util.Date(System.currentTimeMillis());
    }

    public static int betweenDays(Date from, Date to) {
        long duration = to.getTime() - from.getTime();
        return (int) ((duration / DAY) + (duration % DAY == 0 ? 0 : 1));
    }

    public static java.util.Date weekAgo() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -7);
        return new java.util.Date(c.getTimeInMillis());
    }

    //多少天以前
    public static long beforeDays(int days) {
        days = days < 0 ? days : -days;
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, days);
        return c.getTimeInMillis();
    }

    public static long afterDays(int days) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, days);
        return c.getTimeInMillis();
    }

    /**
     * 按中国习惯获取周  周一 返回1； 周日 返回 7
     *
     * @param timeMillis
     * @return
     */
    public static int getWeek1to7(long timeMillis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeMillis);
        int week = c.get(Calendar.DAY_OF_WEEK);
        if (week == 1)
            return 7;
        else
            return week - 1;
    }

    /**
     * 取周名字
     * @param week
     * @return
     */
    public static String getWeekName(int week) {
        switch (week) {
            case 1:
                return "周一";
            case 2:
                return "周二";
            case 3:
                return "周三";
            case 4:
                return "周四";
            case 5:
                return "周五";
            case 6:
                return "周六";
            case 7:
                return "周日";
        }
        return "";
    }

    /**
     * 计算时间的分钟数
     *
     * @param timeMillis
     * @return
     */
    public static int getTimeInMinute(long timeMillis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeMillis);
        return c.get(Calendar.HOUR_OF_DAY) * 60 + c.get(Calendar.MINUTE);
    }

    /**
     * 获取时间戳年
     *
     * @param ts
     * @param calendarType Calendar.YEAR | Calendar.MONTH | Calendar.DAY_OF_MONTH
     * @return
     * @date 下午5:46:28  2014年12月15日
     */
    public static int getTime(long ts, int calendarType) {
        Calendar calDate = Calendar.getInstance();
        calDate.setTime(new Date(ts));
        return calDate.get(calendarType);
    }

    public static String formatTo24Hour(int minute) {
        if (minute <= 0) {
            return "00:00";
        }
        if (minute >= 24 * 60) {
            return "24:00";
        }
        return prefixDecade(minute / 60) + ":" + prefixDecade(minute % 60);
    }

    private static String prefixDecade(int num) {
        return num >= 10 ? "" + num : "0" + num;
    }

    /**
     * 获取alipay 的 it_b_pay<br>
     * 当前时间到limitTime的分钟数,单位(m)
     *
     * @param limitTime
     * @return
     * @date 2015年7月6日
     */
    public static String getMinuteItBPay(Long limitTime) {
        Calendar calendar = Calendar.getInstance();
        Long nowTime = calendar.getTimeInMillis();
        Long betweenMinute = (limitTime - nowTime) / 1000 / 60;
        return betweenMinute + "m";
    }

    /**
     * 获取当前日期,返回数据格式:yyyyMMdd
     *
     * @return
     * @date 2015年7月10日
     */
    public static String getNowDate() {
        return DateUtils.getDateFormat(new Date(), "yyyy-MM-dd");
    }

    /**
     * 获取当前日期
     * @author lei
     * @date 2015年9月29日
     */
    public static String getNowFmtDatetime(String fmt) {
        return DateUtils.getDateFormat(new Date(), fmt);
    }

    /**
     * 获取时间戳
     *
     * @param fmtDateStr 格式：yyyy-MM-dd HH:mm:ss
     * @return
     * @throws DateException
     * @date 2015年7月11日
     */
    public static Long getTime(String fmtDateStr) {
        return DateUtils.getDateFromString(fmtDateStr).getTime();
    }

    /**
     * 判断当前时间是否在今天之内，如果是返回：true,否则返回false
     *
     * @param currentTime(Long)
     * @return
     */
    public static boolean isToday(Long currentTime) {
        return StringUtils.equals(new java.util.Date(System.currentTimeMillis()).toString(),
            new java.util.Date(currentTime).toString());
    }

    /**
     * 判断当前时间是否在一个月内，如果是返回：true,否则返回false
     *
     * @param currentTime(Long)
     * @return
     */
    public static boolean isMonth(Long currentTime) {
        Calendar min = new GregorianCalendar();
        min.add(Calendar.DATE, -30);
        return currentTime >= min.getTimeInMillis();
    }

    public static Long getTodayStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime().getTime();
    }

    public static Long getTodayEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime().getTime();
    }

    public static Long getPresentMonthStartTime(){
    	Calendar monthStart = Calendar.getInstance();
    	monthStart.set(Calendar.DAY_OF_MONTH, 1);
    	monthStart.set(Calendar.HOUR_OF_DAY, 0);
    	monthStart.set(Calendar.MINUTE, 0);
    	monthStart.set(Calendar.SECOND, 0);
    	monthStart.set(Calendar.MILLISECOND, 0);
    	return monthStart.getTime().getTime();
    }
    
    public static Long getOneHourAgoTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
        return calendar.getTime().getTime();
    }

    public static String longToDateStr(long time, String fmtDateStr) {
        SimpleDateFormat s = new SimpleDateFormat(fmtDateStr);
        String dateStr = s.format(new Date(time));
        return dateStr;
    }

    /**
     * 获取格式化后的时间字符串
     *
     * @param time
     * @return
     * @date 2015年12月1日
     */
    public static String formatChars(long time) {
        return longToDateStr(time, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 距离futureTime 的秒数
     *
     * @param futureTime
     * @return
     * @date 2015年11月27日
     */
    public static int getAfterSeconds(Long futureTime) {
        if (futureTime == null) {
            return 0;
        }
        int durationSeconds = (int) ((futureTime - System.currentTimeMillis()) / 1000);
        return durationSeconds >= 0 ? durationSeconds : 0;
    }

    /**
     * 获取00:00 到 hourOfDay的分钟数
     *
     * @param hourOfDay
     * @return
     * @date 2015年12月1日
     */
    public static int hourToMinutes(String hourOfDay) {
        if (StringUtils.isBlank(hourOfDay)) {
            return 0;
        }
        int hour = Integer.valueOf(StringUtils.substringBefore(hourOfDay, ":"));
        int minute = Integer.valueOf(StringUtils.substringAfter(hourOfDay, ":"));
        return hour * 60 + minute;
    }

    /**
     * 根据年龄获取对应的年月日
     * 
     * @param age
     * @return
     */
    public static Long getAgeTime(Integer age) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.YEAR,-age);
        return calendar.getTime().getTime();
    }
    
    /**
     * 用于判断支付密码相关使用的时间计算方法
     * 
     * @param minute
     * @return
     */
    public static Long getAccountPayTime(Integer minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,-minute);
        return calendar.getTime().getTime();
    }

    /**
     * 根据传入的时间返回指定的今日时间
     * 例: 传入09:30:00 --> 返回2016-09-30 09:30:00
     * @param time 时间字符串
     * @return 指定今日时间
     */
    @SuppressWarnings("deprecation")
	public static Date getTargetTime(Time time) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, time.getHours());
        c.set(Calendar.MINUTE, time.getMinutes());
        c.set(Calendar.SECOND, time.getSeconds());
        return c.getTime();
    }

    /**
     * 判断时间段是否有交集
     * @author stone
     * @date 2017年9月20日
     * @param start1
     * @param end1
     * @param start2
     * @param end2
     * @return
     */
    public static boolean isTimesIntersection(Date start1,Date end1,Date start2,Date end2){
    	long start1l = start1.getTime();
    	long end1l = end1.getTime();
    	long start2l = start2.getTime();
    	long end2l = end2.getTime();
    	return end1l>=start2l&&start1l<=end2l;
    }
    
    /**
     * 比较compare日期是否在start和end之间，一个为null就返回false
     * @author stone
     * @date 2017年9月28日
     * @param compare
     * @param start
     * @param end
     * @return
     */
    public static boolean isDateBetween(Date compare, Date start, Date end){
    	if(compare==null||start==null||end==null)
    		return false;
    	long cl = compare.getTime();
    	long sl = start.getTime();
    	long el = end.getTime();
    	return cl>=sl&&cl<=el;
    }

    /**
     * 检测指定时间是否在当前时间左右范围之间
     * @param checkTime 指定时间（毫秒）
     * @param rangeTime 时间范围（毫秒）
     * @return
     */
    public static boolean isTimeInCurrentRange(long checkTime, long rangeTime){
        long current = System.currentTimeMillis();
        return (current-rangeTime)<=checkTime&&checkTime<=(current+rangeTime);
    }

    /**
     * 比较当前日期是否在start和end之间，一个为null就返回false
     * @author stone
     * @date 2017年9月28日
     * @param start
     * @param end
     * @return
     */
    public static boolean isNowBetween(Date start,Date end){
    	return isDateBetween(new Date(), start, end);
    }
    /**
     * 当前日期在目标日期之后(大于或等于
     * @author stone
     * @date 2017年10月13日
     * @param date 如果date==null 返回false
     * @return
     */
    public static boolean isAfter(Date date){
    	return date != null && System.currentTimeMillis() >= date.getTime();
    }
    /**
     * 当前日期在目标日期之前(小于
     * @author stone
     * @date 2017年10月13日
     * @param date 如果date==null 返回false
     * @return
     */
    public static boolean isBefore(Date date){
    	return date != null && System.currentTimeMillis() < date.getTime();
    }

    /**
     * 返回今天的开始时间(SQL99时间格式)
     * @return 今天的开始时间
     */
    public static String getTodayBeginTimeWithSql99Format() {
        return SQL99_TIME_FORMAT.format(new Date(getTodayStartTime()));
    }

    /**
     * 返回今天的结束时间(SQL99时间格式)
     * @return 今天的结束时间
     */
    public static String getTodayEndTimeWithSql99Format() {
        return SQL99_TIME_FORMAT.format(new Date(getTodayEndTime()));
    }
    
    /**
     * 返回本月的开始时间(SQL99时间格式)
     * @return 本月的开始时间
     */
    public static String getPresentMonthStartTimeWithSql99Format() {
        return SQL99_TIME_FORMAT.format(new Date(getPresentMonthStartTime()));
    }

    /**
     * 返回昨天的开始时间(SQL99时间格式)
     * @return 昨天的开始时间
     */
    public static String getYesterDayBeiginTimeWithSql99Format() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(getTodayStartTime()));
        calendar.add(Calendar.DATE, -1);
        return SQL99_TIME_FORMAT.format(calendar.getTime());
    }

    /**
     * 返回昨天的结束时间(SQL99时间格式)
     * @return 昨天的结束时间
     */
    public static String getYesterDayEndTimeWithSql99Format() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(getTodayEndTime()));
        calendar.add(Calendar.DATE, -1);
        return SQL99_TIME_FORMAT.format(calendar.getTime());
    }

    /**
     * 按照指定的时间format返回昨日日期
     * @param df 日期包装器
     * @return 昨日日期
     */
    public static String getLastDateWithFormat(DateFormat df) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return df.format(calendar.getTime());
    }

    /**
     * 返回昨日日期按照数字格式返回
     * @return 数字日期
     */
    public static String getLastDateWithNumberFormat() {
        return getLastDateWithFormat(DATE_NUMBER_FORMAT);
    }
    
    /**
     * @param fmtDateStr 格式：yyyy-MM-dd HH:mm:ss
     * @return
     * @throws DateException
     * @author stone
     * @date 2015年7月11日
     */
    public static Timestamp newTimestamp(String fmtDateStr) {
        return new Timestamp(getTime(fmtDateStr));
    }
    
    /**
	 * 时间区域拆分成更多时间段
	 * @author stone
	 * @date 2017年12月15日
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param hhmmss  拆分详细时间，时分秒：HH:mm:ss
	 * @param splitWeeks 哪一天拆分，星期几
	 * @return key-子时间段开始时间，value-子时间段结束时间
	 * @throws ParseException 
	 */
	public static Map<Date,Date> splitDateRange(Date startDate,Date endDate,String hhmmss,Weeks... splitWeeks) throws ParseException{
		Map<Date,Date> result = new TreeMap<Date,Date>(new Comparator<Date>() {
			@Override
			public int compare(Date o1, Date o2) {
				return o1.compareTo(o2);
			}
		});
		Set<Weeks> weekset = new HashSet<Weeks>(Arrays.asList(splitWeeks));
		Date lastPoint = startDate;
		Calendar last = Calendar.getInstance();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(Date s = startDate;s.before(endDate);){
			last.setTime(s);
			int week = last.get(Calendar.DAY_OF_WEEK);
			if(weekset.contains(Weeks.getWeekByCalendarWeekInt(week))){
				String point = sdf1.format(s)+" "+hhmmss;
				Date pointDate = sdf2.parse(point);
				if(!pointDate.equals(lastPoint)){
					result.put(lastPoint, pointDate);
					lastPoint = pointDate;
				}
			}
			last.add(Calendar.DAY_OF_YEAR, 1);
			s = last.getTime();
		}
		if(result.get(lastPoint)==null){
			result.put(lastPoint, endDate);
		}
		return result;
	}

    /**
     * 获取当前日期,返回数据格式:yyyy-MM-dd
     *
     * @return
     * @date 2015年7月10日
     */
    public static String getNowDateFmt() {
        return DateUtils.getDateFormat(new Date(), "yyyy-MM-dd");
    }

    /**
     * 获取当前yyyyMMddHHmmss
     * @return
     */
    public static String getYyyyMMddHHmmss(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = format.format(date);
        return time;
    }

}
