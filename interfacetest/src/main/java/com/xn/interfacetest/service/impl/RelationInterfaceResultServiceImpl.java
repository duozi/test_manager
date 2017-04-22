/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.service.impl;

import java.util.List;
import java.util.Map;

import com.xn.interfacetest.api.ParamsAssertService;
import com.xn.interfacetest.api.RelationAssertResultService;
import com.xn.interfacetest.dto.ParamsAssertDto;
import com.xn.interfacetest.dto.RelationAssertResultDto;
import com.xn.interfacetest.response.AssertItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xn.common.utils.BeanUtils;
import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;
import com.xn.interfacetest.api.RelationInterfaceResultService;
import com.xn.interfacetest.dao.RelationInterfaceResultMapper;
import com.xn.interfacetest.dto.RelationInterfaceResultDto;
import com.xn.interfacetest.entity.RelationInterfaceResult;
import com.xn.interfacetest.util.CollectionUtils;


/**
 * RelationInterfaceResult Service实现
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
@Transactional
public class RelationInterfaceResultServiceImpl implements RelationInterfaceResultService {

    /**
     *  Dao
     */
    @Autowired
    private RelationInterfaceResultMapper relationInterfaceResultMapper;

    @Autowired
    private RelationAssertResultService relationAssertResultService;

    @Override
    @Transactional(readOnly = true)
    public RelationInterfaceResultDto get(Object condition)
	{  
        RelationInterfaceResult relationInterfaceResult = relationInterfaceResultMapper.get(condition);
        RelationInterfaceResultDto relationInterfaceResultDto = BeanUtils.toBean(relationInterfaceResult,RelationInterfaceResultDto.class);
	    return relationInterfaceResultDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(RelationInterfaceResultDto condition) {
        return relationInterfaceResultMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelationInterfaceResultDto> list(RelationInterfaceResultDto condition) {
        List<RelationInterfaceResult> list = relationInterfaceResultMapper.list(condition);
        List<RelationInterfaceResultDto> dtoList = CollectionUtils.transform(list, RelationInterfaceResultDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelationInterfaceResultDto> list(Map<String,Object> condition) {
        List<RelationInterfaceResult> list = relationInterfaceResultMapper.list(condition);
        List<RelationInterfaceResultDto> dtoList = CollectionUtils.transform(list, RelationInterfaceResultDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<RelationInterfaceResultDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public RelationInterfaceResultDto save(RelationInterfaceResultDto relationInterfaceResultDto) {
        RelationInterfaceResult relationInterfaceResult = BeanUtils.toBean(relationInterfaceResultDto,RelationInterfaceResult.class);
        relationInterfaceResultMapper.save(relationInterfaceResult);
        relationInterfaceResultDto.setId(relationInterfaceResult.getId());
        return relationInterfaceResultDto;
    }

    @Override
    public int save(List<RelationInterfaceResultDto> relationInterfaceResultDtos) {
        if (relationInterfaceResultDtos == null || relationInterfaceResultDtos.isEmpty()) {
            return 0;
        }
        List<RelationInterfaceResult> relationInterfaceResults = CollectionUtils.transform(relationInterfaceResultDtos, RelationInterfaceResult.class);
        return relationInterfaceResultMapper.saveBatch(relationInterfaceResults);
    }

    @Override
    public int update(RelationInterfaceResultDto relationInterfaceResultDto) {
        RelationInterfaceResult relationInterfaceResult = BeanUtils.toBean(relationInterfaceResultDto,RelationInterfaceResult.class);
        return relationInterfaceResultMapper.update(relationInterfaceResult);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return relationInterfaceResultMapper.deleteByPK(id);
    }

    @Override
    public int delete(RelationInterfaceResultDto relationInterfaceResultDto) {
        RelationInterfaceResult relationInterfaceResult = BeanUtils.toBean(relationInterfaceResultDto,RelationInterfaceResult.class);
        return relationInterfaceResultMapper.delete(relationInterfaceResult);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return relationInterfaceResultMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<RelationInterfaceResultDto> relationInterfaceResults) {
        return 0;
    }

    @Override
    public List<RelationInterfaceResultDto> getByParams(Map<String, Object> params) {
        List<RelationInterfaceResult> list = relationInterfaceResultMapper.getByParams(params);
        List<RelationInterfaceResultDto> dtoList = CollectionUtils.transform(list, RelationInterfaceResultDto.class);

        //查询断言字段
        for(RelationInterfaceResultDto relationInterfaceResultDto: dtoList){
            List<RelationAssertResultDto> relationAssertResultDtos = relationAssertResultService.getByReportIdAndCaseId(relationInterfaceResultDto.getReportId(),relationInterfaceResultDto.getCaseId());
            relationInterfaceResultDto.setAssertItemList(relationAssertResultDtos);
        }

        return dtoList;
    }

}
