/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.service.impl;

import com.xn.common.entity.BeanUtils;
import com.xn.common.utils.CollectionUtils;
import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;
import com.xn.interfacetest.dao.TimeConfigMapper;
import com.xn.interfacetest.dto.TimeConfigDto;
import com.xn.interfacetest.entity.TimeConfig;
import com.xn.interfacetest.service.TimeConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;



/**
 * TimeConfig Service实现
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
@Transactional
public class TimeConfigServiceImpl implements TimeConfigService {

    /**
     *  Dao
     */
    @Autowired
    private TimeConfigMapper timeConfigMapper;

    @Override
    @Transactional(readOnly = true)
    public TimeConfigDto get(Object condition)
	{  
        TimeConfig timeConfig = timeConfigMapper.get(condition);
        TimeConfigDto timeConfigDto = BeanUtils.toBean(timeConfig,TimeConfigDto.class);
	    return timeConfigDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(TimeConfigDto condition) {
        return timeConfigMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimeConfigDto> list(TimeConfigDto condition) {
        List<TimeConfig> list = timeConfigMapper.list(condition);
        List<TimeConfigDto> dtoList = CollectionUtils.transform(list, TimeConfigDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimeConfigDto> list(Map<String,Object> condition) {
        List<TimeConfig> list = timeConfigMapper.list(condition);
        List<TimeConfigDto> dtoList = CollectionUtils.transform(list, TimeConfigDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<TimeConfigDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public TimeConfigDto save(TimeConfigDto timeConfigDto) {
        TimeConfig timeConfig = BeanUtils.toBean(timeConfigDto,TimeConfig.class);
        timeConfigMapper.save(timeConfig);
        timeConfigDto.setId(timeConfig.getId());
        return timeConfigDto;
    }

    @Override
    public int save(List<TimeConfigDto> timeConfigDtos) {
        if (timeConfigDtos == null || timeConfigDtos.isEmpty()) {
            return 0;
        }
        List<TimeConfig> timeConfigs = CollectionUtils.transform(timeConfigDtos, TimeConfig.class);
        return timeConfigMapper.saveBatch(timeConfigs);
    }

    @Override
    public int update(TimeConfigDto timeConfigDto) {
        TimeConfig timeConfig = BeanUtils.toBean(timeConfigDto,TimeConfig.class);
        return timeConfigMapper.update(timeConfig);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return timeConfigMapper.deleteByPK(id);
    }

    @Override
    public int delete(TimeConfigDto timeConfigDto) {
        TimeConfig timeConfig = BeanUtils.toBean(timeConfigDto,TimeConfig.class);
        return timeConfigMapper.delete(timeConfig);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return timeConfigMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<TimeConfigDto> timeConfigs) {
        return 0;
    }

}
