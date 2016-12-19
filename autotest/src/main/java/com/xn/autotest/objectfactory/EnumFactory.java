package com.xn.autotest.objectfactory;


import com.xn.autotest.utils.StringUtil;

import java.lang.reflect.Type;


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
