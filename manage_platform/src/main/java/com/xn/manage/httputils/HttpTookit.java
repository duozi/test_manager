package com.xn.manage.httputils;

import com.alibaba.fastjson.JSON;
import com.xiaoniu.dataplatform.utils.PropertyUtil;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.collections.MapUtils.isNotEmpty;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

/**
 * 基于 httpclient 4.3.1版本的 http工具类
 * 
 * @author tanhui
 * @version 1.0
 */
public class HttpTookit {
	private static final String DELETE = "DELETE";
	private static final String PUT = "PUT";
	private static final String GET = "GET";
	private static final String POST = "POST";
	private static final String DEFAULT_CONTENT_TYPE = "application/json";

	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(HttpTookit.class);

	public static final String CHARSET = "UTF-8";
	/**
	 * 连接超时时间
	 */
	public static final int CONNECTION_TIMEOUT_MS;
	/**
	 * 读取数据超时时间
	 */
	public static final int SO_TIMEOUT_MS;

	public static final String DEFAULT_CALL_ID = "CCXICREDIT";

	static {
		CONNECTION_TIMEOUT_MS = Integer.parseInt(PropertyUtil.getProperty("HTTP_CONNECTION_TIMEOUT"));
		SO_TIMEOUT_MS = Integer.parseInt(PropertyUtil.getProperty("HTTP_READ_TIMEOUT"));
	}
	

	/**
	 * 获取HttpClient对象
	 *
	 * @return
	 */
	private static CloseableHttpClient getHttpClient(){
		return getHttpClient(SO_TIMEOUT_MS, CONNECTION_TIMEOUT_MS);
	}
	private static CloseableHttpClient getHttpClient(int readTimeout, int connectionTimeout) {
		RequestConfig config = RequestConfig.custom().setConnectTimeout(connectionTimeout).setSocketTimeout(readTimeout).build();
		CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
		return httpClient;
	}
	

	public static String doGet(String url) throws Exception{
		return doGet(url, null, null);
	}
	
	public static String doGet(String url, int readTimeout, int connectionTimeout) throws Exception{
		return doGet(url, null, null, CHARSET, readTimeout, connectionTimeout);
	}

	public static String doGet(String url, Map<String, String> params)throws Exception {
		return doGet(url, params, null, CHARSET);
	}

	public static String doGet(String url, Map<String, String> params, Map<String, String> heads)throws Exception {
		return doGet(url, params, heads, CHARSET);
	}
	
	public static String doGet(String url, Map<String, String> params, Map<String, String> heads, String charset)throws Exception {
		return doGet(url, params, heads, charset, SO_TIMEOUT_MS, CONNECTION_TIMEOUT_MS);
	}

	public static String doPost(String url, Map<String, String> params) throws Exception {
		return doPost(url, params, null, CHARSET);
	}

	public static String doPost(String url, Map<String, String> params, Map<String, String> heads) throws Exception {
		return doPost(url, params, heads, CHARSET);
	}

	public static String doPut(String url, Map<String, String> params) throws Exception {
		return doPut(url, JSON.toJSONString(params), null, CHARSET);
	}

	public static String doPut(String url, Map<String, String> params, HashMap<String, String> heads) throws Exception {
		return doPut(url, JSON.toJSONString(params), heads, CHARSET);
	}
	
	public static String doDelete(String url)throws Exception {
		return doDelete(url, null, null, CHARSET);
	}

	public static String doDelete(String url, Map<String, String> params)throws Exception {
		return doDelete(url, params, null, CHARSET);
	}

	public static String doDelete(String url, Map<String, String> params, Map<String, String> heads)throws Exception {
		return doDelete(url, params, heads, CHARSET);
	}

