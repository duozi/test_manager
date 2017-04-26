package com.xn.interfacetest.objectfactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.xn.interfacetest.util.MapUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.xn.interfacetest.util.ReflectionUtils;
import com.xn.interfacetest.model.KeyValueStore;


public class MapFactory extends InstanceFactory {
    @Override
    protected Object create(Type type, Object value) {
        if (value == null) return null;
        Map<Object, Object> temp = new LinkedHashMap<Object, Object>();
        if (value instanceof Map) {
            temp.putAll((Map) value);
        } else if (value instanceof List) {
            List<KeyValueStore> items = (List<KeyValueStore>) value;
            for (KeyValueStore item : items) {
                temp.put(item.getName(), item.getValue());
            }
        } else if (isJson(value)) {
            Map<Object, Object> jsonMap= (Map<Object, Object>) JSON.parse(value.toString());
            //JSONObject jsonMap = JSON.parseObject(value.toString());
            temp.putAll(jsonMap);
        }
        return createMap(type, temp);
    }

    private Object createMap(Type type, Map<Object, Object> temp) {
        Map result = MapUtils.init(type);
        Type keyClass = extractKeyClass(type, temp);
        Type valueClass = extractValueClass(type, temp);
        for (Map.Entry<Object, Object> entry : temp.entrySet()) {
            if (isKeyClassElement(entry)) continue;
            if (isValueClassElement(entry)) continue;
            result.put(BeanUtils.create(keyClass, entry.getKey()), BeanUtils.create(valueClass, entry.getValue()));
        }
        return result;
    }

    private boolean isValueClassElement(Map.Entry<Object, Object> entry) {
        return entry.getKey().equals("__valueClass");
    }

    private boolean isKeyClassElement(Map.Entry<Object, Object> entry) {
        return entry.getKey().equals("__keyClass");
    }

    private Type extractKeyClass(Type type, Map<Object, Object> temp) {
        String keyClassName = (String) temp.get("__keyClass");
        if (StringUtils.isNotBlank(keyClassName)) {
            return ReflectionUtils.loadClass(keyClassName);
        }
        if (type instanceof ParameterizedType) {
            return ((ParameterizedType) type).getActualTypeArguments()[0];
        }
        return String.class;
    }

    private Type extractValueClass(Type type, Map<Object, Object> temp) {
        String valueClassName = (String) temp.get("__valueClass");
        if (StringUtils.isNotBlank(valueClassName)) {
            return ReflectionUtils.loadClass(valueClassName);
        }
        if (type instanceof ParameterizedType) {
            return ((ParameterizedType) type).getActualTypeArguments()[1];
        }
        return Object.class;
    }

    private boolean isJson(Object value) {
        if (!(value instanceof String)) return false;
        String json = value.toString();
        return json.startsWith("{") && json.endsWith("}");
    }

    @Override
    public boolean support(Type type) {
        if (type instanceof Class) {
            return Map.class.isAssignableFrom((Class) type);
        }
        if (type instanceof ParameterizedType) {
            Class rawType = (Class) ((ParameterizedType) type).getRawType();
            return Map.class.isAssignableFrom(rawType);
        }
        return false;
    }
}
