package com.xn.performance.util.jmeter;

import com.jcraft.jsch.*;

import java.io.*;
import java.util.Vector;

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
            if (session.isConnected()) {
                testFlag = true;
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

    /**
     *      * 上传文件
     *      * @param directory  上传的目录 如/tmp
     *      * @param uploadFile 要上传的文件
     *     
     */
    public static void upload(String directory, String uploadFile, String host, String user, String psw, int port) {
        Session session = null;
        ChannelSftp openChannel = null;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, port);
// 下面3句表示使用账号密码登录，不用公私匙
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.setPassword(psw);
            session.connect();
            openChannel = (ChannelSftp) session.openChannel("sftp");
            openChannel.connect();
            try{
                Vector content = openChannel.ls(directory);
                if(content == null) {
                    openChannel.mkdir(directory);
                }
            } catch (SftpException e) {
                openChannel.mkdir(directory);
            }
            //开始上传文件
            openChannel.cd(directory);
            File file = new File(uploadFile);
            // put方法用于上传文件，get方法获取文件
            openChannel.put(new FileInputStream(file), file.getName());
            System.out.println("Upload Success!");
        } catch (JSchException | SftpException | IOException e) {
            System.out.println(e.getMessage());
//            writeExecCommand("local" + host + ".txt", e.getMessage());
        } finally {
            if (openChannel != null && !openChannel.isClosed()) {
                openChannel.disconnect();
            }
            if (session != null && session.isConnected()) {
//创建连接后这个session是一直可用的，所以不需要关闭。由Session中open的Channel在使用后应该关闭。
                session.disconnect();
            }
        }
    }

    public static void main(String[] args) {
//        String host = "10.17.2.77";
//        String user = "root";
//        String psw = "xnhack";
//        int port = 65300;
//        String command = "ls";
        //String rult = exec(host, user, psw, port, command);
        //System.out.println(rult);
        //System.out.println(exec(host, user, psw, port, "pwd"));
        //System.out.println(exec(host, user, psw, port, "/tmp/apache-jmeter-3.1/bin/jmeter-server"));
//        System.out.println(test_link(host,user,psw,port));
//        exec_command(host, user, psw, port, command);
//        exec_command(host, user, psw, port, "pwd");
//        upload("/temp/file","D:\\test.properties",host,user,psw,port);
//        exec_command(host, user, psw, port, "bash /data/apache-jmeter-3.1/bin/jmeter-server");
//        Thread.currentThread().stop();
        String[] s=" aksjfldsfds".trim().split(" ");
        System.out.println(s.length);
    }


}
