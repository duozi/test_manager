package com.xn.manage.httputils;

import com.xiaoniu.dataplatform.utils.AES;
import com.xiaoniu.dataplatform.utils.PropertyUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Created by tanhui on 2016/9/26.
 */
public class ParamsUtils {


    //配置敏感参数AES加密
    private static final String AESKey = "s1RvWpxtesoJvPq5WMhuYeW0F9cQYYfq";

    private static final String HUAZHENG_APPID;
    private static final String HUAZHENG_SECRET;
    private static final String VERSION = "0.2";


    static {
        HUAZHENG_APPID = AES.decryptFromBase64(PropertyUtil.getProperty("huazheng.appId"), AESKey);
        HUAZHENG_SECRET = AES.decryptFromBase64(PropertyUtil.getProperty("huazheng.secret"), AESKey);
    }


    public static TreeMap<String, String> createReqParams(TreeMap<String, String> paramsMap){
        //计算签名
        Set<Map.Entry<String, String>> entrySet = paramsMap.entrySet();
        List<String> list = new LinkedList<String>();
        for(Map.Entry<String, String> entry:entrySet){
            list.add(StringUtils.join(Arrays.asList("&",entry.getKey(),"=",entry.getValue()),""));
        }
        String params = StringUtils.join(list,"");
        params = params.substring(1);
        String sign = DigestUtils.md5Hex(params);
        System.out.println("签名sign:" + params);

        //设置签名
        paramsMap.put("sign", sign);
        paramsMap.remove("secret"); // 不需要传递秘钥

        return paramsMap;
    }


    public static TreeMap<String, String> initCommonReqParam(String productNo){

        TreeMap<String, String> paramsMap = new TreeMap<String, String>();
        //设置请求参数
        paramsMap.put("appId", HUAZHENG_APPID); // AppId
        paramsMap.put("secret", HUAZHENG_SECRET); // 秘钥
        paramsMap.put("version", VERSION);
        paramsMap.put("productNo", productNo);//产品编号

        return paramsMap;
    }



}
