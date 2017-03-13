package com.xn.performance.service.impl;/**
 * Created by xn056839 on 2017/3/8.
 */

import com.xn.performance.dto.*;
import com.xn.performance.service.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class StartUp {
    private static final Logger logger = LoggerFactory.getLogger(StartUp.class);
    public static Map<Integer, ConcurrentLinkedQueue> PERFORMANCE_Map = new HashMap<Integer, ConcurrentLinkedQueue>();
    @Autowired
    PerformanceResultService performanceResultService;

    @Autowired
    PerformancePlanService performancePlanService;
    @Autowired
    JmeterService jmeterService;

    @Autowired
    PerformanceScriptService performanceScriptService;

    @Autowired
    PerformanceScenarioService performanceScenarioService;

    @PostConstruct
    public void getTask() {
        PerformanceResultDto performanceResultDto = new PerformanceResultDto();
        performanceResultDto.setExecuteStatus("未执行");
        List<PerformancePlanShowDto> list = performanceResultService.getTask(performanceResultDto);

        jmeterService.addToQueue(list);

    }


    public void executeOnce(PerformancePlanShowDto performancePlanShowDto) {
        //更新计划的状态为执行中
        PerformancePlanDto performancePlanDto = new PerformancePlanDto();
        performancePlanDto.setId(performancePlanShowDto.getPlanId());
        performancePlanDto.setPlanStatus("执行中");
        performancePlanService.update(performancePlanDto);
        //更新结果的状态为执行中
        PerformanceResultDto performanceResultDto = new PerformanceResultDto();
        performanceResultDto.setId(performancePlanShowDto.getId());
        performanceResultDto.setExecuteStatus("执行中");
        performanceResultService.update(performanceResultDto);

        //获得脚本
        Integer scriptId = performancePlanShowDto.getScriptId();
        PerformanceScriptDto performanceScriptDto = new PerformanceScriptDto();
        performanceScriptDto.setId(scriptId);
        performanceScriptDto = performanceScriptService.get(performanceScriptDto);
        //脚本的地址
        String scriptPath = performanceScriptDto.getPath();
        //获得场景
        Integer scenarioId = performancePlanShowDto.getScenarioId();
        PerformanceScenarioDto performanceScenarioDto = new PerformanceScenarioDto();
        performanceScenarioDto.setId(scenarioId);
        performanceScenarioDto = performanceScenarioService.get(performanceScenarioDto);


        generateJmeterScript(scriptPath,performanceScenarioDto);
    }

    public static void generateJmeterScript(String scriptPath, PerformanceScenarioDto performanceScenarioDto) {
        File script = new File(scriptPath);
        SAXReader saxReader = new SAXReader();

        //获得最新的jar的名字
        Document document = null;

        try {
            document = saxReader.read(new File(scriptPath));
        } catch (DocumentException e) {
            e.printStackTrace();
        }


        Element scenario = (Element) document.selectSingleNode("jmeterTestPlan/hashTree/hashTree/ThreadGroup");
        // 获取所有子元素
        List<Element> list = scenario.elements();
        for (Element element : list) {
            String name = element.attribute("name").getValue();
            //线程数
            if (name.equals("ThreadGroup.num_threads")) {
                element.setText(String.valueOf(performanceScenarioDto.getConcurrency()));
            }
            //所有线程在多少时间内启动完毕
            else if (name.equals("ThreadGroup.ramp_time")) {
                element.setText(String.valueOf(performanceScenarioDto.getStartup()));
            }

            //是否调用执行器
            else if (name.equals("ThreadGroup.scheduler")) {
                String scheduler = String.valueOf(performanceScenarioDto.getScheduler());
                element.setText(scheduler);
            }

            //开始时间
            else if (name.equals("ThreadGroup.start_time")) {
                String setStartTime = String.valueOf(performanceScenarioDto.getSetStartTime());
                element.setText(setStartTime);
            }
            //结束时间
            else if (name.equals("ThreadGroup.end_time")) {
                String setEndTime = String.valueOf(performanceScenarioDto.getSetEndTime());
                element.setText(setEndTime);
            }
            //持续时间
            else if (name.equals("ThreadGroup.duration")) {
                String executeTime = String.valueOf(performanceScenarioDto.getExecuteTime());
                element.setText(executeTime);
            }
            //延迟时间
            else if (name.equals("ThreadGroup.delay")) {
                String delayTime = String.valueOf(performanceScenarioDto.getDelayTime());
                element.setText(delayTime);
            }


        }

        Element cycle = (Element) document.selectSingleNode("jmeterTestPlan/hashTree/hashTree/ThreadGroup/elementProp/intProp");
        cycle.setText(String.valueOf(performanceScenarioDto.getCycle()));
        // 输出格式
        OutputFormat outformat = new OutputFormat();
        // 指定XML编码
        outformat.setEncoding("UTF-8");
        outformat.setNewlines(true);
        outformat.setIndent(true);
        outformat.setTrimText(true);

        //上传脚本名称
        String fileName = script.getName();
        //除名称之外的路径
        String path = scriptPath.split(fileName)[0];
        //jmeter脚本的名称
        String jmeterScriptFileName = "jmeter_script_" + fileName;

        try {
            OutputStream out = new FileOutputStream(path + jmeterScriptFileName);
            XMLWriter xmlwriter = new XMLWriter(out, outformat);
            xmlwriter.write(document);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
       PerformanceScenarioService performanceScenarioService=new PerformanceScenarioServiceImpl();
        PerformanceScenarioDto performanceScenarioDto=new PerformanceScenarioDto();
        performanceScenarioDto.setId(11);
        performanceScenarioDto=performanceScenarioService.get(performanceScenarioDto);
        generateJmeterScript("E:\\upload\\线程组.jmx", performanceScenarioDto);
    }

}
