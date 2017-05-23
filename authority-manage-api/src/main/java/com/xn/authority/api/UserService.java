/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.authority.api;

import java.util.List;
import java.util.Map;

import com.xn.authority.dto.ResourcesDto;
import com.xn.authority.dto.RoleDto;
import com.xn.common.utils.PageResult;

import com.xn.authority.dto.UserDto;

/**
 * 用户表 Service
 * 
 * @author chenhening
 * @date 2017-05-11
 */
public interface UserService {

    /**
     * 查询单个记录用户表
     * 主键：id 
     * @param condition 主键/Map/查询对象
     * @return 用户表
     */
    UserDto get(Object condition);

    /**
     * 统计用户表数量
     * 
     * @param condition 用户表查询条件对象
     * @return 统计数量
     */
    long count(UserDto condition);

    /**
     * 根据组合条件查询用户表
     * 
     * @param condition 用户表查询对象
     * @return 用户表集合,如果不存在,返回Empty List
     */
    List<UserDto> list(UserDto condition);

    /**
     * 根据组合条件查询,不建议用该方法进行分页  用户表
     * 
     * @param condition 用户表查询对象
     * @return 用户表集合,如果不存在,返回Empty List
     */
    List<UserDto> list(Map<String, Object> condition);

    /**
     * 查询用户的权限
     * @param userId
     * @return
     */
    List<ResourcesDto> listAllResource(Long userId);

    /**
     * 查询用户的角色
     * @param userId
     * @return
     */
    List<RoleDto> listAllRole(Long userId);
    /**
     * 根据组合条件做分页查询,需要condition中包含分页对象page  用户表
     * 
     * @param condition 用户表查询对象
     * @return 用户表集合,如果不存在,返回Empty List
     */
    PageResult<UserDto> page(Map<String, Object> condition);

    /**
     * 更新角色关联关系
     * @param user
     * @param rid
     */
    void update( UserDto user, Long rid);

    /**
     * 保存账号并更新角色关联关系
     * @param user
     * @param rid
     */
    void saveUser( UserDto user, Long rid);
    /**
     * 保存用户表
     * 
     * @param user 用户表
     * @return 带主键的DTO
     */
    UserDto save(UserDto userDto);

    /**
     * 根据名称查询用户
     * @param condition
     * @return
     */
    List<UserDto> findByAccount(Map<String, Object> condition);
    /**
     * 批量保存用户表
     * 
     * @param users 用户表
     * @return 带主键的DTO
     */
    int save(List<UserDto> userDtos);

    /**
     * 更新用户表
     * 
     * @param userDto 用户
     * @return 操作影响行数
     */
    int update(UserDto userDto);
    
    /**
     * 根据主键删除用户表
     * 不建议，建议使用delete(User user)
     * @param id 主键
     * @return 操作影响行数
     */
    int deleteByPK(Long id);
    
    /**
     * 删除用户表
     * 
     * @param userDto id 主键
     * @return 操作影响行数
     */
    int delete(UserDto userDto);
    
    /**
     * 根据主键删除用户表
     * 不建议，建议使用delete(UserDto user)
     * @param ids 主键
     * @return 操作影响行数
     */
    public int deleteBatchByPK(List<Long> ids);
    
    
    /**
     * 批量删除用户表
     * 
     * @param id 主键
     * @return 操作影响行数
     */
    int deleteBatch(List<UserDto> users);
}
