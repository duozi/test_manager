/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.authority.api;


import com.xn.authority.dto.RoleDto;
import com.xn.common.utils.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 角色表 Service
 * 
 * @author chenhening
 * @date 2017-05-11
 */
public interface RoleService {

    /**
     * 查询单个记录角色表
     * 主键：id 
     * @param condition 主键/Map/查询对象
     * @return 角色表
     */
    RoleDto get(Object condition);

    /**
     * 统计角色表数量
     * 
     * @param condition 角色表查询条件对象
     * @return 统计数量
     */
    long count(RoleDto condition);

    /**
     * 根据组合条件查询角色表
     * 
     * @param condition 角色表查询对象
     * @return 角色表集合,如果不存在,返回Empty List
     */
    List<RoleDto> list(RoleDto condition);

    /**
     * 根据组合条件查询,不建议用该方法进行分页  角色表
     * 
     * @param condition 角色表查询对象
     * @return 角色表集合,如果不存在,返回Empty List
     */
    List<RoleDto> list(Map<String, Object> condition);
    
    /**
     * 根据组合条件做分页查询,需要condition中包含分页对象page  角色表
     * 
     * @param condition 角色表查询对象
     * @return 角色表集合,如果不存在,返回Empty List
     */
    PageResult<RoleDto> page(Map<String, Object> condition);
    

    /**
     * 保存角色表
     * 
     * @param role 角色表
     * @return 带主键的DTO
     */
    RoleDto save(RoleDto roleDto);

    /**
     * 批量保存角色表
     * 
     * @param roles 角色表
     * @return 带主键的DTO
     */
    int save(List<RoleDto> roleDtos);

    /**
     * 更新角色表
     * 
     * @param role 角色表
     * @return 操作影响行数
     */
    int update(RoleDto roleDto);
    
    /**
     * 根据主键删除角色表
     * 不建议，建议使用delete(Role role)
     * @param id 主键
     * @return 操作影响行数
     */
    int deleteByPK(Long id);
    
    /**
     * 删除角色表
     * 
     * @param id 主键
     * @return 操作影响行数
     */
    int delete(RoleDto roleDto);
    
    /**
     * 根据主键删除角色表
     * 不建议，建议使用delete(RoleDto role)
     * @param id 主键
     * @return 操作影响行数
     */
    public int deleteBatchByPK(List<Long> ids);
    
    
    /**
     * 批量删除角色表
     * 
     * @param id 主键
     * @return 操作影响行数
     */
    int deleteBatch(List<RoleDto> roles);

    /**
     * 根据用户id查询角色
     * @param userId
     * @return
     */
    List<RoleDto> listAllRoleByUserId(Long userId);
}
