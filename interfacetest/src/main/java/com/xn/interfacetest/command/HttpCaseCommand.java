package com.xn.interfacetest.command;/**
 * Created by xn056839 on 2016/10/31.
 */

import com.alibaba.fastjson.JSON;
import com.xn.interfacetest.dto.RelationInterfaceResultDto;
import com.xn.interfacetest.response.Response;
import com.xn.interfacetest.result.ReportResult;
import com.xn.interfacetest.service.RelationInterfaceResultService;
import com.xn.interfacetest.util.FileUtil;
import com.xn.interfacetest.util.HttpUtils;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.*;

public class HttpCaseCommand implements CaseCommand {
    private static final Logger logger = LoggerFactory.getLogger(HttpCaseCommand.class);
    private static HttpCaseCommand httpCaseCommand ;  //  关键点1   静态初使化 一个工具类  这样是为了在spring初使化之前
    @Autowired
    private RelationInterfaceResultService relationInterfaceResultService;  //添加所需service的私有成员
    private Response response = new Response();
    private String type;//请求类型:get\post
    private String url;//请求路径
    private String params;//请求参数
    private String paramsType;//请求参数类型
    private String timeout;//请求超时时间
    private String result;//请求结果
    private String propType;//请求协议类型：http、https
    public HttpCaseCommand( String type, String url, String params, String paramsType, String timeout,String propType) {
        this.type = type;
        this.url = url;
        this.params = params;
        this.paramsType = paramsType;
        this.timeout = timeout;
        this.propType = propType;
    }

