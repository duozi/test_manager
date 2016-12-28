/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.assertBean.redisAssert.service.impl;

import com.xn.autotest.bean.BeanUtils;
import com.xn.autotest.dao.RedisAssertMapper;
import com.xn.autotest.bean.assertBean.redisAssert.dto.RedisAssertDto;
import com.xn.autotest.bean.assertBean.redisAssert.entity.RedisAssert;
import com.xn.autotest.bean.assertBean.redisAssert.service.RedisAssertService;
import com.xn.autotest.mybatis.PageInfo;
import com.xn.autotest.mybatis.PageResult;
import com.xn.autotest.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;



/**
 * RedisAssert Service实现
 * 
 * @author xn056839
 * @date 2016-12-22
 */
@Service
public class RedisAssertServiceImpl implements RedisAssertService {

    /**
     *  Dao
     */
    @Resource
    RedisAssertMapper redisAssertMapper;

    @Override
    public RedisAssertDto get(Object condition)
	{  
        RedisAssert redisAssert = redisAssertMapper.get(condition);
        RedisAssertDto redisAssertDto = BeanUtils.toBean(redisAssert,RedisAssertDto.class);
	    return redisAssertDto;  
	}  

    @Override
    public long count(RedisAssertDto condition) {
        return redisAssertMapper.count(condition);
    }

    @Override
    public List<RedisAssertDto> list(RedisAssertDto condition) {
        List<RedisAssert> list = redisAssertMapper.list(condition);
        List<RedisAssertDto> dtoList = CollectionUtils.transform(list, RedisAssertDto.class);
        return dtoList;
    }

    @Override
    public List<RedisAssertDto> list(Map<String,Object> condition) {
        List<RedisAssert> list = redisAssertMapper.list(condition);
        List<RedisAssertDto> dtoList = CollectionUtils.transform(list, RedisAssertDto.class);
        return dtoList;
    }
    
    @Override
    public PageResult<RedisAssertDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public RedisAssertDto save(RedisAssertDto redisAssertDto) {
        RedisAssert redisAssert = BeanUtils.toBean(redisAssertDto,RedisAssert.class);
        redisAssertMapper.save(redisAssert);
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
    public int deleteByPK(Integer id) {
        return redisAssertMapper.deleteByPK(id);
    }

    @Override
    public int delete(RedisAssertDto redisAssertDto) {
        RedisAssert redisAssert = BeanUtils.toBean(redisAssertDto,RedisAssert.class);
        return redisAssertMapper.delete(redisAssert);
    }
    
    @Override
    public int deleteBatchByPK(List<Integer> ids) {
        return redisAssertMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<RedisAssertDto> redisAsserts) {
        return 0;
    }

}
