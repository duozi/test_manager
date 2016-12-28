/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.assertBean.dbAssert.service.impl;

import com.xn.autotest.bean.BeanUtils;
import com.xn.autotest.dao.DbAssertMapper;
import com.xn.autotest.bean.assertBean.dbAssert.dto.DbAssertDto;
import com.xn.autotest.bean.assertBean.dbAssert.entity.DbAssert;
import com.xn.autotest.bean.assertBean.dbAssert.service.DbAssertService;
import com.xn.autotest.mybatis.PageInfo;
import com.xn.autotest.mybatis.PageResult;
import com.xn.autotest.utils.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * DbAssert Service实现
 *
 * @author xn056839
 * @date 2016-12-22
 */

public class DbAssertServiceImpl implements DbAssertService {

    /**
     * Dao
     */
    @Resource
    DbAssertMapper dbAssertMapper;

    @Override
    public DbAssertDto get(Object condition) {
        DbAssert dbAssert = dbAssertMapper.get(condition);
        DbAssertDto dbAssertDto = BeanUtils.toBean(dbAssert, DbAssertDto.class);
        return dbAssertDto;
    }

    @Override
    public long count(DbAssertDto condition) {
        return dbAssertMapper.count(condition);
    }

    @Override
    public List<DbAssertDto> list(DbAssertDto condition) {
        List<DbAssert> list = dbAssertMapper.list(condition);
        List<DbAssertDto> dtoList = CollectionUtils.transform(list, DbAssertDto.class);
        return dtoList;
    }

    @Override
    public List<DbAssertDto> list(Map<String, Object> condition) {
        List<DbAssert> list = dbAssertMapper.list(condition);
        List<DbAssertDto> dtoList = CollectionUtils.transform(list, DbAssertDto.class);
        return dtoList;
    }

    @Override
    public PageResult<DbAssertDto> page(Map<String, Object> condition) {
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public DbAssertDto save(DbAssertDto dbAssertDto) {
        DbAssert dbAssert = BeanUtils.toBean(dbAssertDto, DbAssert.class);
        dbAssertMapper.save(dbAssert);
        dbAssertDto.setId(dbAssert.getId());
        return dbAssertDto;
    }

    @Override
    public int save(List<DbAssertDto> dbAssertDtos) {
        if (dbAssertDtos == null || dbAssertDtos.isEmpty()) {
            return 0;
        }
        List<DbAssert> dbAsserts = CollectionUtils.transform(dbAssertDtos, DbAssert.class);
        return dbAssertMapper.saveBatch(dbAsserts);
    }

    @Override
    public int update(DbAssertDto dbAssertDto) {
        DbAssert dbAssert = BeanUtils.toBean(dbAssertDto, DbAssert.class);
        return dbAssertMapper.update(dbAssert);
    }

    @Override
    public int deleteByPK(Integer id) {
        return dbAssertMapper.deleteByPK(id);
    }

    @Override
    public int delete(DbAssertDto dbAssertDto) {
        DbAssert dbAssert = BeanUtils.toBean(dbAssertDto, DbAssert.class);
        return dbAssertMapper.delete(dbAssert);
    }

    @Override
    public int deleteBatchByPK(List<Integer> ids) {
        return dbAssertMapper.deleteBatchByPK(ids);
    }

    @Override
    public int deleteBatch(List<DbAssertDto> dbAsserts) {
        return 0;
    }

}
