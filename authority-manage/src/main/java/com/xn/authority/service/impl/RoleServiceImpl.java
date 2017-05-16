/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.authority.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xn.authority.api.RoleResourcesService;
import com.xn.authority.api.RoleService;
import com.xn.authority.dao.RoleMapper;
import com.xn.authority.dto.RoleDto;
import com.xn.authority.dto.RoleResourcesDto;
import com.xn.authority.entity.Role;
import com.xn.authority.utils.CollectionUtils;
import com.xn.common.utils.BeanUtils;
import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * 角色表 Service实现
 * 
 * @author chenhening
 * @date 2017-05-11
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    /**
     * 角色表 Dao
     */
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleResourcesService roleResourcesService;

    @Override
    @Transactional(readOnly = true)
    public RoleDto get(Object condition)
	{  
        Role role = roleMapper.get(condition);
        RoleDto roleDto = BeanUtils.toBean(role,RoleDto.class);
	    return roleDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(RoleDto condition) {
        return roleMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDto> list(RoleDto condition) {
        List<Role> list = roleMapper.list(condition);
        List<RoleDto> dtoList = CollectionUtils.transform(list, RoleDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDto> list(Map<String,Object> condition) {
        List<Role> list = roleMapper.list(condition);
        List<RoleDto> dtoList = CollectionUtils.transform(list, RoleDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<RoleDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public RoleDto save(RoleDto roleDto) {
        Role role = BeanUtils.toBean(roleDto,Role.class);
        roleMapper.save(role);
        roleDto.setId(role.getId());
        return roleDto;
    }

    @Override
    public int save(List<RoleDto> roleDtos) {
        if (roleDtos == null || roleDtos.isEmpty()) {
            return 0;
        }
        List<Role> roles = CollectionUtils.transform(roleDtos, Role.class);
        return roleMapper.saveBatch(roles);
    }

    @Override
    public int update(RoleDto roleDto) {
        Role role = BeanUtils.toBean(roleDto,Role.class);
        return roleMapper.update(role);
    }
    
    @Override
    public int deleteByPK(Long id) {
        int count = 0;
        // 删除角色
        count = roleMapper.deleteByPK(id);
        // 删除角色权限关联
        RoleResourcesDto dto = new RoleResourcesDto();
        dto.setRoleId(id);
        roleResourcesService.delete(dto);
        return count;
    }

    @Override
    public int delete(RoleDto roleDto) {
        Role role = BeanUtils.toBean(roleDto,Role.class);
        return roleMapper.delete(role);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return roleMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<RoleDto> roles) {
        return 0;
    }

    @Override
    public List< RoleDto> listAllRoleByUserId(Long userId) {

        if (userId == null) {
            return Collections.emptyList();
        }
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("userId", userId);
        condition.put("status", "Y");
        List< Role> list =roleMapper.listAllRoleByUserId(condition);
        List< RoleDto> dtoList = CollectionUtils.transform(list, RoleDto.class);
        return dtoList;
    }
}
