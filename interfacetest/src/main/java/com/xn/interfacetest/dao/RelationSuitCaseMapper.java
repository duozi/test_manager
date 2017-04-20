/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xn.common.base.BaseMapper;
import com.xn.interfacetest.entity.RelationSuitCase;
import org.springframework.stereotype.Service;

/**
 * RelationSuitCase Dao 接口
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
public interface RelationSuitCaseMapper extends BaseMapper<RelationSuitCase, Long> {

    List<Long> listGroupByInterface(Map<String, Object> paramsMap);

    void deleteByInterfaceAndSuit(@Param("suitId") Long suitId, @Param("interfaceId")Long interfaceId);

    RelationSuitCase selectBySuitAndCase(@Param("caseId")Long caseId,@Param("suitId") Long suitId);
}
