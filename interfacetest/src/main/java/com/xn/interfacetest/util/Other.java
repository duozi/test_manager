package com.xn.interfacetest.util;/**
 * Created by xn056839 on 2016/11/14.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.tools.jar.Main;

public class Other {
    private static final Logger logger = LoggerFactory.getLogger(Other.class);

//    public static void main(String[] args) {
//        String oldPath = "d:/suite/dubbo/";
//        String newPath = "d:/suite2/suite/dubbo/";
//        File oldFile = new File(oldPath);
//        File newFile = new File(newPath);
//        File[] oldFilelist = oldFile.listFiles();
//        File[] newFilelist = newFile.listFiles();
//        for (File interfaceFile : oldFilelist) {
//
//            String interfaceName = interfaceFile.getName();
//            File[] oldFileInterface = interfaceFile.listFiles();
//            for (File methodFile : oldFileInterface) {
//                String methodName = methodFile.getName();
//                if (methodName.equals("serviceConfig.properties")) {
//                    String content = FileUtil.fileReadeForStr(methodFile).replace("\t", "").replace("\n", "\r\n");
//                    FileUtil.fileWrite((newPath + "/" + interfaceName + "/" + methodName).replace("#", ""), content);
//                }
//                if (methodFile.isDirectory()) {
//                    File[] oldFileMethod = methodFile.listFiles();
//                    for (File caseFile : oldFileMethod) {
//                        String caseName = caseFile.getName();
//                        if (caseFile.isDirectory() && caseName.equals("demo_1")) {
//                            File[] fileList = caseFile.listFiles();
//                            for (File file : fileList) {
//                                String fileName = file.getName();
//                                String path = interfaceName + "/" + methodName + "/" + caseName + "/" + fileName;
//                                if (fileName.equals("before") || fileName.equals("after") || fileName.equals("assert")) {
//                                    String content = FileUtil.fileReadeForStr(file).replace("\t", "").replace("\n", "\r\n");
//                                    FileUtil.fileWrite(newPath + path.replace("#", ""), content);
//                                } else if (!fileName.equals("log")) {
//                                    List<String> lines = FileUtil.fileReadeForList(file);
//                                    Map<String, String> oldMap = new HashMap<String, String>();
//                                    for (String line : lines) {
//                                        String key = "";
//                                        String value = "";
//                                        key = line.split("=")[0];
//                                        if (line.split("=").length == 2) {
//                                            value = line.split("=")[1];
//                                        }
//
//
//                                        oldMap.put(key, value);
//                                    }
//                                    String filepath = newPath + path.replace("#", "");
//                                    File newfile = new File(filepath);
//                                    List<String> newlines = FileUtil.fileReadeForList(newfile);
//
//                                    String newContent = "";
//                                    for (String s : newlines) {
//                                        if (s.contains(":") && s.contains(",")) {
//                                            String mapkey = s.substring(s.indexOf("\"") + 1, s.indexOf("\"", 4));
//                                            if (oldMap.containsKey(mapkey)) {
//                                                String replace = "\"" + oldMap.get(mapkey) + "\"";
//                                                s = s.replace("\"\"", replace);
//                                            }
//                                        }
//                                        newContent += s + "\r\n";
//
//
//                                    }
//
//
//                                    FileUtil.fileWrite(newPath + path.replace("#", ""), newContent);
//                                }
//
//
//                            }
//                        }
//                    }
//                }
//            }
//
//        }
//    }

//    public static void main(String[] args) {
//        String s="\t\t\"appVersion\":\"\",";
//        System.out.println(s.substring(s.indexOf("\""),s.indexOf("\"",4)));
//
//    }
public static void main(String[] args) {
    System.out.println(System.getProperty("java.ext.dirs"));
    ClassLoader test_cl = Main.class.getClassLoader();
//    ClassLoader lib_cl = Lib.class.getClassLoader();
//    System.out.println(test_cl == lib_cl);
    System.out.println(test_cl);
//    System.out.println(lib_cl);
}
}
