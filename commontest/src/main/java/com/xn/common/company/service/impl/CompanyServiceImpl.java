/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.common.company.service.impl;

import com.xn.common.company.dao.CompanyMapper;
import com.xn.common.company.dto.CompanyDto;
import com.xn.common.company.entity.Company;
import com.xn.common.company.service.CompanyService;
import com.xn.common.utils.BeanUtils;
import com.xn.common.utils.CollectionUtils;
import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * Company Service实现
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    /**
     *  Dao
     */
    @Autowired
    public CompanyMapper companyMapper;

    @Override
    @Transactional(readOnly = true)
    public CompanyDto get(Object condition)
	{  
        Company company = companyMapper.get(condition);
        CompanyDto companyDto = BeanUtils.toBean(company,CompanyDto.class);
	    return companyDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(CompanyDto condition) {
        return companyMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyDto> list(CompanyDto condition) {
        List<Company> list = companyMapper.list(condition);
        List<CompanyDto> dtoList = CollectionUtils.transform(list, CompanyDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyDto> list(Map<String,Object> condition) {
        List<Company> list = companyMapper.list(condition);
        List<CompanyDto> dtoList = CollectionUtils.transform(list, CompanyDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<CompanyDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public CompanyDto save(CompanyDto companyDto) {
        Company company = BeanUtils.toBean(companyDto,Company.class);
        companyMapper.save(company);
        companyDto.setId(company.getId());
        return companyDto;
    }

    @Override
    public int save(List<CompanyDto> companyDtos) {
        if (companyDtos == null || companyDtos.isEmpty()) {
            return 0;
        }
        List<Company> companys = CollectionUtils.transform(companyDtos, Company.class);
        return companyMapper.saveBatch(companys);
    }

    @Override
    public int update(CompanyDto companyDto) {
        Company company = BeanUtils.toBean(companyDto,Company.class);
        return companyMapper.update(company);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return companyMapper.deleteByPK(id);
    }

    @Override
    public int delete(CompanyDto companyDto) {
        Company company = BeanUtils.toBean(companyDto,Company.class);
        return companyMapper.delete(company);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return companyMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<CompanyDto> companys) {
        return 0;
    }

}
