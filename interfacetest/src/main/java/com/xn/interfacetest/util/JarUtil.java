package com.xn.interfacetest.util;/**
 * Created by xn056839 on 2016/10/27.
 */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.xn.common.utils.PropertyUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JarUtil {
    private static final Logger logger = LoggerFactory.getLogger(JarUtil.class);

    public static void addJar(String jarPath) {
//        checkNew(jarPath);
//        URLClassLoader loader = null;
//        try {
//            File file = new File(jarPath);
//            File jar=file.listFiles()[0];
//            URL url=new URL("file:"+jar.getAbsolutePath());
//            URL[] urls = new URL[]{url};
//            loader = new URLClassLoader(urls,Thread.currentThread().getContextClassLoader());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new Exception("load  jar error");
//        } finally {
//            return loader;
//        }
        ExtClasspathLoader extClasspathLoader = new ExtClasspathLoader();
        extClasspathLoader.addJarPath(jarPath);
        extClasspathLoader.loadClasspath();

    }

    public static void checkNew(String jarPath)  {
//        GetPara getPara = new GetPara();
//        String path = getPara.getPath();
//        File profile = new File(path + "suite/jar.properties");
//        String checkurl = StringUtil.getPro("jar.properties", "checkurl");
//        String repository = StringUtil.getConfig(profile, "repository","snapshots");
//        String artifact = StringUtil.getConfig(profile, "artifact","");
//        String group = StringUtil.getConfig(profile, "group","");
//        String version = StringUtil.getConfig(profile, "version","");
//
//        checkurl = String.format(checkurl, repository, group, artifact, version);
//
//
//        String newJarName = getNewJarName(checkurl);
//        File file = new File(jarPath);
//        File[] files = file.listFiles();
//        if (files.length == 0) {
//            try {
//                downloadJar(repository, artifact, group, version, jarPath,newJarName);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            String oldJar = files[0].getAbsolutePath();
//            String oldJarName = oldJar.substring(oldJar.lastIndexOf("/") + 1);
//            if (!newJarName.equals("") && !oldJarName.equals(newJarName)) {
//
//                try {
//                    downloadJar(repository, artifact,  group, version, jarPath,newJarName);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                File oldJarFile=new File(oldJar);
//                oldJarFile.delete();
//            }
//
//        }
    }

    public static void downloadJar(String r, String a, String g, String v, String jarPath,String name) throws Exception {
//        String downloadUrl = StringUtil.getPro("jar.properties", "newurl");
//        downloadUrl = String.format(downloadUrl, r, g, a, v);
//        System.out.println(downloadUrl);
//        try {
//
//            File f = new File(jarPath+"/"+name);
//            URL httpurl = new URL(downloadUrl);
//            FileUtils.copyURLToFile(httpurl, f);
//            if (!f.exists()) {
//                logger.error("download jar failed!");
//                throw new Exception("download jar failed!");
//            }
//
//        } catch (Exception e) {
//
//            logger.error("download jar failed!");
//            throw new Exception("download jar failed!");
//        }

    }

    public static String getNewJarName(String urlString) {

        //获得返回的xml
        URL url = null;
        String jarName = "";
        try {
            url = new URL(urlString);
            URLConnection urlConnection = null;
            urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(500);
            InputStream xmlInputStream = null;

            xmlInputStream = urlConnection.getInputStream();
            byte[] testByteArr = new byte[0];

            testByteArr = new byte[xmlInputStream.available()];

            xmlInputStream.read(testByteArr);

            String content = new String(testByteArr);
            //获得最新的jar的名字
            Document document = null;

            document = DocumentHelper.parseText(content);
            Element root = document.getRootElement();
            Element data = root.element("data");
            String jarPath = data.elementText("repositoryPath");
            jarName = jarPath.substring(jarPath.lastIndexOf("/") + 1);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jarName;


    }



    public static List<String[]> getJarMethod(String jarFile) throws Exception {
        String NORMAL_METHOD= "waitequalsnotifynotifyAlltoStringhashCodegetClass";
        List<String[]> a = new ArrayList<String[]>();
        try {
            //通过jarFile 和JarEntry得到所有的类
            JarFile jar = new JarFile(jarFile);//"D:/sip-test.jar"
            Enumeration e = jar.entries();
            while (e.hasMoreElements()) {
                JarEntry entry = (JarEntry) e.nextElement();
                //entry.getMethod()
                if (entry.getName().indexOf("META-INF") < 0) {
                    String sName = entry.getName();
                    String substr[] = sName.split("/");
                    String pName = "";
                    for (int i = 0; i < substr.length - 1; i++) {
                        if (i > 0)
                            pName = pName + "/" + substr[i];
                        else
                            pName = substr[i];
                    }
                    if (sName.indexOf(".class") < 0)
                    {
                        sName = sName.substring(0, sName.length() - 1);
                    }
                    else
                    {
                        //通过URLClassLoader.loadClass方法得到具体某个类
                        URL url1=new URL("file:D:\\jar\\user\\user-interface-2.0.0-20161115.095252-17.jar");
                        URLClassLoader myClassLoader=new URLClassLoader(new URL[]{url1},Thread.currentThread().getContextClassLoader());
                        String ppName = sName.replace("/", ".").replace(".class", "");
                        Class myClass = myClassLoader.loadClass(ppName);
                        //通过getMethods得到类中包含的方法
                        Method m[] = myClass.getMethods();
                        for(int i=0; i<m.length; i++)
                        {
                            String sm = m[i].getName();
                            if (NORMAL_METHOD.indexOf(sm) <0)
                            {
                                String[] c = {sm, sName};
                                a.add(c);
                            }
                        }
                    }
                    String[] b = { sName, pName };
                    a.add(b);
                }
            }
            return a;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return a;
    }
//    public static void main(String[] args) {
//        try {
//            List<String[]> s=getJarMethod("E:\\upload\\commons-codec-1.10.jar");
//            for (String[] s1:s) {
//                for (String s2:s1) {
//                    System.out.println(s2);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
////        URLClassLoader urlClassLoader=addJar("d:\\jar\\user\\");
////        try {
////            Class<?> c =urlClassLoader.loadClass("cn.xn.user.controller.IRegisterService");
////            Method[] methods = c.getDeclaredMethods();
////            for(Method method:methods){
////                System.out.println(method.getName());
////            }
////
////        } catch (ClassNotFoundException e) {
////            e.printStackTrace();
////        }
//    }


    public static void main(String[] args){
        try
        {
            URL  url  = new URL("file:\\C:\\Users\\Administrator\\Downloads\\apache-jmeter-3.0\\apache-jmeter-3.0\\lib\\ext\\Signature.jar");
            URLClassLoader loader = new URLClassLoader(new URL[]{url},Thread.currentThread().getContextClassLoader());

            Class<?> clazz = loader.loadClass("com.xiaoniu.base.encode_main");

            //传入参数类型
            Class<?>[] typesB = new Class[3];
            typesB[0] = String.class;
            typesB[1] = String.class;
            typesB[2] = String.class;

            //传入参数值
            Object[] ObjsB = new Object[3];
            ObjsB[0] = new String("appId=credit-ndf&nonce=56412&reqId=201705031812249926&timestamp=20170503181224");
            ObjsB[1] = "88888888";
            ObjsB[2] = new String("HmacSHA1");

            //指定加密方法
            Method newMethod = clazz.getDeclaredMethod("encode", typesB);
            //调用方法拿到返回值
            String res = newMethod.invoke(clazz, ObjsB).toString();

            System.out.println(res);

            loader.close();  //关闭类的加载器

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //利用jar包加密，返回加密结果
    public static StringBuffer signature (String filePath,String className,String methodName,Class<?>[] types,Object[] values) throws Exception{
        StringBuffer result = new StringBuffer("");
        String pathUrl = PropertyUtil.getProperty("upload_ip") + filePath;
        logger.info("加密jar包所在路径：" + pathUrl);
        try
        {
            URL  url  = new URL(pathUrl);
            URLClassLoader loader = new URLClassLoader(new URL[]{url},Thread.currentThread().getContextClassLoader());

            Class<?> clazz = loader.loadClass(className);

            //指定加密方法
            Method newMethod = clazz.getDeclaredMethod(methodName, types);
            //调用方法拿到返回值
            result = result.append(newMethod.invoke(clazz, values).toString());

            logger.info("加密之后的结果为：" + result);

            loader.close();  //关闭类的加载器

        } catch (Exception e)
        {
            logger.error("加密产生异常：",e.getMessage());
            throw e;
        }

        return result;
    }

}
