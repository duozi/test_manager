package com.xn.manage.shiro;


import com.xn.authority.api.ResourcesService;
import com.xn.authority.api.UserService;
import com.xn.authority.constants.RightConstants;
import com.xn.authority.dto.ResourcesDto;
import com.xn.authority.dto.UserDto;
import com.xn.common.utils.MD5Util;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


/*
 * Created by chenhening on 2017/5/10.
 * */
public class WebAuthorizingRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(WebAuthorizingRealm.class);
    @Autowired
    private UserService userService;

    @Autowired
    private ResourcesService resourcesService;
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        // 获取前台输入的密码
        String username = token.getUsername();
        String password = String.valueOf(token.getPassword());
        if (StringUtils.isNotBlank(username)&&StringUtils.isNotBlank(password)){
            Session session = SecurityUtils.getSubject().getSession();

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("account",username);
            params.put("status",RightConstants.RoleStatus.Y.name());
            List<UserDto> userDtoList=userService.list(params);
            if (userDtoList==null){
                logger.info("账号：{}不存在", username);
                return null;
            }else if (userDtoList!=null && userDtoList.size()>1){
                logger.info("账号：{}重复", username);
                return null;
            }
            UserDto userDto= userDtoList.get(0);

            if (isAuthenticationed(username,userDto.getPassword(),password)){
                //是管理员
                if (RightConstants.AccountTypeStatus.MASTER.getId().equals(userDto.getPrimary())){
                    session.setAttribute("isSon", false);
                }else{
                    session.setAttribute("isSon", true);
                }
                session.setAttribute("USER", userDto);
                return new SimpleAuthenticationInfo(username, userDto.getPassword(), getName());
            }
//            if ("admin".equals(username)&&"123456".equals(password)){
//                return new SimpleAuthenticationInfo(username, "123456", getName());
//            }
        }

        // 认证没有通过
        throw new UnknownAccountException();// 没帐号
    }

    /**
     * 检查账号密码是否正确
     * @param username
     * @param dbPassword
     * @param inputPassword
     * @return
     */
    private boolean isAuthenticationed(String username, String dbPassword, String inputPassword) {
        //TODO 测试
        if (dbPassword.equals(inputPassword)){
            return true;
        }
        String md5Pwd = MD5Util.MD5(username + inputPassword).toLowerCase();
        if (dbPassword.equals(md5Pwd)) {
            return true;
        }
        return false;
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 因为非正常退出，即没有显式调用 SecurityUtils.getSubject().logout()
        // (可能是关闭浏览器，或超时)，但此时缓存依旧存在(principals)，所以会自己跑到授权方法里。
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            doClearCache(principalCollection);
            SecurityUtils.getSubject().logout();
            return null;
        }
        // 获取当前登录的用户名
        String username = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        Map<String, Set<String>> authorizationMap = getAuthorizationInfo(username);

        authorizationInfo.setStringPermissions(authorizationMap.get("permissions")); // 设置权限
        return authorizationInfo;
    }

    /**
     * 获取权限码
     * @param username
     * @return
     */
    private Map<String, Set<String>> getAuthorizationInfo(String username) {

        Map<String, Set<String>> authorizationMap = new HashMap<String, Set<String>>();
        Set<String> codeSet = new HashSet<String>();

        Session session = SecurityUtils.getSubject().getSession();
        Boolean isSon = (Boolean) session.getAttribute("isSon");
        UserDto userDto = (UserDto) session.getAttribute("USER");
        List<ResourcesDto> sourceList = null;
        try {
            if (isSon) {// 子账号授权
                sourceList = userService.listAllResource(userDto.getId());
                if (CollectionUtils.isNotEmpty(sourceList)) {
                    for (ResourcesDto dto : sourceList) {
                        codeSet.add(dto.getCode());
                    }
                }
            } else {// 主账号授权
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("status", RightConstants.RoleStatus.Y.name());
                sourceList = resourcesService.list(params);
                if (CollectionUtils.isNotEmpty(sourceList)) {
                    for (ResourcesDto dto : sourceList) {
                        codeSet.add(dto.getCode());
                    }
                }
            }
        } catch (Exception e) {
            logger.error("授权出现异常", e);
        }
        authorizationMap.put("permissions", codeSet);
        logger.debug("当前登录账户：{}的权限集合：{}", username, codeSet);
        return authorizationMap;
    }
}
