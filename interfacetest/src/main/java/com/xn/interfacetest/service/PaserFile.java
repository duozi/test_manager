package com.xn.interfacetest.service;/**
 * Created by xn056839 on 2016/10/28.
 */

import com.google.common.collect.Lists;
import com.xn.interfacetest.command.*;
import com.xn.interfacetest.model.KeyValueStore;
import com.xn.interfacetest.response.Assert;
import com.xn.interfacetest.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PaserFile {
    private static final Logger logger = LoggerFactory.getLogger(PaserFile.class);

    public static List<Command> dealAfterClassFile(File file) {

        List<Command> afterClass = new ArrayList();
        List<String> lines = FileUtil.fileReadeForList(file);
        for (String line : lines) {
             if (line.startsWith("DB")) {

                String sql = line.split("=", 2)[1];
                 String name=line.split("\\.",2)[0];
                afterClass.add(new DBCommand(name,sql));
            }
        }
        return afterClass;
    }

    //给所有文件去掉#
    public static void dealFile(String path) {
        File folder = new File(path);
        if (folder.getName().contains("#")) {
            File file = new File(folder.getPath().replace("#", ""));
            folder.renameTo(file);
        }
        if (folder.isDirectory()) {
            File[] interfaces = folder.listFiles();
            for (File interfaceFolder : interfaces) {
                dealFile(interfaceFolder.getPath());
            }
        }
    }

    //删除beforeClass和afterClass的文件内容
    public static void dealDBFile(String path) {
        File folder = new File(path);
        if (folder.getName().equals("beforeClass") || folder.getName().equals("afterClass")) {
            String paths = folder.getPath();
            FileUtil.fileWrite(paths, "");
        }
        if (folder.isDirectory()) {
            File[] interfaces = folder.listFiles();
            for (File interfaceFolder : interfaces) {
                dealDBFile(interfaceFolder.getPath());
            }
        }
    }

    public static void main(String[] args) {
        String s="s.123";
        String s1=s.split("\\.")[0];
        System.out.println(s1);

    }

    public  Command dealAssertFile(File file, String caseName,String interfaceName,String methodName) {
        Assert assertItem = new Assert(interfaceName, methodName, caseName);
        List<KeyValueStore> paralist = new ArrayList();
        List<KeyValueStore> redislist = new ArrayList();
        List<Command> redisAssertCommandList = new ArrayList();
        List<Command> dbAssertCommandList = new ArrayList();
        ParaAssertCommand paraAssertCommand=null;
        DBAssertCommand dbAssertCommand = null;
        List<String> lines = FileUtil.fileReadeForList(file);
        int redisFlag = 0;
        int dbFlag = 0;
        RedisAssertCommand redisAssertCommand = null;
        String name="";
        for (String line : lines) {
            if (!line.startsWith("#")) {
                if (redisFlag == 0) {
                    if (dbFlag == 0) {
                        if (line.contains("=") && line.split("=").length == 2) {
                            String type = line.split("=")[0];
                            String value = line.split("=")[1];
                            KeyValueStore keyValueStore = new KeyValueStore(type, value);
                            paralist.add(keyValueStore);
                        } else if (line.trim().equalsIgnoreCase("redis")) {
                            redisFlag = 1;
                            redisAssertCommand = new RedisAssertCommand();
                        } else if (line.trim().endsWith("DB")) {
                            dbFlag = 1;
                            dbAssertCommand = new DBAssertCommand();
                            name=line.split("\\.",2)[0];
                            dbAssertCommand.setName(name);
                        }
                    } else if (dbFlag == 1) {
                        if (line.contains("=")) {
                            String type = line.split("=", 2)[0];
                            String value = line.split("=", 2)[1];
                            if (type.equalsIgnoreCase("sql")) {
                                dbAssertCommand.setSql(value);

                            } else if (type.equalsIgnoreCase("count")) {
                                dbAssertCommand.setExpectCount(value);
                            }
                        } else if (line.trim().equalsIgnoreCase(name+".DB end")) {
                            dbFlag = 0;
                            dbAssertCommand.setAssertItem(assertItem);
                            dbAssertCommandList.add(dbAssertCommand);
                        }
                    }
                } else if (redisFlag == 1) {
                    if (line.trim().equalsIgnoreCase("redis end")) {
                        redisFlag = 0;
                        redisAssertCommand.setRedisParams(Lists.newArrayList(redislist));
                        redisAssertCommand.setAssertItem(assertItem);
                        redislist.clear();
                        redisAssertCommandList.add(redisAssertCommand);
                    } else if (line.contains("=") && line.split("=").length == 2) {
                        String key = line.split("=")[0];
                        String value = line.split("=")[1];
                        if (key.equalsIgnoreCase("redisMethod")) {
                            redisAssertCommand.setRedisMethod(value);

                        } else if (key.equalsIgnoreCase("key")) {
                            redisAssertCommand.setKey(value);
                        } else {
                            if(value.equals("null")){
                                value=null;
                            }
                            KeyValueStore keyValueStore = new KeyValueStore(key, value);
                            redislist.add(keyValueStore);
                        }
                    }
                }


            }

        }
        if (paralist.size() > 0) {
            paraAssertCommand = new ParaAssertCommand(paralist);
            paraAssertCommand.setAssertItem(assertItem);
        }
        return new AssertCommand(paraAssertCommand, redisAssertCommandList, dbAssertCommandList, assertItem);

    }

    public  List<Command> dealBeforeClassFile(File file) {

        List<Command> beforeClass = new ArrayList();
        List<String> lines = FileUtil.fileReadeForList(file);
        for (String line : lines) {
            if (line.contains("DB")) {

                String sql = line.split("=", 2)[1];
                String name=line.split("\\.",2)[0];
                beforeClass.add(new DBCommand(name,sql));
            }
        }
        return beforeClass;
    }

    public List<Command> dealFile(File file) {
        List<Command> list = new ArrayList();

        List<String> lines = FileUtil.fileReadeForList(file);
        RedisCommand redisCommand = null;
        int redisFlag = 0;
        for (String line : lines) {
            if (redisFlag == 0 && line.trim().contains("DB")) {
                String sql = line.split("=", 2)[1];
                String name=line.split("\\.",2)[0];
                list.add(new DBCommand(name,sql));
            } else if (line.trim().equalsIgnoreCase("redis")) {
                redisFlag = 1;
                redisCommand = new RedisCommand();
            } else if (redisFlag == 1) {

                if (line.startsWith("redisMethod")) {
                    if (line.split("=").length == 2) {
                        redisCommand.setMethodName(line.split("=")[1]);
                    }

                } else if (line.trim().equalsIgnoreCase("redis end")) {
                    redisFlag = 0;
                    list.add(redisCommand);
                } else {

                    if (!line.startsWith("#") && line.split("=").length == 2) {
                        String key = line.split("=")[0];
                        String value = line.split("=")[1];
//                        if (line.startsWith("loginName")) {
//                            redisCommand.setLoginName(line.split("=")[1]);
//                        } else if (line.startsWith("systemType")) {
//                            redisCommand.setSystemType(line.split("=")[1]);
//                        } else if (line.startsWith("times")) {
//                            redisCommand.setTimes(Integer.valueOf(line.split("=")[1]));
//                        } else
                            if (key.equalsIgnoreCase("key")) {
                            redisCommand.setKey(value);
                        } else if (key.equalsIgnoreCase("value")) {
                            redisCommand.setValue(value);
                        } else if (key.equalsIgnoreCase("time")) {
                            redisCommand.setTime(Integer.parseInt(value));
                        }
                    }

                }
            }
        }
        return list;
    }
}
