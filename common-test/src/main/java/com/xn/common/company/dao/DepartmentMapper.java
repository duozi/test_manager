/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.common.company.dao;


import com.xn.common.base.BaseMapper;
import com.xn.common.company.entity.Department;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Department Dao 接口
 * 
 * @author Carol
 * @date 2017-02-14
 */

public interface DepartmentMapper extends BaseMapper<Department, Long> {
    int deleteBatchByPK(List<Long> list);

    Department getByNameAndCompanyId(@Param("name") String name,@Param("companyId") Long companyId);
}
