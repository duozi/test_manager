package com.xn.interfacetest.util;

import com.xn.interfacetest.Enum.OperationTypeEnum;
import com.xn.interfacetest.dto.RedisAssertDto;
import com.xn.interfacetest.dto.RelationCaseRedisDto;
import com.xn.interfacetest.dto.TestRedisConfigDto;
import com.xn.interfacetest.entity.RedisAssert;
import com.xn.interfacetest.entity.RelationCaseRedis;
import com.xn.interfacetest.entity.TestRedisConfig;
import com.xn.interfacetest.service.GetPara;
import com.xn.interfacetest.service.RedisAssertService;
import com.xn.interfacetest.service.RelationCaseRedisService;
import com.xn.interfacetest.service.TestRedisConfigService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


@Service("redisUtil")
public class RedisUtil {
    public static final int LOGIN_EXPIRE = 30 * 24 * 60 * 1000;// 登录默认失效三十天
    //    public ApplicationContext context = new ClassPathXmlApplicationContext(
//            "spring.xml");
//    public JedisCluster jedisCluster = (JedisCluster) context.getBean("jedisCluster");
//    @Resource
//    private IUserRedisService userService;
    public static final int PWD_EXPIRE = 24 * 60 * 1000;// 密码默认失效1天
    public static final String BID = "UNIUSER";//缓存的业务id
    public static final String PREFIX_LOGIN = "login";
    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);
    static  ApplicationContext ctx=new ClassPathXmlApplicationContext("/spring/spring-context.xml");
    static RelationCaseRedisService relationCaseRedisService=(RelationCaseRedisService) ctx.getBean("relationCaseRedisService");
    static RedisAssertService redisAssertService=(RedisAssertService) ctx.getBean("redisAssertService");
    static TestRedisConfigService testRedisConfigService=(TestRedisConfigService) ctx.getBean("testRedisConfigService");
    private static JedisCluster jedisCluster;
    /**
     * 登录token保存时间
     */
    private static int loginExpire;
    /**
     * 登录密码失败次数保存时间
     */
    private static int loginPwdErrorExpire;
    /**
     * 支付密码失败次数保存时间
     */
    private static int payPwdErrorExpire;
//
//    static {
//        String obj = StringUtil.getPro("redis.login.expire", "2");
//        loginExpire = null == obj ? LOGIN_EXPIRE : Integer.parseInt(obj);
//        obj = StringUtil.getPro("redis.loginpwderror.expire", "2");
//        loginPwdErrorExpire = null == obj ? PWD_EXPIRE : Integer.parseInt(obj);
//        obj = StringUtil.getPro("redis.paypwderror.expire", "2");
//        payPwdErrorExpire = null == obj ? PWD_EXPIRE : Integer.parseInt(obj);
//    }
//
    public RedisUtil() {
////        GetPara getPara=new GetPara();
////        File file = new File(getPara.getPath()+ "suite/redis.properties");
////
////        String host1 = StringUtil.getConfig(file, "redis.slaver.host1", "");
////        String host2 = StringUtil.getConfig(file, "redis.slaver.host2", "");
////        String host3 = StringUtil.getConfig(file, "redis.slaver.host3", "");
////        String host4 = StringUtil.getConfig(file, "redis.slaver.host4", "");
////
////        int port1 = Integer.parseInt(StringUtil.getConfig(file, "redis.slaver.port1", "0"));
////        int port2 = Integer.parseInt(StringUtil.getConfig(file, "redis.slaver.port2", "0"));
////        int port3 = Integer.parseInt(StringUtil.getConfig(file, "redis.slaver.port3", "0"));
////        int port4 = Integer.parseInt(StringUtil.getConfig(file, "redis.slaver.port4", "0"));
////        if (!host1.equals("") && !host2.equals("") && !host3.equals("") && port1 != 0 && port2 != 0 && port3 != 0) {
////            HostAndPort hostAndPort1 = new HostAndPort(host1, port1);
////            HostAndPort hostAndPort2 = new HostAndPort(host2, port2);
////            HostAndPort hostAndPort3 = new HostAndPort(host3, port3);
////            HostAndPort hostAndPort4 = new HostAndPort(host4, port4);
////            HashSet<HostAndPort> nodes = new HashSet();
////            nodes.add(hostAndPort1);
////            nodes.add(hostAndPort2);
////            nodes.add(hostAndPort3);
////            if(!host4.equals("") && port4 != 0){
////            nodes.add(hostAndPort4);}
////
////            this.jedisCluster = new JedisCluster(nodes, 3000, 30);
////        }
    }


    //初始化redis
    public RedisUtil(Long caseId,Long environmentId) {
        //redis配置名称的集合，稍后通过名称遍历查询ip
        HashSet<String> redisNames=new HashSet<String>();
        //查询redis操作---redis断言
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("caseId",caseId);
        List<RedisAssertDto> redisAssertDtoList = redisAssertService.list(params);
        if(null != redisAssertDtoList && redisAssertDtoList.size() > 0){
            for(RedisAssertDto redisAssertDto:redisAssertDtoList){
                if(!redisNames.contains(redisAssertDto.getRedisName())){
                    redisNames.add(redisAssertDto.getRedisName());
                }
            }
        }

        //查询redis操作---redis数据准备
        List<RelationCaseRedisDto> prepareCaseRedisList = relationCaseRedisService.getByCaseIdAndOperateType(caseId, OperationTypeEnum.PREPARE.getId());
        if(null != prepareCaseRedisList && prepareCaseRedisList.size() > 0){
            for(RelationCaseRedisDto redisAssertDto:prepareCaseRedisList){
                if(!redisNames.contains(redisAssertDto.getRedisName())){
                    redisNames.add(redisAssertDto.getRedisName());
                }
            }
        }

        //查询redis操作---redis数据清除
        List<RelationCaseRedisDto> clearCaseRedisList = relationCaseRedisService.getByCaseIdAndOperateType(caseId, OperationTypeEnum.CLEAR.getId());
        if(null != clearCaseRedisList && clearCaseRedisList.size() > 0){
            for(RelationCaseRedisDto redisAssertDto:clearCaseRedisList){
                if(!redisNames.contains(redisAssertDto.getRedisName())){
                    redisNames.add(redisAssertDto.getRedisName());
                }
            }
        }

        HashSet<HostAndPort> nodes = new HashSet();
        if(null != redisNames && redisNames.size() > 0){
            for(String redisName : redisNames){
                TestRedisConfigDto redisConfigDto = testRedisConfigService.getByRedisNameAndEnvironmentId(redisName,environmentId);
                if(null != redisConfigDto) {
                    String ips = redisConfigDto.getIpAddress();
                    if(StringUtils.isNotBlank(ips)){
                        String[] ipAndPorts = ips.split(";|；");
                        for(String ipAndPort:ipAndPorts){
                            String ip = ipAndPort.split(":|：")[0];
                            int port = Integer.parseInt(ipAndPort.split(":|：")[1]);
                            HostAndPort hostAndPort = new HostAndPort(ip, port);
                            nodes.add(hostAndPort);
                        }
                    }

                }
            }
        }
        this.jedisCluster = new JedisCluster(nodes, 3000, 30);
    }

