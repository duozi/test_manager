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
import com.xn.interfacetest.api.RelationAssertResultService;
import com.xn.interfacetest.dao.RelationAssertResultMapper;
import com.xn.interfacetest.dto.RelationAssertResultDto;
import com.xn.interfacetest.entity.RelationAssertResult;
import com.xn.interfacetest.util.CollectionUtils;



/**
 * RelationAssertResult Service实现
 * 
 * @author Carol
 * @date 2017-03-31
 */
@Service
@Transactional
public class RelationAssertResultServiceImpl implements RelationAssertResultService {

    /**
     *  Dao
     */
    @Autowired
    private RelationAssertResultMapper relationAssertResultMapper;

    @Override
    @Transactional(readOnly = true)
    public RelationAssertResultDto get(Object condition)
	{  
        RelationAssertResult relationAssertResult = relationAssertResultMapper.get(condition);
        RelationAssertResultDto relationAssertResultDto = BeanUtils.toBean(relationAssertResult,RelationAssertResultDto.class);
	    return relationAssertResultDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(RelationAssertResultDto condition) {
        return relationAssertResultMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelationAssertResultDto> list(RelationAssertResultDto condition) {
        List<RelationAssertResult> list = relationAssertResultMapper.list(condition);
        List<RelationAssertResultDto> dtoList = CollectionUtils.transform(list, RelationAssertResultDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelationAssertResultDto> list(Map<String,Object> condition) {
        List<RelationAssertResult> list = relationAssertResultMapper.list(condition);
        List<RelationAssertResultDto> dtoList = CollectionUtils.transform(list, RelationAssertResultDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<RelationAssertResultDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public RelationAssertResultDto save(RelationAssertResultDto relationAssertResultDto) {
        RelationAssertResult relationAssertResult = BeanUtils.toBean(relationAssertResultDto,RelationAssertResult.class);
        if(null != relationAssertResultDto.getId()){
            relationAssertResultMapper.update(relationAssertResult);
        } else {
            relationAssertResultMapper.save(relationAssertResult);
        }
        relationAssertResultDto.setId(relationAssertResult.getId());
        return relationAssertResultDto;
    }

    @Override
    public int save(List<RelationAssertResultDto> relationAssertResultDtos) {
        if (relationAssertResultDtos == null || relationAssertResultDtos.isEmpty()) {
            return 0;
        }
        List<RelationAssertResult> relationAssertResults = CollectionUtils.transform(relationAssertResultDtos, RelationAssertResult.class);
        return relationAssertResultMapper.saveBatch(relationAssertResults);
    }

    @Override
    public int update(RelationAssertResultDto relationAssertResultDto) {
        RelationAssertResult relationAssertResult = BeanUtils.toBean(relationAssertResultDto,RelationAssertResult.class);
        return relationAssertResultMapper.update(relationAssertResult);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return relationAssertResultMapper.deleteByPK(id);
    }

    @Override
    public int delete(RelationAssertResultDto relationAssertResultDto) {
        RelationAssertResult relationAssertResult = BeanUtils.toBean(relationAssertResultDto,RelationAssertResult.class);
        return relationAssertResultMapper.delete(relationAssertResult);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return relationAssertResultMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<RelationAssertResultDto> relationAssertResults) {
        return 0;
    }

    @Override
    public List<RelationAssertResultDto> getByReportIdAndCaseId(Long reportId,Long caseId) {
        List<RelationAssertResult> list = relationAssertResultMapper.getByReportIdAndCaseId(reportId,caseId);
        List<RelationAssertResultDto> dtoList = CollectionUtils.transform(list, RelationAssertResultDto.class);
        return dtoList;
    }

}
