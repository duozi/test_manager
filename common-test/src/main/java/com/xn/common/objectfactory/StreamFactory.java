package com.xn.common.objectfactory;

import java.io.InputStream;
import java.lang.reflect.Type;


public class StreamFactory extends InstanceFactory {
    @Override
    protected Object create(Type type, Object value) {
        return value;
    }

    @Override
    public boolean support(Type type) {
        return type.equals(InputStream.class);
    }
}
