package com.xn.performance.service.impl;/**
 * Created by xn056839 on 2017/5/17.
 */

import com.xn.performance.api.PerformanceReportService;
import com.xn.performance.util.jmeter.JmeterChartInfluxdb;
import com.xn.performance.util.jmeter.JmeterChartSqlite;
import com.xn.performance.util.jmeter.SQLite_Act;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.xn.performance.util.PropertyUtil.getProperty;

@Service
public class PerformanceReportServiceImpl implements PerformanceReportService {
    public static String BEFORE = getProperty("reports");
    public static String AFTER = ".db";

    @Override
    public Map<String, Object> generateReport(List<String> dbnames) {

        Map<String, Object> report1 = new LinkedHashMap<String, Object>();
        report1.put("dbnum", dbnames.size());

        List<Map> jsonObjects = new ArrayList<Map>();

        //获取平均响应时间 Average response time
        Map<String, String> avgmap = new LinkedHashMap<String, String>();
        avgmap.put("title", "avg");
        for (int i = 0; i < dbnames.size(); i++) {
            //SQLite_Act.gettablename(dbnames[i]);
            List<List<String>> tv = SQLite_Act.selectData(BEFORE + dbnames.get(i) + AFTER, "select avg(value) as avg from \"jmeter.all.ok.avg\"");
            String avg = tv.get(1).get(0);
            if (avg.indexOf('.') > 0) {
                if (avg.substring(avg.indexOf('.')).length() > 4)
                    avg = avg.substring(0, avg.indexOf('.') + 4);
            }
            avgmap.put("db" + (i + 1), avg);
        }
        jsonObjects.add(avgmap);

        Map<String, String> tpsmap = new LinkedHashMap<String, String>();
        tpsmap.put("title", "tps");
        for (int i = 0; i < dbnames.size(); i++) {
            //获取tps
            List<List<String>> tv = SQLite_Act.selectData(BEFORE + dbnames.get(i) + AFTER, "select avg(value) from \"jmeter.all.ok.count\"");
            String tps = tv.get(1).get(0);
            if (tps.indexOf('.') > 0) {
                if (tps.substring(tps.indexOf('.')).length() > 4)
                    tps = tps.substring(0, tps.indexOf('.') + 4);
            }
            tpsmap.put("db" + (i + 1), tps);
        }
        jsonObjects.add(tpsmap);

        Map<String, String> allcount = new LinkedHashMap<>();
        allcount.put("title", "allcount");
        for (int i = 0; i < dbnames.size(); i++) {
            //获取请求总数
            List<List<String>> tv = SQLite_Act.selectData(BEFORE + dbnames.get(i) + AFTER, "select sum(value) from \"jmeter.all.h.count\"");
            allcount.put("db" + (i + 1), tv.get(1).get(0));
        }
        jsonObjects.add(allcount);

        Map<String, String> cpumap = new LinkedHashMap<String, String>();
        cpumap.put("title", "cpu");
        for (int i = 0; i < dbnames.size(); i++) {
            //获取cpu使用率
            List<List<String>> tv = SQLite_Act.selectData(BEFORE + dbnames.get(i) + AFTER, "select host,avg(usage_system) as sy,avg(usage_user) as us from \"cpu\" where cpu = 'cpu-total' group by host");
            String cpuvalue = "";
            for (int j = 1; j < tv.size(); j++) {
                String us = tv.get(j).get(2);
                if (us.indexOf('.') > 0) {
                    if (us.substring(us.indexOf('.')).length() > 4)
                        us = us.substring(0, us.indexOf('.') + 4);
                }
                String sy = tv.get(j).get(1);
                if (sy.indexOf('.') > 0) {
                    if (sy.substring(sy.indexOf('.')).length() > 4)
                        sy = sy.substring(0, sy.indexOf('.') + 4);
                }
                if (cpuvalue.equals("")) {
                    cpuvalue = tv.get(j).get(0) + ":" + us + "%us" + "|" + sy + "%sy";
                } else {
                    cpuvalue = cpuvalue + ";" + tv.get(j).get(0) + ":" + us + "%us" + "|" + sy + "%sy";
                }
                //cpumap.put("db"+(i+1)+"_"+j, tv.get(j).get(0)+"|"+us+"%us"+"|"+sy+"%sy");
            }
            cpumap.put("db" + (i + 1), cpuvalue);
        }
        jsonObjects.add(cpumap);

        Map<String, String> iomap = new LinkedHashMap<String, String>();
        iomap.put("title", "io");
        for (int i = 0; i < dbnames.size(); i++) {
            //获取磁盘io数据
            List<List<String>> tv = SQLite_Act.selectData(BEFORE + dbnames.get(i) + AFTER, "select host,avg(read_bytes) as read,avg(write_bytes) as write from \"diskio\" group by host");
            String iovalue = "";
            for (int j = 1; j < tv.size(); j++) {
                String danwei = "";
                String read = tv.get(j).get(1);
                if (Double.valueOf(read) <= 1024) {
                    danwei = "bytes(r)";
                } else if (Double.valueOf(read) > 1024 && Double.valueOf(read) <= (1024 * 1024)) {
                    read = Double.toString(Double.valueOf(read) / 1024);
                    danwei = "Kb(r)";
                } else {
                    read = Double.toString(Double.valueOf(read) / (1024 * 1024));
                    danwei = "M(r)";
                }
                if (read.indexOf('.') > 0) {
                    if (read.substring(read.indexOf('.')).length() > 4)
                        read = read.substring(0, read.indexOf('.') + 4);
                }
                read = read + danwei;

                String write = tv.get(j).get(2);
                if (Double.valueOf(write) <= 1024) {
                    danwei = "bytes(w)";
                } else if (Double.valueOf(write) > 1024 && Double.valueOf(write) <= (1024 * 1024)) {
                    write = Double.toString(Double.valueOf(write) / 1024);
                    danwei = "Kb(w)";
                } else {
                    write = Double.toString(Double.valueOf(write) / (1024 * 1024));
                    danwei = "M(w)";
                }
                if (write.indexOf('.') > 0) {
                    if (write.substring(write.indexOf('.')).length() > 4)
                        write = read.substring(0, write.indexOf('.') + 4);
                }
                write = write + danwei;

                if (iovalue.equals("")) {
                    iovalue = tv.get(j).get(0) + ":" + read + "|" + write;
                } else {
                    iovalue = iovalue + ";" + tv.get(j).get(0) + ":" + read + "|" + write;
                }
                //iomap.put("db"+(i+1)+"_"+j, tv.get(j).get(0)+"|"+read+"|"+write);
            }
            iomap.put("db" + (i + 1), iovalue);
        }
        jsonObjects.add(iomap);
        report1.put("report", jsonObjects);

        return report1;
    }





    public Map<String, Object> chartDataAll(Integer id) {
        String dbfile = getProperty("reports") + id + ".db";
        String jmeterPrefix = String.valueOf(id) + ".";
        Map<String, Object> chartdata = new LinkedHashMap<>();
        chartdata=JmeterChartSqlite.ChartDataAll(dbfile,jmeterPrefix);
        return chartdata;
    }

    @Override
    public Map<String, Object> chartInfluxdbData_start_end(String jmeterPrefix, long starttime, long endtime) {
        Map<String, Object> chartdatas = new LinkedHashMap<String, Object>();
        chartdatas=JmeterChartInfluxdb.ChartInfluxdbData_start_end(jmeterPrefix,starttime,endtime);
        return chartdatas;
    }
}
