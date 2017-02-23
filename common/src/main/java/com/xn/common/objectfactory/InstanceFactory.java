package com.xn.common.objectfactory;

import java.lang.reflect.Type;

public abstract class InstanceFactory {
    protected abstract Object create(Type type, Object value);

    public abstract boolean support(Type type);
}
