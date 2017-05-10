package com.xn.manage.utils;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StartJMeterAgent_SSH {

    public static String exec(String host, String user, String psw, int port, String command) {
        String result = "";
        Session session = null;
        ChannelExec openChannel = null;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, port);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setPassword(psw);
            session.connect();
            openChannel = (ChannelExec) session.openChannel("exec");
            openChannel.setCommand(command);
            int exitStatus = openChannel.getExitStatus();
            System.out.println(exitStatus);
            openChannel.connect();
            InputStream in = openChannel.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String buf = null;
            while ((buf = reader.readLine()) != null) {
                result += new String(buf.getBytes("gbk"), "UTF-8") + "    <br>\r\n";
            }
        } catch (JSchException | IOException e) {
            result += e.getMessage();
        } finally {
            if (openChannel != null && !openChannel.isClosed()) {
                openChannel.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
        return result;
    }

    public static boolean test_link(String host, String user, String psw, int port) {
        boolean testFlag = false;
        Session session = null;
        ChannelExec openChannel = null;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, port);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setPassword(psw);
            session.connect(6000);
            if(session.isConnected()){
                testFlag=true;
            }
        } catch (JSchException e) {
            e.printStackTrace();
        } finally {
            return testFlag;
        }
    }

    public static void exec_command(String host, String user, String psw, int port, String command) {
        Session session = null;
        ChannelExec openChannel = null;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, port);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setPassword(psw);
            session.connect();

            openChannel = (ChannelExec) session.openChannel("exec");
            openChannel.setCommand(command);
            int exitStatus = openChannel.getExitStatus();
            System.out.println(exitStatus);
            openChannel.connect();

            //读取返回数据（屏幕输出），判断命令执行未结束则while循环，直到结束，如果是持续执行的命令需要另外kill进程
            InputStream in = openChannel.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String buf = null;
            while ((buf = reader.readLine()) != null) {
                System.out.println(new String(buf.getBytes("UTF-8"), "UTF-8"));
                //writeExecCommand("log" + host + ".txt", new String(buf.getBytes("UTF-8"),"UTF-8"));
            }
            Thread.sleep(3000);
        } catch (JSchException | IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            //writeExecCommand("local" + host + ".txt", e.getMessage());
        } finally {
            if (openChannel != null && !openChannel.isClosed()) {
                openChannel.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }

        }
    }


    public static void main(String[] args) {
        String host = "10.17.2.187";
        String user = "root";
        String psw = "jinrong";
        int port = 65300;
        String command = "tailf nohup.out";
         exec_command(host, user, psw, port, command);

        //System.out.println(exec(host, user, psw, port, "pwd"));
        //System.out.println(exec(host, user, psw, port, "/tmp/apache-jmeter-3.1/bin/jmeter-server"));
//        System.out.println(test_link(host,user,psw,port));
//        exec_command(host, user, psw, port, command);
//        exec_command(host, user, psw, port, "pwd");
//		exec_command(host, user, psw, port, "bash /data/apache-jmeter-3.1/bin/jmeter-server");
        Thread.currentThread().stop();
    }


}
