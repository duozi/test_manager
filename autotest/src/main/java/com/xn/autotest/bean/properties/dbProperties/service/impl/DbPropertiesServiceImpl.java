/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.properties.dbProperties.service.impl;

import com.xn.autotest.bean.BeanUtils;
import com.xn.autotest.bean.properties.dbProperties.dto.DbPropertiesDto;
import com.xn.autotest.bean.properties.dbProperties.entity.DbProperties;
import com.xn.autotest.bean.properties.dbProperties.service.DbPropertiesService;
import com.xn.autotest.dao.DbPropertiesMapper;
import com.xn.autotest.mybatis.PageInfo;
import com.xn.autotest.mybatis.PageResult;
import com.xn.autotest.utils.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * DbProperties Service实现
 * 
 * @author xn056839
 * @date 2016-12-22
 */
@Service
public class DbPropertiesServiceImpl implements DbPropertiesService {

    /**
     *  Dao
     */
    @Resource
    private DbPropertiesMapper dbPropertiesMapper;

    @Override
    public DbPropertiesDto get(Object condition)  
	{  
        DbProperties dbProperties = dbPropertiesMapper.get(condition);
        DbPropertiesDto dbPropertiesDto = BeanUtils.toBean(dbProperties,DbPropertiesDto.class);
	    return dbPropertiesDto;  
	}  

    @Override
    public long count(DbPropertiesDto condition) {
        return dbPropertiesMapper.count(condition);
    }

    @Override
    public List<DbPropertiesDto> list(DbPropertiesDto condition) {
        List<DbProperties> list = dbPropertiesMapper.list(condition);
        List<DbPropertiesDto> dtoList = CollectionUtils.transform(list, DbPropertiesDto.class);
        return dtoList;
    }

    @Override
    public List<DbPropertiesDto> list() {
        List<DbProperties> list = dbPropertiesMapper.list(null);
        List<DbPropertiesDto> dtoList = CollectionUtils.transform(list, DbPropertiesDto.class);
        return dtoList;
    }


    @Override
    public List<DbPropertiesDto> list(Map<String,Object> condition) {
        List<DbProperties> list = dbPropertiesMapper.list(condition);
        List<DbPropertiesDto> dtoList = CollectionUtils.transform(list, DbPropertiesDto.class);
        return dtoList;
    }

    @Override
    public PageResult<DbPropertiesDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public DbPropertiesDto save(DbPropertiesDto dbPropertiesDto) {
        DbProperties dbProperties = BeanUtils.toBean(dbPropertiesDto,DbProperties.class);
        dbPropertiesMapper.save(dbProperties);
        dbPropertiesDto.setId(dbProperties.getId());
        return dbPropertiesDto;
    }

    @Override
    public int save(List<DbPropertiesDto> dbPropertiesDtos) {
        if (dbPropertiesDtos == null || dbPropertiesDtos.isEmpty()) {
            return 0;
        }
        List<DbProperties> dbPropertiess = CollectionUtils.transform(dbPropertiesDtos, DbProperties.class);
        return dbPropertiesMapper.saveBatch(dbPropertiess);
    }

    @Override
    public int update(DbPropertiesDto dbPropertiesDto) {
        DbProperties dbProperties = BeanUtils.toBean(dbPropertiesDto,DbProperties.class);
        return dbPropertiesMapper.update(dbProperties);
    }

    @Override
    public int deleteByPK(Integer id) {
        return dbPropertiesMapper.deleteByPK(id);
    }

    @Override
    public int delete(DbPropertiesDto dbPropertiesDto) {
        DbProperties dbProperties = BeanUtils.toBean(dbPropertiesDto,DbProperties.class);
        return dbPropertiesMapper.delete(dbProperties);
    }

    @Override
    public int deleteBatchByPK(List<Integer> ids) {
        return dbPropertiesMapper.deleteBatchByPK(ids);
    }

    @Override
    public int deleteBatch(List<DbPropertiesDto> dbPropertiess) {
        return 0;
    }

}