//    /**
//     * token保存到redis中
//     *
//     * @param memberNo 用户id（用户数据分离后加上了systemType）
//     * @param tokenId  token
//     * @param source   来源（ios和安卓统一为app）
//     * @throws Exception
//     */
//    public void setTokenId(String memberNo, String tokenId, String source) throws Exception {
//        Param p = new Param();
//        p.setBid(BID);
//        p.setUid(PREFIX_LOGIN + "-" + memberNo);
//        p.setTokenId(tokenId);
//        p.setDate(new Date());
//        p.setSource(source);
//        userService.set(p);
//    }

//    /**
//     * 删除token
//     *
//     * @param memberNo 用户id（用户数据分离后加上了systemType）
//     * @param tokenId  token
//     * @param source   来源（ios和安卓统一为app）
//     * @throws Exception
//     */
//    public void delTokenId(String memberNo, String tokenId, String source) throws Exception {
//        Param p = new Param();
//        p.setBid(BID);
//        p.setUid(PREFIX_LOGIN + "-" + memberNo);
//        p.setTokenId(tokenId);
//        p.setSource(source);
//        userService.del(p);
//    }

//    public void logout(String systemType, String sourceType, String tokenId, String memberNo) throws Exception {
//        String tmpSource = getSourceType(systemType, sourceType);
//        String tmpSystemType = convertSystemType(systemType);
//        delTokenId(tmpSystemType + "-" + memberNo, tokenId, tmpSource);
//    }

//    /**
//     * 获取token
//     *
//     * @param memberNo 用户id（用户数据分离后加上了systemType）
//     * @param tokenId  token
//     * @return
//     * @throws Exception
//     */
//    public boolean getTokenId(String memberNo, String tokenId) throws Exception {
//        Param p = new Param();
//        p.setBid(BID);
//        p.setUid(PREFIX_LOGIN + "-" + memberNo);
//        p.setTokenId(tokenId);
//        p.setDate(new Date());
//        return userService.get(p, loginExpire);
//    }

//    /**
//     * 延长token过期时间
//     *
//     * @param memberNo 用户id（用户数据分离后加上了systemType）
//     * @param tokenId  token
//     * @param source   来源（ios和安卓统一为app）
//     * @throws Exception
//     */
//    public void delayToken(String memberNo, String tokenId, String source) throws Exception {
//        Param p = new Param();
//        p.setBid(BID);
//        p.setUid(PREFIX_LOGIN + "-" + memberNo);
//        p.setTokenId(tokenId);
//        p.setDate(new Date());
//        p.setSource(source);
//        userService.delayToken(p);
//    }

