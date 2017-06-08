package com.xn.performance.util.jmeter;

import com.xn.performance.util.GetTime;

import java.util.*;
import java.util.Map.Entry;

import static com.xn.performance.util.PropertyUtil.getProperty;


/**
 * 提供绘制图表所需的数据,实时数据
 *
 */
public class JmeterChartInfluxdb {
	// 初始化Map  
	public static Map<String , String> jmetersqlstr = new HashMap<String, String>(){{  
		put("tps_ok", "select time,value from \"jmeter.all.ok.count\" where time>%d and time<%d");
		put("tps_all", "select time,value from \"jmeter.all.h.count\" where time>%d and time<%d"); // tps
		put("avg", "select time,value from \"jmeter.all.a.avg\"  where time>%d and time<%d"); // 平均响应时间
		put("pct90", "select time,value from \"jmeter.all.a.pct90\"  where time>%d and time<%d"); // 90%的用户耗时
		put("users", "select time,value from \"jmeter.test.meanAT\"  where time>%d and time<%d"); // 平均线程数，虚拟用户数
		}};
	/**
	 * key 是linux监控数据保存在 influxdb的表名称，这个后面编码要用到
	 */
	public static Map<String , String> linuxsqlstr = new HashMap<String, String>(){{
		put("cpu","select time,host,usage_system as sy,usage_user as us from \"cpu\" where cpu = 'cpu-total' and time>%d and time<%d group by host");
		put("system", "select time,host,load5,load15 from \"system\" where time>%d and time<%d group by host");
		put("mem", "select time,host,total/(1000000000) as total_G,used/(1000000000) as used_G from \"mem\" where time>%d and time<%d group by host");
		put("swap", "select time,host,total/(1024*1024*1024) as total_G,used/(1024*1024*1024) as used_G from \"swap\" where time>%d and time<%d group by host");
		put("diskio","select time,host,read_time,read_bytes,write_time,write_bytes from \"diskio\" where time>%d and time<%d group by host");
		//put("diskio","select a.time as time,a.host as host, case a.read_time-b.read_time when 0 then 0 else (a.read_MB - b.read_MB)/((a.read_time-b.read_time)/1000) end as \"read(MB/S)\",case a.write_time-b.write_time when 0 then 0 else (a.write_MB - b.write_MB)/((a.write_time-b.write_time)/1000) end as \"write(MB/S)\" from (select time,host,sum(read_bytes)/(1024*1024) as read_MB,sum(read_time) as read_time,sum(write_bytes)/(1024*1024) as write_MB,sum(write_time) as write_time from diskio group by time,host) as a join (select time,host,sum(read_bytes)/(1024*1024) as read_MB,sum(read_time) as read_time,sum(write_bytes)/(1024*1024) as write_MB,sum(write_time) as write_time from diskio group by time,host) as b on a.host = b.host and a.time > b.time where a.time>%d and a.time<%d group by a.time,a.host");
		}};  
	
