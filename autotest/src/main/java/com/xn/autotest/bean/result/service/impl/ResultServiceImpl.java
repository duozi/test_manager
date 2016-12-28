/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.result.service.impl;

import com.xn.autotest.bean.BeanUtils;
import com.xn.autotest.dao.ResultMapper;
import com.xn.autotest.bean.result.dto.ResultDto;
import com.xn.autotest.bean.result.entity.Result;
import com.xn.autotest.bean.result.service.ResultService;
import com.xn.autotest.mybatis.PageInfo;
import com.xn.autotest.mybatis.PageResult;
import com.xn.autotest.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


/**
 * Result Service实现
 * 
 * @author xn056839
 * @date 2016-12-22
 */

public class ResultServiceImpl implements ResultService {

    /**
     *  Dao
     */
    @Autowired
    private ResultMapper resultMapper;

    @Override
    public ResultDto get(Object condition)  
	{  
        Result result = resultMapper.get(condition);
        ResultDto resultDto = BeanUtils.toBean(result,ResultDto.class);
	    return resultDto;  
	}  

    @Override
    public long count(ResultDto condition) {
        return resultMapper.count(condition);
    }

    @Override
    public List<ResultDto> list(ResultDto condition) {
        List<Result> list = resultMapper.list(condition);
        List<ResultDto> dtoList = CollectionUtils.transform(list, ResultDto.class);
        return dtoList;
    }

    @Override
    public List<ResultDto> list(Map<String,Object> condition) {
        List<Result> list = resultMapper.list(condition);
        List<ResultDto> dtoList = CollectionUtils.transform(list, ResultDto.class);
        return dtoList;
    }
    
    @Override
    public PageResult<ResultDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public ResultDto save(ResultDto resultDto) {
        Result result = BeanUtils.toBean(resultDto,Result.class);
        resultMapper.save(result);
        resultDto.setId(result.getId());
        return resultDto;
    }

    @Override
    public int save(List<ResultDto> resultDtos) {
        if (resultDtos == null || resultDtos.isEmpty()) {
            return 0;
        }
        List<Result> results = CollectionUtils.transform(resultDtos, Result.class);
        return resultMapper.saveBatch(results);
    }

    @Override
    public int update(ResultDto resultDto) {
        Result result = BeanUtils.toBean(resultDto,Result.class);
        return resultMapper.update(result);
    }
    
    @Override
    public int deleteByPK(Integer id) {
        return resultMapper.deleteByPK(id);
    }

    @Override
    public int delete(ResultDto resultDto) {
        Result result = BeanUtils.toBean(resultDto,Result.class);
        return resultMapper.delete(result);
    }
    
    @Override
    public int deleteBatchByPK(List<Integer> ids) {
        return resultMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<ResultDto> results) {
        return 0;
    }

}
