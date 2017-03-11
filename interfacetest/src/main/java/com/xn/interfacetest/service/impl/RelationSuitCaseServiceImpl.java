/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xn.interfacetest.dto.TestInterfaceDto;
import com.xn.interfacetest.service.TestInterfaceService;
import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.data.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xn.common.utils.BeanUtils;
import com.xn.common.utils.CollectionUtils;
import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;
import com.xn.interfacetest.dao.RelationSuitCaseMapper;
import com.xn.interfacetest.dto.RelationSuitCaseDto;
import com.xn.interfacetest.entity.RelationSuitCase;
import com.xn.interfacetest.service.RelationSuitCaseService;

import javax.servlet.http.HttpServletRequest;

/**
 * RelationSuitCase Service实现
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
@Transactional
public class RelationSuitCaseServiceImpl implements RelationSuitCaseService {

    /**
     *  Dao
     */
    @Autowired
    private RelationSuitCaseMapper relationSuitCaseMapper;

    @Autowired
    private TestInterfaceService testInterfaceService;

    @Override
    @Transactional(readOnly = true)
    public RelationSuitCaseDto get(Object condition)
	{  
        RelationSuitCase relationSuitCase = relationSuitCaseMapper.get(condition);
        RelationSuitCaseDto relationSuitCaseDto = BeanUtils.toBean(relationSuitCase,RelationSuitCaseDto.class);
	    return relationSuitCaseDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(RelationSuitCaseDto condition) {
        return relationSuitCaseMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelationSuitCaseDto> list(RelationSuitCaseDto condition) {
        List<RelationSuitCase> list = relationSuitCaseMapper.list(condition);
        List<RelationSuitCaseDto> dtoList = CollectionUtils.transform(list, RelationSuitCaseDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelationSuitCaseDto> list(Map<String,Object> condition) {
        List<RelationSuitCase> list = relationSuitCaseMapper.list(condition);
        List<RelationSuitCaseDto> dtoList = CollectionUtils.transform(list, RelationSuitCaseDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<RelationSuitCaseDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public RelationSuitCaseDto save(RelationSuitCaseDto relationSuitCaseDto) {
        RelationSuitCase relationSuitCase = BeanUtils.toBean(relationSuitCaseDto,RelationSuitCase.class);
        RelationSuitCase existRelation = relationSuitCaseMapper.selectBySuitAndCase(relationSuitCaseDto.getCaseId(),relationSuitCaseDto.getSuitId());
        //该测试集和测试用例关系要唯一，不存在则新增
        if(null == existRelation){
            relationSuitCaseMapper.save(relationSuitCase);
        }
        relationSuitCaseDto.setId(relationSuitCase.getId());
        return relationSuitCaseDto;
    }

    @Override
    public int save(List<RelationSuitCaseDto> relationSuitCaseDtos) {
        if (relationSuitCaseDtos == null || relationSuitCaseDtos.isEmpty()) {
            return 0;
        }
        List<RelationSuitCase> relationSuitCases = CollectionUtils.transform(relationSuitCaseDtos, RelationSuitCase.class);
        return relationSuitCaseMapper.saveBatch(relationSuitCases);
    }

    @Override
    public int update(RelationSuitCaseDto relationSuitCaseDto) {
        RelationSuitCase relationSuitCase = BeanUtils.toBean(relationSuitCaseDto,RelationSuitCase.class);
        return relationSuitCaseMapper.update(relationSuitCase);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return relationSuitCaseMapper.deleteByPK(id);
    }

    @Override
    public int delete(RelationSuitCaseDto relationSuitCaseDto) {
        RelationSuitCase relationSuitCase = BeanUtils.toBean(relationSuitCaseDto,RelationSuitCase.class);
        return relationSuitCaseMapper.delete(relationSuitCase);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return relationSuitCaseMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<RelationSuitCaseDto> relationSuitCases) {
        return 0;
    }

    @Override
    public List<TestInterfaceDto> listGroupByInterface(Map<String, Object> paramsMap) {
        List<Long> list = relationSuitCaseMapper.listGroupByInterface(paramsMap);
        List<TestInterfaceDto> interfaceDtoList = new ArrayList<TestInterfaceDto>();
        for(Long id : list){
            TestInterfaceDto interfaceDto = testInterfaceService.get(id);
            interfaceDtoList.add(interfaceDto);
        }
        return interfaceDtoList;
    }

    @Override
    public void saveRelation(Long interfaceId, Long suitId, String caseIds) {
        //保存新的测试用例到测试集
        if(StringUtils.isNotBlank(caseIds) && !"null".equals(caseIds)){
            //解析出每一个caseId
            String[] caseArray = caseIds.split(",|，");
            for(String caseidStr : caseArray) {
                RelationSuitCaseDto relationSuitCaseDto = new RelationSuitCaseDto();
                //用例id
                Long caseId = Long.parseLong(caseidStr);
                relationSuitCaseDto.setCaseId(caseId);

                //接口id
                relationSuitCaseDto.setInterfaceId(interfaceId);

                //根据接口查询服务--服务id
                TestInterfaceDto testInterface = testInterfaceService.get(interfaceId);
                if(null != testInterface){
                    relationSuitCaseDto.setServiceId(testInterface.getServiceId());
                }

                //测试集id
                relationSuitCaseDto.setSuitId(suitId);
                //保存测试集和用例的关系
                relationSuitCaseDto = this.save(relationSuitCaseDto);
            }
        }
    }

}
