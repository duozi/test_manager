package com.xn.performance.util;/**
 * Created by xn056839 on 2016/12/23.
 */

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
    public static final String STANDARD_TIME_FORMAT = "yyyy:MM:dd HH:mm:ss";
    public static final String STANDARD_DATE_FORMAT = "yyyy:MM:dd";
    public static final String STANDARD_DATE_FORMAT2 = "yyyy-MM-dd";
    /**
     * 标准日期格式
     */
    public static final String STANDARD_TIME_FORMAT2 = "yyyy-MM-dd HH:mm:ss";

    public static final String STANDARD_TIME_FORMAT3 = "yyyy-MM-dd HH:mm";

    /**
     * 标准日期月格式
     */
    public static final String STANDARD_MONTH_FORMAT = "yyyy-MM";

    public static final String ISO_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private static String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    /**
     * 得到星期几
     *
     * @param date
     * @return
     * @date 2016年5月4日
     * @author chenhening
     */
    public static String getWeekByDate(Date date) {
        int w = getWeekInt(date) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 返回星期几的int
     *
     * @param date
     * @return
     * @date 2016年5月4日
     * @author chenhening
     */
    public static int getWeekInt(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK);
        return w;
    }

    /**
     * 得到今天是几号
     *
     * @param date
     * @return
     * @date 2016年5月4日
     * @author chenhening
     */
    public static int getDayNum(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DATE);
    }

    /**
     * 根据传入日期、得到当天日期当天的起始时间
     *
     * @param date
     * @return
     */
    public static Date getToday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 根据传入日期、得到前一天的起始时间
     *
     * @param date
     * @return
     */
    public static Date getYesterday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     * 根据传入日期、得到当前日期所在星期的开始时间
     *
     * @param date
     * @return
     */
    public static Date getThisWeekDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 从周一开始算
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayWeek == 1) {
            cal.add(Calendar.DATE, -1);
        }
        cal.set(Calendar.DAY_OF_WEEK, 2);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 1);
        return cal.getTime();
    }

    /**
     * 获取当周结束时间
     *
     * @param date
     * @return
     * @date 2016年6月1日
     * @author likejie
     */
    public static Date getThisWeekEnd(Date date) {
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(date);
        currentDate.setFirstDayOfWeek(Calendar.MONDAY);
        currentDate.set(Calendar.HOUR_OF_DAY, 23);
        currentDate.set(Calendar.MINUTE, 59);
        currentDate.set(Calendar.SECOND, 59);
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return currentDate.getTime();
    }

    /**
     * 根据传入日期、得到当前日期所在月份的开始时间
     *
     * @param date
     * @return
     */
    public static Date getThisMonthDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 2);
        return cal.getTime();
    }

    /**
     * 获取当月最后一天
     *
     * @param date
     * @return
     * @date 2016年1月21日
     * @author chenhening
     */
    public static Date getThisMonthLastDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        cal.add(Calendar.MONTH, 1);// 月增加1天
        cal.add(Calendar.DAY_OF_MONTH, -1);// 日期倒数一日,既得到本月最后一天
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }



    /**
     * 根据传入日期、得到当前日期所在季度的开始时间
     *
     * @param date
     * @return
     */
    public static Date getThisSeasonDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int season = (month + 1 + 4) / 4;
        switch (season) {
            case 1:
                cal.set(Calendar.MONTH, Calendar.JANUARY);
                break;
            case 2:
                cal.set(Calendar.MONTH, Calendar.APRIL);
                break;
            case 3:
                cal.set(Calendar.MONTH, Calendar.JULY);
                break;
            case 4:
                cal.set(Calendar.MONTH, Calendar.OCTOBER);
                break;
        }
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 3);
        return cal.getTime();
    }

    /**
     * 根据传入日期、得到当前日期所在年份的开始时间
     *
     * @param date
     * @return
     */
    public static Date getThisYearDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 4);
        return cal.getTime();
    }

    /**
     * 根据传入日期，得到当前日期7天前的日期
     *
     * @param date
     * @return
     */
    public static Date getLastSevenDays(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, -7);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 根据传入日期，得到当前日期n天后的日期
     *
     * @param date
     * @return
     */
    public static Date getLastDays(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, n);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


    /**
     * 获取日期是当年的第几周
     *
     * @param date
     * @return
     * @date 2016年3月3日
     * @author gaoshan
     */
    public static int getWeekOfDate(Date date) {
        Calendar ca = Calendar.getInstance();// 创建一个日期实例
        ca.setTime(date);// 实例化一个日期
        return ca.get(Calendar.WEEK_OF_YEAR);// 获取是第几周
    }


    public static String getDateStr(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 格式化为标准日期格式(yyyy-MM-dd HH:mm:ss)的日期
     *
     * @param strDate 字符串日期
     * @return 标准日期
     */
    public static Date getStandardStringDate(String strDate) {
        return getStringDate(strDate, STANDARD_TIME_FORMAT2);
    }


    /**
     * 格式化为自定义的日期格式
     *
     * @param strDate 字符串日期
     * @param strType 自定义的日期格式
     * @return 自定义的日期
     */
    public static Date getStringDate(String strDate, String strType) {
        Date date = null;

        SimpleDateFormat objSDF = new SimpleDateFormat(strType);
        try {
            date = objSDF.parse(strDate);
        } catch (Exception e) {}

        return date;
    }


    /**
     * 获取当前格式化时间
     *
     * @return
     * @date 2015年7月1日
     * @author Ternence
     */
    public static String currTime() {
        SimpleDateFormat format = new SimpleDateFormat(STANDARD_TIME_FORMAT);
        return format.format(format.format(new Date()));
    }

    public static Date min(Date end, Date now) {
        int compare = end.compareTo(now);
        return compare > 0 ? now : end;
    }

    /**
     * 获取日期当天的最后一秒的时间值
     *
     * 这里没设置到毫秒级别 ，原因是如果设置了毫秒数 比如： 2025-01-01 23:59:59.999 插入数据库后会自动变更为 2025-01-02 00:00:00.000
     * ，如果在不考虑毫秒的情况下，将会出现1秒钟的误差，所以这里不设置毫秒数
     *
     * @param date
     * @return
     * @date 2015年4月21日
     * @author Ternence
     */
    public static Date getLastSencond(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);

        return cal.getTime();
    }

    /**
     * 生成一个当前时间，插入数据库时可能会多1秒，手动设置毫秒为0
     *
     * @return
     * @date 2016年3月14日
     * @author chenhening
     */
    public static Date getCurrDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取某一天的最后一毫秒的日期值
     *
     * @param date
     * @return
     * @date 2015年6月18日
     * @author Ternence
     */
    public static Date getLastMilliSecond(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        return cal.getTime();
    }

    /**
     * 获取某天前一日的第一毫秒的日期值
     *
     * @date 2016-02-22
     * @author guobingluo
     *
     * @param date
     * @return
     */
    public static Date getYesterdayStartTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        return cal.getTime();
    }

    /**
     * 获取某天前一日的最后一毫秒的日期值
     *
     * @date 2016-02-22
     * @author guobingluo
     *
     * @param date
     * @return
     */
    public static Date getYesterdayEndMillSecond(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        return cal.getTime();
    }

    /**
     * 获取指定日期的0点0分的时间
     *
     * @param date
     * @return
     * @date 2015年6月10日
     * @author Ternence
     */
    public static Date getBeginningOfDay(Date date) {
        return DateUtils.truncate(date, Calendar.DATE);
    }

    /**
     * 获取随机日期
     *
     * @param beginDate 起始日期，格式为：yyyy-MM-dd
     * @param endDate 结束日期，格式为：yyyy-MM-dd
     * @return
     */

    public static Date randomDate(String beginDate, String endDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date start = format.parse(beginDate);// 构造开始日期
            Date end = format.parse(endDate);// 构造结束日期
            // getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());

            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取随机日期
     *
     * @param start 起始日期
     * @param end 结束日期
     * @return
     */

    public static Date randomDate(Date start, Date end) {
        try {
            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());

            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static long random(long begin, long end) {
        long rtn = begin + (long) (Math.random() * (end - begin));
        // 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
        if (rtn == begin || rtn == end) {
            return random(begin, end);
        }
        return rtn;
    }


    public static Date add(Date date, int type, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(type, value);
        return calendar.getTime();
    }


    /**
     * 日期加上月数的时间
     *
     * @param date
     * @param month
     * @return
     */
    public static Date dateAddMonth(Date date, int month) {
        return add(date, Calendar.MONTH, month);
    }

    /**
     * 日期加上天数的时间
     *
     * @param date
     * @param day
     * @return
     */
    public static Date dateAddDay(Date date, int day) {
        return add(date, Calendar.DAY_OF_YEAR, day);
    }

    /**
     * 日期加上年数的时间
     *
     * @param date
     * @param year
     * @return
     */
    public static Date dateAddYear(Date date, int year) {
        return add(date, Calendar.YEAR, year);
    }

    /**
     * 判斷一個字符串是不是日期格式
     *
     * @param str
     * @return
     * @date 2015年12月31日
     * @author Administrator
     */
    public static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat(STANDARD_DATE_FORMAT2);
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 获取当月的 天数
     * */
    public static int getCurrentMonthDay() {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 日期转字符串
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return formatDate(date, STANDARD_TIME_FORMAT);
    }

    /**
     * 日期转字符串
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.format(date);
        }
        return null;
    }

    /**
     * 得到时间戳,确保date为"yyyy-MM-dd HH:mm:ss"格式
     *
     * @param date
     */
    public static Long getTimestamp(String date) {
        return getStandardStringDate(date).getTime();
    }

    /**
     * 根据年份和月份获取当前月的最大天数
     *
     * @author Typhoon
     * @date 2016-04-28
     * @since v3.2.0
     * @param year
     * @param month
     * @return
     */
    public static int getMaxDateByYearAndMonth(int year, int month) {
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        return cal.getActualMaximum(Calendar.DATE);
    }

    /**
     *
     * @Title: getNatureMonthDate
     * @Description: 获得几个自然月内最后时间
     * @param @param date
     * @param @param monthNum
     * @param @return 设定文件
     * @return Date 返回类型
     * @throws
     */
    public static Date getNatureMonthDate(Date date, int monthNum) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        cal.add(Calendar.MONTH, monthNum);// 月增加 几个月
        cal.add(Calendar.DAY_OF_MONTH, -1);// 日期倒数一日,既得到本月最后一天
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

//    两个日期之间相差的秒数

    public static int lastSecond(Date startDate,Date endDate) {
        long a = endDate.getTime();
        long b = startDate.getTime();
        int c = (int)((a - b) / 1000);
        return c;
    }
    //    两个日期之间相差的秒数

    public static int lastedMint(Date startDate,Date endDate) {
        long a = endDate.getTime();
        long b = startDate.getTime();
        int c = (int)((a - b) / 1000);
        return c;
    }
}
