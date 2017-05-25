package com.xn.performance.util.jmeter;

import com.xn.performance.util.GetTime;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDB.ConsistencyLevel;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.xn.performance.util.PropertyUtil.getProperty;

public class InfluxDB_Act {
	static String dbName = "telegraf";
	static String connstr = "http://10.17.2.137:8086";
	static String influxdb_user = "userjmeter";
	static String influxdb_pwd = "111111";
	static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
	
	public static List<List<Object>> telegraf_getTabName(String dbName){
		InfluxDB influxDB = null;
		List<List<Object>> tablist;
		try{
			influxDB = InfluxDBFactory.connect(connstr, influxdb_user, influxdb_pwd);
			Query query = new Query("show measurements", dbName);
			QueryResult rult = influxDB.query(query);
			tablist = rult.getResults().get(0).getSeries().get(0).getValues();
			System.out.println(dbName+" tabs is:"+tablist);
		}finally{
			influxDB.close();
		}
		return tablist;
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
        int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
        // 3、取得夏令时差：
        int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset + beforehour));
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
	/**
	 * 获取 UTC 时间
	 * @param beforeSecond 参数为 int 类型，表示比当前时间提前 beforeminute 秒时间，当该参数为负数时表示12小时前，为 0 时是当前时间
	 * @return long类型时间戳
	 */
	public static long getUTCTimeLong(int beforeSecond) {
		if (beforeSecond < 0){
			beforeSecond = 12*60*1000;
		}else{
			beforeSecond = beforeSecond*1000;
		}
        // 1、取得本地时间戳：
        long time_cur = Calendar.getInstance().getTimeInMillis();
        // 2、取得时间偏移量：
        //long zoneOffset = Calendar.getInstance().get(Calendar.ZONE_OFFSET);
        // 3、取得夏令时差：
        //long dstOffset = Calendar.getInstance().get(Calendar.DST_OFFSET);
        return (time_cur-beforeSecond)*1000000; //java时间戳是13位，influxdb是19位
    }
	
	public static void influxdb_to_sqlite3(String dbName, int beforeSecond,int id){
		InfluxDB influxDB = null;
		List<List<Object>> tablist;
		
		//String starttime = getUTCTimeStr(beforehour);
		long starttime = getUTCTimeLong(beforeSecond);
		System.out.println(starttime);

		try{
			influxDB = InfluxDBFactory.connect(connstr, influxdb_user, influxdb_pwd);
			Query query = new Query("show measurements", dbName);
			QueryResult rult = influxDB.query(query);
			tablist = rult.getResults().get(0).getSeries().get(0).getValues();
			System.out.println(dbName+" tabs is:"+tablist);
			//System.out.println(tablist.size());

			// 遍历表名，查询数据
			for (List<Object> tabname : tablist){
				String selstr = String.format("SELECT * FROM \"%s\" Where time > %d", tabname.get(0), starttime);
				//System.out.println(selstr);
				Query query_tab = new Query(selstr, dbName);
				QueryResult rult_tab = influxDB.query(query_tab);
				//System.out.println(rult_tab.getResults().get(0));
				//System.out.println(rult_tab.getResults().get(0).getSeries().get(0).getName());
				//System.out.println(rult_tab.getResults().get(0).getSeries().get(0).getColumns());
				//System.out.println(rult_tab.getResults().get(0).getSeries().get(0).getValues());
				List<String> colnames = rult_tab.getResults().get(0).getSeries().get(0).getColumns();
				for(int i=0;i<colnames.size();i++){
					colnames.set(i, "["+colnames.get(i)+"]");
				}
				ArrayList<List<Object>> colvalues = new ArrayList<List<Object>>();
				for(List<Object> tabvalue : rult_tab.getResults().get(0).getSeries().get(0).getValues()){
					colvalues.add(tabvalue);
				}
				System.out.println(tabname.toString());
				System.out.println(colnames);
				System.out.println(colvalues);
				SQLite_Act.sqlite3_from_influxdb(getProperty("reports")+id+".db", tabname.toString(), colnames, colvalues);
			}
			System.out.println("******* influxdb data to sqlite3 end! *******");
			
		} catch(Exception e){
			e.printStackTrace();
		}finally{
			influxDB.close();
		}
	} 
	
	public static void test(){
		InfluxDB influxDB = InfluxDBFactory.connect("http://10.17.2.137:8086", "userjmeter", "111111"); //后两个数用户和密码
		String dbName = "telegraf";
		//influxDB.createDatabase(dbName);

		BatchPoints batchPoints = BatchPoints
		                .database(dbName)
		                .tag("async", "true")
		                .retentionPolicy("default")
		                .consistency(ConsistencyLevel.ALL)
		                .build();
		Point point1 = Point.measurement("jmeter.all.a.pct90")
		                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
		                    .field("idle", 90L).field("system", 9L)
		                    .field("system", 1L)
		                    .build();
		Point point2 = Point.measurement("disk")
		                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
		                    .field("used", 80L)
		                    .field("free", 1L)
		                    .build();
		batchPoints.point(point1);
		batchPoints.point(point2);
		//influxDB.write(batchPoints);
		Query query = new Query("SELECT * FROM \"jmeter.all.a.pct90\"", dbName);
		QueryResult rult = influxDB.query(query);
		List<List<Object>> tablist = rult.getResults().get(0).getSeries().get(0).getValues();
		System.out.println(tablist); // 表中的数据
		//influxDB.deleteDatabase(dbName);
	}
	/**
	 * 获取一段时间内的 influxdb 数据
	 * @param infdbName  influxdb的数据库名称
	 * @param sqlitedbName sqlite3的数据库 路径+数据库名称
	 * @param starttime long类型起始时间戳
	 * @param endtime  long类型结束时间戳
	 */
	public void influxdb_to_sqlite3(String infdbName, String sqlitedbName, long starttime, long endtime){
		InfluxDB influxDB = null;
		List<List<Object>> tablist;

		//将时间戳修改为 influxdb 使用的 19 位时间戳
		int timenum = GetTime.getLongnum(starttime);
		if (timenum<19){
			starttime = starttime * (long)Math.pow(10,(19-timenum));
		}
		timenum = GetTime.getLongnum(endtime);
		if (timenum<19){
			endtime = endtime * (long)Math.pow(10,(19-timenum));
		}

		try{
			influxDB = InfluxDBFactory.connect(connstr, influxdb_user, influxdb_pwd);
			Query query = new Query("show measurements", infdbName);
			QueryResult rult = influxDB.query(query);
			tablist = rult.getResults().get(0).getSeries().get(0).getValues();
			System.out.println(infdbName+" tabs is:"+tablist);

			// 遍历表名，查询数据
			for (List<Object> tabname : tablist){
				String selstr = String.format("SELECT * FROM \"%s\" Where time >= %d and time <= %d", tabname.get(0), starttime,endtime);
				System.out.println(selstr);
				Query query_tab = new Query(selstr, infdbName);
				QueryResult rult_tab = influxDB.query(query_tab); //查询为空时这里会报错
				//if (rult_tab)
				System.out.println(rult_tab.getResults().get(0));
				if(rult_tab.getResults().get(0).getSeries()==null){
					System.out.println("Table "+tabname+" 查询结果为 null.");
					continue;
				}
				List<String> colnames = rult_tab.getResults().get(0).getSeries().get(0).getColumns();
				// 列名增加 [] 包裹，避免某些关键字列名如 in 无法再sqlite中创建表
				for(int i=0;i<colnames.size();i++){
					colnames.set(i, "["+colnames.get(i)+"]");
				}
				ArrayList<List<Object>> colvalues = new ArrayList<List<Object>>();
				for(List<Object> tabvalue : rult_tab.getResults().get(0).getSeries().get(0).getValues()){
					colvalues.add(tabvalue); // 将表中每行数据添加到 list
				}
				// 将数据转存到sqlite3数据库中
				SQLite_Act.sqlite3_from_influxdb(sqlitedbName, tabname.toString(), colnames, colvalues);
			}
			System.out.println("******* influxdb data to sqlite3 end! *******");

		} catch(Exception e){
			e.printStackTrace();
		}finally{
			influxDB.close();
		}
	}

	/**
	 * 获取一段时间内的 influxdb 数据，对于多个性能测试并发的处理，按照不同的前缀获取表数据
	 * @param infdbName  influxdb的数据库名称
	 * @param sqlitedbName sqlite3的数据库 路径+数据库名称
	 * @param starttime long类型起始时间戳
	 * @param endtime  long类型结束时间戳
	 * @param rootMetricsPrefix jmeter运行写入influxdb的表的前缀，用来排除同时进行的多个性能测试，避免写到同一个表污染了数据
	 */
	public void influxdb_to_sqlite3(String infdbName, String sqlitedbName, long starttime, long endtime, String rootMetricsPrefix){
		InfluxDB influxDB = null;
		List<List<Object>> tablist;

		//将时间戳修改为 influxdb 使用的 19 位时间戳
		int timenum = GetTime.getLongnum(starttime);
		if (timenum<19){
			starttime = starttime * (long)Math.pow(10,(19-timenum));
		}
		timenum = GetTime.getLongnum(endtime);
		if (timenum<19){
			endtime = endtime * (long)Math.pow(10,(19-timenum));
		}

		try{
			influxDB = InfluxDBFactory.connect(connstr, influxdb_user, influxdb_pwd);
			Query query = new Query("show measurements", infdbName);
			QueryResult rult = influxDB.query(query);
			tablist = rult.getResults().get(0).getSeries().get(0).getValues();
			System.out.println(infdbName+" tabs is:"+tablist);

			// 遍历表名，查询数据
			for (List<Object> tabname : tablist){
				String[] tabn = tabname.get(0).toString().split("\\.");
				if (tabn.length>1){
					if (!tabn[0].equals(rootMetricsPrefix.substring(0, rootMetricsPrefix.length()-1)))
						continue;
				}

				String selstr = String.format("SELECT * FROM \"%s\" Where time >= %d and time <= %d", tabname.get(0), starttime,endtime);
				System.out.println(selstr);
				Query query_tab = new Query(selstr, infdbName);
				QueryResult rult_tab = influxDB.query(query_tab); //查询为空时这里会报错
				//if (rult_tab)
				System.out.println(rult_tab.getResults().get(0));
				if(rult_tab.getResults().get(0).getSeries()==null){
					System.out.println("Table "+tabname+" 查询结果为 null.");
					continue;
				}
				List<String> colnames = rult_tab.getResults().get(0).getSeries().get(0).getColumns();
				// 列名增加 [] 包裹，避免某些关键字列名如 in 无法再sqlite中创建表
				for(int i=0;i<colnames.size();i++){
					colnames.set(i, "["+colnames.get(i)+"]");
				}
				ArrayList<List<Object>> colvalues = new ArrayList<List<Object>>();
				for(List<Object> tabvalue : rult_tab.getResults().get(0).getSeries().get(0).getValues()){
					colvalues.add(tabvalue); // 将表中每行数据添加到 list
				}
				// 将数据转存到sqlite3数据库中
				SQLite_Act.sqlite3_from_influxdb(sqlitedbName, tabname.toString(), colnames, colvalues);
			}
			System.out.println("******* influxdb data to sqlite3 end! *******");

		} catch(Exception e){
			e.printStackTrace();
		}finally{
			influxDB.close();
		}
	}
	public static void main( String[] args )
    {
//		influxdb_to_sqlite3("telegraf",1);
    }

}
