package com.xn.manage.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class CollectionUtils {
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
}
