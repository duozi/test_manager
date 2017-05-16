package com.xn.manage.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * Created by zhouxi.zhou on 2016/3/9.
 */
public class StringUtil {
    private final static Logger logger = LoggerFactory.getLogger(StringUtil.class);
    private static String SYSTEM_TYPE = "systemType";


    public static String firstToLow(String s) {

        String str;
        if (s.length() > 2) {
            str = s.substring(0, 1).toLowerCase() + s.substring(1);
        } else {
            str = s;
        }
        return str;
    }

    public static String firstToUp(String s) {

        String str;
        if (s.length() > 2) {
            str = s.substring(0, 1).toUpperCase() + s.substring(1);
        } else {
            str = s;
        }
        return str;
    }

    public static String getPro(String file, String properName) {
        Properties prop = new Properties();
        String value = null;
        InputStream in = Object.class.getResourceAsStream("/" + file);
        try {
            prop.load(in);
            value = prop.getProperty(properName).trim();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("properties file is not exist");
        } finally {
            return value;
        }
    }


    public static Boolean isEmpty(Object value) {
        if (value == null) return true;
        if (org.apache.commons.lang.StringUtils.isBlank(value.toString())) return true;
        return false;
    }

    public static boolean isJson(Object value) {
        if (!(value instanceof String)) return false;
        String json = value.toString();
        return json.startsWith("{") && json.endsWith("}");
    }

    public static String getConfig(File file, String properName, String defaultValue) {

        List<String> lines = FileUtil.fileReadeForList(file);
        for (String line : lines) {
            if (!line.startsWith("#")) {
                if (line.contains("=")) {
                    String split[] = line.split("=", 2);
                    if (split[0].equals(properName)) {
                        return split[1];

                    }

                }
            }
        }
        return defaultValue;
    }




    //生成随机手机号
    public static int getNum(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }

    public static String addLoginName() {
        String[] telFirst = "134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");
        int index = getNum(0, telFirst.length - 1);
        String first = telFirst[index];
        String second = String.valueOf(getNum(1, 888) + 10000).substring(1);
        String thrid = String.valueOf(getNum(1, 9100) + 10000).substring(1);
        return first + second + thrid;

    }


//    public static String setSign(Object obj) {
//
//        TreeMap<String, String> map = beanToSortMap(obj);
//
//        String systemType = map.get(SYSTEM_TYPE);
//
//        String key = getPro("interaction.properties", "key." + systemType);
//
//        String addSign = addSign(map, key);
//        if (org.apache.commons.lang.StringUtils.isNotEmpty(addSign)) {
//            return addSign;
//        }
//        return null;
//    }





    public static void addXMLStartString(StringBuffer result, String s, StringBuffer before) {
        result.append(before).append("<").append(s).append(">");
        addBefore(before);
    }

    public static void addParamStartString(StringBuffer result, StringBuffer before) {
        result.append(before).append("{\r\n\t\"param\":\r\n\t[\r\n");
        addBefore(before);
    }

    public static void addParamEndString(StringBuffer result, StringBuffer before) {
        result.append(before).append("]\r\n");
        delBefore(before);
        result.append(before).append("}");
    }

    public static void addJsonStartList(StringBuffer result, StringBuffer before) {

        result.append("\n").append(before).append("[").append("\n");
        addBefore(before);
    }

    public static void delBefore(StringBuffer before) {
        before.deleteCharAt(before.length() - 1);

    }

    public static void addBefore(StringBuffer before) {
        before.append("\t");

    }

    public static void addJsonStart(StringBuffer result, String fieldName, StringBuffer before) {
        result.append(before).append("\"").append(fieldName).append("\"").append(":");

    }

    public static void addHead(StringBuffer result, StringBuffer before) {

        result.append(before).append("{\n");
        addBefore(before);
    }

    public static void addEnd(StringBuffer result, StringBuffer before) {
        delBefore(before);
        result.append(before).append("},\n");

    }

    public static void addJsonEndList(StringBuffer result, StringBuffer before) {
        delBefore(before);
        result.append(before).append("],").append("\n");

    }

    public static void addJsonStringDefaultValue(StringBuffer result, String value) {
        if (value == null) {
            result.append("\"").append("\",\n");
        } else {
            result.append("\"").append(value).append("\",\n");
        }
    }

    public static void addJsonOtherDefaultValue(StringBuffer result, String value) {
        if (value == null) {
            result.append(",\n");
        } else {
            result.append(value).append(",\n");
        }

    }

    public static void addXMLEndString(StringBuffer result, String s, StringBuffer before, boolean falg) {
        delBefore(before);
        if (falg) {
            result.append(before);
        }
        result.append("</").append(s).append(">\n");


    }

    public static void main(String[] args) {
//        System.out.println(addLoginName());
//        System.out.println(md5("appVersion=2.4.0&friendJson=[{\"friendMobile\":\"111111111114\",\"friendName\":\"测试05\"}]&memberNo=8e299dbf-fd2a-4282-966a-d1f946683133&mobile=1232545&sourceType=android&systemType=QGZ&key=J1IGqSYgjv0pPF6TIgXu4G8KAp6rkd3T"));
//        System.out.println(md5("appVersion=2.4.0&loginName=13480979901&loginPwd=123456&sourceType=android&systemType=QGZ&key=J1IGqSYgjv0pPF6TIgXu4G8KAp6rkd3T"));
//        appVersion=2.4.0&day=false&days=3&hour=72&memberNo=b4f3904f-cfe7-4572-8a07-f0e48bb80a74&refereeNo=1256097325667&sourceType=android&systemType=QGZ&key=J1IGqSYgjv0pPF6TIgXu4G8KAp6rkd3T


//        String s = getSign("checkLoginReq.setAppVersion(\"2.4.0\");\n" +
//                "\t\tcheckLoginReq.setMemberNo(\"05135e9b-7fdb-478a-ba40-569bdf8b7331\");\n" +
//                "\t\tcheckLoginReq.setSign(\"%\");\n" +
//                "\t\tcheckLoginReq.setSourceType(\"android\");\n" +
//                "\t\tcheckLoginReq.setSystemType(\"QGZ\");\n" +
//                "\t\tcheckLoginReq.setTokenId(\"ef8bd754-5124-47c1-b763-57b29010ea45\");");
//
//        System.out.println(s);

//        String str = (String) JOptionPane.showInputDialog(null, "参数：\n", "获得sign", JOptionPane.PLAIN_MESSAGE, null, null,
//                null);
//        JOptionPane.showMessageDialog(null, getSign(str), "显示ID",JOptionPane.PLAIN_MESSAGE);
//
//        UpdateRefereeReq updateRefereeReq = new UpdateRefereeReq();
//        updateRefereeReq.setAppVersion("2.4.0");
//        updateRefereeReq.setMemberNo("b4f3904f-cfe7-4572-8a07-f0e48bb80a74");
//        updateRefereeReq.setDay(false);
//        updateRefereeReq.setRefereeNo("1256097325667");
//        updateRefereeReq.setDays(3);
//        updateRefereeReq.setSourceType("android");
//        updateRefereeReq.setSystemType("QGZ");
//        validateSign(updateRefereeReq);
    }

}
