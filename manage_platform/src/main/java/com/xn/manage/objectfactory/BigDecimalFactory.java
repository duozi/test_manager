package com.xn.manage.objectfactory;

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


    @Override
    public boolean support(Type type) {
        return type.equals(BigDecimal.class);
    }
}
