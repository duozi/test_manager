package com.xn.interfacetest.util;/**
 * Created by xn056839 on 2016/12/5.
 */

import com.google.common.collect.Lists;
import sun.misc.ClassLoaderUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

public final class ExtClasspathLoader  {
    /**
     * URLClassLoader的addURL方法
     */
    private static Method addURL = initAddMethod();

    private static URLClassLoader classloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
    private static List<String> jarList = Lists.newArrayList();

    /**
     * 初始化addUrl 方法.
     *
     * @return 可访问addUrl方法的Method对象
     */
    private static Method initAddMethod() {
        try {
            Method add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
            add.setAccessible(true);
            return add;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 从配置文件中得到配置的需要加载classpath里的资源路径集合
     *
     * @return
     */
    private static List<String> getResFiles() {
        //TODO 从properties文件中读取配置信息略
        return null;
    }

    public static void main(String[] args) throws ClassNotFoundException, IOException {

        ExtClasspathLoader extClasspathLoader = new ExtClasspathLoader();
        extClasspathLoader.addJarPath("D:\\jar\\user");
        extClasspathLoader.loadClasspath();
        Class<?> c = Class.forName("cn.xn.user.service.IPwdService");
        System.out.println(c.getName());

        classloader.close();


        ClassLoaderUtil.releaseLoader(classloader);
        Class<?> c1 = Class.forName("cn.xn.user.service.IFriendService");
        System.out.println(c1.getName());


    }

    public void addJarPath(String jarPath) {
        jarList.add(jarPath);

    }

    /**
     * 加载jar classpath。
     */
    public void loadClasspath() {
        List<String> files = jarList;
        for (String f : files) {
            loadClasspath(f);
        }

//        List<String> resFiles = getResFiles();
//
//        for (String r : resFiles) {
//            loadResourceDir(r);
//        }
    }

    private void loadClasspath(String filepath) {
        File file = new File(filepath);
        loopFiles(file);
    }

    private void loadResourceDir(String filepath) {
        File file = new File(filepath);
        loopDirs(file);
    }

    /**
     * 循环遍历目录，找出所有的资源路径。
     *
     * @param file 当前遍历文件
     */
    private void loopDirs(File file) {
        // 资源文件只加载路径
        if (file.isDirectory()) {
            addURL(file);
            File[] tmps = file.listFiles();
            for (File tmp : tmps) {
                loopDirs(tmp);
            }
        }
    }

    /**
     * 循环遍历目录，找出所有的jar包。
     *
     * @param file 当前遍历文件
     */
    private void loopFiles(File file) {
        if (file.isDirectory()) {
            File[] tmps = file.listFiles();
            for (File tmp : tmps) {
                loopFiles(tmp);
            }
        } else {
            if (file.getAbsolutePath().endsWith(".jar") || file.getAbsolutePath().endsWith(".zip")) {
                addURL(file);
            }
        }
    }

    /**
     * 通过filepath加载文件到classpath。
     *
     * @param file 文件路径
     * @return URL
     * @throws Exception 异常
     */
    private void addURL(File file) {
        try {
//返回类型和值
            addURL.invoke(classloader, new Object[]{file.toURI().toURL()});
        } catch (Exception e) {
        }
    }

    /**
     * 从配置文件中得到配置的需要加载到classpath里的路径集合。
     *
     * @return
     */
    private List<String> getJarFiles() {
        String jarPath = "D:\\jar\\user\\user-interface-2.0.0-20161115.095252-17.jar";
        List list = Lists.newArrayList(jarPath);
        return list;
    }
}
