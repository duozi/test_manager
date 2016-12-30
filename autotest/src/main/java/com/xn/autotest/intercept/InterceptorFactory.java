package com.xn.autotest.intercept;/**
 * Created by xn056839 on 2016/12/30.
 */

import com.xn.autotest.bean.response.Response;
import com.xn.autotest.command.StepCommand;
import com.xn.autotest.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
/**
 * 过滤器服务工厂类，负责管理所有过滤器
 */
public class InterceptorFactory {
    private static final Logger logger = LoggerFactory.getLogger(InterceptorFactory.class);
    static List<Interceptor> interceptors = new ArrayList<Interceptor>();

    static {
//        registerInterceptor(new HttpCallBackInterceptor());
//        registerInterceptor(new RestfulApiInterceptor());
//        registerInterceptor(new ParamIgnoreInterceptor());
//        registerInterceptor(new UploadFileInterceptor());
    }

    public static void registerInterceptor(Interceptor interceptor) {
        interceptors.add(interceptor);
    }

    public void doBefore(StepCommand command, Response response, Context context) {
        for (Interceptor interceptor : interceptors) {
            interceptor.beforeExecute(command, response, context);
        }
    }

    public void doAfter(StepCommand command, Response response, Context context) {
        for (Interceptor interceptor : interceptors) {
            interceptor.afterExecute(command, response, context);
        }
    }

    public static InterceptorFactory getInstance() {
        return factory;
    }

    static InterceptorFactory factory = new InterceptorFactory();

}
