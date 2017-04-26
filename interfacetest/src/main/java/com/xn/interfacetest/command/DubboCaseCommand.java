package com.xn.interfacetest.command;
/**
 * Created by xn056839 on 2016/8/31.
 */


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.fastjson.JSON;
import com.xn.interfacetest.model.KeyValueStore;
import com.xn.interfacetest.model.ServiceDesc;
import com.xn.interfacetest.objectfactory.BeanUtils;
import com.xn.interfacetest.response.Response;
import com.xn.interfacetest.result.ReportResult;
import com.xn.interfacetest.util.FileUtil;
import com.xn.interfacetest.util.ReflectionUtils;


public class DubboCaseCommand implements CaseCommand {
    private static final Logger logger = LoggerFactory.getLogger(DubboCaseCommand.class);
    private static final Map<String, Object> serviceCache = new ConcurrentHashMap<String, Object>();
    private final ServiceDesc serviceDesc;
    List<KeyValueStore> params;
    private Response response = new Response();
    private String request;
    private String casePath;
    private String result;
    private String useSign;
    private String signType;

    public DubboCaseCommand(List<KeyValueStore> params, ServiceDesc serviceDesc, String casePath, String useSign, String signType) {

        this.params = params;
        this.serviceDesc = serviceDesc;
        this.casePath = casePath;
        this.useSign = useSign;
        this.signType = signType;


    }

    private static String getRpcServiceUrl(String url) {
        StringBuilder sb = new StringBuilder("dubbo");
        sb.append("://");
        sb.append(url);
        if (sb.indexOf(":") == -1) {
            sb.append(":").append("20888");
        }
        return sb.toString();
    }

    public String getResult() {
        return result;
    }

    public Response getResponse() {
        return response;
    }

    public String getRequest() {
        return request;
    }

    public Object getRpcService(ServiceDesc serviceDesc) throws NoSuchMethodException, InvocationTargetException {
       if(serviceDesc.getUseZk()){
           return getRpcServiceUseZk(serviceDesc);
       }else{
           return getRpcServiceUseUrl(serviceDesc);
       }
    }

    public Object getRpcServiceUseUrl(ServiceDesc serviceDesc)throws NoSuchMethodException, InvocationTargetException{
        String rpcUrl = getRpcServiceUrl(serviceDesc.getUrl());
        Class<?> interfaceClass = ReflectionUtils.loadClass(serviceDesc.getClazz());
        String cacheKey = serviceDesc.getCacheKey();
        Object service = serviceCache.get(cacheKey);

        if (service == null) {
            synchronized (serviceCache) {
                ApplicationConfig application = new ApplicationConfig();
                application.setName(serviceDesc.getAppName());
                ReferenceConfig<?> reference = new ReferenceConfig();
                reference.setApplication(application);
                reference.setInterface(interfaceClass);
                if (StringUtils.isNotBlank(serviceDesc.getVersion()) && !serviceDesc.getVersion().equals("*")) {
                    reference.setVersion(serviceDesc.getVersion());
                }
                reference.setUrl(rpcUrl);
                if (StringUtils.isNotBlank(serviceDesc.getGroup())) {
                    reference.setGroup(serviceDesc.getGroup());
                }
                reference.setTimeout(Integer.valueOf(serviceDesc.getTimeout()));
                service = reference.get();
                serviceCache.put(cacheKey, service);
            }
        }
        return service;
    }


    public Object getRpcServiceUseZk(ServiceDesc serviceDesc) throws NoSuchMethodException, InvocationTargetException{
        String zkLine = serviceDesc.getZk();
        Class<?> interfaceClass = ReflectionUtils.loadClass(serviceDesc.getClazz());
        String cacheKey = serviceDesc.getCacheKey();
        Object service = serviceCache.get(cacheKey);
        if (service == null) {
            synchronized (serviceCache) {
                // 当前应用配置
                ApplicationConfig application = new ApplicationConfig();
                application.setName(serviceDesc.getAppName());
                String[] zkList = zkLine.split(",");
                List list = new ArrayList();
                // 连接注册中心配置
                for (String zk : zkList) {
                    RegistryConfig registry = new RegistryConfig();
                    registry.setAddress("zookeeper://" + zk);
                    list.add(registry);
                }


                // 注意：ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接
                // 引用远程服务
                ReferenceConfig<?> reference = new ReferenceConfig(); // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
                reference.setApplication(application);
                reference.setRegistries(list);
                // 多个注册中心可以用setRegistries()
                reference.setInterface(interfaceClass);
                if (StringUtils.isNotBlank(serviceDesc.getVersion()) && !serviceDesc.getVersion().equals("*")) {
                    reference.setVersion(serviceDesc.getVersion());
                }

                if (StringUtils.isNotBlank(serviceDesc.getGroup())) {
                    reference.setGroup(serviceDesc.getGroup());
                }
                reference.setTimeout(Integer.valueOf(serviceDesc.getTimeout()));
                service = reference.get();
                serviceCache.put(cacheKey, service);
            }
        }
        return service;
    }

    @Override
    public void execute() {

        try {
            Object service = getRpcService(serviceDesc);
            Method executeMethod = ReflectionUtils.getMethod(serviceDesc.getMethodName(), serviceDesc.getServiceClass());
            Object[] parameters = BeanUtils.getParameters(params, executeMethod.getGenericParameterTypes(), useSign, signType);
            request = JSON.toJSONString(parameters);

            logger.info("Rpc request start: params={}", new Object[]{JSON.toJSONString(parameters)});

            Object result = executeMethod.invoke(service, parameters);

            response.setBody(result);
            FileUtil.fileWrite(casePath, "请求参数：" + request + "\n返回结果：" + response.toString());
            logger.info("Rpc response:{}", new Object[]{JSON.toJSONString(response)});
        } catch (InvocationTargetException ite) {
            logger.error("call rpc error", ite.getTargetException());
            ReportResult.errorPlus();
            result = "error";
            response.setException(ite.getTargetException());

        }
//        catch (IllegalAccessException e) {
//            ReportResult.errorPlus();
//            result = "error";
//            response.setException(e);
//            throw new RuntimeException("illegal access", e);
//        }
        catch (Exception e) {
            logger.error("call rpc error", e);
            response.setException(e);
            result = "error";
            ReportResult.errorPlus();

        }

    }

    @Override
    public void executeWithException() throws Exception {

    }

    @Override
    public void executeWithException(Long reportId) throws Exception {

    }
}
