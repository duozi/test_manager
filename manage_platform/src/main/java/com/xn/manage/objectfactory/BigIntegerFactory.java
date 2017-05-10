package com.xn.manage.objectfactory;


<<<<<<< HEAD
import com.xn.manage.utils.StringUtil;
=======
import com.xn.common.utils.StringUtil;
>>>>>>> hezhouxiyiyangde

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
