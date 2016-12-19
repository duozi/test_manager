package com.xn.autotest.objectfactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.xn.autotest.bean.request.KeyValueStore;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.List;

public class ArrayFactory extends InstanceFactory {

    @Override
    protected Object create(Type genericType, Object value) {
        if (value == null) return null;
        Class<?> componentType = getComponentType(genericType);
        if (componentType == null) {
            throw new RuntimeException("目前不支持泛型数组!");
        }
        try {
            if (value instanceof String) {
                JSONArray objects = JSON.parseArray(value.toString());
                Object bean = Array.newInstance(componentType, objects.size());
                for (int i = 0; i < objects.size(); ++i) {
                    Array.set(bean, i, BeanUtils.create(componentType, objects.get(i)));
                }
                return bean;
            }
        } catch (Exception e) {
        }
        //Object[] values = getValues(value);
        Object[] values;
        if (componentType == byte.class) {
            values = (Object[]) value;
        } else {
            values = getValues(value);
        }
        Object bean = Array.newInstance(componentType, values.length);
        for (int i = 0; i < values.length; ++i) {
            Object element = values[i];
            if (element instanceof KeyValueStore) {
                element = ((KeyValueStore) element).getValue();
            }
            Array.set(bean, i, BeanUtils.create(componentType, element));
        }
        return bean;
    }

    private Class getComponentType(Type genericType) {
        if (genericType instanceof Class) {
            return ((Class) genericType).getComponentType();
        } else if (genericType instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) genericType).getGenericComponentType();
            if (componentType instanceof Class) return (Class) componentType;
            return null;
        }
        return null;
    }

    private Object[] getValues(Object value) {
        if (value instanceof String) {
            return ((String) value).split(",");
        }
        return ((List) value).toArray();
    }

    @Override
    public boolean support(Type type) {
        if (type instanceof Class) {
            if (((Class) type).isArray()) return true;
        }
        return type instanceof GenericArrayType;
    }
}
