/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.authority.api;

import com.xn.authority.dto.RoleResourcesDto;
import com.xn.common.utils.PageResult;

import java.util.List;
import java.util.Map;


/**
 * 角色资源关联表 Service
 * 
 * @author chenhening
 * @date 2017-05-11
 */
public interface RoleResourcesService {

    /**
     * 查询单个记录角色资源关联表
     * 主键：id 
     * @param condition 主键/Map/查询对象
     * @return 角色资源关联表
     */
    RoleResourcesDto get(Object condition);

    /**
     * 统计角色资源关联表数量
     * 
     * @param condition 角色资源关联表查询条件对象
     * @return 统计数量
     */
    long count(RoleResourcesDto condition);

    /**
     * 根据组合条件查询角色资源关联表
     * 
     * @param condition 角色资源关联表查询对象
     * @return 角色资源关联表集合,如果不存在,返回Empty List
     */
    List<RoleResourcesDto> list(RoleResourcesDto condition);

    /**
     * 根据组合条件查询,不建议用该方法进行分页  角色资源关联表
     * 
     * @param condition 角色资源关联表查询对象
     * @return 角色资源关联表集合,如果不存在,返回Empty List
     */
    List<RoleResourcesDto> list(Map<String, Object> condition);
    
    /**
     * 根据组合条件做分页查询,需要condition中包含分页对象page  角色资源关联表
     * 
     * @param condition 角色资源关联表查询对象
     * @return 角色资源关联表集合,如果不存在,返回Empty List
     */
    PageResult<RoleResourcesDto> page(Map<String, Object> condition);
    

    /**
     * 保存角色资源关联表
     * 
     * @param roleResources 角色资源关联表
     * @return 带主键的DTO
     */
    RoleResourcesDto save(RoleResourcesDto roleResourcesDto);

    /**
     * 批量保存角色资源关联表
     * 
     * @param roleResourcess 角色资源关联表
     * @return 带主键的DTO
     */
    int save(List<RoleResourcesDto> roleResourcesDtos);

    /**
     * 更新角色资源关联表
     * 
     * @param roleResources 角色资源关联表
     * @return 操作影响行数
     */
    int update(RoleResourcesDto roleResourcesDto);
    
    /**
     * 根据主键删除角色资源关联表
     * 不建议，建议使用delete(RoleResources roleResources)
     * @param id 主键
     * @return 操作影响行数
     */
    int deleteByPK(Long id);
    
    /**
     * 删除角色资源关联表
     * 
     * @param id 主键
     * @return 操作影响行数
     */
    int delete(RoleResourcesDto roleResourcesDto);
    
    /**
     * 根据主键删除角色资源关联表
     * 不建议，建议使用delete(RoleResourcesDto roleResources)
     * @param id 主键
     * @return 操作影响行数
     */
    public int deleteBatchByPK(List<Long> ids);
    
    
    /**
     * 批量删除角色资源关联表
     * 
     * @param id 主键
     * @return 操作影响行数
     */
    int deleteBatch(List<RoleResourcesDto> roleResourcess);

    /**
     * 保存角色权限关联
     * @param codes
     * @param roleId
     */
    void saveRoleRights(List<Long> codes, Long roleId);
}
