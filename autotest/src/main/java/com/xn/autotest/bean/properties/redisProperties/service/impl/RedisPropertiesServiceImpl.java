/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.properties.redisProperties.service.impl;

import com.xn.autotest.bean.BeanUtils;
import com.xn.autotest.dao.RedisPropertiesMapper;
import com.xn.autotest.bean.properties.redisProperties.dto.RedisPropertiesDto;
import com.xn.autotest.bean.properties.redisProperties.entity.RedisProperties;
import com.xn.autotest.bean.properties.redisProperties.service.RedisPropertiesService;
import com.xn.autotest.mybatis.PageInfo;
import com.xn.autotest.mybatis.PageResult;
import com.xn.autotest.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * RedisProperties Service实现
 * 
 * @author xn056839
 * @date 2016-12-22
 */
@Service
public class RedisPropertiesServiceImpl implements RedisPropertiesService {

    /**
     *  Dao
     */
    @Autowired
    private RedisPropertiesMapper redisPropertiesMapper;

    @Override
    public RedisPropertiesDto get(Object condition)  
	{  
        RedisProperties redisProperties = redisPropertiesMapper.get(condition);
        RedisPropertiesDto redisPropertiesDto = BeanUtils.toBean(redisProperties,RedisPropertiesDto.class);
	    return redisPropertiesDto;  
	}  

    @Override
    public long count(RedisPropertiesDto condition) {
        return redisPropertiesMapper.count(condition);
    }

    @Override
    public List<RedisPropertiesDto> list(RedisPropertiesDto condition) {
        List<RedisProperties> list = redisPropertiesMapper.list(condition);
        List<RedisPropertiesDto> dtoList = CollectionUtils.transform(list, RedisPropertiesDto.class);
        return dtoList;
    }

    @Override
    public List<RedisPropertiesDto> list() {
        List<RedisProperties> list = redisPropertiesMapper.list(null);
        List<RedisPropertiesDto> dtoList = CollectionUtils.transform(list, RedisPropertiesDto.class);
        return dtoList;
    }

    @Override
    public List<RedisPropertiesDto> list(Map<String,Object> condition) {
        List<RedisProperties> list = redisPropertiesMapper.list(condition);
        List<RedisPropertiesDto> dtoList = CollectionUtils.transform(list, RedisPropertiesDto.class);
        return dtoList;
    }
    
    @Override
    public PageResult<RedisPropertiesDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public RedisPropertiesDto save(RedisPropertiesDto redisPropertiesDto) {
        RedisProperties redisProperties = BeanUtils.toBean(redisPropertiesDto,RedisProperties.class);
        redisPropertiesMapper.save(redisProperties);
        redisPropertiesDto.setId(redisProperties.getId());
        return redisPropertiesDto;
    }

    @Override
    public int save(List<RedisPropertiesDto> redisPropertiesDtos) {
        if (redisPropertiesDtos == null || redisPropertiesDtos.isEmpty()) {
            return 0;
        }
        List<RedisProperties> redisPropertiess = CollectionUtils.transform(redisPropertiesDtos, RedisProperties.class);
        return redisPropertiesMapper.saveBatch(redisPropertiess);
    }

    @Override
    public int update(RedisPropertiesDto redisPropertiesDto) {
        RedisProperties redisProperties = BeanUtils.toBean(redisPropertiesDto,RedisProperties.class);
        return redisPropertiesMapper.update(redisProperties);
    }
    
    @Override
    public int deleteByPK(Integer id) {
        return redisPropertiesMapper.deleteByPK(id);
    }

    @Override
    public int delete(RedisPropertiesDto redisPropertiesDto) {
        RedisProperties redisProperties = BeanUtils.toBean(redisPropertiesDto,RedisProperties.class);
        return redisPropertiesMapper.delete(redisProperties);
    }
    
    @Override
    public int deleteBatchByPK(List<Integer> ids) {
        return redisPropertiesMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<RedisPropertiesDto> redisPropertiess) {
        return 0;
    }

}
