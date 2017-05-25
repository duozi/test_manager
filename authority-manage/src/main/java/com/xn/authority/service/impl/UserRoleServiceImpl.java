/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.authority.service.impl;

import java.util.List;
import java.util.Map;

import com.xn.authority.api.UserRoleService;
import com.xn.authority.dao.UserRoleMapper;
import com.xn.authority.dto.UserRoleDto;
import com.xn.authority.entity.UserRole;
import com.xn.authority.utils.CollectionUtils;
import com.xn.common.utils.BeanUtils;
import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * 用户角色关联表 Service实现
 * 
 * @author chenhening
 * @date 2017-05-11
 */
@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    /**
     * 用户角色关联表 Dao
     */
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    @Transactional(readOnly = true)
    public UserRoleDto get(Object condition)
	{  
        UserRole userRole = userRoleMapper.get(condition);
        UserRoleDto userRoleDto = BeanUtils.toBean(userRole,UserRoleDto.class);
	    return userRoleDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(UserRoleDto condition) {
        return userRoleMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserRoleDto> list(UserRoleDto condition) {
        List<UserRole> list = userRoleMapper.list(condition);
        List<UserRoleDto> dtoList = CollectionUtils.transform(list, UserRoleDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserRoleDto> list(Map<String,Object> condition) {
        List<UserRole> list = userRoleMapper.list(condition);
        List<UserRoleDto> dtoList = CollectionUtils.transform(list, UserRoleDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<UserRoleDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public UserRoleDto save(UserRoleDto userRoleDto) {
        UserRole userRole = BeanUtils.toBean(userRoleDto,UserRole.class);
        userRoleMapper.save(userRole);
        userRoleDto.setId(userRole.getId());
        return userRoleDto;
    }

    @Override
    public int save(List<UserRoleDto> userRoleDtos) {
        if (userRoleDtos == null || userRoleDtos.isEmpty()) {
            return 0;
        }
        List<UserRole> userRoles = CollectionUtils.transform(userRoleDtos, UserRole.class);
        return userRoleMapper.saveBatch(userRoles);
    }

    @Override
    public int update(UserRoleDto userRoleDto) {
        UserRole userRole = BeanUtils.toBean(userRoleDto,UserRole.class);
        return userRoleMapper.update(userRole);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return userRoleMapper.deleteByPK(id);
    }

    @Override
    public int delete(UserRoleDto userRoleDto) {
        UserRole userRole = BeanUtils.toBean(userRoleDto,UserRole.class);
        return userRoleMapper.delete(userRole);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return userRoleMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<UserRoleDto> userRoles) {
        return 0;
    }

}
