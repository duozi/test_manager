/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.performance.service.impl;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.xn.performance.dao.PerformanceStressMachineMapper;
import com.xn.performance.dto.PerformanceStressMachineDto;
import com.xn.performance.entity.PerformanceStressMachine;
import com.xn.performance.mybatis.PageInfo;
import com.xn.performance.mybatis.PageResult;
import com.xn.performance.service.PerformanceStressMachineService;
import com.xn.performance.util.BeanUtils;
import com.xn.performance.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;


/**
 * PerformanceStressMachine Service实现
 * 
 * @author zhouxi
 * @date 2017-02-21
 */
@Service
public class PerformanceStressMachineServiceImpl implements PerformanceStressMachineService {

    /**
     *  Dao
     */
    @Autowired
    private PerformanceStressMachineMapper performanceStressMachineMapper;

    @Override
    public PerformanceStressMachineDto get(Object condition)  
	{  
        PerformanceStressMachine performanceStressMachine = performanceStressMachineMapper.get(condition);
        PerformanceStressMachineDto performanceStressMachineDto = BeanUtils.toBean(performanceStressMachine,PerformanceStressMachineDto.class);
	    return performanceStressMachineDto;  
	}  

    @Override
    public long count(PerformanceStressMachineDto condition) {
        return performanceStressMachineMapper.count(condition);
    }

    @Override
    public List<PerformanceStressMachineDto> list(PerformanceStressMachineDto condition) {
        List<PerformanceStressMachine> list = performanceStressMachineMapper.list(condition);
        List<PerformanceStressMachineDto> dtoList = CollectionUtils.transform(list, PerformanceStressMachineDto.class);
        return dtoList;
    }

    @Override
    public List<PerformanceStressMachineDto> list(Map<String,Object> condition) {
        List<PerformanceStressMachine> list = performanceStressMachineMapper.list(condition);
        List<PerformanceStressMachineDto> dtoList = CollectionUtils.transform(list, PerformanceStressMachineDto.class);
        return dtoList;
    }
    
    @Override
    public PageResult<PerformanceStressMachineDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public PerformanceStressMachineDto save(PerformanceStressMachineDto performanceStressMachineDto) {
        PerformanceStressMachine performanceStressMachine = BeanUtils.toBean(performanceStressMachineDto,PerformanceStressMachine.class);
        performanceStressMachineMapper.save(performanceStressMachine);
        performanceStressMachineDto.setId(performanceStressMachine.getId());
        return performanceStressMachineDto;
    }

    @Override
    public int save(List<PerformanceStressMachineDto> performanceStressMachineDtos) {
        if (performanceStressMachineDtos == null || performanceStressMachineDtos.isEmpty()) {
            return 0;
        }
        List<PerformanceStressMachine> performanceStressMachines = CollectionUtils.transform(performanceStressMachineDtos, PerformanceStressMachine.class);
        return performanceStressMachineMapper.saveBatch(performanceStressMachines);
    }

    @Override
    public int update(PerformanceStressMachineDto performanceStressMachineDto) {
        PerformanceStressMachine performanceStressMachine = BeanUtils.toBean(performanceStressMachineDto,PerformanceStressMachine.class);
        return performanceStressMachineMapper.update(performanceStressMachine);
    }
    
    @Override
    public int deleteByPK(Integer id) {
        return performanceStressMachineMapper.deleteByPK(id);
    }

    @Override
    public int delete(PerformanceStressMachineDto performanceStressMachineDto) {
        PerformanceStressMachine performanceStressMachine = BeanUtils.toBean(performanceStressMachineDto,PerformanceStressMachine.class);
        return performanceStressMachineMapper.delete(performanceStressMachine);
    }
    
    @Override
    public int deleteBatchByPK(List<Integer> ids) {
        return performanceStressMachineMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<PerformanceStressMachineDto> performanceStressMachines) {
        return 0;
    }

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }

    @Override
    public boolean testLink(String ip,String username,String password) {

        try {
            return InetAddress.getByName(ip).isReachable(2000);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
