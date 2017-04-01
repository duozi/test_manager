package com.xn.interfacetest.objectfactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.xn.common.utils.CollectionUtils;
import com.xn.interfacetest.model.KeyValueStore;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import static org.apache.commons.lang.StringUtils.*;


public class ListFactory extends InstanceFactory {
    @Override
    protected Object create(Type type, Object value) {
        Collection collection = CollectionUtils.init(type);
        if (value == null) return collection;
        Type elementType = determinedElementType(type);
        if (isJson(value)) {
            return parseFromJson(collection, elementType, value);
        }
        if (isList(value)) {
            return extractFromList(collection, elementType, value);
        }
        return collection;
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

    private Object extractFromList(Collection list, Type elementType, Object value) {
        if (value instanceof JSONArray) {
            for (Object o : (JSONArray) value) {
                list.add(BeanUtils.create(elementType, o));
            }
            return list;
        }
        List<KeyValueStore> values = (List<KeyValueStore>) value;
        for (KeyValueStore element : values) {
            list.add(BeanUtils.create(elementType, element.getValue()));
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
