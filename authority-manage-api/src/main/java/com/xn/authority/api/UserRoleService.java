/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.authority.api;

import java.util.List;
import java.util.Map;
import com.xn.common.utils.PageResult;

import com.xn.authority.dto.UserRoleDto;

/**
 * 用户角色关联表 Service
 * 
 * @author chenhening
 * @date 2017-05-11
 */
public interface UserRoleService {

    /**
     * 查询单个记录用户角色关联表
     * 主键：id 
     * @param condition 主键/Map/查询对象
     * @return 用户角色关联表
     */
    UserRoleDto get(Object condition);

    /**
     * 统计用户角色关联表数量
     * 
     * @param condition 用户角色关联表查询条件对象
     * @return 统计数量
     */
    long count(UserRoleDto condition);

    /**
     * 根据组合条件查询用户角色关联表
     * 
     * @param condition 用户角色关联表查询对象
     * @return 用户角色关联表集合,如果不存在,返回Empty List
     */
    List<UserRoleDto> list(UserRoleDto condition);

    /**
     * 根据组合条件查询,不建议用该方法进行分页  用户角色关联表
     * 
     * @param condition 用户角色关联表查询对象
     * @return 用户角色关联表集合,如果不存在,返回Empty List
     */
    List<UserRoleDto> list(Map<String, Object> condition);
    
    /**
     * 根据组合条件做分页查询,需要condition中包含分页对象page  用户角色关联表
     * 
     * @param condition 用户角色关联表查询对象
     * @return 用户角色关联表集合,如果不存在,返回Empty List
     */
    PageResult<UserRoleDto> page(Map<String, Object> condition);
    

    /**
     * 保存用户角色关联表
     * 
     * @param userRole 用户角色关联表
     * @return 带主键的DTO
     */
    UserRoleDto save(UserRoleDto userRoleDto);

    /**
     * 批量保存用户角色关联表
     * 
     * @param userRoles 用户角色关联表
     * @return 带主键的DTO
     */
    int save(List<UserRoleDto> userRoleDtos);

    /**
     * 更新用户角色关联表
     * 
     * @param userRole 用户角色关联表
     * @return 操作影响行数
     */
    int update(UserRoleDto userRoleDto);
    
    /**
     * 根据主键删除用户角色关联表
     * 不建议，建议使用delete(UserRole userRole)
     * @param id 主键
     * @return 操作影响行数
     */
    int deleteByPK(Long id);
    
    /**
     * 删除用户角色关联表
     * 
     * @param id 主键
     * @return 操作影响行数
     */
    int delete(UserRoleDto userRoleDto);
    
    /**
     * 根据主键删除用户角色关联表
     * 不建议，建议使用delete(UserRoleDto userRole)
     * @param id 主键
     * @return 操作影响行数
     */
    public int deleteBatchByPK(List<Long> ids);
    
    
    /**
     * 批量删除用户角色关联表
     * 
     * @param id 主键
     * @return 操作影响行数
     */
    int deleteBatch(List<UserRoleDto> userRoles);
}
