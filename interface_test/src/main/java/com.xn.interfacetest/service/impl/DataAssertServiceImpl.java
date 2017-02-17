/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.service.impl;

import org.springframework.stereotype.Service;
import com.xn.common.entity.BeanUtils;
import com.xn.common.utils.CollectionUtils;
import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;
import com.xn.interfacetest.dao.DataAssertMapper;
import com.xn.interfacetest.dto.DataAssertDto;
import com.xn.interfacetest.entity.DataAssert;
import com.xn.interfacetest.service.DataAssertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * DataAssert Service实现
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
@Transactional
public class DataAssertServiceImpl implements DataAssertService {

    /**
     *  Dao
     */
    @Autowired
    private DataAssertMapper dataAssertMapper;

    @Override
    @Transactional(readOnly = true)
    public DataAssertDto get(Object condition)
	{  
        DataAssert dataAssert = dataAssertMapper.get(condition);
        DataAssertDto dataAssertDto = BeanUtils.toBean(dataAssert,DataAssertDto.class);
	    return dataAssertDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(DataAssertDto condition) {
        return dataAssertMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DataAssertDto> list(DataAssertDto condition) {
        List<DataAssert> list = dataAssertMapper.list(condition);
        List<DataAssertDto> dtoList = CollectionUtils.transform(list, DataAssertDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DataAssertDto> list(Map<String,Object> condition) {
        List<DataAssert> list = dataAssertMapper.list(condition);
        List<DataAssertDto> dtoList = CollectionUtils.transform(list, DataAssertDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<DataAssertDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public DataAssertDto save(DataAssertDto dataAssertDto) {
        DataAssert dataAssert = BeanUtils.toBean(dataAssertDto,DataAssert.class);
        dataAssertMapper.save(dataAssert);
        dataAssertDto.setId(dataAssert.getId());
        return dataAssertDto;
    }

    @Override
    public int save(List<DataAssertDto> dataAssertDtos) {
        if (dataAssertDtos == null || dataAssertDtos.isEmpty()) {
            return 0;
        }
        List<DataAssert> dataAsserts = CollectionUtils.transform(dataAssertDtos, DataAssert.class);
        return dataAssertMapper.saveBatch(dataAsserts);
    }

    @Override
    public int update(DataAssertDto dataAssertDto) {
        DataAssert dataAssert = BeanUtils.toBean(dataAssertDto,DataAssert.class);
        return dataAssertMapper.update(dataAssert);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return dataAssertMapper.deleteByPK(id);
    }

    @Override
    public int delete(DataAssertDto dataAssertDto) {
        DataAssert dataAssert = BeanUtils.toBean(dataAssertDto,DataAssert.class);
        return dataAssertMapper.delete(dataAssert);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return dataAssertMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<DataAssertDto> dataAsserts) {
        return 0;
    }

}
