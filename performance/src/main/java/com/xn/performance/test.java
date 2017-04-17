package com.xn.performance;/**
 * Created by xn056839 on 2017/4/13.
 */

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.cookie.CookieSpec;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class test {
    private static final Logger logger = LoggerFactory.getLogger(test.class);

    public static void main(String[] args) {
        try {
            HttpClient client = new HttpClient();
            client.getHostConfiguration().setHost("10.17.2.137", 3000);
            // 模拟登录页面 login.jsp->main.jsp
            PostMethod post = new PostMethod( "/login" );
            NameValuePair name = new NameValuePair( "name" , "jmeterxn" );
            NameValuePair pass = new NameValuePair( "password" , "jmeterxn" );
            NameValuePair email = new NameValuePair( "email" , "" );
            post.setRequestBody( new NameValuePair[]{name,pass,email});
            int status = client.executeMethod(post);
            System.out.println(post.getResponseBodyAsString());
            post.releaseConnection();
            // 查看 cookie 信息
            CookieSpec cookiespec = CookiePolicy.getDefaultSpec();
            Cookie[] cookies = cookiespec.match("10.17.2.137", 3000, "/login" , false , client.getState().getCookies());
            if (cookies.length == 0) {
                System.out.println( "None" );
            } else {
                for ( int i = 0; i < cookies.length; i++) {
                    System.out.println(cookies[i].toString());
                }
            }
            // 访问所需的页面 main2.jsp
            GetMethod get=new GetMethod("/dashboard/db/agent01_nmon");
            client.executeMethod(get);
            //System.out.println(get.getResponseBodyAsString());
            get.releaseConnection();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
