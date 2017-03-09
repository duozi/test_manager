package com.xn.performance.service.impl;/**
 * Created by xn056839 on 2017/3/7.
 */

import com.xn.performance.dto.PerformanceResultDto;
import com.xn.performance.service.JmeterService;
import com.xn.performance.util.jmeter.XNJmeterStartRemot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.Date;

import static com.xn.performance.service.impl.StartUp.PERFORMANCE_QUEUE;

@Service
public class JmeterServiceImpl implements JmeterService {
    private static final Logger logger = LoggerFactory.getLogger(JmeterServiceImpl.class);


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
    public void executePlan(PerformanceResultDto performanceResultDto) {
       Date executeTime=performanceResultDto.getSetStartTime();
        //立即执行
        if (executeTime==null) {

            PERFORMANCE_QUEUE.add(performanceResultDto);
        }
        //定时执行
        else {

        }
    }

    public boolean stopPlan(String ip) {
        return xnJmeterStartRemot.stop();
    }


}