	/**
	 * 使用默认配置的influxdb地址获取数据, 从测试开始时间到最新数据
	 */
	public static Map<String,Object> ChartInfluxdb_jmeter(String jmeterPrefix, String jmetersqlstr_key,long starttime){
		String connstr = getProperty("influx_db_connstar");
		String influxdb_user = getProperty("influxdb_user");
		String influxdb_pwd = getProperty("influxdb_pwd");
		return ChartInfluxdb_jmeter(connstr,influxdb_user,influxdb_pwd,jmeterPrefix,jmetersqlstr_key,starttime, GetTime.getUTCTimeLong19(0));
	}
	/**
	 * 使用默认配置的influxdb地址获取数据
	 * connstr = "http://10.17.2.239:8086";
	 * influxdb_user = "userjmeter";
	 * influxdb_pwd = "111111";
	 * 其他参数与方法ChartInfluxdb_jmeter(String jmeterPrefix, String jmetersqlstr_key,long starttime, long endtime)一样
	 */
	public static Map<String,Object> ChartInfluxdb_jmeter(String jmeterPrefix, String jmetersqlstr_key,long starttime, long endtime){
		String connstr = getProperty("influx_db_connstar");
		String influxdb_user = getProperty("influxdb_user");
		String influxdb_pwd = getProperty("influxdb_pwd");
		return ChartInfluxdb_jmeter(connstr,influxdb_user,influxdb_pwd,jmeterPrefix,jmetersqlstr_key,starttime,endtime);
	}
	/**
	 * 访问jmeter的influxdb库获取数据，用于chart.js绘图，格式为x轴 为time，y轴为一组list可以为多个线条
	 * @param infConnStr influxdb 地址 ,例如:http://10.17.2.239:8086
	 * @param infuser  访问数据库的 用户
	 * @param infpwd   访问数据库的用户对应的密码
	 * 
	 * @param jmeterPrefix jmeter性能测试保存数据到influxdb的前缀
	 * @param jmetersqlstr_key 定义好的sql语句Map的key，目前支持：tps_ok tps_all avg pct90 users
	 * @param starttime 19位起止时间戳
	 * @param endtime
	 * @return Map<String,Object>类型数据,对应绘图的 x 轴 time list数据 和 y 轴的 value 数据
	 */
	public static Map<String,Object> ChartInfluxdb_jmeter(String infConnStr, String infuser, String infpwd,String jmeterPrefix, String jmetersqlstr_key,long starttime, long endtime){
		String sqltmpstr = jmetersqlstr.get(jmetersqlstr_key).replace("jmeter.", jmeterPrefix);
		Map<String,Object> chartdata = new LinkedHashMap<String, Object>(); 
		//将时间戳修改为 influxdb 使用的 19 位时间戳
		int timenum = GetTime.getLongnum(starttime);
		if (timenum<19){
			starttime = starttime * (long)Math.pow(10,(19-timenum));
		}
		timenum = GetTime.getLongnum(endtime);
		if (timenum<19){
			endtime = endtime * (long)Math.pow(10,(19-timenum));
		}
		
		String newsqlstr = String.format(sqltmpstr,starttime,endtime);
		InfluxDB_Act influxdbjmeter = new InfluxDB_Act(infConnStr,infuser,infpwd);
		List<List<Object>> dbdata = influxdbjmeter.influxdb_get_data("jmeter", newsqlstr);
		if(dbdata.size()==0){
			List<String> xvalue = new ArrayList<String>();
			List<String> yvalue = new ArrayList<String>();
			for(int tmp = 1; tmp < 11; tmp++){
				xvalue.add("X"+tmp);
				java.util.Random random=new java.util.Random();// 定义随机类
				int result=random.nextInt(10);// 返回[0,10)集合中的整数，注意不包括10
				yvalue.add(Integer.toString(result+1));
			}
			chartdata.put("time", xvalue);
			chartdata.put("value", yvalue);
			return chartdata;
		}
		//System.out.println(dbdata);
		List<String> xvalue = new ArrayList<String>();
		List<String> yvalue = new ArrayList<String>();
		// 第一行即 0 下标的行是title，表名称，所以从1开始
		for(int i=0;i<dbdata.get(1).size();i++){
			List<Object> tmp = (List<Object>)dbdata.get(1).get(i);
			xvalue.add(GetTime.datauctTolocal(tmp.get(0).toString()));
			yvalue.add(tmp.get(1).toString());
		}
		chartdata.put("time", xvalue);
		chartdata.put("value", yvalue);
		return chartdata;
	}
	
	/**
	 * 使用默认配置的influxdb地址获取数据, 从测试开始时间到最新数据
	 */
	public static Map<String, List<Object>> ChartInfluxdb_linux(String linuxsqlstr_kye,long starttime){
		String connstr = getProperty("influx_db_connstar");
		String influxdb_user = getProperty("influxdb_user");
		String influxdb_pwd = getProperty("influxdb_pwd");
		return ChartInfluxdb_linux(connstr,influxdb_user,influxdb_pwd,linuxsqlstr_kye,starttime,GetTime.getUTCTimeLong19(0));
	}
	
