package com.xn.performance.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class GetTime {

	/**
	 * 获取 UTC 时间戳
	 * @param beforeminute 参数为 int 类型，表示比当前时间提前 beforeminute 分钟时间，当该参数为负数时表示12小时前，为 0 时是当前时间
	 * @return long类型时间戳， 注意 influxdb时间戳是19位，这里获取的是13位所以最后 *1000000补足位数
	 */
	public static long getUTCTimeLong19(int beforeminute) {
		if (beforeminute < 0){
			beforeminute = 12*60*60*1000;
		}else{
			beforeminute = beforeminute*60*1000;
		}
        // 1、取得本地时间戳：
        long time_cur = Calendar.getInstance().getTimeInMillis();
        // 2、取得时间偏移量：
        //long zoneOffset = Calendar.getInstance().get(Calendar.ZONE_OFFSET);
        // 3、取得夏令时差：
        //long dstOffset = Calendar.getInstance().get(Calendar.DST_OFFSET);
        return (time_cur-beforeminute)*1000000; //java时间戳是13位，influxdb是19位
    }
	public static long getUTCTimeLong13(int beforeminute) {
		if (beforeminute < 0){
			beforeminute = 12*60*60*1000;
		}else{
			beforeminute = beforeminute*60*1000;
		}
        long time_cur = Calendar.getInstance().getTimeInMillis();
        return (time_cur-beforeminute); //java时间戳是13位，influxdb是19位
    }
	
	/**
	 * 获取 UTC 时间
	 * @param beforehour 参数为 int 类型，表示比当前时间提前 beforehour 小时时间，当该参数为负数时表示12小时前，为 0 时是当前时间
	 * @return
	 */
	public static String getUTCTimeStr(int beforehour) {
		if (beforehour < 0){
			beforehour = 12*60*60*1000;
		}else{
			beforehour = beforehour*60*60*1000;
		}
		
        StringBuffer UTCTimeBuffer = new StringBuffer();
        // 1、取得本地时间：
        Calendar cal = Calendar.getInstance() ;
        // 2、取得时间偏移量：
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        // 3、取得夏令时差：
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset + beforehour));
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND); 
        UTCTimeBuffer.append(year).append("-").append(String.format("%02d", month)).append("-").append(String.format("%02d", day)) ;
        UTCTimeBuffer.append("T").append(String.format("%02d", hour)).append(":").append(String.format("%02d", minute)).append(":").append(String.format("%02d", second)).append("Z");
        try{
            //format.parse(UTCTimeBuffer.toString());
            return UTCTimeBuffer.toString() ;
        }catch(Exception e)
        {
            e.printStackTrace() ;
        }
        return null ;
    }
	
	public static int getLongnum(long time_long) {
		int size = 1;
        while ((time_long = time_long / 10) != 0) {
            size++;
        }
		return size;
	}
	/**
	 * UTC时间转本地（北京）时间
	 * @param s UTC时间格式  "2017-05-26T05:33:54Z" or "2017-05-26 05:33:54"
	 * @return 本地（北京）时间 “2017-05-26 13:33:54”
	 */
	public static String datauctTolocal(String s){
		String res="";//=s.replace("T", " ").replaceAll("Z", "");
		SimpleDateFormat simpleDateFormat;
		if(s.indexOf("T")>0 && s.indexOf("Z")>0){
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		}else{
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));//时区定义并进行时间获取
		Date utcdate=null;
		try {
			utcdate = simpleDateFormat.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat localFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		localFormater.setTimeZone(TimeZone.getDefault()); // 设置按本地时间显示
		String localTime = localFormater.format(utcdate.getTime());
		return localTime;
	}
	public static void main( String[] args ){
		System.out.println(getLongnum(1493863971973L));
	}
}