	/**
	 * HTTP Get 获取内容
	 * 
	 * @param url 请求的url地址"?"之前的地址
	 * @param params 请求的参数
	 * @param heads 请求头参数
	 * @param charset 编码格式
	 * @param readTimeout 读取超时
	 * @param connectionTimeout 连接超时
	 * @return 页面内容
	 * @throws Exception 
	 */
	public static String doGet(String url, Map<String, String> params, Map<String, String> heads, String charset, int readTimeout, int connectionTimeout) throws Exception {
		checkArgument(isNotBlank(url), "url is null");
		heads = setDefaultHeads(heads);
		long start = System.currentTimeMillis();
		log(GET, url, params, heads);
		
		try {
			if (isNotEmpty(params)) {
				List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
				for (Map.Entry<String, String> entry : params.entrySet()) {
					String value = entry.getValue();
					if (isNotEmpty(value)) {
						pairs.add(new BasicNameValuePair(entry.getKey(), value));
					}
				}
				url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
			}
			HttpGet httpGet = new HttpGet(url);
			if (isNotEmpty(heads)) {
				for (String k : heads.keySet()) {
					httpGet.addHeader(k, heads.get(k));
				}
			}
			CloseableHttpResponse response = getHttpClient(readTimeout, connectionTimeout).execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpGet.abort();
				httpStatusErrorLog(GET, url, JSON.toJSONString(params), JSON.toJSONString(heads), statusCode);
				throw new RuntimeException("HttpClient,error status code :" + statusCode + " url :" + url);
			}
			HttpEntity entity = response.getEntity();
			String result = null;
			if (entity != null) {
				result = EntityUtils.toString(entity, "utf-8");
			}
			EntityUtils.consume(entity);
			response.close();
			outRespLog(start, result);
			return result;
		} catch (Exception e) {
			logger.error("http请求有误.", e);
			throw e;
		}
	}

	
	private static String doPost(String url, Map<String, String> params, Map<String, String> heads, String charset) throws Exception {
		return doPost(url, JSON.toJSONString(params), heads, charset);
	}
	
	/**
	 * HTTP Post 获取内容
	 * 
	 * @param url 请求的url地址 "?"之前的地址
	 * @param json 请求的参数
	 * @param heads 请求体参数
	 * @param charset 编码格式
	 * @return 页面内容
	 * @throws Exception 
	 */
	public static String doPost(String url, String json, Map<String, String> heads, String charset) throws Exception {
		checkArgument(isNotBlank(url), "url is null");
		heads = setDefaultHeads(heads);
		long start = System.currentTimeMillis();
		logger.info("type : {}, url : {}, params : {}, heads : {}", POST, url, json, heads);
		
		try {
			HttpPost httpPost = new HttpPost(url);
			if (isNotEmpty(json)) {
				httpPost.setEntity(new StringEntity(json, charset));
			}
			if (isNotEmpty(heads)) {
				for (String k : heads.keySet()) {
					httpPost.addHeader(k, heads.get(k));
				}
			}
			CloseableHttpResponse response = getHttpClient().execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpPost.abort();
				httpStatusErrorLog(POST, url, json, JSON.toJSONString(heads), statusCode);
				throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}
			HttpEntity entity = response.getEntity();
			String result = null;
			if (entity != null) {
				result = EntityUtils.toString(entity, "utf-8");
			}
			EntityUtils.consume(entity);
			response.close();
			outRespLog(start, result);
			return result;
		} catch (Exception e) {
			logger.error("http请求有误.", e);
			throw e;
		}
	}
	
	public  static String doNaturePost(String url, Map<String, String> params, Map<String, String> heads, String charset) throws Exception {
		checkArgument(isNotBlank(url), "url is null");
		heads = setDefaultHeads(heads);
		long start = System.currentTimeMillis();
		log(POST, url, params, heads);
		
		try {
			if (isNotEmpty(params)) {
				List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
				for (Map.Entry<String, String> entry : params.entrySet()) {
					String value = entry.getValue();
					if (isNotEmpty(value)) {
						pairs.add(new BasicNameValuePair(entry.getKey(), value));
					}
				}
				url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
			}
			HttpPost httpPost = new HttpPost(url);
			CloseableHttpResponse response = getHttpClient().execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpPost.abort();
				httpStatusErrorLog(POST, url, JSON.toJSONString(params), JSON.toJSONString(heads), statusCode);
				throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}
			HttpEntity entity = response.getEntity();
			String result = null;
			if (entity != null) {
				result = EntityUtils.toString(entity, CHARSET);
			}
			EntityUtils.consume(entity);
			response.close();
			outRespLog(start, result);
			return result;
		} catch (Exception e) {
			logger.error("http请求有误.", e);
			throw e;
		}
	}

	/**
	 * HTTP Put内容
	 *
	 * @param url 请求的url地址 "?"之前的地址
	 * @param json 请求的参数
	 * @param heads 请求头参数
	 * @param charset 编码格式
	 * @return
	 * @throws Exception 
	 */
	public static String doPut(String url, String json, Map<String, String> heads, String charset) throws Exception {
		checkArgument(isNotBlank(url), "url is null");
		heads = setDefaultHeads(heads);
		long start = System.currentTimeMillis();
		logger.info("type : {}, url : {}, params : {}, heads : {}", PUT, url, json, heads);
		
		try {
			HttpPut httpPut = new HttpPut(url);
			if (isNotEmpty(json)) {
				httpPut.setEntity(new StringEntity(json, charset));
			}
			if (isNotEmpty(heads)) {
				for (String k : heads.keySet()) {
					httpPut.addHeader(k, heads.get(k));
				}
			}
			CloseableHttpResponse response = getHttpClient().execute(httpPut);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpPut.abort();
				httpStatusErrorLog(PUT, url, json, JSON.toJSONString(heads), statusCode);
				throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}
			HttpEntity entity = response.getEntity();
			String result = null;
			if (entity != null) {
				result = EntityUtils.toString(entity, "utf-8");
			}
			EntityUtils.consume(entity);
			response.close();
			outRespLog(start, result);
			return result;
		} catch (Exception e) {
			logger.error("http请求有误.", e);
			throw e;
		}
	}


	/**
	 * HTTP Delete
	 *
	 * @param url 请求的url地址 "?"之前的地址
	 * @param params 请求的参数
	 * @param heads 请求头参数
	 * @param charset 编码格式
	 * @return
	 * @throws Exception 
	 */
	private static String doDelete(String url, Map<String, String> params, Map<String, String> heads, String charset) throws Exception {
		checkArgument(isNotBlank(url), "url is null");
		heads = setDefaultHeads(heads);
		long start = System.currentTimeMillis();
		log(DELETE, url, params, heads);
		
		try {
			if (isNotEmpty(params)) {
				List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
				for (Map.Entry<String, String> entry : params.entrySet()) {
					String value = entry.getValue();
					if (isNotEmpty(value)) {
						pairs.add(new BasicNameValuePair(entry.getKey(), value));
					}
				}
				url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
			}
			HttpDelete httpDelete = new HttpDelete(url);
			if (isNotEmpty(heads)) {
				for (String k : heads.keySet()) {
					httpDelete.addHeader(k, heads.get(k));
				}
			}
			
			CloseableHttpResponse response = getHttpClient().execute(httpDelete);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpDelete.abort();
				httpStatusErrorLog(DELETE, url, JSON.toJSONString(params), JSON.toJSONString(heads), statusCode);
				throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}
			HttpEntity entity = response.getEntity();
			String result = null;
			if (entity != null) {
				result = EntityUtils.toString(entity, "utf-8");
			}
			EntityUtils.consume(entity);
			response.close();
			outRespLog(start, result);
			return result;
		} catch (Exception e) {
			logger.error("http请求有误.", e);
			throw e;
		}
	}
	
	/**
	 * 设置默认请求头参数
	 *
	 * @param heads
	 * @return
	 */
	private static Map<String, String> setDefaultHeads(Map<String, String> heads) {
		if (heads == null) 
			heads = new HashMap<String, String>();
        heads.put("caller-id", DEFAULT_CALL_ID);
        heads.put("Content-Type", DEFAULT_CONTENT_TYPE);
		return heads;
	}

	/**
	 * 日志输出
	 *
	 * @param type
	 * @param url
	 * @param params
	 * @param heads
	 */
	private static void log(String type, String url, Map<String, String> params, Map<String, String> heads) {
		String paramsStr = isNotEmpty(params) ? JSON.toJSON(params).toString() : "null";
		String headsStr = isNotEmpty(heads) ? JSON.toJSON(heads).toString() : "null";
		logger.info("type : {}, url : {}, params : {}, heads : {}", type, url, paramsStr, headsStr);
	}

	/**
	 * 输出返回日志
	 *
	 * @param start 请求开始即使那
	 * @param result
	 */
	private static void outRespLog(long start, String result) {
		logger.info("response: {}, 耗时: {} ms", result.replaceAll("\n", ""), System.currentTimeMillis() - start);
	}
	
	/**
	 * http访问返回异常错误码
	 *
	 * @param url
	 * @param params
	 * @param heads
	 * @param statusCode
	 */
	private static void httpStatusErrorLog(String type, String url, String params, String heads, int statusCode) {
		logger.error("HttpClient请求返回异常, error status code :{}, url:{}, params: {}, heads: {}", statusCode, url, params, heads);
	}
}