    public static void main(String[] args) throws IOException {
//        String request = "{\n" +
//                "\"buyer_uin\":\"87f4246c-831b-4296-be28-b45cab4a410e\",\n" +
//                "\"input_charset\":\"UTF-8\",\n" +
//                "\"partner_id\":\"10002\",\n" +
//                "\"partner_trade_no\":\"20161206123456\",\n" +
//                "\"sign_type\":\"md5\",\n" +
//                "\"sign\":\"fvdasfsdfdg\"\n" +
//                "}";
        String request = "planeName=acd";
        String url1 = "http://localhost:8080/new_plane/check_plane_name";
//        String url1 = "www.2cto.com";
        try {
            //创建连接
            URL url = new URL(url1);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setConnectTimeout(20000);
            connection.setReadTimeout(300000);
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");

            connection.connect();

            //POST请求
            DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());
//JSONObject object=JSONObject.fromObject(request);

            out.writeBytes(request);
            out.flush();
            out.close();
//            InputStream is;
//            if (connection.getResponseCode() >= 400) {
//                is = connection.getErrorStream();
//            } else {
//                is = connection.getInputStream();
//            }


            //读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            System.out.println(sb);
            reader.close();
            // 断开连接
            connection.disconnect();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setRelationInterfaceResultService(RelationInterfaceResultService  relationInterfaceResultService) {
        this.relationInterfaceResultService = relationInterfaceResultService;
    }

    @PostConstruct     //关键二   通过@PostConstruct 和 @PreDestroy 方法 实现初始化和销毁bean之前进行的操作
    public void init() {
        httpCaseCommand = this;
        httpCaseCommand.relationInterfaceResultService = this.relationInterfaceResultService;   // 初使化时将已静态化的testService实例化
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getParamsType() {
        return paramsType;
    }

    public void setParamsType(String paramsType) {
        this.paramsType = paramsType;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String getRequest() {
        return params;
    }

    //发送请求
    public void httpRequest(Long caseId,Long interfaceId,Long planId,Long reportId){
        if(propType.equalsIgnoreCase("http")){
            sendRequestHttp( caseId, interfaceId, planId, reportId);
        } else if(propType.equalsIgnoreCase("https")){
            sendRequestHttps( caseId, interfaceId, planId, reportId);
        }
    }

    //发送https请求
    private void sendRequestHttps(Long caseId, Long interfaceId, Long planId,Long reportId) {
    }

    //发送http请求
    private void sendRequestHttp(Long caseId,Long interfaceId,Long planId,Long reportId){
        // Post请求的url，与get不同的是不需要带参数
        URL postUrl = null;
        try {
            postUrl = new URL(url);

            // 打开连接
            HttpURLConnection connection = (HttpURLConnection) postUrl
                    .openConnection();
            // Output to the connection. Default is
            // false, set to true because post
            // method must write something to the
            // connection
            // 设置是否向connection输出，因为这个是post请求，参数要放在
            // http正文内，因此需要设为true
            connection.setDoOutput(true);
            connection.setConnectTimeout(Integer.parseInt(timeout));
            // Read from the connection. Default is true.
            connection.setDoInput(true);
            // Set the post method. Default is GET
            connection.setRequestMethod(type);
            // Post cannot use caches
            // Post 请求不能使用缓存
            connection.setUseCaches(false);
            // This method takes effects to
            // every instances of this class.
            // URLConnection.setFollowRedirects是static函数，作用于所有的URLConnection对象。
            // connection.setFollowRedirects(true);

            // This methods only
            // takes effacts to this
            // instance.
            // URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
            connection.setInstanceFollowRedirects(true);
            connection.setConnectTimeout(20000);
            connection.setReadTimeout(300000);
            // Set the content type to urlencoded,
            // because we will write
            // some URL-encoded content to the
            // connection. Settings above must be set before connect!
            // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
            // 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
            // 进行编码

            String type = "";
            if (paramsType.equalsIgnoreCase("form")) {
                type = "x-www-form-urlencoded";
            } else if (paramsType.equalsIgnoreCase("json")) {
                type = "json";
            }
            connection.setRequestProperty("Content-Type", "application/" + type + "; charset=utf-8");
            // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
            // 要注意的是connection.getOutputStream会隐含的进行connect。
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection
                    .getOutputStream());
            // The URL-encoded contend
            // 正文，正文内容其实跟get的URL中'?'后的参数字符串一致

            // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面

            out.write(params.getBytes());

            logger.info("Http request start: params=[{}]", params);

            out.flush();
            out.close(); // flush and close
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            String result = "";
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            Object obj = JSONObject.fromObject(result);
            response.setBody(obj);

            logger.info("Rpc response:{}", new Object[]{JSON.toJSONString(response)});

            reader.close();
            connection.disconnect();
        } catch (MalformedURLException e) {
            response.setException(e);
            e.printStackTrace();
            result = "error";
        } catch (ProtocolException e) {
            e.printStackTrace();
            response.setException(e);
            result = "error";
        } catch (ConnectException e) {
            e.printStackTrace();
            response.setException(e);
            result = "error";
        } catch (IOException e) {
            e.printStackTrace();
            response.setException(e);
            result = "error";
        } finally {
            //结果id和计划id为空表示是用例的调试，用例调试不需要保存结果
            if(reportId != null && null != planId){
                //将请求结果保存至数据库
                RelationInterfaceResultDto relationInterfaceResultDto = new RelationInterfaceResultDto();
                relationInterfaceResultDto.setPlanId(planId);
                relationInterfaceResultDto.setCaseId(caseId);
                relationInterfaceResultDto.setInterfaceId(interfaceId);
                relationInterfaceResultDto.setRequestData(params);
                relationInterfaceResultDto.setResponseData(response.toString());
                relationInterfaceResultDto.setResult(result);
                relationInterfaceResultDto.setReportId(reportId);
                relationInterfaceResultService.save(relationInterfaceResultDto);
            }

        }
    }

    @Override
    public void execute(Long caseId,Long interfaceId,Long planId,Long reportId) {
        httpRequest(caseId, interfaceId, planId, reportId);
    }

    @Override
    public void execute() {

    }

    @Override
    public void executeWithException() throws Exception {

    }

    @Override
    public void executeWithException(Long reportId) throws Exception {

    }
}