	/**
	 * 使用默认配置的influxdb地址获取数据
	 * connstr = "http://10.17.2.239:8086";
	 * influxdb_user = "userjmeter";
	 * influxdb_pwd = "111111";
	 * 其他参数与方法ChartInfluxdb_linux(String infConnStr, String infuser, String infpwd, String linuxsqlstr_kye,long starttime, long endtime)一致
	 */
	public static Map<String, List<Object>> ChartInfluxdb_linux(String linuxsqlstr_kye,long starttime, long endtime){
		String connstr = getProperty("influx_db_connstar");
		String influxdb_user = getProperty("influxdb_user");
		String influxdb_pwd = getProperty("influxdb_pwd");
		return ChartInfluxdb_linux(connstr,influxdb_user,influxdb_pwd,linuxsqlstr_kye,starttime,endtime);
	}
	/**
	 * 获取jmeter性能测试期间的收集到的linux server性能数据，cpu、io等
	 * @param dbname  sqlite db 路径 + 文件名称，例如："/target/jmeter-reports/10min-go.db"
	 * @param sqlstr 查询sql语句，例如：
	 * @param params 如果sql中有 ? 替换参数，这个参数不定长对应?， 可用于查询指定时间范围的数据
	 * @return 返回Map<String,Object>格式数据 Object 为 List<String>，对应查询出的结果
	 */
	public static Map<String,List<Object>> ChartInfluxdb_linux(String infConnStr, String infuser, String infpwd, String linuxsqlstr_kye,long starttime, long endtime){
		String sqltmpstr = linuxsqlstr.get(linuxsqlstr_kye);
		
		Map<String,List<Object>> chartdata = new LinkedHashMap<String, List<Object>>(); 

		int timenum = GetTime.getLongnum(starttime);
		if (timenum<19){
			starttime = starttime * (long)Math.pow(10,(19-timenum));
		}
		timenum = GetTime.getLongnum(endtime);
		if (timenum<19){
			endtime = endtime * (long)Math.pow(10,(19-timenum));
		}
		String newsqlstr = String.format(sqltmpstr,starttime,endtime);
		//String hostselsql = String.format("select host from %s group by host", linuxsqlstr_kye);
		
		InfluxDB_Act influxdbjmeter = new InfluxDB_Act(infConnStr,infuser,infpwd);
		List<List<Object>> infdata = influxdbjmeter.influxdb_get_data("telegraf", newsqlstr);
		//System.out.println(infdata); //[[host], [yuntools-perf-agent1.localdomain], [yuntools-perf-agent2.localdomain]]
		
		chartdata.put("time", new ArrayList<Object>()); //定义x轴名称
		// i=0取到的第一列是标题，及表每列的名称，忽略
		for (int i = 1; i<infdata.size(); i++){
			//定义多个曲线的 key 名称, 只定义一次
			for (int keyname = 2; keyname<infdata.get(0).size(); keyname++){
				List<Object> onetime = (List<Object>)infdata.get(i).get(0);
				chartdata.put(onetime.get(1)+"_"+infdata.get(0).get(keyname), new ArrayList<Object>());
			}
			// 循环添加数据
			for(int j=0;j<infdata.get(i).size();j++){
				List<Object> onetime = (List<Object>)infdata.get(i).get(j);
				for (int k=2;k<onetime.size();k++){
					//第一行是time, 第二行是 host, 后面的菜蔬数据
					if (i==1 && k==2){ // 确保时间只添加一次，可能会导致误差在10s内，可接受
						chartdata.get("time").add(GetTime.datauctTolocal(onetime.get(0).toString()));
					}
					chartdata.get(onetime.get(1)+"_"+infdata.get(0).get(k)).add(onetime.get(k));
				}
			}
		}
		return chartdata;
	}
	
