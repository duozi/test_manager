/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dao;


import com.xn.common.base.BaseMapper;
import com.xn.interfacetest.entity.RelationAssertResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * RelationAssertResult Dao 接口
 * 
 * @author Carol
 * @date 2017-03-31
 */
@Service
public interface RelationAssertResultMapper extends BaseMapper<RelationAssertResult, Long> {

    List<RelationAssertResult> getByReportIdAndCaseId(@Param("reportId") Long reportId,@Param("caseId") Long caseId);
}
