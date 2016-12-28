/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.request.cases.service.impl;

import com.xn.autotest.bean.BeanUtils;
import com.xn.autotest.dao.CasesMapper;
import com.xn.autotest.bean.request.cases.dto.CasesDto;
import com.xn.autotest.bean.request.cases.entity.Cases;
import com.xn.autotest.bean.request.cases.service.CasesService;
import com.xn.autotest.mybatis.PageInfo;
import com.xn.autotest.mybatis.PageResult;
import com.xn.autotest.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


/**
 * Cases Service实现
 * 
 * @author xn056839
 * @date 2016-12-22
 */

public class CasesServiceImpl implements CasesService {

    /**
     *  Dao
     */
    @Autowired
    private CasesMapper casesMapper;

    @Override
    public CasesDto get(Object condition)
	{  
        Cases cases = casesMapper.get(condition);
        CasesDto casesDto = BeanUtils.toBean(cases,CasesDto.class);
	    return casesDto;  
	}  

    @Override
    public long count(CasesDto condition) {
        return casesMapper.count(condition);
    }

    @Override
    public List<CasesDto> list(CasesDto condition) {
        List<Cases> list = casesMapper.list(condition);
        List<CasesDto> dtoList = CollectionUtils.transform(list, CasesDto.class);
        return dtoList;
    }

    @Override
    public List<CasesDto> list(Map<String,Object> condition) {
        List<Cases> list = casesMapper.list(condition);
        List<CasesDto> dtoList = CollectionUtils.transform(list, CasesDto.class);
        return dtoList;
    }
    
    @Override
    public PageResult<CasesDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public CasesDto save(CasesDto casesDto) {
        Cases cases = BeanUtils.toBean(casesDto,Cases.class);
        casesMapper.save(cases);
        casesDto.setId(cases.getId());
        return casesDto;
    }

    @Override
    public int save(List<CasesDto> casesDtos) {
        if (casesDtos == null || casesDtos.isEmpty()) {
            return 0;
        }
        List<Cases> casess = CollectionUtils.transform(casesDtos, Cases.class);
        return casesMapper.saveBatch(casess);
    }

    @Override
    public int update(CasesDto casesDto) {
        Cases cases = BeanUtils.toBean(casesDto,Cases.class);
        return casesMapper.update(cases);
    }
    
    @Override
    public int deleteByPK(Integer id) {
        return casesMapper.deleteByPK(id);
    }

    @Override
    public int delete(CasesDto casesDto) {
        Cases cases = BeanUtils.toBean(casesDto,Cases.class);
        return casesMapper.delete(cases);
    }
    
    @Override
    public int deleteBatchByPK(List<Integer> ids) {
        return casesMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<CasesDto> casess) {
        return 0;
    }

}
