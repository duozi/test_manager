package com.xn.common.objectfactory;

import java.lang.reflect.Type;

public class BooleanFactory extends InstanceFactory {
    @Override
    protected Object create(Type type, Object value) {
        if (value == null) {
            return type.equals(Boolean.TYPE) ? false : null;
        }
        return Boolean.valueOf(value.toString());
    }

    @Override
    public boolean support(Type type) {
        return type.equals(Boolean.class) || type.equals(Boolean.TYPE);
    }
}
