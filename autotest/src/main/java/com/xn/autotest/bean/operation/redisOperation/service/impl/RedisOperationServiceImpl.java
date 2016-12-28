/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.operation.redisOperation.service.impl;

import com.xn.autotest.bean.BeanUtils;
import com.xn.autotest.dao.RedisOperationMapper;
import com.xn.autotest.bean.operation.redisOperation.dto.RedisOperationDto;
import com.xn.autotest.bean.operation.redisOperation.entity.RedisOperation;
import com.xn.autotest.bean.operation.redisOperation.service.RedisOperationService;
import com.xn.autotest.mybatis.PageInfo;
import com.xn.autotest.mybatis.PageResult;
import com.xn.autotest.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;




/**
 * RedisOperation Service实现
 * 
 * @author xn056839
 * @date 2016-12-22
 */

public class RedisOperationServiceImpl implements RedisOperationService {

    /**
     *  Dao
     */
    @Autowired
    private RedisOperationMapper redisOperationMapper;

    @Override
    public RedisOperationDto get(Object condition)
	{  
        RedisOperation redisOperation = redisOperationMapper.get(condition);
        RedisOperationDto redisOperationDto = BeanUtils.toBean(redisOperation,RedisOperationDto.class);
	    return redisOperationDto;  
	}  

    @Override
    public long count(RedisOperationDto condition) {
        return redisOperationMapper.count(condition);
    }

    @Override
    public List<RedisOperationDto> list(RedisOperationDto condition) {
        List<RedisOperation> list = redisOperationMapper.list(condition);
        List<RedisOperationDto> dtoList = CollectionUtils.transform(list, RedisOperationDto.class);
        return dtoList;
    }

    @Override
    public List<RedisOperationDto> list(Map<String,Object> condition) {
        List<RedisOperation> list = redisOperationMapper.list(condition);
        List<RedisOperationDto> dtoList = CollectionUtils.transform(list, RedisOperationDto.class);
        return dtoList;
    }
    
    @Override
    public PageResult<RedisOperationDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public RedisOperationDto save(RedisOperationDto redisOperationDto) {
        RedisOperation redisOperation = BeanUtils.toBean(redisOperationDto,RedisOperation.class);
        redisOperationMapper.save(redisOperation);
        redisOperationDto.setId(redisOperation.getId());
        return redisOperationDto;
    }

    @Override
    public int save(List<RedisOperationDto> redisOperationDtos) {
        if (redisOperationDtos == null || redisOperationDtos.isEmpty()) {
            return 0;
        }
        List<RedisOperation> redisOperations = CollectionUtils.transform(redisOperationDtos, RedisOperation.class);
        return redisOperationMapper.saveBatch(redisOperations);
    }

    @Override
    public int update(RedisOperationDto redisOperationDto) {
        RedisOperation redisOperation = BeanUtils.toBean(redisOperationDto,RedisOperation.class);
        return redisOperationMapper.update(redisOperation);
    }
    
    @Override
    public int deleteByPK(Integer id) {
        return redisOperationMapper.deleteByPK(id);
    }

    @Override
    public int delete(RedisOperationDto redisOperationDto) {
        RedisOperation redisOperation = BeanUtils.toBean(redisOperationDto,RedisOperation.class);
        return redisOperationMapper.delete(redisOperation);
    }
    
    @Override
    public int deleteBatchByPK(List<Integer> ids) {
        return redisOperationMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<RedisOperationDto> redisOperations) {
        return 0;
    }

}
