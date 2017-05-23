package com.xn.manage.objectfactory;

import static org.apache.commons.lang.StringUtils.trim;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xn.manage.utils.ReflectionUtils;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

public class ObjectFactory extends InstanceFactory {

    @Override
    protected Object create(Type type, Object value) {
        if (value == null) return value;
        if (!(type instanceof Class)) {
            throw new RuntimeException("现在还不支持自定义泛型类型");
        }
//        if (value instanceof String && ReflectionUtils.hasDefaultConstructor((Class) type)) {
//            return jsonObject((Class) type, trim(value.toString()));
//        }
        if (value instanceof String) {
            value = jsonMap(value);
        }
        if (!(value instanceof Map))
            return value;
        return createObjectFromMap((Class) type, (Map) value);
    }

    private Object createObjectFromMap(Class type, Map value) {
        Map properties = new HashMap(value.size());
        properties.putAll(value);
        type = determinedActualType(type, properties);
        Object bean = newInstance(type, properties);
        List<Field> fields = ReflectionUtils.getAllFields(type);
        for (Field field : fields) {
            if (properties.containsKey(field.getName())) {
                Object fieldValue = properties.get(field.getName());
                ReflectionUtils.setFieldValue(bean, field,
                        BeanUtils.create(field.getGenericType(), fieldValue));
            }
        }
        return bean;
    }

    private JSONObject jsonMap(Object value) {
        return JSON.parseObject(trim(value.toString()));
    }

    private Object jsonObject(Class type, String json) {
        if (isJson(json)) {
            return JSON.parseObject(json, type);
        }
        return json;
    }

    private boolean isJson(String json) {
        return json.startsWith("{") && json.endsWith("}");
    }

    @Override
    public boolean support(Type type) {
        return false;
    }

    //TODO Maybe there is a bug.
    private Object newInstance(Class type, Map properties) {
        try {
            Constructor parameterless = ReflectionUtils.getParameterless(type);
            if (parameterless != null) {
                return ReflectionUtils.newInstance(parameterless, new Object[0]);
            }
            CtClass ctClass = wrappedCtClass(type);
            Constructor[] constructors = type.getDeclaredConstructors();
            CtConstructor[] ctConstructors = ctClass.getDeclaredConstructors();
            for (int i = 0; i < constructors.length; ++i) {
                //跳过私有的无参构造函数
                if (constructors[i].getParameterTypes().length == 0) continue;
                String[] parameterNames = getParameterNames(ctConstructors[i]);
                Constructor constructor = constructors[i];
                Object[] args = getParameters(constructor, parameterNames, properties);
                if (args == null) continue;
                removeProperties(properties, parameterNames);
                return ReflectionUtils.newInstance(constructor, args);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //确定真正的类型，一般情况下，是不需要这个 __type 的，但在以下情况需要指定：
    //1 传入的type是一个接口或基类，但期望得到的是具体类
    //2 传入的是Object，期望得到具体类
    private Class determinedActualType(Class type, Map properties) {
        if (properties.containsKey("__type")) {
            String className = properties.get("__type").toString();
            return ReflectionUtils.loadClass(className);
        }
        return type;
    }

    private void removeProperties(Map properties, String[] parameterNames) {
        for (String parameterName : parameterNames) {
            properties.remove(parameterName);
        }
    }

    private CtClass wrappedCtClass(Class type) {
        try {
            //期望大部分情况下处理的是无参构造函数的对象，所以无需用到ClassPool
            //如果大部分情况都是有参构造函数对象则应该将ClassPool.getDefault()提成field.
            return ClassPool.getDefault().get(type.getName());
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Object[] getParameters(Constructor constructor, String[] parameterNames, Map properties) throws CannotCompileException {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Type[] genericTypes = constructor.getGenericParameterTypes();
        if (parameterTypes.length != parameterNames.length) return null;
        Object[] args = new Object[parameterNames.length];
        for (int i = 0; i < parameterTypes.length; ++i) {
            if (!properties.containsKey(parameterNames[i])) return null;
            args[i] = getParameter(genericTypes[i], properties.get(parameterNames[i]));
        }
        return args;
    }

    private Object getParameter(Type parameterType, Object value) throws CannotCompileException {
        return BeanUtils.create(parameterType, value);
    }

    private String[] getParameterNames(CtConstructor constructor) throws NotFoundException {
        MethodInfo methodInfo = constructor.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attribute = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        String[] paramNames = new String[constructor.getParameterTypes().length];
        int pos = Modifier.isStatic(constructor.getModifiers()) ? 0 : 1;
        for (int i = 0; i < paramNames.length; i++)
            paramNames[i] = attribute.variableName(i + pos);
        return paramNames;
    }
}
