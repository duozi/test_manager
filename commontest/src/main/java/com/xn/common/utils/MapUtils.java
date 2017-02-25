package com.xn.common.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;


public class MapUtils {
    public static Map init(Type type) {
        if (type instanceof Class) {
            return initFromClass((Class) type);
        }
        if (type instanceof ParameterizedType) {
            return initFromClass((Class) ((ParameterizedType) type).getRawType());
        }
        return null;
    }

    private static Map initFromClass(Class type) {
        if (type.isInterface()) return new HashMap();
        return (Map) ReflectionUtils.newInstance(type);
    }

}
