/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.operation.dbOperation.service.impl;

import com.xn.autotest.bean.BeanUtils;
import com.xn.autotest.dao.DbOperationMapper;
import com.xn.autotest.bean.operation.dbOperation.dto.DbOperationDto;
import com.xn.autotest.bean.operation.dbOperation.entity.DbOperation;
import com.xn.autotest.bean.operation.dbOperation.service.DbOperationService;
import com.xn.autotest.mybatis.PageInfo;
import com.xn.autotest.mybatis.PageResult;
import com.xn.autotest.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


/**
 * DbOperation Service实现
 * 
 * @author xn056839
 * @date 2016-12-22
 */

public class DbOperationServiceImpl implements DbOperationService {

    /**
     *  Dao
     */
    @Autowired
    private DbOperationMapper dbOperationMapper;

    @Override
    public DbOperationDto get(Object condition)  
	{  
        DbOperation dbOperation = dbOperationMapper.get(condition);
        DbOperationDto dbOperationDto = BeanUtils.toBean(dbOperation,DbOperationDto.class);
	    return dbOperationDto;  
	}  

    @Override
    public long count(DbOperationDto condition) {
        return dbOperationMapper.count(condition);
    }

    @Override
    public List<DbOperationDto> list(DbOperationDto condition) {
        List<DbOperation> list = dbOperationMapper.list(condition);
        List<DbOperationDto> dtoList = CollectionUtils.transform(list, DbOperationDto.class);
        return dtoList;
    }

    @Override
    public List<DbOperationDto> list(Map<String,Object> condition) {
        List<DbOperation> list = dbOperationMapper.list(condition);
        List<DbOperationDto> dtoList = CollectionUtils.transform(list, DbOperationDto.class);
        return dtoList;
    }
    
    @Override
    public PageResult<DbOperationDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public DbOperationDto save(DbOperationDto dbOperationDto) {
        DbOperation dbOperation = BeanUtils.toBean(dbOperationDto,DbOperation.class);
        dbOperationMapper.save(dbOperation);
        dbOperationDto.setId(dbOperation.getId());
        return dbOperationDto;
    }

    @Override
    public int save(List<DbOperationDto> dbOperationDtos) {
        if (dbOperationDtos == null || dbOperationDtos.isEmpty()) {
            return 0;
        }
        List<DbOperation> dbOperations = CollectionUtils.transform(dbOperationDtos, DbOperation.class);
        return dbOperationMapper.saveBatch(dbOperations);
    }

    @Override
    public int update(DbOperationDto dbOperationDto) {
        DbOperation dbOperation = BeanUtils.toBean(dbOperationDto,DbOperation.class);
        return dbOperationMapper.update(dbOperation);
    }
    
    @Override
    public int deleteByPK(Integer id) {
        return dbOperationMapper.deleteByPK(id);
    }

    @Override
    public int delete(DbOperationDto dbOperationDto) {
        DbOperation dbOperation = BeanUtils.toBean(dbOperationDto,DbOperation.class);
        return dbOperationMapper.delete(dbOperation);
    }
    
    @Override
    public int deleteBatchByPK(List<Integer> ids) {
        return dbOperationMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<DbOperationDto> dbOperations) {
        return 0;
    }

}
