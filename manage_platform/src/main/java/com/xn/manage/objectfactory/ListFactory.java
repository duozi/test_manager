package com.xn.manage.objectfactory;

import static org.apache.commons.lang.StringUtils.endsWith;
import static org.apache.commons.lang.StringUtils.startsWith;
import static org.apache.commons.lang.StringUtils.trim;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.xn.interfacetest.objectfactory.BeanUtils;


public class ListFactory extends InstanceFactory {
    @Override
    protected Object create(Type type, Object value) {
        return null;
    }

    private Type determinedElementType(Type genericType) {
        Type elementType = Object.class;
        if (genericType instanceof ParameterizedType) {
            elementType = ((ParameterizedType) genericType).getActualTypeArguments()[0];
        }
        return elementType;
    }

    private Object parseFromJson(Collection list, Type elementType, Object value) {
        JSONArray array = (JSONArray) jsonList(trim(value.toString()));
        if (array == null) return null;
        for (Object o : array) {
            list.add(BeanUtils.create(elementType, o));
        }
        return list;
    }



    private boolean isList(Object value) {
        return Collection.class.isAssignableFrom(value.getClass());
    }

    private boolean isJson(Object value) {
        return value instanceof String;
    }

    private Object jsonList(String input) {
        if (startsWith(input, "[") && endsWith(input, "]")) {
            return JSON.parse(input);
        }
        return null;
    }

    @Override
    public boolean support(Type type) {
        if (type instanceof Class) {
            return Collection.class.isAssignableFrom((Class) type);
        }
        if (type instanceof ParameterizedType) {
            Class rawType = (Class) ((ParameterizedType) type).getRawType();
            return Collection.class.isAssignableFrom(rawType);
        }
        return false;
    }
}
