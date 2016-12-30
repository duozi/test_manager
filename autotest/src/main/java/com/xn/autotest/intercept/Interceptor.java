package com.xn.autotest.intercept;/**
 * Created by xn056839 on 2016/12/30.
 */

import com.xn.autotest.context.Context;

/**
 * 拦截器
 * @param <Command>
 * @param <T>
 * @param <K>
 */
public interface Interceptor<Command, T, K> {
    public Object beforeExecute(Command command, T param, Context context);

    public Object afterExecute(Command command, K response, Context context);
}
