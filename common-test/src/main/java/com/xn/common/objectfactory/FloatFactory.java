package com.xn.common.objectfactory;

import java.lang.reflect.Type;

public class FloatFactory extends InstanceFactory {
    @Override
    protected Object create(Type type, Object value) {
        if (value == null) {
            return type.equals(Float.TYPE) ? 0.0F : null;
        }
        return Float.valueOf(value.toString());
    }

    @Override
    public boolean support(Type type) {
        return type.equals(Float.class) || type.equals(Float.TYPE);
    }
}
