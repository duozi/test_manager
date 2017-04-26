/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xn.common.base.BaseMapper;
import com.xn.interfacetest.entity.ParamEntity;
import com.xn.interfacetest.entity.TestParams;
import org.springframework.stereotype.Service;

/**
 * TestParams Dao 接口
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
public interface TestParamsMapper extends BaseMapper<TestParams, Long> {

    List<TestParams> getParamsByInterfaceId(String interfaceId);

    List<ParamEntity> listByCaseIdFromRelation(Long caseId);

    void deleteByInterfaceId(Long interfaceId);

    void deleteByInterfaceIdAndParamName(@Param("interfaceId") Long interfaceId, @Param("name") String name);

    /**
     * 查询未删除的指定接口的指定名称的参数
     * @param id
     * @param newParam
     * @param i
     * @return
     */
    TestParams getParamsByInterfaceIdAndName(@Param("interfaceId") Long interfaceId, @Param("name") String name,@Param("isDelete") int isDelete);
}
