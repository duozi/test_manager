package com.xn.performance.service.impl;/**
 * Created by xn056839 on 2017/3/7.
 */

import com.xn.performance.dto.PerformancePlanShowDto;
import com.xn.performance.dto.PerformanceResultDto;
import com.xn.performance.service.JmeterService;
import com.xn.performance.service.PerformanceResultService;
import com.xn.performance.util.jmeter.XNJmeterStartRemot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.xn.performance.service.impl.StartUp.PERFORMANCE_Map;


@Service
public class JmeterServiceImpl implements JmeterService {
    private static final Logger logger = LoggerFactory.getLogger(JmeterServiceImpl.class);
    @Autowired
    PerformanceResultService performanceResultService;

    XNJmeterStartRemot xnJmeterStartRemot = new XNJmeterStartRemot();

    public void execute(String stressMachineIp) {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String ip = addr.getHostAddress();//获得本机IP
            xnJmeterStartRemot.remoteStart(stressMachineIp, ip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void executePlan(String executeType, PerformanceResultDto performanceResultDto) {

        //立即执行
        if (executeType.equals("now")) {
            List<PerformancePlanShowDto> performancePlanShowDtoList = performanceResultService.getTask(performanceResultDto);

            addToQueue(performancePlanShowDtoList);

        }
        //定时执行
        else {

        }
    }

    public void addToQueue(List<PerformancePlanShowDto> list) {
        for (PerformancePlanShowDto performancePlanShowDto : list) {
            Integer stressMachineId = performancePlanShowDto.getStressMachineId();
            if (PERFORMANCE_Map.containsKey(stressMachineId)) {
                PERFORMANCE_Map.get(stressMachineId).add(performancePlanShowDto);
            } else {
                ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();
                queue.offer(performancePlanShowDto);
                PERFORMANCE_Map.put(stressMachineId, queue);

            }

        }
    }


    public boolean stopPlan(String ip) {
        return xnJmeterStartRemot.stop();
    }


}
