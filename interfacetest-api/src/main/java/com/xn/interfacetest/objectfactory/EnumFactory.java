package com.xn.interfacetest.objectfactory;


import java.lang.reflect.Type;

import com.xn.common.utils.StringUtil;


public class EnumFactory extends InstanceFactory {
    @Override
    protected Object create(Type type, Object value) {
        if (StringUtil.isEmpty(value)) return null;

        return Enum.valueOf((Class) type, value.toString());
    }

    @Override
    public boolean support(Type type) {
        if (type instanceof Class) {
            return ((Class) type).isEnum();
        }
        return false;
    }
}
