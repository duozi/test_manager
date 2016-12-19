package com.xn.autotest.objectfactory;


import com.xn.autotest.bean.request.KeyValueStore;
import com.xn.autotest.utils.ReflectionUtils;
import com.xn.autotest.utils.SignUtil;
import com.xn.autotest.utils.StringUtil;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static com.xn.autotest.utils.SignUtil.beanToSortMap;


public class BeanUtils {
    private final static List<InstanceFactory> REGISTEDFACTORIES = new ArrayList<InstanceFactory>();

    private final static ObjectFactory DEFAULTFACTORY = new ObjectFactory();

    static {
        REGISTEDFACTORIES.add(new IntFactory());
        REGISTEDFACTORIES.add(new LongFactory());
        REGISTEDFACTORIES.add(new DoubleFactory());
        REGISTEDFACTORIES.add(new CharFactory());
        REGISTEDFACTORIES.add(new StringFactory());
        REGISTEDFACTORIES.add(new FloatFactory());
        REGISTEDFACTORIES.add(new ArrayFactory());
        REGISTEDFACTORIES.add(new BooleanFactory());
        REGISTEDFACTORIES.add(new MapFactory());
        REGISTEDFACTORIES.add(new DateTimeFactory());
        REGISTEDFACTORIES.add(new BigIntegerFactory());
        REGISTEDFACTORIES.add(new BigDecimalFactory());
        REGISTEDFACTORIES.add(new EnumFactory());
        REGISTEDFACTORIES.add(new ListFactory());
        REGISTEDFACTORIES.add(new StreamFactory());
    }

    //
//    /*
//   按顺序匹配参数
//    */
//    public static Object[] getParameters(List<KeyValueStore> params, Type[] parameterTypes) {
//        Object[] result = new Object[parameterTypes.length];
//        for (int i = 0; i < parameterTypes.length; ++i) {
//
//                try {
//                    Class<?> clazz = Class.forName(parameterTypes[i].toString().split(" ")[1]);
//                    Object obj = clazz.newInstance();
//                    for(KeyValueStore keyValueStore:params){
//                        ReflectionUtils.setFieldValue(obj,keyValueStore.getName(),keyValueStore.getValue());
//                    }
//
//                    result[i] = create(parameterTypes[i], obj);
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InstantiationException e) {
//                    e.printStackTrace();
//                }
//
////            KeyValueStore keyValuePair = params.get(i);
////            result[i] = create(parameterTypes[i], keyValuePair.getValue());
//        }
//
//        return result;
//    }
    public static Object[] getParameters(List<KeyValueStore> params, Type[] parameterTypes, String useSign, String signType) {
//        if (parameterTypes.length != params.size()) {
//            throw new IllegalArgumentException();
//        }
        Object[] result = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; ++i) {
            KeyValueStore keyValuePair = params.get(i);
            result[i] = create(parameterTypes[i], keyValuePair.getValue());
            //加密
            TreeMap<String, String> map = beanToSortMap(result[i]);
            String sign = map.remove("sign");
            if (useSign.equals("true") && StringUtils.isEmpty(sign)) {
                if (signType.equalsIgnoreCase("md5")) {
                    String systemType = map.get("systemType");

                    String key = StringUtil.getPro("test.properties", signType+".key." + systemType);

                    String addSign = SignUtil.addSignDubboMd5(map, key);
                    ReflectionUtils.setFieldValue(result[i], "sign", addSign);
                }else if (signType.equalsIgnoreCase("sha")){}
            }
        }
        return result;
    }

//    public static TreeMap<String, String> beanToSortMap(Object obj) {
//        TreeMap<String, String> params = new TreeMap<String, String>();
//        try {
//            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
//            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
//            for (int i = 0; i < descriptors.length; i++) {
//                String name = descriptors[i].getName();
//                if (!StringUtils.equals(name, "class")) {
//                    Object o = propertyUtilsBean.getNestedProperty(obj, name);
//                    if (o != null)
//                        params.put(name, o.toString());
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return params;
//    }

    public static <T> T create(Type type, Object value) {
        for (InstanceFactory factory : REGISTEDFACTORIES) {
            if (factory.support(type)) {
                return (T) factory.create(type, value);
            }
        }
        return (T) DEFAULTFACTORY.create(type, value);
    }
}
