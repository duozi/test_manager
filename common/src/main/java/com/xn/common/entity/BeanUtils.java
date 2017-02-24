package com.xn.common.entity;/**
 * Created by xn056839 on 2016/12/23.
 */


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.text.SimpleDateFormat;
import java.util.Map;

public class BeanUtils {
    private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);
    /**
     * 获取对象中的所有非空字段，并生成json串返回 忽略为空的字段
     *
     * @author Zhoutao
     * @date 2014-7-10
     * @recently Zhoutao
     * @param obj
     * @return json 字符串 eg:{"jobType":"DM","slCode":3565,"usrCode":35}
     */
    public static String describe(Object obj) {
        return JSON.toJSONString(obj);
    }


    /**
     * 格式化输出对象中的所有字段
     *
     * @param obj
     * @return json串，有缩进
     */
    public static String describePretty(Object obj) {
        return JSON.toJSONString(obj, true);
    }

    /**
     * 将一个 JavaBean 对象转化为一个 Map,字段名作为key，字段值作为value
     *
     * @author Zhoutao
     * @date 2014-7-30
     * @recently Zhoutao
     * @param bean
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(Object bean) {
        if (bean instanceof Map) {
            return (Map<String, Object>) bean;
        }
        ObjectMapper m = new ObjectMapper();
        m.setDateFormat(new SimpleDateFormat());
        Map<String, Object> map = m.convertValue(bean, Map.class);
        return map;
    }

    /**
     * map 转 javabean
     *
     * @author Zhoutao
     * @date 2014-7-30
     * @recently Zhoutao
     * @param <T>
     * @param map
     * @param clazz
     * @return
     */
    public static <T> T toBean(Map<String, Object> map, Class<T> clazz) {
        ObjectMapper m = new ObjectMapper();
        m.setDateFormat(new SimpleDateFormat());
        T t = m.convertValue(map, clazz);
        return t;
    }

    /**
     * 对象转换 ,将source对象中的所有属性复制到clazz类的实例中并返回
     *
     * 这里采用fastjson来实现转换操作，原因是先将对象序列化为串，然后在将串反序列化为另一对象，这一过程中不需要考虑类型不匹配的情况 ，
     * 比如：包装类型的Integer值为null，需要转换到int；又或者强类型转换，两个List泛型类型不一致的情况
     *
     * @param source
     * @param clazz
     * @return
     * @date 2015年7月8日
     * @author Ternence
     */
    public static <T> T toBean(Object source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        try {
            String jsonstr = JSON.toJSONString(source);
            return JSON.parseObject(jsonstr, clazz);
        } catch (RuntimeException e) {
            logger.error("", e);
        }
        return null;
    }

    public static <T> T toBean(Object source, Class<T> clazz, String...ignoreProperties) {
        try {
            T t = clazz.newInstance();
            copyProperties(t, source, ignoreProperties);
            return t;
        } catch (InstantiationException e) {
            logger.error("", e);
        } catch (IllegalAccessException e) {
            logger.error("", e);
        }
        return null;
    }


    /**
     * 复制属性 调用apache的工具类，进行封装，去除了反射异常
     *
     * @see BeanUtilsBean#copyProperties
     * @author Zhoutao
     * @date 2014-8-6
     * @recently Zhoutao
     * @param dest
     * @param orig
     */
    public static void copyProperties(Object dest, Object orig) {
        try {
            org.apache.commons.beanutils.PropertyUtils.copyProperties(dest, orig);

        } catch (Exception e) {
            logger.debug("copyProperties() error:" + dest.getClass() + "'", e);
        }
    }

    /**
     * 复制属性 忽略指定的属性
     *
     * @param dest 目标对象
     * @param orig 源对象
     * @param ignoreProperties 需要忽略copy的属性
     * @date 2015年5月8日
     * @author Ternence
     */
    public static void copyProperties(Object dest, Object orig, String[] ignoreProperties) {
        try {
            org.springframework.beans.BeanUtils.copyProperties(orig, dest, ignoreProperties);

        } catch (Exception e) {
            logger.debug("copyProperties() error:" + dest.getClass() + "'", e);
        }
    }


    /**
     * 复制原始对象中非null字段到目标对象
     *
     *
     * @author Zhoutao
     * @date 2014-8-6
     * @recently Zhoutao
     * @param dest
     * @param orig
     */
    public static void copyNotNullProperties(Object dest, Object orig) {
        try {
            copyProperties(dest, orig, false, false);
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug("copyProperties() error:" + dest.getClass() + "'", e);
            }
        }
    }

    /**
     * 复制原始对象中字段到目标对象中的空字段 目标对象中的字段如果已经有值,不进行覆盖
     *
     * @author Zhoutao
     * @date 2014-8-6
     * @param dest
     * @param orig
     */
    public static void copyToNullProperties(Object dest, Object orig) {
        try {
            copyProperties(dest, orig, true, false);
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug("copyProperties() error:" + dest.getClass() + "'", e);
            }
        }
    }

    /**
     *
     * 复制原始对象中非null字段到目标对象中的null字段 NN:not null property (非null字段)
     *
     * @author Zhoutao
     * @date 2014-8-6
     * @recently Zhoutao
     * @param dest
     * @param orig
     */
    public static void copyNNToNullProperties(Object dest, Object orig) {
        try {
            copyProperties(dest, orig, false, false);
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug("copyProperties() error:" + dest.getClass() + "'", e);
            }
        }
    }

    /**
     *
     * 复制原始对象中非null字段到目标对象中的非null字段 NN:not null property (非null字段)
     *
     * @author Zhoutao
     * @date 2014-8-6
     * @recently Zhoutao
     * @param dest
     * @param orig
     */
    public static void copyNNToNNProperties(Object dest, Object orig) {
        try {
            copyProperties(dest, orig, false, true);
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug("copyProperties() error:" + dest.getClass() + "'", e);
            }
        }
    }


    /**
     * 复制标准的javabean对象
     *
     * @author Zhoutao
     * @date 2014-8-6
     * @recently Zhoutao
     * @param dest
     * @param orig
     * @param copyNull 是否复制null字段到目标对象
     * @param copyToNotNull 如果目标对象字段值不为null，是否允许复制到目标字段.true:不允许，false:允许
     * @throws Exception
     */
    public static void copyProperties(Object dest, Object orig, boolean copyNull, boolean copyToNotNull) throws Exception {

        if (dest == null) {
            throw new IllegalArgumentException("No destination bean specified");
        }
        if (orig == null) {
            throw new IllegalArgumentException("No origin bean specified");
        }
        PropertyDescriptor[] origDescriptors = PropertyUtils.getPropertyDescriptors(orig);
        for (int i = 0; i < origDescriptors.length; i++) {
            String name = origDescriptors[i].getName();
            if (PropertyUtils.isReadable(orig, name) && PropertyUtils.isWriteable(dest, name)) {
                try {
                    Object value = PropertyUtils.getSimpleProperty(orig, name);
                    if (value == null && !copyNull) {
                        continue;
                    }
                    if (copyToNotNull) {
                        Object destFieldValue = PropertyUtils.getSimpleProperty(dest, name);
                        if (destFieldValue == null) {
                            continue;
                        }
                    }

                    PropertyUtils.setSimpleProperty(dest, name, value);
                } catch (NoSuchMethodException e) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Error writing to '" + name + "' on class '" + dest.getClass() + "'", e);
                    }
                }
            }
        }

    }


    /**
     *
     * 初始化对象中的包装字段 Boolean类型的默认false，String类型的默认为"",Number类型的默认为0
     *
     * @param bean
     * @date 2015年1月21日
     * @author Ternence
     */
    public static void initProperties(Object bean) {
        PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(bean);
        if (ArrayUtils.isEmpty(propertyDescriptors)) {
            return;
        }

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String name = propertyDescriptor.getName();
            try {
                Object value = PropertyUtils.getSimpleProperty(bean, name);
                if (value == null) {
                    if (propertyDescriptor.getPropertyType().isAssignableFrom(Boolean.class)) {
                        PropertyUtils.setSimpleProperty(bean, name, false);
                    }

                    if (propertyDescriptor.getPropertyType().isAssignableFrom(String.class)) {
                        PropertyUtils.setSimpleProperty(bean, name, "");
                    }

                    if (propertyDescriptor.getPropertyType().isAssignableFrom(Number.class)) {
                        PropertyUtils.setSimpleProperty(bean, name, "");
                    }

                }
            } catch (Exception e) {
                logger.warn("", e);
            }
        }
    }
}
