package com.xn.interfacetest; /**
 * Created by xn056839 on 2016/12/26.
 */

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

public class Ognl {
    private static final Logger logger = LoggerFactory.getLogger(Ognl.class);
    /**
     * 可以用于判断 Map,Collection,String,Array是否为空
     *
     * @param o
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean isEmpty(Object o) throws IllegalArgumentException {
        if (o == null)
            return true;
        if (o instanceof String) {
            return StringUtils.isEmpty((String) o);
        } else if (o instanceof Collection) {
            return CollectionUtils.isEmpty((Collection) o);
        } else if (o.getClass().isArray()) {
            return ArrayUtils.isEmpty((Object[]) o);
        } else if (o instanceof Map) {
            MapUtils.isEmpty((Map) o);
        } else if (o instanceof Date) {
            return o == null;
        } else if (o instanceof Number) {
            return o == null;
        } else if (o instanceof Boolean) {
            return o == null;
        } else {
            throw new IllegalArgumentException("Illegal argument type,must be : Map,Collection,Array,String. but was:"
                    + o.getClass());
        }

        return false;
    }

    /**
     * 可以用于判断 Map,Collection,String,Array是否不为空
     *
     * @param o
     * @return
     */
    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    public static boolean isNotEmpty(Object... objects) {
        if (objects == null)
            return false;
        for (Object obj : objects) {
            if (isEmpty(obj)) {
                return false;
            }
            ;
        }
        return true;
    }

    public static boolean isNotBlank(Object o) {
        return !isBlank(o);
    }

    public static boolean isNumber(Object o) {
        if (o instanceof Number) {
            return true;
        } else if (o instanceof String) {
            return NumberUtils.isNumber((String) o);
        } else {
            return false;
        }
    }

    public static boolean isBlank(Object o) {
        return StringUtils.isBlank((String) o);
    }



    public static boolean isBlank(String str) {
        return StringUtils.isBlank(str);
    }

}
