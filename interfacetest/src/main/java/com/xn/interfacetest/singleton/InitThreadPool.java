package com.xn.interfacetest.singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xn058121 on 2017/5/22.
 */
public class InitThreadPool {
    private static  InitThreadPool instance = null;

    private ExecutorService excutePool = null;

    public static synchronized InitThreadPool getInstance() {
        if (instance == null) {
            instance =  new InitThreadPool();
        }
        return instance;
    }

    public ExecutorService init() throws Exception {
        excutePool = Executors.newFixedThreadPool(10);
        return excutePool;
    }
}
