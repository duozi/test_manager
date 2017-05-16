/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.authority.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.xn.authority.api.ResourcesService;
import com.xn.authority.api.RoleService;
import com.xn.authority.api.UserRoleService;
import com.xn.authority.api.UserService;
import com.xn.authority.dao.UserMapper;
import com.xn.authority.dto.ResourcesDto;
import com.xn.authority.dto.RoleDto;
import com.xn.authority.dto.UserDto;
import com.xn.authority.dto.UserRoleDto;
import com.xn.authority.entity.User;
import com.xn.authority.utils.CollectionUtils;
import com.xn.common.utils.BeanUtils;
import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 用户表 Service实现
 * 
 * @author chenhening
 * @date 2017-05-11
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    /**
     * 用户表 Dao
     */
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ResourcesService resourcesService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    @Transactional(readOnly = true)
    public UserDto get(Object condition)
	{  
        User user = userMapper.get(condition);
        UserDto userDto = BeanUtils.toBean(user,UserDto.class);
	    return userDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(UserDto condition) {
        return userMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> list(UserDto condition) {
        List<User> list = userMapper.list(condition);
        List<UserDto> dtoList = CollectionUtils.transform(list, UserDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> list(Map<String,Object> condition) {
        List<User> list = userMapper.list(condition);
        List<UserDto> dtoList = CollectionUtils.transform(list, UserDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<UserDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public UserDto save(UserDto userDto) {
        User user = BeanUtils.toBean(userDto,User.class);
        userMapper.save(user);
        userDto.setId(user.getId());
        return userDto;
    }

    @Override
    public int save(List<UserDto> userDtos) {
        if (userDtos == null || userDtos.isEmpty()) {
            return 0;
        }
        List<User> users = CollectionUtils.transform(userDtos, User.class);
        return userMapper.saveBatch(users);
    }

    @Override
    public int update(UserDto userDto) {
        User user = BeanUtils.toBean(userDto,User.class);
        return userMapper.update(user);
    }
    
    @Override
    public int deleteByPK(Long id) {
        int re = 0;
        re = userMapper.deleteByPK(id);
        // 删除用户角色关联
        if (re > 0) {
            UserRoleDto dto = new UserRoleDto();
            dto.setUserId(id);
            userRoleService.delete(dto);
        }
        return re;
    }

    @Override
    public int delete(UserDto userDto) {
        User user = BeanUtils.toBean(userDto,User.class);
        return userMapper.delete(user);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return userMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<UserDto> users) {
        return 0;
    }

    @Override
    public List<RoleDto> listAllRole(Long userId) {
        List< RoleDto> userRoles = roleService.listAllRoleByUserId(userId);
        return userRoles;
    }
    @Override
    public List<ResourcesDto> listAllResource(Long userId) {
        /* 查询用户所属的角色列表 */
        List<RoleDto> roleList = listAllRole(userId);

        if (roleList==null ||roleList.size()<=0) {
            return Collections.emptyList();
        } else {
            return resourcesService.listAllResource(roleList); // 查询当前用户角色拥有的权限
        }
    }

    @Override
    public void update(UserDto user, Long rid) {
        try {
            UserDto dbDto = get(user.getId());
            // 删除旧角色关联
             UserRoleDto ur = new UserRoleDto();
            ur.setUserId(dbDto.getId());
            userRoleService.delete(ur);
            // 保存新关联
            if (rid != 0) {
                ur.setRoleId(rid);
                userRoleService.save(ur);
            }
        } catch (Exception e) {
            logger.error("修改账号角色出现异常", e);
        }
    }

    @Override
    public void saveUser(UserDto user, Long rid) {
        try {
            // 保存子账号信息
            UserDto dbDto = save(user);
            // 保存账号角色关联
            UserRoleDto ur = new UserRoleDto();
            ur.setUserId(dbDto.getId());
            ur.setRoleId(rid);
            userRoleService.save(ur);
        } catch (Exception e) {
            logger.error("添加子账号出现异常", e);
        }
    }
}
