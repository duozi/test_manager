package com.xn.performance.util.jmeter;

import java.util.*;

/**
 * 提供绘制图表所需的数据
 *
 */
public class JmeterChartSqlite {
	// 初始化Map  
	public static Map<String , String> jmetersqlstr = new HashMap<String, String>(){{  
		put("tps_ok", "select time,value from \"jmeter.all.ok.count\"");
		put("tps_all", "select time,value from \"jmeter.all.h.count\""); // tps
		put("tps_ko", "select time,value from \"jmeter.all.ko.count\"");
		put("avg", "select time,value from \"jmeter.all.a.avg\""); // 平均响应时间
		put("pct90", "select time,value from \"jmeter.all.a.pct90\""); // 90%的用户耗时
		put("users", "select time,value from \"jmeter.test.meanAT\""); // 平均线程数，虚拟用户数
		}};
	public static Map<String , String> linuxsqlstr = new HashMap<String, String>(){{  
		put("cpu-total", "select time,usage_system as sy,usage_user as us from \"cpu\" where cpu = 'cpu-total' and host = ?");
		put("load", "select time,load1,load5,load15 from \"system\" where host = ?");
		put("mem", "select time,total/(1000000000) as total_G,used/(1000000000) as used_G from \"mem\" where host = ?");
		put("swap", "select time,total/(1024*1024*1024) as total_G,used/(1024*1024*1024) as used_G from \"swap\" where host = ?");
		//put("diskio","select time,sum(read_bytes)/(1024*1024) as read_MB,sum(read_time) as read_time,sum(write_bytes)/(1024*1024) as write_MB,sum(write_time) as write_time from diskio where host = ? group by time,host");
		put("diskio","select a.time as time, case a.read_time-b.read_time when 0 then 0 else (a.read_MB - b.read_MB)/((a.read_time-b.read_time)/1000) end as \"read(MB/S)\",case a.write_time-b.write_time when 0 then 0 else (a.write_MB - b.write_MB)/((a.write_time-b.write_time)/1000) end as \"write(MB/S)\" from (select time,host,sum(read_bytes)/(1024*1024) as read_MB,sum(read_time) as read_time,sum(write_bytes)/(1024*1024) as write_MB,sum(write_time) as write_time from diskio group by time,host) as a join (select time,host,sum(read_bytes)/(1024*1024) as read_MB,sum(read_time) as read_time,sum(write_bytes)/(1024*1024) as write_MB,sum(write_time) as write_time from diskio group by time,host) as b on a.host = b.host and a.time > b.time where a.host = ? group by a.time,a.host");
		}};  
	/**
	 * 获取jmeter性能测试期间的jmeter数据，tps、平均响应时间等
	 * @param dbname  sqlite db 路径 + 文件名称，例如："/target/jmeter-reports/10min-go.db"
	 * @param sqlstr 查询sql语句，例如：
	 * @param params 如果sql中有 ? 替换参数，这个参数不定长对应?， 可用于查询指定时间范围的数据
	 * @return 返回Map<String,Object>格式数据 Object 为 List<String>，对应查询出的结果
	 */
	public static Map<String,Object> ChartSqlite_jmeter(String dbname, String jmeterPrefix, String sqlstr,String... params){
		String sqltmpstr = sqlstr.replace("jmeter.", jmeterPrefix);
		Map<String,Object> chartdata = new LinkedHashMap<String, Object>(); 
		List<List<String>> tv = SQLite_Act.selectData(dbname, sqltmpstr,params);
		// 查询出的结果list中第一个是表头title信息，需要排除
		//System.out.println(tv); //[[time, value], [2017-05-08T03:04:25Z, 101.0], [2017-05-08T03:04:26Z, 100.0]]
		//System.out.println(tv.get(0));//列头title，例如 [time, value]
		// 构造返回值，用户绘图，x轴固定，y轴可能有多组数据，例如不同ip的cpu使用率需要分开
		List<String> xvalue = new ArrayList<String>();
		List<String> yvalue = new ArrayList<String>();
		for(int i=1;i<tv.size();i++){
			xvalue.add(tv.get(i).get(0));
			yvalue.add(tv.get(i).get(1));
		}
		chartdata.put("time", xvalue);
		chartdata.put("value", yvalue);
		
		return chartdata;
	}
	
