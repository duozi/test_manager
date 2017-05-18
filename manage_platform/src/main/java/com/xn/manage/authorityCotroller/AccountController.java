package com.xn.manage.authorityCotroller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xn.authority.api.RoleService;
import com.xn.authority.api.UserService;
import com.xn.authority.constants.RightConstants;
import com.xn.authority.dto.RoleDto;
import com.xn.authority.dto.UserDto;
import com.xn.authority.model.Result;
import com.xn.common.utils.MD5Util;
import com.xn.common.utils.PageInfo;
import com.xn.common.utils.PageResult;
import com.xn.common.utils.PageUtil;
import com.xn.manage.utils.ModelUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("account")
public class AccountController  {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    /**
     * 账户列表
     *
     * @param request
     * @param pageInfo
     * @return
     * @date 2017年5月12日
     * @author chenhening
     */
    @RequestMapping("/account_list")
    public String list(ModelMap model, HttpServletRequest request, PageInfo pageInfo) {

        RoleDto d = new RoleDto();
        d.setStatus(RightConstants.RoleStatus.Y.name());
        List<RoleDto> allroles = roleService.list(d);
        model.addAttribute("allroles", allroles);

        Map<String, Object> params = new HashMap<String, Object>();
        StringBuilder pageParams = new StringBuilder(); // 用于页面分页查询的的url参数
        // 角色名称
        String name = request.getParameter("roleName");
        if (StringUtils.isNotBlank(name)) {
            params.put("name", name);
            model.put("name",name);
            pageParams.append("&name=").append(name);
        }
        // 登录账号
        String account = request.getParameter("account");
        if (StringUtils.isNotBlank(account)) {
            params.put("account", account);
            model.put("account",account);
            pageParams.append("&account=").append(account);
        }
        if (pageInfo.getCurrentPage() < 1) {
            pageInfo.setCurrentPage(1);
        }
        pageInfo.setPagination(true);
        pageInfo.setPageSize(10);
        params.put("page", pageInfo);
        params.put("status", RightConstants.RoleStatus.Y.name());
        PageResult<UserDto> pageResult = userService.page(params);

        ModelUtils.setResult(model, pageResult.getPage(), pageResult.getList());
        return "authority/account_list";

    }
    /*
    @RequestMapping(value = "/accountlist")
    @ResponseBody
    public Map<String, Object> accountlist(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        // 角色名称
        String name = request.getParameter("name");
        if (StringUtils.isNotBlank(name)) {
            params.put("name", name);
        }
        // 登录账号
        String account = request.getParameter("account");
        if (StringUtils.isNotBlank(account)) {
            params.put("account", account);
        }
        PageInfo pageInfo = PageUtil.getPageInfo(request);
        params.put("page", pageInfo);
        params.put("status", RightConstants.RoleStatus.Y.name());
        PageResult<UserDto> pageResult = userService.page(params);
        return PageUtil.getPageResult(pageResult.getList(), pageResult.getPage());
    }
*/
    /**
     * 异步拉取角色列表
     *
     * @return
     * @date 2017年5月12日
     * @author chenhening
     */
    @RequestMapping("/rolelist")
    @ResponseBody
    public List<RoleDto> rolelist() {
        Map<String, Object> params = new HashMap<String, Object>();
        List<RoleDto> allroles = roleService.list(params);
        return allroles;
    }

    /**
     * 异步验证账户名是否存在
     *
     * @param account
     * @return
     * @date 2017年5月12日
     * @author chenhening
     */
    @RequestMapping("/checkexist")
    @ResponseBody
    public Boolean validateAccountName(@RequestParam(value = "account", required = true) String account) {
        Map<String, Object> params = new HashMap<String, Object>();
        String acc = null;
        if (StringUtils.isNotBlank(account)) {
            acc = account.trim();
        }
        params.put("account", acc);

        // 查询的为账号模糊匹配的结果
        List<UserDto> users = userService.list(params);
        if (CollectionUtils.isNotEmpty(users)) {
            for (UserDto d : users) {
                if (d.getAccount().equals(acc)) {
                    return Boolean.valueOf(false);
                }
            }
        }
        return Boolean.valueOf(true);
    }


    /**
     * 增加账号 修改角色入口
     *
     * @param user
     * @return
     * @date 2017年5月12日
     * @author chenhening
     */
    @RequestMapping("/save")
    @ResponseBody
    public Result updateRole(UserDto user, HttpServletRequest request) {
        String role =request.getParameter("role");
        Result result = new Result();
        if (user == null||"".equals(role)) {
            result.error("请求参数错误，请重试");
            return result;
        }
        try {
            Long roleId = Long.parseLong(role);
            // update
            if (user != null && user.getId() != null) {
                userService.update(user, roleId);
                result.setMessage("角色修改成功");
            } else {// insert
                String account = user.getAccount().trim();
                String password = user.getPassword().trim();

                user.setAccount(account);
                user.setPassword(MD5Util.MD5(account + password).toLowerCase());
                user.setCreateTime(new Date());
                user.setUpdateTime(new Date());
                // 默认为子账号
                user.setPrimary(RightConstants.AccountTypeStatus.SUB.getId());
                user.setStatus(RightConstants.RoleStatus.Y.name());
                userService.saveUser(user, roleId);
                result.setMessage("新增账户成功");
            }
        } catch (Exception e) {
            logger.error("系统错误", e);
            result.error("保存失败，请重试！");
        }
        return result;
    }


    @RequestMapping("/updatepwd")
    @ResponseBody
    public Result updatePwd(UserDto user) {
        Result result = new Result();
        try {
            if (user != null && user.getId() != null) {
                UserDto dbUser = userService.get(user.getId());
                if (dbUser == null) {
                    result.error("账户不存在");
                    return result;
                }
                String account = dbUser.getAccount().trim();
                dbUser.setPassword(MD5Util.MD5(account + user.getPassword()).toLowerCase());
                dbUser.setUpdateTime(new Date());
                userService.update(dbUser);
                result.setMessage("密码修改成功");
            } else {
                result.error("参数错误，请重试！");
            }
        } catch (Exception e) {
            logger.error("修改密码时系统错误", e);
            result.error("保存失败，请重试!");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Result delete(@RequestParam(value = "id") Long id) {
        Result result = new Result();
        try {
            UserDto dto = userService.get(id);
            if (dto == null) {
                result.error("账号不存在，请重试！");
                return result;
            }
            userService.deleteByPK(id);
            result.setMessage("删除成功");
        } catch (Exception e) {
            logger.error("删除失败", e);
            result.error("删除失败，请重试！");
        }
        return result;
    }


}
