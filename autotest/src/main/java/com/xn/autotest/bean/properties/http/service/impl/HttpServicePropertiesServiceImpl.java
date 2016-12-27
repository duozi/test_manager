/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.properties.http.service.impl;

import com.xn.autotest.bean.BeanUtils;
import com.xn.autotest.dao.HttpServicePropertiesMapper;
import com.xn.autotest.bean.properties.http.dto.HttpServicePropertiesDto;
import com.xn.autotest.bean.properties.http.entity.HttpServiceProperties;
import com.xn.autotest.bean.properties.http.service.HttpServicePropertiesService;
import com.xn.autotest.mybatis.PageInfo;
import com.xn.autotest.mybatis.PageResult;
import com.xn.autotest.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


/**
 * HttpServiceProperties Service实现
 * 
 * @author xn056839
 * @date 2016-12-22
 */

public class HttpServicePropertiesServiceImpl implements HttpServicePropertiesService {

    /**
     *  Dao
     */
    @Autowired
    private HttpServicePropertiesMapper httpServicePropertiesMapper;

    @Override
    public HttpServicePropertiesDto get(Object condition)
	{  
        HttpServiceProperties httpServiceProperties = httpServicePropertiesMapper.get(condition);
        HttpServicePropertiesDto httpServicePropertiesDto = BeanUtils.toBean(httpServiceProperties,HttpServicePropertiesDto.class);
	    return httpServicePropertiesDto;  
	}  

    @Override
    public long count(HttpServicePropertiesDto condition) {
        return httpServicePropertiesMapper.count(condition);
    }

    @Override
    public List<HttpServicePropertiesDto> list(HttpServicePropertiesDto condition) {
        List<HttpServiceProperties> list = httpServicePropertiesMapper.list(condition);
        List<HttpServicePropertiesDto> dtoList = CollectionUtils.transform(list, HttpServicePropertiesDto.class);
        return dtoList;
    }

    @Override
    public List<HttpServicePropertiesDto> list(Map<String,Object> condition) {
        List<HttpServiceProperties> list = httpServicePropertiesMapper.list(condition);
        List<HttpServicePropertiesDto> dtoList = CollectionUtils.transform(list, HttpServicePropertiesDto.class);
        return dtoList;
    }
    
    @Override
    public PageResult<HttpServicePropertiesDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public HttpServicePropertiesDto save(HttpServicePropertiesDto httpServicePropertiesDto) {
        HttpServiceProperties httpServiceProperties = BeanUtils.toBean(httpServicePropertiesDto,HttpServiceProperties.class);
        httpServicePropertiesMapper.save(httpServiceProperties);
        httpServicePropertiesDto.setId(httpServiceProperties.getId());
        return httpServicePropertiesDto;
    }

    @Override
    public int save(List<HttpServicePropertiesDto> httpServicePropertiesDtos) {
        if (httpServicePropertiesDtos == null || httpServicePropertiesDtos.isEmpty()) {
            return 0;
        }
        List<HttpServiceProperties> httpServicePropertiess = CollectionUtils.transform(httpServicePropertiesDtos, HttpServiceProperties.class);
        return httpServicePropertiesMapper.saveBatch(httpServicePropertiess);
    }

    @Override
    public int update(HttpServicePropertiesDto httpServicePropertiesDto) {
        HttpServiceProperties httpServiceProperties = BeanUtils.toBean(httpServicePropertiesDto,HttpServiceProperties.class);
        return httpServicePropertiesMapper.update(httpServiceProperties);
    }
    
    @Override
    public int deleteByPK(Integer id) {
        return httpServicePropertiesMapper.deleteByPK(id);
    }

    @Override
    public int delete(HttpServicePropertiesDto httpServicePropertiesDto) {
        HttpServiceProperties httpServiceProperties = BeanUtils.toBean(httpServicePropertiesDto,HttpServiceProperties.class);
        return httpServicePropertiesMapper.delete(httpServiceProperties);
    }
    
    @Override
    public int deleteBatchByPK(List<Integer> ids) {
        return httpServicePropertiesMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<HttpServicePropertiesDto> httpServicePropertiess) {
        return 0;
    }

}
