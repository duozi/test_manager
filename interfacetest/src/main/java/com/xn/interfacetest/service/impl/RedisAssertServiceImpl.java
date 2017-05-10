/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xn.common.utils.BeanUtils;
import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;
import com.xn.interfacetest.api.RedisAssertService;
import com.xn.interfacetest.dao.RedisAssertMapper;
import com.xn.interfacetest.dto.RedisAssertDto;
import com.xn.interfacetest.entity.RedisAssert;
import com.xn.interfacetest.util.CollectionUtils;



/**
 * RedisAssert Service实现
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
@Transactional
public class RedisAssertServiceImpl implements RedisAssertService {

    /**
     *  Dao
     */
    @Autowired
    private RedisAssertMapper redisAssertMapper;

    @Override
    @Transactional(readOnly = true)
    public RedisAssertDto get(Object condition)
	{  
        RedisAssert redisAssert = redisAssertMapper.get(condition);
        RedisAssertDto redisAssertDto = BeanUtils.toBean(redisAssert,RedisAssertDto.class);
	    return redisAssertDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(RedisAssertDto condition) {
        return redisAssertMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RedisAssertDto> list(RedisAssertDto condition) {
        List<RedisAssert> list = redisAssertMapper.list(condition);
        List<RedisAssertDto> dtoList = CollectionUtils.transform(list, RedisAssertDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RedisAssertDto> list(Map<String,Object> condition) {
        List<RedisAssert> list = redisAssertMapper.list(condition);
        List<RedisAssertDto> dtoList = CollectionUtils.transform(list, RedisAssertDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<RedisAssertDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public RedisAssertDto save(RedisAssertDto redisAssertDto) {
        RedisAssert redisAssert = BeanUtils.toBean(redisAssertDto,RedisAssert.class);
        if(null == redisAssertDto.getId()){
            redisAssertMapper.save(redisAssert);
        } else {
            redisAssertMapper.update(redisAssert);
        }

        redisAssertDto.setId(redisAssert.getId());
        return redisAssertDto;
    }

    @Override
    public int save(List<RedisAssertDto> redisAssertDtos) {
        if (redisAssertDtos == null || redisAssertDtos.isEmpty()) {
            return 0;
        }
        List<RedisAssert> redisAsserts = CollectionUtils.transform(redisAssertDtos, RedisAssert.class);
        return redisAssertMapper.saveBatch(redisAsserts);
    }

    @Override
    public int update(RedisAssertDto redisAssertDto) {
        RedisAssert redisAssert = BeanUtils.toBean(redisAssertDto,RedisAssert.class);
        return redisAssertMapper.update(redisAssert);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return redisAssertMapper.deleteByPK(id);
    }

    @Override
    public int delete(RedisAssertDto redisAssertDto) {
        RedisAssert redisAssert = BeanUtils.toBean(redisAssertDto,RedisAssert.class);
        return redisAssertMapper.delete(redisAssert);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return redisAssertMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<RedisAssertDto> redisAsserts) {
        return 0;
    }

    @Override
    public List<RedisAssertDto> getByCaseId(Long caseId) {
        List<RedisAssert> list = redisAssertMapper.getByCaseId(caseId);
        List<RedisAssertDto> dtoList = CollectionUtils.transform(list, RedisAssertDto.class);
        return dtoList;
    }

}
