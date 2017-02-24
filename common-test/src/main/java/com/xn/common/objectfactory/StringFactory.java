package com.xn.common.objectfactory;

import java.lang.reflect.Type;

public class StringFactory extends InstanceFactory {
    @Override
    protected Object create(Type type, Object value) {
        return value;
    }

    @Override
    public boolean support(Type type) {
        return type.equals(String.class);
    }
}