	/**
	 * 特殊处理下磁盘io，influxdb不支持表名 as 别名， 及 别名.列标题，导致无法用sql计算io，只能代码计算了
	 * @param linuxsqlstr_kye
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	public static List<Map<Object,Map<String,List<Object>>>> ChartInfluxdb_linux_io(String linuxsqlstr_kye,long starttime, long endtime){
		String sqltmpstr = linuxsqlstr.get(linuxsqlstr_kye);
		/**
		 * influxdb不支持表名 as 别名， 及按照time group by 导致，复杂聚合语句 from (select ...) as o 的写法不能用，只能在代码中实现，便于计算io数据
		 * <Object,Map<Object,Double[]>> 用于实现聚合group by
		 * <host, Map<time, Double[read_time,read_bytes,write_time,write_bytes]
		 */
		Map<Object,Map<Object,Double[]>> groupdata = new LinkedHashMap<Object,Map<Object,Double[]>>(); 
		int timenum = GetTime.getLongnum(starttime);
		if (timenum<19){
			starttime = starttime * (long)Math.pow(10,(19-timenum));
		}
		timenum = GetTime.getLongnum(endtime);
		if (timenum<19){
			endtime = endtime * (long)Math.pow(10,(19-timenum));
		}
		String newsqlstr = String.format(sqltmpstr,starttime,endtime);
		InfluxDB_Act influxdbjmeter = new InfluxDB_Act();
		List<List<Object>> infdata = influxdbjmeter.influxdb_get_data("telegraf", newsqlstr);
		//System.out.println(infdata); //[[host], [yuntools-perf-agent1.localdomain], [yuntools-perf-agent2.localdomain]]
		// i=0取到的第一列是标题，及表每列的名称，忽略
		for (int i = 1; i<infdata.size(); i++){
			for(int j =0; j < infdata.get(i).size(); j++){
				List<Object> tmp = (List<Object>)infdata.get(i).get(j); 
				//System.out.println(infdata.get(i).get(j)); //[2017-05-27T06:19:30Z, SZV-SIT2-SAPP1, 46717.0, 4.5011456E8, 970778.0, 4.729479168E9]
				// 判断对应host下的 map列表的 time key是否存在，存储每个time下的read数据
				// 首先判断 host是否有，没有就增加，有了直接添加数据
				if(groupdata.containsKey(tmp.get(1))==false){
					groupdata.put(tmp.get(1), new LinkedHashMap<Object,Double[]>());
				}
				if(groupdata.get(tmp.get(1)).containsKey(tmp.get(0))==false){
					groupdata.get(tmp.get(1)).put(tmp.get(0), new Double[4]);
					groupdata.get(tmp.get(1)).get(tmp.get(0))[0] = 0.0;
					groupdata.get(tmp.get(1)).get(tmp.get(0))[1] = 0.0;
					groupdata.get(tmp.get(1)).get(tmp.get(0))[2] = 0.0;
					groupdata.get(tmp.get(1)).get(tmp.get(0))[3] = 0.0;
				}
				// tmp list 中的顺序  time,host,read_time,read_bytes,write_time,write_bytes
				// 合计同一个host，相同时间点的 read_time
				groupdata.get(tmp.get(1)).get(tmp.get(0))[0] = groupdata.get(tmp.get(1)).get(tmp.get(0))[0] + Double.valueOf(tmp.get(2).toString());
				// 合计同一个host，相同时间点的 read_bytes
				groupdata.get(tmp.get(1)).get(tmp.get(0))[1] = groupdata.get(tmp.get(1)).get(tmp.get(0))[1] + Double.valueOf(tmp.get(3).toString());
				// 合计同一个host，相同时间点的 write_time
				groupdata.get(tmp.get(1)).get(tmp.get(0))[2] = groupdata.get(tmp.get(1)).get(tmp.get(0))[2] + Double.valueOf(tmp.get(4).toString());
				// 合计同一个host，相同时间点的 write_bytes
				groupdata.get(tmp.get(1)).get(tmp.get(0))[3] = groupdata.get(tmp.get(1)).get(tmp.get(0))[3] + Double.valueOf(tmp.get(5).toString());
			}
		}

