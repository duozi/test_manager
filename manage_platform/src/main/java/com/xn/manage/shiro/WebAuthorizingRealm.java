package com.xn.manage.shiro;


import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;



/*
 * Created by chenhening on 2017/5/10.
 * */
public class WebAuthorizingRealm extends AuthorizingRealm {


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        // 获取前台输入的密码
        String username = token.getUsername();
        String password = String.valueOf(token.getPassword());
        if ("123456".equals(password)){
            return new SimpleAuthenticationInfo(username, "123456", getName());
        }
        // 认证没有通过
        throw new UnknownAccountException();// 没帐号
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        return null;
    }
}
