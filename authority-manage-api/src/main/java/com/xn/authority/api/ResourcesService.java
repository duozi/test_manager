/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.authority.api;

import java.util.List;
import java.util.Map;
import com.xn.authority.dto.ResourcesDto;
import com.xn.authority.dto.RoleDto;
import com.xn.common.utils.PageResult;

/**
 * 菜单资源表 Service
 * 
 * @author chenhening
 * @date 2017-05-11
 */
public interface ResourcesService {

    /**
     * 查询单个记录菜单资源表
     * 主键：id 
     * @param condition 主键/Map/查询对象
     * @return 菜单资源表
     */
    ResourcesDto get(Object condition);

    /**
     * 统计菜单资源表数量
     * 
     * @param condition 菜单资源表查询条件对象
     * @return 统计数量
     */
    long count(ResourcesDto condition);

    /**
     * 根据组合条件查询菜单资源表
     * 
     * @param condition 菜单资源表查询对象
     * @return 菜单资源表集合,如果不存在,返回Empty List
     */
    List<ResourcesDto> list(ResourcesDto condition);

    /**
     * 根据组合条件查询,不建议用该方法进行分页  菜单资源表
     * 
     * @param condition 菜单资源表查询对象
     * @return 菜单资源表集合,如果不存在,返回Empty List
     */
    List<ResourcesDto> list(Map<String, Object> condition);
    
    /**
     * 根据组合条件做分页查询,需要condition中包含分页对象page  菜单资源表
     * 
     * @param condition 菜单资源表查询对象
     * @return 菜单资源表集合,如果不存在,返回Empty List
     */
    PageResult<ResourcesDto> page(Map<String, Object> condition);
    

    /**
     * 保存菜单资源表
     * 
     * @param resources 菜单资源表
     * @return 带主键的DTO
     */
    ResourcesDto save(ResourcesDto resourcesDto);

    /**
     * 批量保存菜单资源表
     * 
     * @param resourcess 菜单资源表
     * @return 带主键的DTO
     */
    int save(List<ResourcesDto> resourcesDtos);

    /**
     * 更新菜单资源表
     * 
     * @param resources 菜单资源表
     * @return 操作影响行数
     */
    int update(ResourcesDto resourcesDto);
    
    /**
     * 根据主键删除菜单资源表
     * 不建议，建议使用delete(Resources resources)
     * @param id 主键
     * @return 操作影响行数
     */
    int deleteByPK(Long id);
    
    /**
     * 删除菜单资源表
     * 
     * @param id 主键
     * @return 操作影响行数
     */
    int delete(ResourcesDto resourcesDto);
    
    /**
     * 根据主键删除菜单资源表
     * 不建议，建议使用delete(ResourcesDto resources)
     * @param id 主键
     * @return 操作影响行数
     */
    public int deleteBatchByPK(List<Long> ids);
    
    
    /**
     * 批量删除菜单资源表
     * 
     * @param id 主键
     * @return 操作影响行数
     */
    int deleteBatch(List<ResourcesDto> resourcess);

    /**
     * 根据角色列表查询权限
     * @param roleList
     * @return
     */
    List< ResourcesDto> listAllResource(List<RoleDto> roleList);

    /**
     * 获取权限树集合
     * @param id
     * @return
     */
    List< ResourcesDto> listAllRightTree(Long id);

    /**
     * 根据角色查询权限资源
     * @param roleId
     * @return
     */
    List< ResourcesDto> listAllResource(Long roleId);
}
