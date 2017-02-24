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
import com.xn.common.utils.CollectionUtils;
import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;
import com.xn.interfacetest.dao.ParamsAssertMapper;
import com.xn.interfacetest.dto.ParamsAssertDto;
import com.xn.interfacetest.entity.ParamsAssert;
import com.xn.interfacetest.service.ParamsAssertService;


/**
 * ParamsAssert Service实现
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
@Transactional
public class ParamsAssertServiceImpl implements ParamsAssertService {

    /**
     *  Dao
     */
    @Autowired
    private ParamsAssertMapper paramsAssertMapper;

    @Override
    @Transactional(readOnly = true)
    public ParamsAssertDto get(Object condition)
	{  
        ParamsAssert paramsAssert = paramsAssertMapper.get(condition);
        ParamsAssertDto paramsAssertDto = BeanUtils.toBean(paramsAssert,ParamsAssertDto.class);
	    return paramsAssertDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(ParamsAssertDto condition) {
        return paramsAssertMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParamsAssertDto> list(ParamsAssertDto condition) {
        List<ParamsAssert> list = paramsAssertMapper.list(condition);
        List<ParamsAssertDto> dtoList = CollectionUtils.transform(list, ParamsAssertDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParamsAssertDto> list(Map<String,Object> condition) {
        List<ParamsAssert> list = paramsAssertMapper.list(condition);
        List<ParamsAssertDto> dtoList = CollectionUtils.transform(list, ParamsAssertDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<ParamsAssertDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public ParamsAssertDto save(ParamsAssertDto paramsAssertDto) {
        ParamsAssert paramsAssert = BeanUtils.toBean(paramsAssertDto,ParamsAssert.class);
        paramsAssertMapper.save(paramsAssert);
        paramsAssertDto.setId(paramsAssert.getId());
        return paramsAssertDto;
    }

    @Override
    public int save(List<ParamsAssertDto> paramsAssertDtos) {
        if (paramsAssertDtos == null || paramsAssertDtos.isEmpty()) {
            return 0;
        }
        List<ParamsAssert> paramsAsserts = CollectionUtils.transform(paramsAssertDtos, ParamsAssert.class);
        return paramsAssertMapper.saveBatch(paramsAsserts);
    }

    @Override
    public int update(ParamsAssertDto paramsAssertDto) {
        ParamsAssert paramsAssert = BeanUtils.toBean(paramsAssertDto,ParamsAssert.class);
        return paramsAssertMapper.update(paramsAssert);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return paramsAssertMapper.deleteByPK(id);
    }

    @Override
    public int delete(ParamsAssertDto paramsAssertDto) {
        ParamsAssert paramsAssert = BeanUtils.toBean(paramsAssertDto,ParamsAssert.class);
        return paramsAssertMapper.delete(paramsAssert);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return paramsAssertMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<ParamsAssertDto> paramsAsserts) {
        return 0;
    }

}
