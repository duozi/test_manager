package com.xn.manage.objectfactory;


<<<<<<< HEAD
import com.xn.manage.utils.StringUtil;
=======
import com.xn.common.utils.StringUtil;
>>>>>>> hezhouxiyiyangde

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
