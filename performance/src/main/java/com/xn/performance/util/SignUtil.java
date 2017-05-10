package com.xn.performance.util;/**
 * Created by xn056839 on 2016/11/14.
 */

//import com.xn.common.Exception.CaseErrorEqualException;
//import com.xn.common.autotestService.GetPara;
//import com.xn.common.sign.MessageAddSign;
//import com.xn.common.sign.PayAddSign;
//import com.xn.common.sign.UserAddSign;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class SignUtil {
    private static final Logger logger = LoggerFactory.getLogger(SignUtil.class);
    private static final String CHARSET = "UTF-8";

    public static String md5(String str, String charset) {
        if (StringUtils.isEmpty(charset)) {
            return encrypt(str, CHARSET);
        } else {
            return encrypt(str, charset);
        }

    }

    private static String encrypt(String str, String charset) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (StringUtils.isNotBlank(charset)) {
                md.update(str.getBytes(charset));
            } else {
                md.update(str.getBytes());
            }

            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            str = buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return str;
    }


//    public static List<String> dubboAddSign(List<String> lines) throws CaseErrorEqualException {
//        Collections.sort(lines);
//        String param = "";
//        String key = "";
//        for (String line : lines) {
//            if (!line.startsWith("#") && line.split("=").length == 2) {
//
//                param += line + "&";
//                if (line.startsWith("systemType")) {
//                    String value = line.split("=")[1];
//                    key = getPro("interaction.properties", "key." + value);
//                }
//            }
//        }
//
//
//        if (!key.equals("")) {
//            param += "key=" + key;
//        }
//        if (param.equals("")) {
//            throw new CaseErrorEqualException("all parameters are null");
//        } else if (!param.contains("systemType")) {
//            logger.error("systemType is not exist,cannot add sign");
//            throw new CaseErrorEqualException("systemType is not exist,cannot add sign");
//        }
//
//        String sign = md5(param);
//
//        lines.add("sign=" + sign);
//        return lines;
//
//    }


    public static TreeMap<String, String> beanToSortMap(Object obj) {
        TreeMap<String, String> params = new TreeMap<String, String>();
        try {
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
            for (int i = 0; i < descriptors.length; i++) {
                String name = descriptors[i].getName();
                if (!StringUtils.equals(name, "class")) {
                    Object o = propertyUtilsBean.getNestedProperty(obj, name);
                    if (o != null)
                        params.put(name, o.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }


//    public static String httpAddSign(TreeMap<String, String> map, boolean useSign, String signType,String paramType) throws CaseErrorEqualException {
//
//        String system = new GetPara().getSystem();
//        if (system.equalsIgnoreCase("user")) {
//            UserAddSign userAddSing = new UserAddSign();
//            return userAddSing.UserHttpAddSing(map, useSign, signType);
//        } else if (system.equalsIgnoreCase("message")) {
//            MessageAddSign messageAddSign = new MessageAddSign();
//            return messageAddSign.MessageHttpAddSing(map, useSign, signType);
//        } else if (system.equalsIgnoreCase("unipay")) {
//            PayAddSign payAddSign = new PayAddSign();
//            return payAddSign.PayHttpAddSing(map, useSign, signType,paramType);
//        } else {
//            return null;
//        }
//
//    }

    public static String mapToString(TreeMap<String, String> treeMap) {
        //遍历签名参数
        StringBuilder sign_sb = new StringBuilder();

        Iterator<String> it = treeMap.keySet().iterator();
        while (it.hasNext()) {
            String mapKey = it.next();
            if (StringUtils.isEmpty(treeMap.get(mapKey)))
                continue;
            if (StringUtils.isEmpty(sign_sb.toString())) {
                sign_sb.append(mapKey + "=" + treeMap.get(mapKey));
            } else {
                sign_sb.append("&" + mapKey + "=" + treeMap.get(mapKey));
            }
        }
        return sign_sb.toString();
    }


    public static String encrypt(String decript) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes("UTF-8"));
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

//    public static boolean validateSign(Object obj) {
//
//        TreeMap<String, String> map = RequestSignUtils.beanToSortMap(obj);
//        String sign = map.remove("sign");
//
//
//        String key = getPro("interaction.properties", "key.QGZ");
//
//        String addSign = RequestSignUtils.addSign(map, key);
//        if (StringUtils.isNotEmpty(addSign) && addSign.equals(sign)) {
//            return true;
//        } else {
//            logger.info("isPassSign@, 签名错误，传入签名:{},计算出的签名:{}", sign, addSign);
//            return false;
//        }
//    }

    public static String addSignDubboMd5(TreeMap<String, String> treeMap, String key) {
        //遍历签名参数
        StringBuilder sign_sb = new StringBuilder();

        Iterator<String> it = treeMap.keySet().iterator();
        while (it.hasNext()) {
            String mapKey = it.next();
            if (StringUtils.isEmpty(treeMap.get(mapKey)))
                continue;
            if (StringUtils.isEmpty(sign_sb.toString())) {
                sign_sb.append(mapKey + "=" + treeMap.get(mapKey));
            } else {
                sign_sb.append("&" + mapKey + "=" + treeMap.get(mapKey));
            }
        }
        sign_sb.append("&key=" + key);

        return md5(sign_sb.toString(),null);
    }

    public static void main(String[] args) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("test", "interaction");
        String s = map.remove("jest");
        System.out.println(StringUtils.isEmpty(s));
    }
}
