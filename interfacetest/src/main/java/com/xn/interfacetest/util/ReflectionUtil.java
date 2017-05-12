package com.xn.interfacetest.util;


import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import java.lang.reflect.*;

public class ReflectionUtil
{
    private static final Log logger = LogFactory.getLog(ReflectionUtil.class);

    public static void setFieldValue(Object object, String fieldName, Object value)
    {
        Field field = getDeclaredField(object, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
        }
        makeAccessible(field);
        try
        {
            field.set(object, value);
        }
        catch (IllegalAccessException e)
        {
        }
    }

    public static Object getFieldValue(Object object, String fieldName)
    {
        Field field = getDeclaredField(object, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
        }
        makeAccessible(field);

        Object result = null;
        try
        {
            result = field.get(object);
        }
        catch (IllegalAccessException e)
        {
        }

        return result;
    }

    public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes, Object[] parameters)
            throws InvocationTargetException
    {
        Method method = getDeclaredMethod(object, methodName, parameterTypes);
        if (method == null) {
            throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + object + "]");
        }
        method.setAccessible(true);
        try
        {
            return method.invoke(object, parameters);
        }
        catch (IllegalAccessException e)
        {
        }

        return null;
    }

    protected static Field getDeclaredField(Object object, String fieldName)
    {
        for (Class superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass())
        {
            try
            {
                return superClass.getDeclaredField(fieldName);
            }
            catch (NoSuchFieldException e)
            {
            }
        }
        return null;
    }

    protected static void makeAccessible(Field field)
    {
        if ((!Modifier.isPublic(field.getModifiers())) || (!Modifier.isPublic(field.getDeclaringClass().getModifiers())))
        {
            field.setAccessible(true);
        }
    }

    protected static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes)
    {
        for (Class superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass())
        {
            try
            {
                return superClass.getDeclaredMethod(methodName, parameterTypes);
            }
            catch (NoSuchMethodException e)
            {
            }
        }

        return null;
    }

    public static <T> Class<T> getSuperClassGenricType(Class clazz)
    {
        return getSuperClassGenricType(clazz, 0);
    }

    public static Class getSuperClassGenricType(Class clazz, int index)
    {
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType))
        {
            logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
            return Object.class;
        }

        Type[] params = ((ParameterizedType)genType).getActualTypeArguments();

        if ((index >= params.length) || (index < 0))
        {
            logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);
            return Object.class;
        }
        if (!(params[index] instanceof Class))
        {
            logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
            return Object.class;
        }

        return (Class)params[index];
    }

    public static IllegalArgumentException convertToUncheckedException(Exception e)
    {
        if (((e instanceof IllegalAccessException)) || ((e instanceof IllegalArgumentException)) || ((e instanceof NoSuchMethodException))) {
            return new IllegalArgumentException("Refelction Exception.", e);
        }
        return new IllegalArgumentException(e);
    }

    public static Field getFieldByFieldName(Object obj, String fieldName)
    {
        for (Class superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass())
        {
            try
            {
                return superClass.getDeclaredField(fieldName);
            }
            catch (NoSuchFieldException e)
            {
            }
        }
        return null;
    }

    public static Object getValueByFieldName(Object obj, String fieldName)
            throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException
    {
        Field field = getFieldByFieldName(obj, fieldName);
        Object value = null;
        if (field != null)
        {
            if (field.isAccessible())
            {
                value = field.get(obj);
            }
            else
            {
                field.setAccessible(true);
                value = field.get(obj);
                field.setAccessible(false);
            }
        }
        return value;
    }

    public static void setValueByFieldName(Object obj, String fieldName, Object value)
            throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException
    {
        Field field = obj.getClass().getDeclaredField(fieldName);
        if (field.isAccessible())
        {
            field.set(obj, value);
        }
        else
        {
            field.setAccessible(true);
            field.set(obj, value);
            field.setAccessible(false);
        }
    }
}