	public static Map<String,List<String>> ChartSqlite_linux(String dbname, String sqlstr){
		int table_start = sqlstr.toUpperCase().indexOf("from ".toUpperCase()) + 5;
		String table_tmp = sqlstr.substring(table_start).trim();
		int table_end = table_tmp.indexOf(" ");
		return ChartSqlite_linux(dbname,sqlstr,table_tmp.substring(0,table_end).trim());
	}
	/**
	 * 获取jmeter性能测试期间的收集到的linux server性能数据，cpu、io等
	 * @param dbname  sqlite db 路径 + 文件名称，例如："/target/jmeter-reports/10min-go.db"
	 * @param sqlstr 查询sql语句，例如：
	 * @param params 如果sql中有 ? 替换参数，这个参数不定长对应?， 可用于查询指定时间范围的数据
	 * @return 返回Map<String,Object>格式数据 Object 为 List<String>，对应查询出的结果
	 */
	public static Map<String,List<String>> ChartSqlite_linux(String dbname, String sqlstr, String tablename){
		Map<String,List<String>> chartdata = new LinkedHashMap<String, List<String>>(); 
		
		//int table_start = sqlstr.toUpperCase().indexOf("from ".toUpperCase()) + 5;
		//String table_tmp = sqlstr.substring(table_start).trim();
		//int table_end = table_tmp.indexOf(" ");
		//String hostselsql = String.format("select host from %s group by host", table_tmp.substring(0,table_end).trim());
		String hostselsql = String.format("select host from %s group by host", tablename);
		
		List<List<String>> hosts = SQLite_Act.selectData(dbname, hostselsql);
		//System.out.println(hosts); //[[host], [yuntools-perf-agent1.localdomain], [yuntools-perf-agent2.localdomain]]
		for (int i = 1; i<hosts.size(); i++){
			List<List<String>> tv = SQLite_Act.selectData(dbname, sqlstr,hosts.get(i).get(0));
			boolean istime = false;
			if(chartdata.containsKey("time") == false){
				chartdata.put("time", new ArrayList<String>());
				istime = true;
			}
			for (int mapkey = 1; mapkey<tv.get(0).size();mapkey++){
				chartdata.put(hosts.get(i).get(0)+"_"+tv.get(0).get(mapkey), new ArrayList<String>());
			}
			//System.out.println(chartdata);
			for(int j=1;j<tv.size();j++){
				for (int mapkey = 0; mapkey<tv.get(j).size();mapkey++){
					if (mapkey == 0){
						if (istime)
							chartdata.get("time").add(tv.get(j).get(0));
					}else{
						chartdata.get(hosts.get(i).get(0)+"_"+tv.get(0).get(mapkey)).add(tv.get(j).get(mapkey));
					}
				}
			}
		}
		return chartdata;
	}
	
	public static Map<String,Object> ChartDataAll(String dbfile,String jmeterPrefix){
		Map<String,Object> chartdata = new LinkedHashMap<String, Object>(); 
		chartdata.put("tps_all", ChartSqlite_jmeter(dbfile,jmeterPrefix,JmeterChartSqlite.jmetersqlstr.get("tps_all")));
		chartdata.put("tps_ok", ChartSqlite_jmeter(dbfile,jmeterPrefix,JmeterChartSqlite.jmetersqlstr.get("tps_ok")));
		chartdata.put("avg", ChartSqlite_jmeter(dbfile,jmeterPrefix,JmeterChartSqlite.jmetersqlstr.get("avg")));
		chartdata.put("pct90", ChartSqlite_jmeter(dbfile,jmeterPrefix,JmeterChartSqlite.jmetersqlstr.get("pct90")));
		chartdata.put("users", ChartSqlite_jmeter(dbfile,jmeterPrefix,JmeterChartSqlite.jmetersqlstr.get("users")));
		chartdata.put("cpu-total", ChartSqlite_linux(dbfile,JmeterChartSqlite.linuxsqlstr.get("cpu-total")));
		chartdata.put("load", ChartSqlite_linux(dbfile,JmeterChartSqlite.linuxsqlstr.get("load")));
		chartdata.put("mem/G", ChartSqlite_linux(dbfile,JmeterChartSqlite.linuxsqlstr.get("mem")));
		chartdata.put("swap/G", ChartSqlite_linux(dbfile,JmeterChartSqlite.linuxsqlstr.get("swap")));
		chartdata.put("diskio", ChartSqlite_linux(dbfile,JmeterChartSqlite.linuxsqlstr.get("diskio"),"diskio"));
		return chartdata;
	}
	

	public static void main( String args[] )
	{
		String haha = "select time,load1,load15 from \"system\" where host = ?";
		int table_start = haha.toUpperCase().indexOf("from ".toUpperCase()) + 5;
		String table_tmp = haha.substring(table_start).trim();
		int table_end = table_tmp.indexOf(" ");
		System.out.println(table_tmp.substring(0,table_end).trim());
		
		String db1 = "target/jmeter-reports/10min-go.db";
		String db2 = "target/jmeter-reports/10min-go_20170508_110422.db";

		Map<String, Object> chart_tps = ChartSqlite_jmeter(db2,"jmeter.",JmeterChartSqlite.jmetersqlstr.get("users"));
		System.out.println(chart_tps);
		//{avg1=12.871, avg2=10.103, oktps1=121.080, oktps2=117.280, allcount1=28874.0, allcount2=33604.0, cpu1_1=yuntools-perf-agent1.localdomain|0.511%us|0.212%sy, cpu1_2=yuntools-perf-agent2.localdomain|0.068%us|0.076%sy, cpu2_1=yuntools-perf-agent1.localdomain|0.526%us|0.197%sy, cpu2_2=yuntools-perf-agent2.localdomain|0.077%us|0.075%sy, io1_1=yuntools-perf-agent1.localdomain|161.70732421875M|211.106416015625M, io1_2=yuntools-perf-agent2.localdomain|73.92216796875M|135.529619140625M, io2_1=yuntools-perf-agent1.localdomain|162.75498046875M|211.771416015625M, io2_2=yuntools-perf-agent2.localdomain|73.92216796875M|135.615380859375M}
		System.out.println("----linux--------");
		Map<String, List<String>> chart_linux = ChartSqlite_linux(db2,JmeterChartSqlite.linuxsqlstr.get("cpu-total"));
		System.out.println(chart_linux);
		System.out.println("----all--------");
		Map<String, Object> chartall = ChartDataAll("target/jmeter-reports/10min-go.db","jmeter.");
		System.out.println(chartall);

	}
}