		/** 这个map用于汇总计算后得到的io数据
		 * OObject,Map<String,List<List<Object>>>
		 * host => key=time=>time[list], key=read_kb/s=>read_kb/s[list],key=write_kb/s=>write_kb/s[list]]
		 */
		System.out.println(groupdata);
		List<Map<Object,Map<String,List<Object>>>> chartdatas = new ArrayList<Map<Object,Map<String,List<Object>>>>(); 
		// 每个host time不同，单独计算
		for(Entry<Object, Map<Object, Double[]>> hosttimedata : groupdata.entrySet()){
			Map<Object,Map<String,List<Object>>> chartdata = new LinkedHashMap<Object,Map<String,List<Object>>>(); 
			// 判断chartdata是否有host key，没有就添加
			if(chartdata.containsKey(hosttimedata.getKey())==false){
				chartdata.put(hosttimedata.getKey(), new LinkedHashMap<String,List<Object>>());
			}
			// 从时间的第二个开始，用于计算时间1到2之间，io time 差 和 readbytes差，计算每秒速度
			Double[] oldtimedata = new Double[4];
			oldtimedata[0] = 0.0;
			oldtimedata[1] = 0.0;
			oldtimedata[2] = 0.0;
			oldtimedata[3] = 0.0;
			int count = 0;
			List<Object> time_x = new ArrayList<Object>();
			List<Object> read_y = new ArrayList<Object>();
			List<Object> write_y = new ArrayList<Object>();
			for(Entry<Object, Double[]> timedata : hosttimedata.getValue().entrySet()){
				if(count == 0){
					oldtimedata[0] = timedata.getValue()[0];
					oldtimedata[1] = timedata.getValue()[1];
					oldtimedata[2] = timedata.getValue()[2];
					oldtimedata[3] = timedata.getValue()[3];
					count = 1;
				}else{
					time_x.add(GetTime.datauctTolocal(timedata.getKey().toString()));
					if(timedata.getValue()[0]-oldtimedata[0]==0){
						read_y.add(0.0);
					}else{
						read_y.add(((timedata.getValue()[1]-oldtimedata[1])/1024)/((timedata.getValue()[0]-oldtimedata[0])/1000));
						oldtimedata[0] = timedata.getValue()[0];
						oldtimedata[1] = timedata.getValue()[1];
					}
					if(timedata.getValue()[0]-oldtimedata[0]==0){
						write_y.add(0.0);
					}else{
						write_y.add(((timedata.getValue()[3]-oldtimedata[3])/1024)/((timedata.getValue()[2]-oldtimedata[2])/1000));
						oldtimedata[2] = timedata.getValue()[2];
						oldtimedata[3] = timedata.getValue()[3];
					}
				}
			}
			chartdata.get(hosttimedata.getKey()).put("time", time_x);
			chartdata.get(hosttimedata.getKey()).put("read_Kb/s", read_y);
			chartdata.get(hosttimedata.getKey()).put("write_Kb/s", write_y);
			
			chartdatas.add(chartdata);
		}
		return chartdatas;
	}
	
	public static Map<String,Object> ChartInfluxdbData_start_now(String jmeterPrefix,long starttime){
		return ChartInfluxdbData_start_end(jmeterPrefix,starttime,GetTime.getUTCTimeLong19(0));
	}
	public static Map<String,Object> ChartInfluxdbData_start_end(String jmeterPrefix,long starttime,long endtime){
		Map<String,Object> chartdata = new LinkedHashMap<String, Object>(); 
		//chartdata.put("tps_all", ChartInfluxdb_jmeter(jmeterPrefix,"tps_all",starttime,endtime));
		
		chartdata.put("tps_ok", ChartInfluxdb_jmeter(jmeterPrefix,"tps_ok",starttime,endtime));
		chartdata.put("avg", ChartInfluxdb_jmeter(jmeterPrefix,"avg",starttime,endtime));
		chartdata.put("pct90", ChartInfluxdb_jmeter(jmeterPrefix,"pct90",starttime,endtime));
		chartdata.put("users", ChartInfluxdb_jmeter(jmeterPrefix,"users",starttime,endtime));
		chartdata.put("cpu-total", ChartInfluxdb_linux("cpu",starttime,endtime));
		chartdata.put("load", ChartInfluxdb_linux("system",starttime,endtime));
		chartdata.put("mem/G", ChartInfluxdb_linux("mem",starttime,endtime));
		chartdata.put("swap/G", ChartInfluxdb_linux("swap",starttime,endtime));
		
		chartdata.put("diskio", ChartInfluxdb_linux_io("diskio",starttime,endtime));
		return chartdata;
	}
	

	public static void main( String args[] )
	{
		Map<String, List<Object>> chartdata = ChartInfluxdb_linux("cpu",GetTime.getUTCTimeLong19(10),GetTime.getUTCTimeLong19(0)); 
		System.out.println(chartdata);
		//Map<String,Object> alldata = ChartInfluxdbData_start_end("100.",1495776804316000000L,1495776864316000000L);
		
		Map<String,Object> alldata = ChartInfluxdbData_start_end("100.",GetTime.getUTCTimeLong19(2),GetTime.getUTCTimeLong19(0));
		System.out.println(alldata);
		// "100." 是jmeter数据库表前缀，默认是 jmeter.
		//Map<String, Object> chartdata = ChartInfluxdb_jmeter("100.","tps_ok",1495776804316000000L,1495776864316000000L);
		//System.out.println(chartdata);
	}
}
