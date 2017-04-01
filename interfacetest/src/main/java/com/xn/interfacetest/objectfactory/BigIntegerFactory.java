package com.xn.interfacetest.objectfactory;


import com.xn.common.utils.StringUtil;

import java.lang.reflect.Type;
import java.math.BigInteger;


public class BigIntegerFactory extends InstanceFactory {
    @Override
    protected Object create(Type type, Object value) {
        if (StringUtil.isEmpty(value)) return null;
        return new BigInteger(value.toString());
    }

    @Override
    public boolean support(Type type) {
        return type.equals(BigInteger.class);
    }
}
