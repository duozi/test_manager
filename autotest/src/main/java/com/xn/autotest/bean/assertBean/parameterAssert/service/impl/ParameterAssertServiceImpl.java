/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.assertBean.parameterAssert.service.impl;

import com.xn.autotest.bean.BeanUtils;
import com.xn.autotest.dao.ParameterAssertMapper;
import com.xn.autotest.bean.assertBean.parameterAssert.dto.ParameterAssertDto;
import com.xn.autotest.bean.assertBean.parameterAssert.entity.ParameterAssert;
import com.xn.autotest.bean.assertBean.parameterAssert.service.ParameterAssertService;
import com.xn.autotest.mybatis.PageInfo;
import com.xn.autotest.mybatis.PageResult;
import com.xn.autotest.utils.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * ParameterAssert Service实现
 * 
 * @author xn056839
 * @date 2016-12-22
 */

public class ParameterAssertServiceImpl implements ParameterAssertService {

    /**
     *  Dao
     */
    @Resource
    ParameterAssertMapper parameterAssertMapper;

    @Override
    public ParameterAssertDto get(Object condition)  
	{  
        ParameterAssert parameterAssert = parameterAssertMapper.get(condition);
        ParameterAssertDto parameterAssertDto = BeanUtils.toBean(parameterAssert,ParameterAssertDto.class);
	    return parameterAssertDto;  
	}  

    @Override
    public long count(ParameterAssertDto condition) {
        return parameterAssertMapper.count(condition);
    }

    @Override
    public List<ParameterAssertDto> list(ParameterAssertDto condition) {
        List<ParameterAssert> list = parameterAssertMapper.list(condition);
        List<ParameterAssertDto> dtoList = CollectionUtils.transform(list, ParameterAssertDto.class);
        return dtoList;
    }

    @Override
    public List<ParameterAssertDto> list(Map<String,Object> condition) {
        List<ParameterAssert> list = parameterAssertMapper.list(condition);
        List<ParameterAssertDto> dtoList = CollectionUtils.transform(list, ParameterAssertDto.class);
        return dtoList;
    }
    
    @Override
    public PageResult<ParameterAssertDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public ParameterAssertDto save(ParameterAssertDto parameterAssertDto) {
        ParameterAssert parameterAssert = BeanUtils.toBean(parameterAssertDto,ParameterAssert.class);
        parameterAssertMapper.save(parameterAssert);
        parameterAssertDto.setId(parameterAssert.getId());
        return parameterAssertDto;
    }

    @Override
    public int save(List<ParameterAssertDto> parameterAssertDtos) {
        if (parameterAssertDtos == null || parameterAssertDtos.isEmpty()) {
            return 0;
        }
        List<ParameterAssert> parameterAsserts = CollectionUtils.transform(parameterAssertDtos, ParameterAssert.class);
        return parameterAssertMapper.saveBatch(parameterAsserts);
    }

    @Override
    public int update(ParameterAssertDto parameterAssertDto) {
        ParameterAssert parameterAssert = BeanUtils.toBean(parameterAssertDto,ParameterAssert.class);
        return parameterAssertMapper.update(parameterAssert);
    }
    
    @Override
    public int deleteByPK(Integer id) {
        return parameterAssertMapper.deleteByPK(id);
    }

    @Override
    public int delete(ParameterAssertDto parameterAssertDto) {
        ParameterAssert parameterAssert = BeanUtils.toBean(parameterAssertDto,ParameterAssert.class);
        return parameterAssertMapper.delete(parameterAssert);
    }
    
    @Override
    public int deleteBatchByPK(List<Integer> ids) {
        return parameterAssertMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<ParameterAssertDto> parameterAsserts) {
        return 0;
    }

}
