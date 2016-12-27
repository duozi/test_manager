/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.autotest.bean.request.interfacesBean.service.impl;

import com.xn.autotest.bean.BeanUtils;
import com.xn.autotest.dao.InterfaceMapper;
import com.xn.autotest.bean.request.interfacesBean.dto.InterfaceDto;
import com.xn.autotest.bean.request.interfacesBean.entity.Interface;
import com.xn.autotest.bean.request.interfacesBean.service.InterfaceService;
import com.xn.autotest.mybatis.PageInfo;
import com.xn.autotest.mybatis.PageResult;
import com.xn.autotest.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;





/**
 * Interface Service实现
 * 
 * @author xn056839
 * @date 2016-12-22
 */

public class InterfaceServiceImpl implements InterfaceService {

    /**
     *  Dao
     */
    @Autowired
    private InterfaceMapper interfaceMapper;

    @Override
    public InterfaceDto get(Object condition)
	{
        Interface interfaceBean = interfaceMapper.get(condition);
        InterfaceDto interfaceDto = BeanUtils.toBean(interfaceBean,InterfaceDto.class);
	    return interfaceDto;  
	}  

    @Override
    public long count(InterfaceDto condition) {
        return interfaceMapper.count(condition);
    }

    @Override
    public List<InterfaceDto> list(InterfaceDto condition) {
        List<Interface> list = interfaceMapper.list(condition);
        List<InterfaceDto> dtoList = CollectionUtils.transform(list, InterfaceDto.class);
        return dtoList;
    }

    @Override
    public List<InterfaceDto> list(Map<String,Object> condition) {
        List<Interface> list = interfaceMapper.list(condition);
        List<InterfaceDto> dtoList = CollectionUtils.transform(list, InterfaceDto.class);
        return dtoList;
    }
    
    @Override
    public PageResult<InterfaceDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public InterfaceDto save(InterfaceDto interfaceDto) {
        Interface interfaceBean = BeanUtils.toBean(interfaceDto,Interface.class);
        interfaceMapper.save(interfaceBean);
        interfaceDto.setId(interfaceBean.getId());
        return interfaceDto;
    }

    @Override
    public int save(List<InterfaceDto> interfaceDtos) {
        if (interfaceDtos == null || interfaceDtos.isEmpty()) {
            return 0;
        }
        List<Interface> interfaces = CollectionUtils.transform(interfaceDtos, Interface.class);
        return interfaceMapper.saveBatch(interfaces);
    }

    @Override
    public int update(InterfaceDto interfaceDto) {
        Interface interfaceBean = BeanUtils.toBean(interfaceDto,Interface.class);
        return interfaceMapper.update(interfaceBean);
    }
    
    @Override
    public int deleteByPK(Integer id) {
        return interfaceMapper.deleteByPK(id);
    }

    @Override
    public int delete(InterfaceDto interfaceDto) {
        Interface interfaceBean = BeanUtils.toBean(interfaceDto,Interface.class);
        return interfaceMapper.delete(interfaceBean);
    }
    
    @Override
    public int deleteBatchByPK(List<Integer> ids) {
        return interfaceMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<InterfaceDto> interfaces) {
        return 0;
    }

}
