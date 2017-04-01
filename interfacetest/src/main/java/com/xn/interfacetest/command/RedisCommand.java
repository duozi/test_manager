package com.xn.interfacetest.command;/**
 * Created by xn056839 on 2016/9/12.
 */


import com.xn.interfacetest.service.GetPara;
import com.xn.interfacetest.util.RedisUtil;
import com.xn.interfacetest.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class RedisCommand implements Command {

    public static final String PREFIX_LOGINPWD = "loginPwd";
    private static final Logger logger = LoggerFactory.getLogger(RedisCommand.class);
    private String methodName;
    private String key;
    private String value;
    private int time;
    private Long caseId;
    private Long environmentId;

    public static void main(String[] args) {
        GetPara getPara = new GetPara();
        getPara.setPath("");
        RedisCommand redisCommand = new RedisCommand();
//        redisCommand.setKey("UNIUSER-login-QGZ-77683f51-da44-4f2d-8120-e009ef3bf351");
//        redisCommand.setValue("[{\"bid\":\"UNIUSER\",\"date\":1474513018036,\"source\":\"app\",\"tokenId\":\"77683f51-da44-4f2d-8120-e009ef3bf351\",\"uid\":\"login-QGZ-77683f51-da44-4f2d-8120-e009ef3bf351\"}]");
//        redisCommand.del(redisCommand.getKey());


        redisCommand.set("UNIUSER-loginPwd-QGZ-33333333335", "2", 0);

    }

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public Long getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(Long environmentId) {
        this.environmentId = environmentId;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        if (value.contains("getTime")) {
            value = value.replace("getTime", String.valueOf(new Date().getTime()));
        }

        this.value = value;
//        System.out.println("--------"+this.value);
    }

    public int getTime() {
        return time;
    }

//    /**
//     * 从redis中获取登录错误次数
//     */
//    private int getErrorTimes() {
//        try {
//            int errorTime = redisUtil.getErrorTime(PREFIX_LOGINPWD + "-" + systemType + "-" + loginName);
//            logger.info("错误次数为---------------" + errorTime);
//            return errorTime;
//        } catch (Exception e1) {
//            logger.error(String.format("~~~[%s]缓存中获取登录密码错误次数异常~~~", loginName), e1);
//        }
//        return 0;
//    }

//    /**
//     * 把用户登录密码错误次数存入redis
//     */
//    private void saveErrorTime() {
//        String key = PREFIX_LOGINPWD + "-" + systemType + "-" + loginName;
//        try {
//            redisUtil.putErrorTime(key, times, "login");
//        } catch (Exception e) {
//            logger.error(String.format("~~~~用户名:[%s],密码错误次数存入redis中报错：[%s]~~~~", loginName, e));
//            e.printStackTrace();
//        }
//
//
//    }

//    private void logoutDelToken() {
//        try {
//            redisUtil.logout(systemType, sourceType, tokenId, memberNo);
//        } catch (Exception e) {
//            logger.error("delete logout error");
//            e.printStackTrace();
//        }
//    }

//    private void loginSaveToken() {
//        redisUtil.saveToken2Redis(systemType, sourceType, loginName, tokenId, memberNo);
//    }

    public void setTime(int time) {
        this.time = time;
    }

    private void set(String key, String value, int time) {
        if (StringUtil.isEmpty(key) || StringUtil.isEmpty(value)) {
            return;
        }
        RedisUtil.set(key, value, time);
    }

    private String get(String key) {
        if (StringUtil.isEmpty(key)) {
            return null;
        }
        return RedisUtil.get(key);
    }

    private void del(String key) {
        RedisUtil.del(key);
    }

    @Override
    public void execute(Long caseId, Long interfaceId, Long planId) {

    }

    @Override
    public void execute() {
        if (methodName.equalsIgnoreCase("set")) {
            set(key, value, time);
        } else if (methodName.equalsIgnoreCase("del")) {
            del(key);
        }
    }

    @Override
    public void executeWithException() throws Exception {

    }
}
