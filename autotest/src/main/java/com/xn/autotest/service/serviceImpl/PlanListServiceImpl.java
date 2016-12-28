package com.xn.autotest.service.serviceImpl;/**
 * Created by xn056839 on 2016/12/13.
 */

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.xn.autotest.bean.CommonResult;
import com.xn.autotest.bean.request.plan.dto.PlanDto;
import com.xn.autotest.bean.request.plan.service.PlanService;
import com.xn.autotest.bean.request.system.dto.SystemDto;
import com.xn.autotest.bean.request.system.service.SystemService;
import com.xn.autotest.enums.ResultMsgEnum;
import com.xn.autotest.service.PlanListService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class PlanListServiceImpl implements PlanListService {
    private static final Logger logger = LoggerFactory.getLogger(PlanListServiceImpl.class);
    @Resource
    PlanService planService;
    @Resource
    SystemService systemService;

    /**
     * 根据id查一个plan
     *
     * @param planDto
     * @return
     */
    @Override
    public CommonResult<PlanDto> getPlanById(PlanDto planDto) {
        CommonResult<PlanDto> result = new CommonResult<>();
        if (planDto.getId() == 0) {
            result.setCode(ResultMsgEnum.PARAMS_ERROR.getReturnCode());
            result.setMessage(ResultMsgEnum.PARAMS_ERROR.getReturnMsg());
            return result;
        } else {
            try {
                result.setCode(ResultMsgEnum.SUCCESS.getReturnCode());
                result.setMessage(ResultMsgEnum.SUCCESS.getReturnMsg());
                PlanDto planDtoResult = planService.get(planDto);
                result.setData(planDtoResult);
                return result;
            } catch (Exception e) {
                result.setCode(ResultMsgEnum.FAILURE_DATABASE.getReturnCode());
                result.setMessage(ResultMsgEnum.FAILURE_DATABASE.getReturnMsg());

                return result;
            }

        }


    }

    /**
     * 保存计划
     *
     * @param planDto
     * @return
     */
    @Override
    public CommonResult<PlanDto> savePlan(PlanDto planDto) {
        CommonResult<PlanDto> result = new CommonResult();
        result.setCode(ResultMsgEnum.SUCCESS.getReturnCode());
        result.setMessage(ResultMsgEnum.SUCCESS.getReturnMsg());
        try {
            PlanDto planDtoSave = planService.save(planDto);

            if (planDto.getId() != 0) {
                result.setData(planDtoSave);
                return result;

            } else {
                result.setCode(ResultMsgEnum.SAVE_FAILED.getReturnCode());
                result.setMessage(ResultMsgEnum.SAVE_FAILED.getReturnMsg());
                return result;
            }
        } catch (Exception e) {

            result.setCode(ResultMsgEnum.FAILURE_DATABASE.getReturnCode());
            result.setMessage(ResultMsgEnum.FAILURE_DATABASE.getReturnMsg());
            return result;
        }
    }

    @Override
    public CommonResult<Integer> updatePlanById(PlanDto planDto) {
        CommonResult<Integer> result = new CommonResult();
        result.setCode(ResultMsgEnum.SUCCESS.getReturnCode());
        result.setMessage(ResultMsgEnum.SUCCESS.getReturnMsg());
        try {
            if (planDto.getId() != 0) {
                Integer updateCount = planService.update(planDto);
                result.setData(updateCount);
                return result;
            } else {
                result.setCode(ResultMsgEnum.UPDATE_FAILED.getReturnCode());
                result.setMessage(ResultMsgEnum.UPDATE_FAILED.getReturnMsg());
                return result;
            }
        } catch (Exception e) {
            result.setCode(ResultMsgEnum.FAILURE_DATABASE.getReturnCode());
            result.setMessage(ResultMsgEnum.FAILURE_DATABASE.getReturnMsg());
            return result;
        }

    }

    @Override
    public CommonResult<Integer> deletePlanById(PlanDto planDto) {
        CommonResult<Integer> result = new CommonResult();
        result.setCode(ResultMsgEnum.SUCCESS.getReturnCode());
        result.setMessage(ResultMsgEnum.SUCCESS.getReturnMsg());
        try {

            if (planDto.getId() != 0) {
                Integer delCount = planService.delete(planDto);
                result.setData(delCount);
                return result;
            } else {
                result.setCode(ResultMsgEnum.DEL_FAILED.getReturnCode());
                result.setMessage(ResultMsgEnum.DEL_FAILED.getReturnMsg());
                return result;
            }
        } catch (Exception e) {
            result.setCode(ResultMsgEnum.FAILURE_DATABASE.getReturnCode());
            result.setMessage(ResultMsgEnum.FAILURE_DATABASE.getReturnMsg());
            return result;
        }
    }

    /**
     * 返回符合条件的计划list
     *
     * @param map
     * @return
     */
    @Override
    public CommonResult<List<PlanDto>> getPlanByParams(Map<String, Object> map) {
        CommonResult<List<PlanDto>> result = new CommonResult();
        result.setCode(ResultMsgEnum.SUCCESS.getReturnCode());
        result.setMessage(ResultMsgEnum.SUCCESS.getReturnMsg());

        Integer planId = (Integer) map.get("planId");
        String systemName = (String) map.get("systemName");
        Integer executeType = (Integer) map.get("executeType");
        Integer planType = (Integer) map.get("planType");

        PlanDto planDto = new PlanDto();
        planDto.setId(planId);
        planDto.setExecuteType(executeType);
        planDto.setPlanType(planType);
        try {
            List<PlanDto> planDtoList = planService.list(planDto);
            List<PlanDto> returnList = Lists.newArrayList();
            if (StringUtils.isEmpty(systemName)) {

                result.setData(returnList);
                return result;

            } else {
                for (PlanDto plan : planDtoList) {

                    Set<String> set = Sets.newHashSet(plan.getSystemName().split(","));

                    if (set.contains(systemName)) {
                        returnList.add(plan);

                    }
                }
            }

            result.setData(returnList);
            return result;
        } catch (Exception e) {
            result.setCode(ResultMsgEnum.FAILURE_DATABASE.getReturnCode());
            result.setMessage(ResultMsgEnum.FAILURE_DATABASE.getReturnMsg());
            return result;
        }
    }

    /**
     * 根据id查一个system
     *
     * @param
     * @return
     */
    @Override
    public CommonResult<SystemDto> getSystemByParam(SystemDto systemDto) {
        CommonResult<SystemDto> result = new CommonResult<>();
        result.setCode(ResultMsgEnum.SUCCESS.getReturnCode());
        result.setMessage(ResultMsgEnum.SUCCESS.getReturnMsg());
        try {
            if (systemDto.getId() == 0) {
                result.setCode(ResultMsgEnum.PARAMS_ERROR.getReturnCode());
                result.setMessage(ResultMsgEnum.PARAMS_ERROR.getReturnMsg());
                return result;
            } else {

                SystemDto systemDtoResult = systemService.get(systemDto);
                result.setData(systemDtoResult);
                return result;

            }
        } catch (Exception e) {
            result.setCode(ResultMsgEnum.FAILURE_DATABASE.getReturnCode());
            result.setMessage(ResultMsgEnum.FAILURE_DATABASE.getReturnMsg());
            return result;
        }


    }


}
