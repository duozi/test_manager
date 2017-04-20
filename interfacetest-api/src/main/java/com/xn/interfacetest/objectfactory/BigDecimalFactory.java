package com.xn.interfacetest.objectfactory;

import java.lang.reflect.Type;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.xn.common.utils.StringUtil;


public class BigDecimalFactory extends InstanceFactory {
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
    protected Object create(Type type, Object value) {
        if (StringUtil.isEmpty(value)) return null;

        return new BigDecimal(value.toString());
    }

    @Override
    public boolean support(Type type) {
        return type.equals(BigDecimal.class);
    }
}
