package com.xn.manage.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyUtil extends PropertyPlaceholderConfigurer {
	
	private static Map<String, Object> ctxPropertiesMap; 
    
    public static String getProperty(String name) {
    	Object obj = ctxPropertiesMap.get(name);
    	if (null == obj) {
			return null;
		}
        return obj.toString();
    }
 
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
}
