package com.xn.authority.utils;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;

public abstract class ReflectionUtils {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

    private static final Pattern METHODPATTERN = Pattern.compile("([a-zA-Z_][a-zA-Z_0-9 ]*)\\((.*)\\)");

    private static final Map<String, Method> METHOD_MAP = new HashMap<String, Method>();


    public static List<Field> getAllFields(Class clazz) {
        if (clazz.equals(Object.class)) {
            return Collections.EMPTY_LIST;
        }
        ArrayList<Field> fields = new ArrayList<Field>();
        fields.addAll(asList(clazz.getDeclaredFields()));
        fields.addAll(getAllFields(clazz.getSuperclass()));
        return fields;
    }

//    public static void setFieldValue(Object target, String fieldName, Object value) {
//        Field field = getField(target.getClass(), fieldName);
//        field.setAccessible(true);
//        value = BeanUtils.create(field.getType(), value);
//        setFieldValue(target, field, value);
//    }

    public static void setFieldValue(Object target, Field field, Object value) {
        try {
            Method setter = getSetter(field);
            if (setter != null) {
                invoke(setter, target, value);
            } else {
                field.setAccessible(true);
                field.set(target, value);
            }
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(String.format("Field: %s", field.getName()), e);
        }
    }

    private static Method getSetter(Field field) {
        Class<?> encloseClass = field.getDeclaringClass();
        return getMethod(encloseClass, getSetterName(field), field.getType());
    }

    private static String getSetterName(Field field) {
        String name = field.getName();
        return String.format("set%s", StringUtils.capitalize(name));
    }


    public static <T> T newInstance(Class<? extends T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            logger.error(e.getMessage(), e.getCause());
            throw new RuntimeException(String.format("Class: %s", clazz.getName()), e.getCause());
        }
    }

    public static <T> T newInstance(Constructor<T> ctor, Object[] args) {
        try {
            ctor.setAccessible(true);
            return ctor.newInstance(args);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(String.format("Class: %s", ctor.getName()), e);
        }
    }

    public static Constructor getParameterless(Class clazz) {
        Constructor[] constructors = clazz.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            if (constructor.getParameterTypes().length == 0) {
                constructor.setAccessible(true);
                return constructor;
            }
        }
        return null;
    }

    public static boolean hasDefaultConstructor(Class clazz) {
        Constructor constructor = getParameterless(clazz);
        return constructor != null;
    }

    public static Object getValue(Object target, String property) {
        try {
            Field field = getField(target.getClass(), property);
            field.setAccessible(true);
            return field.get(target);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public static Method getMethod(Class target, String name, Class... classes) {
        try {
            return target.getMethod(name, classes);
        } catch (NoSuchMethodException e) {
            Method[] methods = target.getMethods();
            for (Method method : methods) {
                if (method.getName().equals(name) && match(method.getParameterTypes(), classes)) {
                    return method;
                }
            }
            return null;
        }
    }

    private static boolean match(Class[] parameterTypes, Class[] classes) {
        for (int i = 0; i < classes.length; ++i) {
            if (!match(parameterTypes[i], classes[i])) {
                return false;
            }
        }
        return true;
    }

    private static boolean match(Class superClass, Class subClass) {
        return superClass.isAssignableFrom(subClass);
    }

    public static Object invoke(Method method, Object target, Object... args) {
        try {
            method.setAccessible(true);
            return method.invoke(target, args);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(String.format("Method: %s", method.getName()), e);
        }
    }

    public static Field getField(Class clazz, String propertyName) {
        try {
            return clazz.getDeclaredField(propertyName);
        } catch (NoSuchFieldException e) {
            if (clazz.getSuperclass() != null) {
                return getField(clazz.getSuperclass(), propertyName);
            }
            throw new RuntimeException(String.format("Field: %s", propertyName), e);
        }
    }

    public static Object getStaticFieldValue(Class clazz, String fieldName) {
        Field field = getField(clazz, fieldName);
        try {
            field.setAccessible(true);
            return field.get(null);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public static Boolean hasField(Class clazz, String fieldName) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }

    //    public static Class loadClass(String className) {
//        try {
//
//            return  Class.forName(className);
////            return Class.forName(className);
//        } catch (ClassNotFoundException e) {
//            logger.error(e.getMessage(), e);
//            throw new RuntimeException(e.getMessage(), e);
//        }
//    }
    public static Class loadClass(String className) {
        try {
            return Class.forName(className);

        } catch (ClassNotFoundException e) {

            logger.error("load class error");
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    public static Method getMethod(Object service, String methodName) throws Exception {
        return getMethod(methodName, service.getClass());
    }

    public static Method getMethod(String methodName, Class clazz) throws Exception {
        String key = String.format("%s.%s", clazz.getCanonicalName(), methodName);
        Method method = METHOD_MAP.get(key);
        if (method != null) {
            return method;
        }
        Method[] methods = clazz.getDeclaredMethods();
        for (Method m : methods) {
            if (match(m, methodName)) {
                METHOD_MAP.put(key, m);
                return m;
            }
        }
        throw new Exception(String.format("method<%s> not found in class<%s>", methodName, clazz.getName()));

    }


    private static boolean match(Method m, String method) {
        Matcher matcher = METHODPATTERN.matcher(method);
        if (matcher.find()) {
            String methodName = StringUtils.trim(matcher.group(1));
            if (!m.getName().equals(methodName)) return false;
            String parameter = matcher.group(2);
            String[] parameters = StringUtils.split(parameter, ",");
            if (m.getParameterTypes().length != parameters.length) return false;
            return parametersMatch(m.getParameterTypes(), parameters);
        }
        return m.getName().equals(method);
    }

    private static boolean parametersMatch(Class[] parameterTypes, String[] parameters) {
        for (int i = 0; i < parameterTypes.length; ++i) {
            if (!isSame(parameterTypes[i], parameters[i])) return false;
        }
        return true;
    }

    private static boolean isSame(Class parameterType, String parameter) {
        String actualName = parameterType.getCanonicalName();
        parameter = StringUtils.trim(parameter);
        if (actualName.equalsIgnoreCase(parameter)) return true;
        return actualName.endsWith(parameter);
    }

    public static boolean isConstant(Field field) {
        return Modifier.isFinal(field.getModifiers());
    }
}
