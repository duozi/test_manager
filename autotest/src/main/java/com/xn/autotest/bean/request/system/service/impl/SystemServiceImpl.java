/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.request.system.service.impl;

import com.xn.autotest.bean.BeanUtils;
import com.xn.autotest.dao.SystemMapper;
import com.xn.autotest.bean.request.system.dto.SystemDto;
import com.xn.autotest.bean.request.system.entity.System;
import com.xn.autotest.bean.request.system.service.SystemService;
import com.xn.autotest.mybatis.PageInfo;
import com.xn.autotest.mybatis.PageResult;
import com.xn.autotest.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * System Service实现
 * 
 * @author xn056839
 * @date 2016-12-22
 */
@Service
public class SystemServiceImpl implements SystemService {

    /**
     *  Dao
     */
    @Autowired
    private SystemMapper systemMapper;

    @Override
    public SystemDto get(Object condition)  
	{  
        System system = systemMapper.get(condition);
        SystemDto systemDto = BeanUtils.toBean(system,SystemDto.class);
	    return systemDto;  
	}  

    @Override
    public long count(SystemDto condition) {
        return systemMapper.count(condition);
    }

    @Override
    public List<SystemDto> list(SystemDto condition) {
        List<System> list = systemMapper.list(condition);
        List<SystemDto> dtoList = CollectionUtils.transform(list, SystemDto.class);
        return dtoList;
    }

    @Override
    public List<SystemDto> list(Map<String,Object> condition) {
        List<System> list = systemMapper.list(condition);
        List<SystemDto> dtoList = CollectionUtils.transform(list, SystemDto.class);
        return dtoList;
    }
    
    @Override
    public PageResult<SystemDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public SystemDto save(SystemDto systemDto) {
        System system = BeanUtils.toBean(systemDto,System.class);
        systemMapper.save(system);
        systemDto.setId(system.getId());
        return systemDto;
    }

    @Override
    public int save(List<SystemDto> systemDtos) {
        if (systemDtos == null || systemDtos.isEmpty()) {
            return 0;
        }
        List<System> systems = CollectionUtils.transform(systemDtos, System.class);
        return systemMapper.saveBatch(systems);
    }

    @Override
    public int update(SystemDto systemDto) {
        System system = BeanUtils.toBean(systemDto,System.class);
        return systemMapper.update(system);
    }
    
    @Override
    public int deleteByPK(Integer id) {
        return systemMapper.deleteByPK(id);
    }

    @Override
    public int delete(SystemDto systemDto) {
        System system = BeanUtils.toBean(systemDto,System.class);
        return systemMapper.delete(system);
    }
    
    @Override
    public int deleteBatchByPK(List<Integer> ids) {
        return systemMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<SystemDto> systems) {
        return 0;
    }

}
