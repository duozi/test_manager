/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.authority.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xn.authority.api.RoleResourcesService;
import com.xn.authority.api.RoleService;
import com.xn.authority.dao.RoleResourcesMapper;
import com.xn.authority.dto.RoleDto;
import com.xn.authority.dto.RoleResourcesDto;
import com.xn.authority.entity.RoleResources;
import com.xn.common.utils.BeanUtils;
import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import com.xn.authority.utils.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




/**
 * 角色资源关联表 Service实现
 * 
 * @author chenhening
 * @date 2017-05-11
 */
@Service
@Transactional
public class RoleResourcesServiceImpl implements RoleResourcesService {

    /**
     * 角色资源关联表 Dao
     */
    @Autowired
    private RoleResourcesMapper roleResourcesMapper;

    @Autowired
    private RoleService roleService;

    @Override
    @Transactional(readOnly = true)
    public RoleResourcesDto get(Object condition)
	{  
        RoleResources roleResources = roleResourcesMapper.get(condition);
        RoleResourcesDto roleResourcesDto = BeanUtils.toBean(roleResources,RoleResourcesDto.class);
	    return roleResourcesDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(RoleResourcesDto condition) {
        return roleResourcesMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleResourcesDto> list(RoleResourcesDto condition) {
        List<RoleResources> list = roleResourcesMapper.list(condition);
        List<RoleResourcesDto> dtoList = CollectionUtils.transform(list, RoleResourcesDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleResourcesDto> list(Map<String,Object> condition) {
        List<RoleResources> list = roleResourcesMapper.list(condition);
        List<RoleResourcesDto> dtoList = CollectionUtils.transform(list, RoleResourcesDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<RoleResourcesDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public RoleResourcesDto save(RoleResourcesDto roleResourcesDto) {
        RoleResources roleResources = BeanUtils.toBean(roleResourcesDto,RoleResources.class);
        roleResourcesMapper.save(roleResources);
        roleResourcesDto.setId(roleResources.getId());
        return roleResourcesDto;
    }

    @Override
    public int save(List<RoleResourcesDto> roleResourcesDtos) {
        if (roleResourcesDtos == null || roleResourcesDtos.isEmpty()) {
            return 0;
        }
        List<RoleResources> roleResourcess = CollectionUtils.transform(roleResourcesDtos, RoleResources.class);
        return roleResourcesMapper.saveBatch(roleResourcess);
    }

    @Override
    public int update(RoleResourcesDto roleResourcesDto) {
        RoleResources roleResources = BeanUtils.toBean(roleResourcesDto,RoleResources.class);
        return roleResourcesMapper.update(roleResources);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return roleResourcesMapper.deleteByPK(id);
    }

    @Override
    public int delete(RoleResourcesDto roleResourcesDto) {
        RoleResources roleResources = BeanUtils.toBean(roleResourcesDto,RoleResources.class);
        return roleResourcesMapper.delete(roleResources);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return roleResourcesMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<RoleResourcesDto> roleResourcess) {
        return 0;
    }

    @Override
    public void saveRoleRights(List<Long> codes, Long roleId) {
        // 删除旧关联关系
        RoleResourcesDto dto = new RoleResourcesDto();
        dto.setRoleId(roleId);
        delete(dto);

        // 插入新关联关系
        List<RoleResources> list = new ArrayList<RoleResources>(codes.size());

        for (Long code : codes) {
            RoleResources rr = new RoleResources();
            rr.setRoleId(roleId);
            rr.setSourceId(code);
            list.add(rr);
        }
        roleResourcesMapper.saveBatch(list);

        RoleDto rd = new RoleDto();
        rd.setId(roleId);
        rd.setUpdateTime(new Date());
        roleService.update(rd);
    }
}
