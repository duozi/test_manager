/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.common.company.dao;

import com.xn.common.base.BaseMapper;
import com.xn.common.company.entity.Company;

import java.util.List;

/**
 * Company Dao 接口
 * 
 * @author Carol
 * @date 2017-02-14
 */

public interface CompanyMapper extends BaseMapper<Company, Long> {
    /** 批量删除对象 返回删除的数量 **/
    int deleteBatchByPK(List<Long> list);

    Company getByName(String name);
}
