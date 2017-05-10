/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xn.common.base.BaseMapper;
import com.xn.interfacetest.entity.RelationCaseDatabase;
import org.springframework.stereotype.Service;

/**
 * RelationCaseDatabase Dao 接口
 * 
 * @author Carol
 * @date 2017-02-14
 */

@Service
public interface RelationCaseDatabaseMapper extends BaseMapper<RelationCaseDatabase, Long> {

    List<RelationCaseDatabase> getByCaseId(@Param("caseId") Long caseId);

    List<RelationCaseDatabase> getByCaseIdAndOperateType(@Param("caseId") Long caseId,@Param("operateType") int operateType);
}
