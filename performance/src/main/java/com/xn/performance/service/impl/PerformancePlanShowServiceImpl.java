/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.performance.service.impl;

import com.xn.performance.dao.PerformancePlanShowMapper;
import com.xn.performance.dto.PerformancePlanMonitoredDto;
import com.xn.performance.dto.PerformancePlanShowDto;
import com.xn.performance.entity.PerformancePlanShow;
import com.xn.performance.mybatis.PageInfo;
import com.xn.performance.mybatis.PageResult;
import com.xn.performance.service.PerformancePlanMonitoredService;
import com.xn.performance.service.PerformancePlanService;
import com.xn.performance.service.PerformancePlanShowService;
import com.xn.performance.util.BeanUtils;
import com.xn.performance.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * PerformancePlan Service实现
 *
 * @author zhouxi
 * @date 2017-02-21
 */
@Service
public class PerformancePlanShowServiceImpl implements PerformancePlanShowService {

    /**
     * Dao
     */
    @Autowired
    private PerformancePlanShowMapper performancePlanShowMapper;

    @Autowired
    private PerformancePlanService performancePlanService;

    @Autowired
    private PerformancePlanMonitoredService performancePlanMonitoredService;

    @Override
    public PerformancePlanShowDto get(Object condition) {
        PerformancePlanShow performancePlanShow = performancePlanShowMapper.get(condition);
        PerformancePlanShowDto performancePlanShowDto = BeanUtils.toBean(performancePlanShow, PerformancePlanShowDto.class);
        return performancePlanShowDto;
    }

    @Override
    public long count(PerformancePlanShowDto condition) {
        return performancePlanShowMapper.count(condition);
    }

    @Override
    public List<PerformancePlanShowDto> list(PerformancePlanShowDto condition) {
        List<PerformancePlanShow> list = performancePlanShowMapper.list(condition);
        List<PerformancePlanShowDto> dtoList = CollectionUtils.transform(list, PerformancePlanShowDto.class);
        return dtoList;
    }

    @Override
    public List<PerformancePlanShowDto> list(Map<String, Object> condition) {
        List<PerformancePlanShow> list = performancePlanShowMapper.list(condition);
        List<PerformancePlanShowDto> dtoList = CollectionUtils.transform(list, PerformancePlanShowDto.class);
        return dtoList;
    }

    @Override
    public PageResult<PerformancePlanShowDto> page(Map<String, Object> condition) {
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public PerformancePlanShowDto save(PerformancePlanShowDto performancePlanShowDto) {
        PerformancePlanShow performancePlanShow = BeanUtils.toBean(performancePlanShowDto, PerformancePlanShow.class);
        performancePlanShowMapper.save(performancePlanShow);
        performancePlanShowDto.setId(performancePlanShow.getId());
        return performancePlanShowDto;
    }

    @Override
    public int save(List<PerformancePlanShowDto> performancePlanShowDtos) {
        if (performancePlanShowDtos == null || performancePlanShowDtos.isEmpty()) {
            return 0;
        }
        List<PerformancePlanShow> performancePlanShows = CollectionUtils.transform(performancePlanShowDtos, PerformancePlanShow.class);
        return performancePlanShowMapper.saveBatch(performancePlanShows);
    }

    @Override
    public int update(PerformancePlanShowDto performancePlanShowDto) {
        PerformancePlanShow performancePlanShow = BeanUtils.toBean(performancePlanShowDto, PerformancePlanShow.class);
        return performancePlanShowMapper.update(performancePlanShow);
    }

    @Override
    public int deleteByPK(Integer id) {
        return performancePlanShowMapper.deleteByPK(id);
    }

    @Override
    public int delete(PerformancePlanShowDto performancePlanShowDto) {
        PerformancePlanShow performancePlanShow = BeanUtils.toBean(performancePlanShowDto, PerformancePlanShow.class);
        return performancePlanShowMapper.delete(performancePlanShow);
    }

    @Override
    public int deleteBatchByPK(List<Integer> ids) {
        return performancePlanShowMapper.deleteBatchByPK(ids);
    }

    @Override
    public int deleteBatch(List<PerformancePlanShowDto> performancePlanShows) {
        return 0;
    }

    @Override
    public List<PerformancePlanShowDto> getPlanShow(PerformancePlanShowDto performancePlanShowDto) {
        List<PerformancePlanShowDto> performancePlanShowDtoList = performancePlanService.show(performancePlanShowDto);
        for (PerformancePlanShowDto item : performancePlanShowDtoList) {
            Map<String, Object> map = new HashMap<>();
            map.put("planId", item.getPlanId());
            List<PerformancePlanMonitoredDto> performancePlanMonitoredDtoList = performancePlanMonitoredService.list(map);
            item.setPerformancePlanMonitoredDtoList(performancePlanMonitoredDtoList);
        }
        return performancePlanShowDtoList;
    }

    @Override
    public List<PerformancePlanShowDto> getResultList(PerformancePlanShowDto performancePlanShowDto) {
        List<PerformancePlanShowDto> performancePlanShowDtoList = performancePlanService.resultList(performancePlanShowDto);
        for (PerformancePlanShowDto item : performancePlanShowDtoList) {
            Map<String, Object> map = new HashMap<>();
            map.put("planId", item.getPlanId());
            List<PerformancePlanMonitoredDto> performancePlanMonitoredDtoList = performancePlanMonitoredService.list(map);
            item.setPerformancePlanMonitoredDtoList(performancePlanMonitoredDtoList);
        }
        return performancePlanShowDtoList;
    }

}
