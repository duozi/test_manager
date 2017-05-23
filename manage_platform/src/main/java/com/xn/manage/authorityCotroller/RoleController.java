package com.xn.manage.authorityCotroller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xn.authority.api.ResourcesService;
import com.xn.authority.api.RoleResourcesService;
import com.xn.authority.api.RoleService;
import com.xn.authority.api.UserRoleService;
import com.xn.authority.constants.RightConstants;
import com.xn.authority.dto.ResourcesDto;
import com.xn.authority.dto.RoleDto;
import com.xn.authority.dto.UserRoleDto;
import com.xn.authority.model.Result;
import com.xn.common.base.CommonResult;
import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;
import com.xn.common.utils.PageUtil;
import com.xn.manage.performanceController.PerformanceScenarioController;
import com.xn.manage.utils.ModelUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * 角色管理
 */
@Controller
@RequestMapping("role")
public class RoleController  {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourcesService resourcesService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleResourcesService roleResourcesService;

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    /**
     * 角色列表页面
     *
     * @return
     * @date 
     * @author chenhening
     */
   /* @RequestMapping(value = "/role_list")
    public String toRole() {
        return "authority/role_list";
    }*/


    @RequestMapping(value = "/role_list")
    public String roleList(HttpServletRequest request, ModelMap map, PageInfo pageInfo) {
        StringBuilder pageParams = new StringBuilder(); // 用于页面分页查询的的url参数
        Map<String, Object> params = new HashMap<String, Object>();
        String name = request.getParameter("name");

        if (StringUtils.isNotBlank(name)) {
            params.put("name", name);
            map.addAttribute("name",name);
            pageParams.append("&name=").append(name);
        }
        if (pageInfo.getCurrentPage() < 1) {
            pageInfo.setCurrentPage(1);
        }
        pageInfo.setPagination(true);
        pageInfo.setPageSize(10);
        params.put("page", pageInfo);
        params.put("status", RightConstants.RoleStatus.Y.name());
        pageInfo.setParams(pageParams.toString());

        PageResult<RoleDto> pageResult = roleService.page(params);
        ModelUtils.setResult(map, pageResult.getPage(), pageResult.getList());

        return "authority/role_list";
    }

    /**
     * 异步校验查询角色名称
     *
     * @param name
     * @return
     * @date 2017年5月12日
     * @author chenhening
     */
    @RequestMapping(value = "/checkexist", method = RequestMethod.POST)
    @ResponseBody
    public Boolean validPackageName(@RequestParam(value = "name", required = true) String name) {
        Boolean result = Boolean.TRUE;
        RoleDto condition = new RoleDto();
        condition.setName(name);
        condition.setStatus(RightConstants.RoleStatus.Y.name());
        long num = roleService.count(condition);
        if (num > 0) {
            return Boolean.FALSE;
        }
        return result;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result savePackage(@RequestParam(value = "name") String name) {
        Result result = new Result();

        if (StringUtils.isEmpty(name)) {
            result.error("角色名称不能为空");
            return result;
        }

        RoleDto condition = new RoleDto();
        condition.setName(name);
        condition.setStatus(RightConstants.RoleStatus.Y.name());
        condition.setCreateTime(new Date());
        condition.setUpdateTime(new Date());
        try {
            roleService.save(condition);
            result.setMessage("角色保存成功");
        } catch (Exception e) {
            logger.error("保存角色失败", e);
            result.error("保存角色信息失败");
            return result;
        }
        return result;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result deletePackage(HttpServletRequest request) {
        Result result = new Result();
        String id = request.getParameter("id");
        if (StringUtils.isEmpty(id)) {
            logger.info("删除角色id为空");
            result.error("删除失败");
            return result;
        }
        Long roleId = Long.valueOf(id);
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("roleId", roleId);
        List<UserRoleDto> list = userRoleService.list(condition);
        if (CollectionUtils.isNotEmpty(list)) {// 有账户使用该角色
            result.setCode("hasRelation");
            result.error("当前角色仍有账户使用，不可删除");
            return result;
        }
        try {
            // 删除角色 以及角色关联
            roleService.deleteByPK(roleId);
            result.setMessage("角色删除成功");
        } catch (Exception e) {
            logger.error("删除角色失败", e);
            result.error("删除角色失败");
            return result;
        }
        return result;
    }

    /**
     * 角色权限编辑页面
     * 
     * @param model
     * @param id
     * @return
     * @author chenhening
     */
    @RequestMapping("/edit_role_right")
    public String editShopRight(Model model, Long id) {
        Assert.notNull(id, "角色ID不能为空");
        // 查询当前角色的权限集合
//        List<ResourcesDto> allRightTree = resourcesService.listAllRightTree(id);
//        model.addAttribute("tree", allRightTree);
        model.addAttribute("roleId", id);
        return "authority/edit_role_right";
    }

    /**
     * 查询当前角色的权限集合
     * @param request
     * @return
     */
    @RequestMapping(value = "/getTree", method = RequestMethod.POST)
    @ResponseBody
    public Result getTree(HttpServletRequest request) {
        String id = request.getParameter("id");
        Assert.notNull(id, "角色ID不能为空");

        Result result = new Result();
        List<ResourcesDto> allRightTree ;
        try {
            allRightTree = resourcesService.listAllRightTree(Long.parseLong(id));
            result.setAttach(allRightTree);
            result.setMessage("成功");
        } catch (Exception e) {
            logger.error("失败", e);
            result.error("失败");
            return result;
        }
        return result;
    }

    @RequestMapping("/right_save")
    @ResponseBody
    public Result saveRight(Model model, @RequestParam(value = "codes[]", required = false) List<Long> codes, Long roleId) {
        Result result = new Result();
        if (CollectionUtils.isEmpty(codes)) {
            result.error("至少选择一个权限");
            return result;
        }
        if (roleId != null && !NumberUtils.isNumber(String.valueOf(roleId))) {
            result.error("角色ID错误");
            return result;
        }
        try {
            roleResourcesService.saveRoleRights(codes, roleId);
        } catch (Exception e) {
            result.error("服务器错误，请重试");
            logger.error("保存角色权限失败", e);
        }
        return result;
    }

}
