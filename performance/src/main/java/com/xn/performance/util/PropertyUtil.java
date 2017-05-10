<<<<<<< HEAD:manage_platform/src/main/java/com/xn/manage/utils/PropertyUtil.java
package com.xn.manage.utils;
=======
package com.xn.performance.util;
>>>>>>> hezhouxiyiyangde:performance/src/main/java/com/xn/performance/util/PropertyUtil.java

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyUtil extends PropertyPlaceholderConfigurer {
	
	private static Map<String, Object> ctxPropertiesMap; 
    
    @Override 
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props); 
        ctxPropertiesMap = new HashMap<String, Object>(); 
        for (Object key : props.keySet()) { 
            String keyStr = key.toString(); 
            String value = props.getProperty(keyStr); 
            ctxPropertiesMap.put(keyStr, value); 
        } 
    } 
 
    public static String getProperty(String name) { 
    	Object obj = ctxPropertiesMap.get(name);
    	if (null == obj) {
			return null;
		}
        return obj.toString(); 
    }
<<<<<<< HEAD:manage_platform/src/main/java/com/xn/manage/utils/PropertyUtil.java
=======

    public static void setProperty(String name,Object value) {
       ctxPropertiesMap.put(name,value);
    }
>>>>>>> hezhouxiyiyangde:performance/src/main/java/com/xn/performance/util/PropertyUtil.java
}
