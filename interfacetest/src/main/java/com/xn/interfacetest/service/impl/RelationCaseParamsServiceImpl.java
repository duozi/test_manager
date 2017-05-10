/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.service.impl;


import java.util.List;
import java.util.Map;

import com.xn.interfacetest.Enum.ParamsGroupTypeEnum;
import com.xn.interfacetest.api.TestCaseService;
import com.xn.interfacetest.dto.TestCaseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xn.common.utils.BeanUtils;
import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;
import com.xn.interfacetest.api.RelationCaseParamsService;
import com.xn.interfacetest.dao.RelationCaseParamsMapper;
import com.xn.interfacetest.dto.RelationCaseParamsDto;
import com.xn.interfacetest.entity.RelationCaseParams;
import com.xn.interfacetest.util.CollectionUtils;



/**
 * RelationCaseParams Service实现
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
@Transactional
public class RelationCaseParamsServiceImpl implements RelationCaseParamsService {

    /**
     *  Dao
     */
    @Autowired
    private RelationCaseParamsMapper relationCaseParamsMapper;

    @Autowired
    private TestCaseService testCaseService;

    @Override
    @Transactional(readOnly = true)
    public RelationCaseParamsDto get(Object condition)
	{  
        RelationCaseParams relationCaseParams = relationCaseParamsMapper.get(condition);
        RelationCaseParamsDto relationCaseParamsDto = BeanUtils.toBean(relationCaseParams,RelationCaseParamsDto.class);
	    return relationCaseParamsDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(RelationCaseParamsDto condition) {
        return relationCaseParamsMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelationCaseParamsDto> list(RelationCaseParamsDto condition) {
        List<RelationCaseParams> list = relationCaseParamsMapper.list(condition);
        List<RelationCaseParamsDto> dtoList = CollectionUtils.transform(list, RelationCaseParamsDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelationCaseParamsDto> list(Map<String,Object> condition) {
        List<RelationCaseParams> list = relationCaseParamsMapper.list(condition);
        List<RelationCaseParamsDto> dtoList = CollectionUtils.transform(list, RelationCaseParamsDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<RelationCaseParamsDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public RelationCaseParamsDto save(RelationCaseParamsDto relationCaseParamsDto) {
        RelationCaseParams relationCaseParams = BeanUtils.toBean(relationCaseParamsDto,RelationCaseParams.class);
        if(null == relationCaseParamsDto.getId()){
            relationCaseParamsMapper.save(relationCaseParams);
        } else {
            relationCaseParamsMapper.update(relationCaseParams);
        }

        relationCaseParamsDto.setId(relationCaseParams.getId());

        //更新用例参数类型为----配置的参数
        TestCaseDto testCaseDto = testCaseService.get(relationCaseParamsDto.getCaseId());
        if(null != testCaseDto && ((null != testCaseDto.getParamsType() && testCaseDto.getParamsType() != ParamsGroupTypeEnum.KEY.getId())|| null == testCaseDto.getParamsType())){
            testCaseDto.setParamsType(ParamsGroupTypeEnum.KEY.getId());
            testCaseService.updatePart(testCaseDto);
        }

        return relationCaseParamsDto;
    }

    @Override
    public int save(List<RelationCaseParamsDto> relationCaseParamsDtos) {
        if (relationCaseParamsDtos == null || relationCaseParamsDtos.isEmpty()) {
            return 0;
        }
        List<RelationCaseParams> relationCaseParamss = CollectionUtils.transform(relationCaseParamsDtos, RelationCaseParams.class);
        return relationCaseParamsMapper.saveBatch(relationCaseParamss);
    }

    @Override
    public int update(RelationCaseParamsDto relationCaseParamsDto) {
        RelationCaseParams relationCaseParams = BeanUtils.toBean(relationCaseParamsDto,RelationCaseParams.class);
        return relationCaseParamsMapper.update(relationCaseParams);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return relationCaseParamsMapper.deleteByPK(id);
    }

    @Override
    public int delete(RelationCaseParamsDto relationCaseParamsDto) {
        RelationCaseParams relationCaseParams = BeanUtils.toBean(relationCaseParamsDto,RelationCaseParams.class);
        return relationCaseParamsMapper.delete(relationCaseParams);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return relationCaseParamsMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<RelationCaseParamsDto> relationCaseParamss) {
        return 0;
    }

    @Override
    public RelationCaseParamsDto getByCaseIdAndParamName(String valueName, Long caseId) {
        RelationCaseParams relationCaseParams = relationCaseParamsMapper.getByCaseIdAndParamName( valueName,caseId);
        RelationCaseParamsDto relationCaseParamsDto = BeanUtils.toBean(relationCaseParams,RelationCaseParamsDto.class);
        return relationCaseParamsDto;
    }

}
