package com.xn.performance.service.impl;/**
 * Created by xn056839 on 2017/5/17.
 */

import com.xn.performance.api.PerformanceReportService;
import com.xn.performance.util.jmeter.SQLite_Act;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.xn.performance.util.PropertyUtil.getProperty;

@Service
public class PerformanceReportServiceImpl implements PerformanceReportService {
    public static String BEFORE=getProperty("reports");
    public static String AFTER=".db";

    @Override
    public Map<String,Object> generateReport(List<String> dbnames) {

        Map<String,Object> report1 = new LinkedHashMap<String,Object>();
        report1.put("dbnum", dbnames.size());

        List<Map> jsonObjects = new ArrayList<Map>();

        //获取平均响应时间 Average response time
        Map<String,String> avgmap = new LinkedHashMap<String,String>();
        avgmap.put("title", "avg");
        for(int i=0;i<dbnames.size();i++){
            //SQLite_Act.gettablename(dbnames[i]);
            List<List<String>> tv = SQLite_Act.selectData(BEFORE+dbnames.get(i)+AFTER, "select avg(value) as avg from \"jmeter.all.ok.avg\"");
            String avg = tv.get(1).get(0);
            if (avg.indexOf('.')>0){
                if(avg.substring(avg.indexOf('.')).length()>4)
                    avg = avg.substring(0, avg.indexOf('.')+4);
            }
            avgmap.put("db"+(i+1), avg);
        }
        jsonObjects.add(avgmap);

        Map<String,String> tpsmap = new LinkedHashMap<String,String>();
        tpsmap.put("title", "tps");
        for(int i=0;i<dbnames.size();i++){
            //获取tps
            List<List<String>> tv = SQLite_Act.selectData(BEFORE+dbnames.get(i)+AFTER, "select avg(value) from \"jmeter.all.ok.count\"");
            String tps = tv.get(1).get(0);
            if (tps.indexOf('.')>0){
                if(tps.substring(tps.indexOf('.')).length()>4)
                    tps = tps.substring(0, tps.indexOf('.')+4);
            }
            tpsmap.put("db"+(i+1), tps);
        }
        jsonObjects.add(tpsmap);

        Map<String,String> allcount = new LinkedHashMap<>();
        allcount.put("title", "allcount");
        for(int i=0;i<dbnames.size();i++){
            //获取请求总数
            List<List<String>> tv = SQLite_Act.selectData(BEFORE+dbnames.get(i)+AFTER, "select sum(value) from \"jmeter.all.h.count\"");
            allcount.put("db"+(i+1), tv.get(1).get(0));
        }
        jsonObjects.add(allcount);

        Map<String,String> cpumap = new LinkedHashMap<String,String>();
        cpumap.put("title", "cpu");
        for(int i=0;i<dbnames.size();i++){
            //获取cpu使用率
            List<List<String>> tv = SQLite_Act.selectData(BEFORE+dbnames.get(i)+AFTER, "select host,avg(usage_system) as sy,avg(usage_user) as us from \"cpu\" where cpu = 'cpu-total' group by host");
            String cpuvalue = "";
            for(int j=1;j<tv.size();j++){
                String us = tv.get(j).get(2);
                if(us.indexOf('.')>0){
                    if(us.substring(us.indexOf('.')).length()>4)
                        us = us.substring(0, us.indexOf('.')+4);
                }
                String sy = tv.get(j).get(1);
                if(sy.indexOf('.')>0){
                    if(sy.substring(sy.indexOf('.')).length()>4)
                        sy = sy.substring(0, sy.indexOf('.')+4);
                }
                if (cpuvalue.equals("")){
                    cpuvalue = tv.get(j).get(0)+":"+us+"%us"+"|"+sy+"%sy";
                }else{
                    cpuvalue = cpuvalue + ";" + tv.get(j).get(0)+":"+us+"%us"+"|"+sy+"%sy";
                }
                //cpumap.put("db"+(i+1)+"_"+j, tv.get(j).get(0)+"|"+us+"%us"+"|"+sy+"%sy");
            }
            cpumap.put("db"+(i+1), cpuvalue);
        }
        jsonObjects.add(cpumap);

        Map<String,String> iomap = new LinkedHashMap<String,String>();
        iomap.put("title", "io");
        for(int i=0;i<dbnames.size();i++){
            //获取磁盘io数据
            List<List<String>> tv = SQLite_Act.selectData(BEFORE+dbnames.get(i)+AFTER, "select host,avg(read_bytes) as read,avg(write_bytes) as write from \"diskio\" group by host");
            String iovalue = "";
            for(int j=1;j<tv.size();j++){
                String danwei = "";
                String read = tv.get(j).get(1);
                if(Double.valueOf(read)<=1024){
                    danwei = "bytes(r)";
                }else if(Double.valueOf(read)>1024 && Double.valueOf(read)<=(1024*1024)){
                    read =  Double.toString(Double.valueOf(read)/1024);
                    danwei = "Kb(r)";
                }else{
                    read =  Double.toString(Double.valueOf(read)/(1024*1024));
                    danwei = "M(r)";
                }
                if(read.indexOf('.')>0){
                    if(read.substring(read.indexOf('.')).length()>4)
                        read = read.substring(0, read.indexOf('.')+4);
                }
                read = read + danwei;

                String write = tv.get(j).get(2);
                if(Double.valueOf(write)<=1024){
                    danwei = "bytes(w)";
                }else if(Double.valueOf(write)>1024 && Double.valueOf(write)<=(1024*1024)){
                    write =  Double.toString(Double.valueOf(write)/1024);
                    danwei = "Kb(w)";
                }else{
                    write =  Double.toString(Double.valueOf(write)/(1024*1024));
                    danwei = "M(w)";
                }
                if(write.indexOf('.')>0){
                    if(write.substring(write.indexOf('.')).length()>4)
                        write = read.substring(0, write.indexOf('.')+4);
                }
                write = write + danwei;

                if (iovalue.equals("")){
                    iovalue = tv.get(j).get(0)+":"+read+"|"+write;
                }else{
                    iovalue = iovalue + ";" + tv.get(j).get(0)+":"+read+"|"+write;
                }
                //iomap.put("db"+(i+1)+"_"+j, tv.get(j).get(0)+"|"+read+"|"+write);
            }
            iomap.put("db"+(i+1), iovalue);
        }
        jsonObjects.add(iomap);
        report1.put("report", jsonObjects);

        return report1;
    }
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
    public  Map<String,Object> ChartSqlite_jmeter(String dbname, String jmeterPrefix, String sqlstr,String... params){
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

    public  Map<String,List<String>> ChartSqlite_linux(String dbname, String sqlstr){
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
    public  Map<String,List<String>> ChartSqlite_linux(String dbname, String sqlstr, String tablename){
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

    public  Map<String,Object> chartDataAll(Integer id){
        String dbfile=getProperty("reports")+id+".db";
        String jmeterPrefix= String.valueOf(id)+".";
        Map<String,Object> chartdata = new LinkedHashMap<>();
        chartdata.put("tps_all", ChartSqlite_jmeter(dbfile,jmeterPrefix, jmetersqlstr.get("tps_all")));
        chartdata.put("tps_ok", ChartSqlite_jmeter(dbfile,jmeterPrefix,jmetersqlstr.get("tps_ok")));
        chartdata.put("avg", ChartSqlite_jmeter(dbfile,jmeterPrefix,jmetersqlstr.get("avg")));
        chartdata.put("pct90", ChartSqlite_jmeter(dbfile,jmeterPrefix,jmetersqlstr.get("pct90")));
        chartdata.put("users", ChartSqlite_jmeter(dbfile,jmeterPrefix,jmetersqlstr.get("users")));
        chartdata.put("cpu-total", ChartSqlite_linux(dbfile,linuxsqlstr.get("cpu-total")));
        chartdata.put("load", ChartSqlite_linux(dbfile,linuxsqlstr.get("load")));
        chartdata.put("mem/G", ChartSqlite_linux(dbfile,linuxsqlstr.get("mem")));
        chartdata.put("swap/G", ChartSqlite_linux(dbfile,linuxsqlstr.get("swap")));
        chartdata.put("diskio", ChartSqlite_linux(dbfile,linuxsqlstr.get("diskio"),"diskio"));
        return chartdata;
    }
}
