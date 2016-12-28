/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.request.method.service.impl;


import com.xn.autotest.bean.BeanUtils;
import com.xn.autotest.dao.MethodMapper;
import com.xn.autotest.bean.request.method.dto.MethodDto;
import com.xn.autotest.bean.request.method.entity.Method;
import com.xn.autotest.bean.request.method.service.MethodService;
import com.xn.autotest.mybatis.PageInfo;
import com.xn.autotest.mybatis.PageResult;
import com.xn.autotest.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


/**
 * Method Service实现
 * 
 * @author xn056839
 * @date 2016-12-22
 */

public class MethodServiceImpl implements MethodService {

    /**
     *  Dao
     */
    @Autowired
    private MethodMapper methodMapper;

    @Override
    public MethodDto get(Object condition)  
	{  
        Method method = methodMapper.get(condition);
        MethodDto methodDto = BeanUtils.toBean(method,MethodDto.class);
	    return methodDto;  
	}  

    @Override
    public long count(MethodDto condition) {
        return methodMapper.count(condition);
    }

    @Override
    public List<MethodDto> list(MethodDto condition) {
        List<Method> list = methodMapper.list(condition);
        List<MethodDto> dtoList = CollectionUtils.transform(list, MethodDto.class);
        return dtoList;
    }

    @Override
    public List<MethodDto> list(Map<String,Object> condition) {
        List<Method> list = methodMapper.list(condition);
        List<MethodDto> dtoList = CollectionUtils.transform(list, MethodDto.class);
        return dtoList;
    }
    
    @Override
    public PageResult<MethodDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public MethodDto save(MethodDto methodDto) {
        Method method = BeanUtils.toBean(methodDto,Method.class);
        methodMapper.save(method);
        methodDto.setId(method.getId());
        return methodDto;
    }

    @Override
    public int save(List<MethodDto> methodDtos) {
        if (methodDtos == null || methodDtos.isEmpty()) {
            return 0;
        }
        List<Method> methods = CollectionUtils.transform(methodDtos, Method.class);
        return methodMapper.saveBatch(methods);
    }

    @Override
    public int update(MethodDto methodDto) {
        Method method = BeanUtils.toBean(methodDto,Method.class);
        return methodMapper.update(method);
    }
    
    @Override
    public int deleteByPK(Integer id) {
        return methodMapper.deleteByPK(id);
    }

    @Override
    public int delete(MethodDto methodDto) {
        Method method = BeanUtils.toBean(methodDto,Method.class);
        return methodMapper.delete(method);
    }
    
    @Override
    public int deleteBatchByPK(List<Integer> ids) {
        return methodMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<MethodDto> methods) {
        return 0;
    }

}
