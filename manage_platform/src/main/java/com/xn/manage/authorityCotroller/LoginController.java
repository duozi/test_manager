package com.xn.manage.authorityCotroller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenhening on 2017/5/15.
 */

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    /**
     * 登陆页的映射
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login")
    public String login(Model model) throws Exception {
        return "/login";
    }

    @RequestMapping(value = "/403")
    public String noAuth(Model model) throws Exception {
        return "/common/403";
    }
    @RequestMapping(value = "/index")
    public String index(Model model)   {
        return "/common/index";
    }

    @RequestMapping(value = "/sure_login")
    @ResponseBody
    public Object login(HttpServletRequest request) {
        //String resultPageURL = InternalResourceViewResolver.FORWARD_URL_PREFIX + "/403";
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
//        String rememberme = request.getParameter("rememberme");

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//        if (StringUtils.isNotBlank(rememberme)) {
//            token.setRememberMe(true);
//        } else {
//            token.setRememberMe(false);
//        }
        logger.debug("为了验证登录用户而封装的token为" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));
        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", -1);
        try {
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            logger.debug("对用户[" + username + "]进行登录验证..验证开始");
            currentUser.login(token);
            logger.debug("对用户[" + username + "]进行登录验证..验证通过");
            map.put("status", 0);
            map.put("msg", "登陆成功");
            return map;
        } catch (UnknownAccountException uae) {
            logger.debug("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
            map.put("msg", "账号不存在!");
        } catch (IncorrectCredentialsException ice) {
            logger.debug("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
            token.clear();
            map.put("msg", "用户名或密码错误");
        } catch (LockedAccountException lae) {
            logger.debug("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
            map.put("msg", "账号被锁定!");
        } catch (ExcessiveAttemptsException eae) {
            logger.debug("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
            token.clear();
            map.put("msg", "输入用户名密码或次数过多,账户已被锁定,半小时后解锁");
        } catch (AuthenticationException ae) {
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            logger.debug("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
            logger.debug("",ae);
            map.put("msg", "未知错误,请联系管理员.");
        }
        //验证是否登录成功
        if (currentUser.isAuthenticated()) {
            logger.debug("用户[" + username + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
        } else {
            token.clear();
        }
        return map;
    }

    /**
     * 退出
     *
     * @param request
     */
    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            subject.logout();
        }
        return "/login";
    }
}