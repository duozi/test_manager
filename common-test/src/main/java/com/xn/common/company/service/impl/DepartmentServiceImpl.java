/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.common.company.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xn.common.api.DepartmentService;
import com.xn.common.company.dao.DepartmentMapper;
import com.xn.common.company.entity.Department;
import com.xn.common.dto.DepartmentDto;
import com.xn.common.utils.BeanUtils;
import com.xn.common.utils.CollectionUtils;
import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;



/**
 * Department Service实现
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    /**
     *  Dao
     */
    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    @Transactional(readOnly = true)
    public DepartmentDto get(Object condition)
	{  
        Department department = departmentMapper.get(condition);
        DepartmentDto departmentDto = BeanUtils.toBean(department,DepartmentDto.class);
	    return departmentDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(DepartmentDto condition) {
        return departmentMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentDto> list(DepartmentDto condition) {
        List<Department> list = departmentMapper.list(condition);
        List<DepartmentDto> dtoList = CollectionUtils.transform(list, DepartmentDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentDto> list(Map<String,Object> condition) {
        List<Department> list = departmentMapper.list(condition);
        List<DepartmentDto> dtoList = CollectionUtils.transform(list, DepartmentDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<DepartmentDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public DepartmentDto save(DepartmentDto departmentDto) {
        Department department = BeanUtils.toBean(departmentDto,Department.class);
        departmentMapper.save(department);
        departmentDto.setId(department.getId());
        return departmentDto;
    }

    @Override
    public int save(List<DepartmentDto> departmentDtos) {
        if (departmentDtos == null || departmentDtos.isEmpty()) {
            return 0;
        }
        List<Department> departments = CollectionUtils.transform(departmentDtos, Department.class);
        return departmentMapper.saveBatch(departments);
    }

    @Override
    public int update(DepartmentDto departmentDto) {
        Department department = BeanUtils.toBean(departmentDto,Department.class);
        return departmentMapper.update(department);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return departmentMapper.deleteByPK(id);
    }

    @Override
    public int delete(DepartmentDto departmentDto) {
        Department department = BeanUtils.toBean(departmentDto,Department.class);
        return departmentMapper.delete(department);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return departmentMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<DepartmentDto> departments) {
        return 0;
    }

}
