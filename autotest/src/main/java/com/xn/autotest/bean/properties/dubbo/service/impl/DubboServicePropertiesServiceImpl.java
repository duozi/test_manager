/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.properties.dubbo.service.impl;


import com.xn.autotest.bean.BeanUtils;
import com.xn.autotest.dao.DubboServicePropertiesMapper;
import com.xn.autotest.bean.properties.dubbo.dto.DubboServicePropertiesDto;
import com.xn.autotest.bean.properties.dubbo.entity.DubboServiceProperties;
import com.xn.autotest.bean.properties.dubbo.service.DubboServicePropertiesService;
import com.xn.autotest.mybatis.PageInfo;
import com.xn.autotest.mybatis.PageResult;
import com.xn.autotest.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


/**
 * DubboServiceProperties Service实现
 * 
 * @author xn056839
 * @date 2016-12-22
 */

public class DubboServicePropertiesServiceImpl implements DubboServicePropertiesService {

    /**
     *  Dao
     */
    @Autowired
    private DubboServicePropertiesMapper dubboServicePropertiesMapper;

    @Override
    public DubboServicePropertiesDto get(Object condition)  
	{  
        DubboServiceProperties dubboServiceProperties = dubboServicePropertiesMapper.get(condition);
        DubboServicePropertiesDto dubboServicePropertiesDto = BeanUtils.toBean(dubboServiceProperties,DubboServicePropertiesDto.class);
	    return dubboServicePropertiesDto;  
	}  

    @Override
    public long count(DubboServicePropertiesDto condition) {
        return dubboServicePropertiesMapper.count(condition);
    }

    @Override
    public List<DubboServicePropertiesDto> list(DubboServicePropertiesDto condition) {
        List<DubboServiceProperties> list = dubboServicePropertiesMapper.list(condition);
        List<DubboServicePropertiesDto> dtoList = CollectionUtils.transform(list, DubboServicePropertiesDto.class);
        return dtoList;
    }

    @Override
    public List<DubboServicePropertiesDto> list(Map<String,Object> condition) {
        List<DubboServiceProperties> list = dubboServicePropertiesMapper.list(condition);
        List<DubboServicePropertiesDto> dtoList = CollectionUtils.transform(list, DubboServicePropertiesDto.class);
        return dtoList;
    }
    
    @Override
    public PageResult<DubboServicePropertiesDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public DubboServicePropertiesDto save(DubboServicePropertiesDto dubboServicePropertiesDto) {
        DubboServiceProperties dubboServiceProperties = BeanUtils.toBean(dubboServicePropertiesDto,DubboServiceProperties.class);
        dubboServicePropertiesMapper.save(dubboServiceProperties);
        dubboServicePropertiesDto.setId(dubboServiceProperties.getId());
        return dubboServicePropertiesDto;
    }

    @Override
    public int save(List<DubboServicePropertiesDto> dubboServicePropertiesDtos) {
        if (dubboServicePropertiesDtos == null || dubboServicePropertiesDtos.isEmpty()) {
            return 0;
        }
        List<DubboServiceProperties> dubboServicePropertiess = CollectionUtils.transform(dubboServicePropertiesDtos, DubboServiceProperties.class);
        return dubboServicePropertiesMapper.saveBatch(dubboServicePropertiess);
    }

    @Override
    public int update(DubboServicePropertiesDto dubboServicePropertiesDto) {
        DubboServiceProperties dubboServiceProperties = BeanUtils.toBean(dubboServicePropertiesDto,DubboServiceProperties.class);
        return dubboServicePropertiesMapper.update(dubboServiceProperties);
    }
    
    @Override
    public int deleteByPK(Integer id) {
        return dubboServicePropertiesMapper.deleteByPK(id);
    }

    @Override
    public int delete(DubboServicePropertiesDto dubboServicePropertiesDto) {
        DubboServiceProperties dubboServiceProperties = BeanUtils.toBean(dubboServicePropertiesDto,DubboServiceProperties.class);
        return dubboServicePropertiesMapper.delete(dubboServiceProperties);
    }
    
    @Override
    public int deleteBatchByPK(List<Integer> ids) {
        return dubboServicePropertiesMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<DubboServicePropertiesDto> dubboServicePropertiess) {
        return 0;
    }

}
