package com.xn.manage.httputils;

import com.xiaoniu.dataplatform.utils.PropertyUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.Map.Entry;

public class HzReqUtil {
    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;

    private static final Logger logger = LoggerFactory.getLogger(HttpTookit.class);

    /**
     * 连接超时时间
     */
    public static final int CONNECTION_TIMEOUT_MS;
    /**
     * 读取数据超时时间
     */
    public static final int SO_TIMEOUT_MS;

    static {
        CONNECTION_TIMEOUT_MS = Integer.parseInt(PropertyUtil.getProperty("HTTP_CONNECTION_TIMEOUT"));
        SO_TIMEOUT_MS = Integer.parseInt(PropertyUtil.getProperty("HTTP_READ_TIMEOUT"));

        // 设置连接池  
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小  
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时  
        configBuilder.setConnectTimeout(CONNECTION_TIMEOUT_MS);
        // 设置读取超时  
        configBuilder.setSocketTimeout(SO_TIMEOUT_MS);
        // 设置从连接池获取连接实例的超时  
        configBuilder.setConnectionRequestTimeout(CONNECTION_TIMEOUT_MS);
        // 在提交请求之前 测试连接是否可用  
        configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        TreeMap<String, String> paramsMap = new TreeMap<String, String>();
        //设置请求参数
        paramsMap.put("appId", "xiaoniu"); // AppId
        paramsMap.put("secret", "Yvtpz43qy65mQEh78PTJtBzXmiNAQADtfQ"); // 秘钥
        paramsMap.put("version", "0.2");
        paramsMap.put("productNo", "HZ_XXXXXX");//产品编号

        //业务参数(不同产品业务参数不一样，请参照接口手册)
        paramsMap.put("userId", "123456789012345678");
        paramsMap.put("userName", "张三");

        //计算签名
        Set<Entry<String, String>> entrySet = paramsMap.entrySet();
        List<String> list = new LinkedList<String>();
        for (Entry<String, String> entry : entrySet) {
            list.add(StringUtils.join(Arrays.asList("&", entry.getKey(), "=", entry.getValue()), ""));
        }
        String params = StringUtils.join(list, "");
        params = params.substring(1);
        String sign = DigestUtils.md5Hex(params);
        System.out.println("签名sign:" + params);

        //设置签名
        paramsMap.put("sign", sign);
        paramsMap.remove("secret"); // 不需要传递秘钥

        //发送请求
        String result = post("https://api.huadata.com/result", paramsMap);
        System.out.println(result);
    }

    /**
     * 初始化httpClient
     *
     * @return
     */
    public static CloseableHttpClient initHttpClient() {

        SSLConnectionSocketFactory sslsf = null;
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT)
                //设置请求和传输超时时间
                .setConnectTimeout(5000).setConnectionRequestTimeout(3000).setSocketTimeout(5000)
                .build();
        try {
            SSLContext sslContext = new org.apache.http.ssl.SSLContextBuilder()
                    .loadTrustMaterial(null, new TrustStrategy() {
                        //信任所有证书
                        public boolean isTrusted(X509Certificate[] chain,
                                                 String authType) throws CertificateException {
                            return true;
                        }
                    }).build();
            //跳过证书Hostname验证
            sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        HttpClientBuilder httpClientBuilder = HttpClients.custom()
                .setDefaultRequestConfig(globalConfig)
                .setSSLSocketFactory(sslsf);

        return httpClientBuilder.build();
    }

    /**
     * 发送POST请求
     *
     * @param url
     * @param paramsMap
     * @return
     */
    public static String post(String url, Map<String, String> paramsMap) {
        CloseableHttpClient client = initHttpClient();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if ((paramsMap != null) && (!paramsMap.isEmpty())) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Entry<String, String> param : paramsMap.entrySet()) {
                    if (StringUtils.isNotBlank(param.getValue())) {
                        paramList.add(new BasicNameValuePair(param.getKey(), param.getValue().trim()));
                    } else {
                        paramList.add(new BasicNameValuePair(param.getKey(), param.getValue()));
                    }
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
            }
            response = client.execute(method);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    responseText = EntityUtils.toString(entity, "UTF-8");
                }
                logger.info("返回结果：" + responseText);
            } else {
                logger.error("数据中心返回结果异常.....");
                responseText = "HTTP_FAIL";
            }
        } catch (Exception e) {
            logger.error("HTTP请求出现异常,原因：{}",e.getMessage());
            responseText = "HTTP_FAIL";
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (Exception e) {
            }
        }
        return responseText;
    }
}
