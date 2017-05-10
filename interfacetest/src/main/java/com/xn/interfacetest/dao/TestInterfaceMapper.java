/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.dao;

import java.util.List;
import java.util.Map;

import com.xn.common.base.BaseMapper;
import com.xn.interfacetest.dto.TestInterfaceDto;
import com.xn.interfacetest.dto.TestSuitDto;
import com.xn.interfacetest.entity.TestInterface;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * TestInterface Dao 接口
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
public interface TestInterfaceMapper extends BaseMapper<TestInterface, Long> {

    List<TestInterface> listByParams(Map<String, Object> params);

    String getParamsByInterfaceId(String interfaceId);

    TestInterface getByCaseId(Long caseId);

    List<TestInterface> listWithInfoByIds(String[] interfaceArray);

    void changeStatus(@Param("status")int status, @Param("id") Long id);

    List<TestInterface> listAllBySuitId(Long suitId);

    void changeStatusList(@Param("status")int status,@Param("ids")  List<TestInterfaceDto> interfaceIdList);

    List<TestInterface> listAllBySuitList(@Param("testSuitDtoList") List<TestSuitDto> testSuitDtoList);
}
