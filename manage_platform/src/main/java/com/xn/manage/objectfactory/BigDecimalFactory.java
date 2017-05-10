package com.xn.manage.objectfactory;

<<<<<<< HEAD
import com.alibaba.dubbo.common.utils.StringUtils;
import com.xn.manage.utils.StringUtil;

import java.lang.reflect.Type;
import java.math.BigDecimal;


public class BigDecimalFactory extends InstanceFactory {
    @Override
    protected Object create(Type type, Object value) {
        if (StringUtil.isEmpty(value)) return null;

        return new BigDecimal(value.toString());
    }

=======
import java.lang.reflect.Type;
import java.math.BigDecimal;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.xn.common.utils.StringUtil;


public class BigDecimalFactory extends InstanceFactory {
>>>>>>> hezhouxiyiyangde
    public static Boolean isEmpty(Object value) {
        if (value == null) return true;
        if (StringUtils.isBlank(value.toString())) return true;
        return false;
    }

    public static boolean isJson(Object value) {
        if (!(value instanceof String)) return false;
        String json = value.toString();
        return json.startsWith("{") && json.endsWith("}");
    }

<<<<<<< HEAD
=======
    @Override
    protected Object create(Type type, Object value) {
        if (StringUtil.isEmpty(value)) return null;

        return new BigDecimal(value.toString());
    }
>>>>>>> hezhouxiyiyangde

    @Override
    public boolean support(Type type) {
        return type.equals(BigDecimal.class);
    }
}
