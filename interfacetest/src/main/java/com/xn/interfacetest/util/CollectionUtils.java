package com.xn.interfacetest.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xn.common.utils.BeanUtils;
import com.xn.common.utils.DateUtil;

public class CollectionUtils {
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
    public static Collection init(Type type) {
        if (type instanceof Class) {
            return initFromClass((Class) type);
        }
        if (type instanceof ParameterizedType) {
            return initFromClass((Class) ((ParameterizedType) type).getRawType());
        }
        return null;
    }

    private static Collection initFromClass(Class type) {
        if (type.isInterface()) {
            if (Set.class.isAssignableFrom(type)) return new HashSet();
            if (List.class.isAssignableFrom(type)) return new ArrayList();
            return new ArrayList();
        }
        return (Collection) ReflectionUtils.newInstance(type);
    }


    /**
     * 转换List
     *
     * @param source
     * @param clazz
     * @return
     * @date 2015年4月27日
     * @author Ternence
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> List<T> transform(List<?> source, Class<T> clazz) {
        List<T> list = new ArrayList<T>(source.size());
        try {
            for (Object object : source) {
                T t = BeanUtils.toBean(object, clazz);
                list.add(t);
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return list;
    }


    /**
     *
     * @param source
     * @param clazz
     * @return
     * @date 2015年6月1日
     * @author Ternence
     */
    public static <T> List<T> transform(List<?> source, Class<T> clazz, String[] ignoreProperties) {
        List<T> list = new ArrayList<T>(source.size());
        try {
            for (Object object : source) {
                T t = clazz.newInstance();
                BeanUtils.copyProperties(t, object, ignoreProperties);
                list.add(t);
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return list;
    }

}
