/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.authority.service.impl;

import java.util.*;

import com.xn.authority.api.ResourcesService;
import com.xn.authority.constants.RightConstants;
import com.xn.authority.dao.ResourcesMapper;
import com.xn.authority.dto.ResourcesDto;
import com.xn.authority.dto.RoleDto;
import com.xn.authority.entity.Resources;
import com.xn.authority.utils.CollectionUtils;
import com.xn.common.utils.BeanUtils;
import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * 菜单资源表 Service实现
 * 
 * @author chenhening
 * @date 2017-05-11
 */
@Service
@Transactional
public class ResourcesServiceImpl implements ResourcesService {

    /**
     * 菜单资源表 Dao
     */
    @Autowired
    private ResourcesMapper resourcesMapper;

    @Override
    @Transactional(readOnly = true)
    public ResourcesDto get(Object condition)  
	{  
        Resources resources = resourcesMapper.get(condition);
        ResourcesDto resourcesDto = BeanUtils.toBean(resources,ResourcesDto.class);
	    return resourcesDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(ResourcesDto condition) {
        return resourcesMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourcesDto> list(ResourcesDto condition) {
        List<Resources> list = resourcesMapper.list(condition);
        List<ResourcesDto> dtoList = CollectionUtils.transform(list, ResourcesDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourcesDto> list(Map<String,Object> condition) {
        List<Resources> list = resourcesMapper.list(condition);
        List<ResourcesDto> dtoList = CollectionUtils.transform(list, ResourcesDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<ResourcesDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public ResourcesDto save(ResourcesDto resourcesDto) {
        Resources resources = BeanUtils.toBean(resourcesDto,Resources.class);
        resourcesMapper.save(resources);
        resourcesDto.setId(resources.getId());
        return resourcesDto;
    }

    @Override
    public int save(List<ResourcesDto> resourcesDtos) {
        if (resourcesDtos == null || resourcesDtos.isEmpty()) {
            return 0;
        }
        List<Resources> resourcess = CollectionUtils.transform(resourcesDtos, Resources.class);
        return resourcesMapper.saveBatch(resourcess);
    }

    @Override
    public int update(ResourcesDto resourcesDto) {
        Resources resources = BeanUtils.toBean(resourcesDto,Resources.class);
        return resourcesMapper.update(resources);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return resourcesMapper.deleteByPK(id);
    }

    @Override
    public int delete(ResourcesDto resourcesDto) {
        Resources resources = BeanUtils.toBean(resourcesDto,Resources.class);
        return resourcesMapper.delete(resources);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return resourcesMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<ResourcesDto> resourcess) {
        return 0;
    }

    @Override
    public List<ResourcesDto> listAllResource(List< RoleDto> roleList) {

        if (roleList==null||roleList.size()<=0) {
            return Collections.emptyList();
        }
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("roles", roleList);
        condition.put("status", RightConstants.RoleStatus.Y);

        List<Resources> list = resourcesMapper.listAllResource(condition);
        List<ResourcesDto> dtoList = CollectionUtils.transform(list, ResourcesDto.class);
        return dtoList;
    }

    @Override
    public List<ResourcesDto> listAllRightTree(Long roleId) {
        // 当前角色的权限集合
        List<ResourcesDto> roleRights = listAllResource(roleId);

        ResourcesDto dto = new ResourcesDto();

        List<ResourcesDto> allRights = list(dto);

        if (allRights==null||allRights.size()<=0) {
            return null;
        }
        // 过滤掉 权限管理中的编码
        Iterator<ResourcesDto> it = allRights.iterator();
        while (it.hasNext()) {
            String code = it.next().getCode();
            if (code.equals(RightConstants.FilterResource.RIGHT) || code.equals(RightConstants.FilterResource.RIGHTROLE) || code.equals(RightConstants.FilterResource.RIGHTUSER)) {
                it.remove();
            }
        }
        List<ResourcesDto> allRightTree = assemblyRightTree(allRights, null, roleRights);

        return allRightTree;

    }

    private List<ResourcesDto> assemblyRightTree1(List<ResourcesDto> allRights, Long pid, List<ResourcesDto> roleRights) {
        List<ResourcesDto> allRightTree = new ArrayList<ResourcesDto>();
        // 此处应循环一级菜单
        for (ResourcesDto right : allRights) {
            ResourcesDto rightDto = new ResourcesDto();
            rightDto.setCode(right.getCode());
            rightDto.setName(right.getName());
            rightDto.setPid(pid);
            allRightTree.add(rightDto);

            // 是否选中
            for (ResourcesDto roleRight : roleRights) {
                if (roleRight.getCode().equals(right.getCode())) {
                    rightDto.setChecked(true);
                }
            }
            // 存在子菜单，递归调用
            List<ResourcesDto> subs = listByPid(right.getId());
            if (subs!=null&&subs.size()>0) {
                rightDto.setSubRights(assemblyRightTree(subs, right.getId(), roleRights));
            }
        }
        return allRightTree;
    }

    /**
     * @param allRights 所有的资源集合
     * @param pid 父节点
     * @param roleRights 当前角色所拥有的权限
     *
     * @return
     */
    private List<ResourcesDto> assemblyRightTree(List<ResourcesDto> allRights, Long pid, List<ResourcesDto> roleRights) {

        List<ResourcesDto> allRightTree = new ArrayList<ResourcesDto>();

        if (allRights==null||allRights.size()<=0) {
            return null;
        }
        List<String> codeList = new ArrayList<String>();
        for (ResourcesDto dto : roleRights) {
            codeList.add(dto.getCode());
        }
        for (ResourcesDto right : allRights) {
            if (right.getPid() == null) {
                // 设置一级菜单是否选中
                if (codeList!=null&&codeList.size()>0 && codeList.contains(right.getCode())) {
                    right.setChecked(true);
                }
                List<ResourcesDto> subs = listByPid(right.getId());
                // 设置子菜单是否选中
                if (subs!=null&&subs.size()>0 ) {
                    for (ResourcesDto sub : subs) {
                        if (codeList!=null&&codeList.size()>0 && codeList.contains(sub.getCode())) {
                            sub.setChecked(true);
                        }
                        // 挂上页面元素权限
                        List<ResourcesDto> btnsubs = listByPid(sub.getId());
                        if (btnsubs!=null&&btnsubs.size()>0) {
                            for (ResourcesDto btn : btnsubs) {
                                if (codeList!=null&&codeList.size()>0&& codeList.contains(btn.getCode())) {
                                    btn.setChecked(true);
                                }
                            }
                        }
                        sub.setSubRights(btnsubs);
                    }
                }
                right.setSubRights(subs);
                allRightTree.add(right);
            }
        }
        return allRightTree;
    }

    private List<ResourcesDto> listByPid(Long pid) {
        ResourcesDto dto = new ResourcesDto();
        dto.setPid(pid);
        List<ResourcesDto> subs = list(dto);
        return subs;
    }

    @Override
    public List<ResourcesDto> listAllResource(Long roleId) {
        List<RoleDto> roleList = new ArrayList<RoleDto>();
        RoleDto roleDto = new RoleDto();
        roleDto.setId(roleId);
        roleList.add(roleDto);
        return listAllResource(roleList);
    }
}