//    /**
//     * 获取登录失败次数
//     *
//     * @param key 登录手机号（用户数据分离后加上了systemType）
//     * @return
//     * @throws Exception
//     */
//    public int getErrorTime(String key) throws Exception {
//        int count = 0;
//        String value = commonService.get(BID, key);
//        if (!StringUtil.isEmpty(value)) {
//            count = Integer.parseInt(value);
//        }
//        return count;
//    }

//    /**
//     * 保存登录失败次数
//     *
//     * @param key   登录手机号（用户数据分离后加上了systemType）
//     * @param count 次数
//     * @param flag
//     * @throws Exception
//     */
//    public void putErrorTime(String key, int count, String flag) throws Exception {
//        if (flag.equals("login")) {
//
//            commonService.set(BID, key, String.valueOf(count), loginPwdErrorExpire);
//        }
//        if (flag.equals("pay")) {
//            commonService.set(BID, key, String.valueOf(count), payPwdErrorExpire);
//        }
//    }

//    /**
//     * 删除登录失败次数
//     *
//     * @param key 登录手机号（用户数据分离后加上了systemType）
//     * @throws Exception
//     */
//    public void deleteErrorTime(String key) throws Exception {
//        commonService.del(BID, key);
//    }

//    /**
//     * 登录令牌存入redis中并返回给前置系统
//     */
//    public void saveToken2Redis(String systemType, String sourceType, String loginName, String tokenId, String memberNo) {
//        try {
//            String tmpSystemType = convertSystemType(systemType);
//            String tmpSource = getSourceType(systemType, sourceType);
//            delTokenId(tmpSystemType + "-" + memberNo, tokenId, tmpSource);
//            // redis中存入token信息
//            setTokenId(tmpSystemType + "-" + memberNo, tokenId, tmpSource);
//            // 删除错误次数记录
//            deleteErrorTime(PREFIX_LOGINPWD + "-" + systemType + "-" + loginName);
//        } catch (Exception e) {
//            logger.error(String.format("~~~[%s]-[%s]用户token存入redis中报错：[%s]~~~~", memberNo, tokenId, e));
//        }
//    }

//    /**
//     * 处理特殊的系统类型（业务编码)
//     */
//    private String convertSystemType(String systemType) {
//        if (SystemType.CHANNEL2.getText().equals(systemType)) {
//            return SystemType.CHANNEL.getText();
//        }
//        return systemType;
//    }

//    /**
//     * 获取用户的登录来源
//     */
//    private String getSourceType(String systemType, String source) {
//        StringBuffer sb = new StringBuffer();
//
//        if (!SystemType.QGZ.getText().equals(systemType)) {
//            sb.append(systemType).append("-");
//        }
//        if (SourceType.ANDROID.getText().equalsIgnoreCase(source) || SourceType.IOS.getText().equalsIgnoreCase(source)) {
//            // 如果来源类型是安卓或ios，则统一认为是app（客户端），这样做是要实现踢人的功能
//            sb.append("app");
//        } else {
//            sb.append(source);
//        }
//        return sb.toString();
//    }

    public static void set(String key, String value, int time) {

        jedisCluster.set(key, value);
        if (time != 0) {
            jedisCluster.expire(key, time);
            logger.info("set to redis key:{} value:{},limit time is " + time + " seconds", key, value);
        } else {

            logger.info("set to redis key:{} value:{},limit time is forever", key, value);
            return;
        }
    }

    public static void del(String key) {
        jedisCluster.del(key);
        logger.info("del from redis key:{}", key);
    }

    public static String get(String key) {
        String value = jedisCluster.get(key);
        logger.info("get value from redis,key:{},value:{}", key, value);
        return value;
    }

    public static void setTime(String key, int expire) {
        jedisCluster.expire(key, expire);
        logger.info("set expire ,key:{},exprie:{}second", key, expire);
    }

    public static boolean isExit(String key) {
        Boolean isExit = jedisCluster.exists(key);
        if (isExit) {
            logger.info("key:{} is exit in redis", key);
        } else {
            logger.info("key:{} is not exit in redis", key);
        }
        return isExit;
    }

    public static Long getTime(String key) {
        Long time = jedisCluster.ttl(key);
        logger.info("key:{} expire time is {}", key, time);
        return time;
    }


    public static void main(String[] args) {
//        RedisUtil redisUtil = new RedisUtil();
//        redisUtil.set("interaction", "{\"data\":\"111\"}", 0);
////        redisUtil.expire("interaction",200);
//        redisUtil.isExit("UNIUSER-login-QGZ-00683f51-da44-4f2d-8120-e009ef3bf351");
//        System.out.println(redisUtil.isExit("UNIUSER-login-QGZ-00683f51-da44-4f2d-8120-e009ef3bf351"));

//        System.out.println(new Date().getTime());
    }
}
