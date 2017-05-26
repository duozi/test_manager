/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dao;


import com.xn.common.base.BaseMapper;
import com.xn.interfacetest.entity.RelationCaseParams;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * RelationCaseParams Dao 接口
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
public interface RelationCaseParamsMapper extends BaseMapper<RelationCaseParams, Long> {

    RelationCaseParams getByCaseIdAndParamName(@Param("name") String valueName,@Param("caseId") Long caseId,@Param("isDelete") Integer isDelete);
}
