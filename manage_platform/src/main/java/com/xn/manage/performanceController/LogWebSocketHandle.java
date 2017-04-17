package com.xn.manage.performanceController;/**
 * Created by xn056839 on 2017/4/13.
 */

import com.xn.manage.utils.TailLogThread;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.InputStream;
@ServerEndpoint("/log")
public class LogWebSocketHandle {
    private Process process;
    private InputStream inputStream;
    /**
     * 新的WebSocket请求开启
     */
    @OnOpen
    public void onOpen(Session session) {
        try {
            // 执行tail -f命令
            process = Runtime.getRuntime().exec("tail -f /jmeter-server.log");
            inputStream = process.getInputStream();
            // 一定要启动新的线程，防止InputStream阻塞处理WebSocket的线程
            TailLogThread thread = new TailLogThread(inputStream, session);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * WebSocket请求关闭
     */
    @OnClose
    public void onClose() {
        try {
            if(inputStream != null)
                inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(process != null)
            process.destroy();
    }
    @OnError
    public void onError(Throwable thr) {
        thr.printStackTrace();
    }
}