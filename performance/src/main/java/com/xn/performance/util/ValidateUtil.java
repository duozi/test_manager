package com.xn.performance.util;/**
 * Created by xn056839 on 2017/2/22.
 */

import com.xn.performance.annotation.NotNullField;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class ValidateUtil {
    private static final Logger logger = LoggerFactory.getLogger(ValidateUtil.class);


    public static boolean validate(Object obj) {
        if(obj == null) return false;

        for(Class<?> clazz = obj.getClass() ; clazz != Object.class ; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            if(fields == null || fields.length == 0) return false;
            for (Field field : fields) {

                if(field.getAnnotation(NotNullField.class) != null) {
                    String name = field.getName();
                    try {
                        Object o = PropertyUtils.getNestedProperty(obj, name);
                        if(o == null) {
                            logger.info("====PropertyUtils.getNestedProperty====name" + name);
                            return false;
                        }
                        if(o instanceof String) {
                            if(StringUtils.isEmpty((String)o)) {
                                logger.info("====the field:" + name + " is isEmpty!");
                                return false;
                            }
                        }
                    } catch (Exception e) {
                        logger.error("====the field:" + name + " is Exception e:" + e);
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
