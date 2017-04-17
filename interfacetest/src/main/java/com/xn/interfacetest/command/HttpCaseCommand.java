package com.xn.interfacetest.command;/**
 * Created by xn056839 on 2016/10/31.
 */

import com.alibaba.fastjson.JSON;
import com.xn.interfacetest.dto.RelationInterfaceResultDto;
import com.xn.interfacetest.dto.TestCaseDto;
import com.xn.interfacetest.dto.TestInterfaceDto;
import com.xn.interfacetest.dto.TestSuitDto;
import com.xn.interfacetest.response.Response;
import com.xn.interfacetest.result.ReportResult;
import com.xn.interfacetest.service.RelationInterfaceResultService;
import com.xn.interfacetest.util.FileUtil;
import com.xn.interfacetest.util.HttpClientUtil;
import com.xn.interfacetest.util.SpringContextUtil;
import junit.framework.TestCase;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HttpCaseCommand implements CaseCommand {
    private static final Logger logger = LoggerFactory.getLogger(HttpCaseCommand.class);
    private static HttpCaseCommand httpCaseCommand ;  //  关键点1   静态初使化 一个工具类  这样是为了在spring初使化之前
    private static SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
    private RelationInterfaceResultService relationInterfaceResultService = (RelationInterfaceResultService) SpringContextUtil.getBean("relationInterfaceResultService");

    private Response response = new Response();
    private String type;//请求类型:get\post
    private String url;//请求路径
    private String params;//请求参数
    private String contentType;//请求参数类型
    private String timeout;//请求超时时间
    private String result;//请求结果
    private String propType;//请求协议类型：http、https
    private Long planId;
    private TestSuitDto suitDto;
    private Long reportId;
    private TestCaseDto caseDto;
    private TestInterfaceDto interfaceDto;

    public HttpCaseCommand(String type, String url, String params, String contentType, String timeout, String propType, TestCaseDto caseDto, TestInterfaceDto interfaceDto, Long planId, Long reportId, TestSuitDto suitDto) {
        this.type = type;
        this.url = url;
        this.params = params;
        this.contentType = contentType;
        this.timeout = timeout;
        this.propType = propType;
        this.planId = planId;
        this.interfaceDto = interfaceDto;
        this.reportId = reportId;
        this.caseDto = caseDto;
        this.suitDto = suitDto;
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
        String request = "{\"appId\":\"test\",\"accessToken\":\"P2VxFTzzNqvUSijd1iGUKlaPcEsow5Rt3Hzxnzm01d1qi2HnT9rQdaz9Yw3LPpgZ\",\"reqId\":\"test1463629050504829411\"}";
        String url1 = "http://10.17.2.162:10080/cx580credit-service/inputsCondition";
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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
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
    public void httpRequest(){
        String responseStr = "";
        RelationInterfaceResultDto relationInterfaceResultDto = new RelationInterfaceResultDto();
        try {
            relationInterfaceResultDto.setExcuteTime(format.format(new Date()));
            if(contentType.contains("json")){
                responseStr =  HttpClientUtil.sendHttpPostJson(url,params);
            } else if(contentType.contains("form")){
                responseStr =  HttpClientUtil.sendHttpPost(url,params);
            } else if(contentType.contains("xml")){
                responseStr = HttpClientUtil.sendHttpPostXml(url,params);
            }
            result = "success";
        }catch (Exception e){
            ReportResult.errorPlus();
            logger.error("请求异常{}:",e);
            response.setException(e);
            result = "error";
        } finally {
            //保存请求结果
            response.setBody(responseStr);
            relationInterfaceResultDto.setPlanId(planId);
            relationInterfaceResultDto.setSuitId(suitDto.getId());
            relationInterfaceResultDto.setCaseId(caseDto.getId());
            relationInterfaceResultDto.setInterfaceId(interfaceDto.getId());
            relationInterfaceResultDto.setSuitName(suitDto.getName());
            relationInterfaceResultDto.setInterfaceName(caseDto.getName());
            relationInterfaceResultDto.setCaseName(interfaceDto.getName());
            relationInterfaceResultDto.setRequestData(params);
            relationInterfaceResultDto.setResponseData(responseStr);
            relationInterfaceResultDto.setResult(result);
            relationInterfaceResultDto.setReportId(reportId);
            relationInterfaceResultService.save(relationInterfaceResultDto);
        }
    }

    //发送https请求
    private void sendRequestHttps(Long caseId, Long interfaceId, Long planId,Long reportId) {
    }

//    //发送http请求
//    private void sendRequestHttp(){
//        // Post请求的url，与get不同的是不需要带参数
//        URL postUrl = null;
//        try {
//            logger.info("发送http请求：" + url);
//            postUrl = new URL(url);
//
//            // 打开连接
//            HttpURLConnection connection = (HttpURLConnection) postUrl
//                    .openConnection();
//            // Output to the connection. Default is
//            // false, set to true because post
//            // method must write something to the
//            // connection
//            // 设置是否向connection输出，因为这个是post请求，参数要放在
//            // http正文内，因此需要设为true
//            connection.setDoOutput(true);
//            connection.setConnectTimeout(Integer.parseInt(timeout));
//            // Read from the connection. Default is true.
//            connection.setDoInput(true);
//            // Set the post method. Default is GET
//            connection.setRequestMethod(type);
//            // Post cannot use caches
//            // Post 请求不能使用缓存
//            connection.setUseCaches(false);
//            // This method takes effects to
//            // every instances of this class.
//            // URLConnection.setFollowRedirects是static函数，作用于所有的URLConnection对象。
//            // connection.setFollowRedirects(true);
//
//            // This methods only
//            // takes effacts to this
//            // instance.
//            // URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
//            connection.setInstanceFollowRedirects(true);
//            connection.setConnectTimeout(20000);
//            connection.setReadTimeout(300000);
//            // Set the content type to urlencoded,
//            // because we will write
//            // some URL-encoded content to the
//            // connection. Settings above must be set before connect!
//            // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
//            // 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
//            // 进行编码
//
//            connection.setRequestProperty("Accept-Charset", "utf-8");
//            connection.setRequestProperty("Content-Type", contentType +"; charset=utf-8");
//            // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
//            // 要注意的是connection.getOutputStream会隐含的进行connect。
//            connection.connect();
//            DataOutputStream out = new DataOutputStream(connection
//                    .getOutputStream());
//            // The URL-encoded contend
//            // 正文，正文内容其实跟get的URL中'?'后的参数字符串一致
//
//            // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面
//
//            out.write(params.getBytes());
//
//            logger.info("Http request start: params=[{}]", params);
//
//            out.flush();
//            out.close(); // flush and close
//            BufferedReader reader = new BufferedReader(new InputStreamReader(
//                    connection.getInputStream()));
//            String line;
//            StringBuffer responseData = new StringBuffer("");
//            while ((line = reader.readLine()) != null) {
//                line = new String(line.getBytes(), "utf-8");
//                responseData.append(line);
//            }
//            logger.info("responseData:{}",responseData);
//
////            Object obj = JSONObject.fromObject(result);
//            response.setBody(responseData);
//            result = "success";
//
//            logger.info("Rpc response:{}", new Object[]{JSON.toJSONString(response)});
//
//            reader.close();
//            connection.disconnect();
//        } catch (MalformedURLException e) {
//            response.setException(e);
////            e.printStackTrace();
//            response.setBody(e);
//            result = "error";
//        } catch (ProtocolException e) {
//            //          e.printStackTrace();
//            response.setException(e);
//            response.setBody(e);
//            result = "error";
//        } catch (ConnectException e) {
////             e.printStackTrace();
//            response.setException(e);
//            result = "error";
//        } catch (IOException e) {
////            e.printStackTrace();
//            response.setException(e);
//            response.setBody(e);
//            result = "error";
//        } finally {
//            //结果id和计划id为空表示是用例的调试，用例调试保存结果,但是不保存计划id和report的id
//      //      if(reportId != null && null != planId){
//                //将请求结果保存至数据库
//                RelationInterfaceResultDto relationInterfaceResultDto = new RelationInterfaceResultDto();
//                relationInterfaceResultDto.setPlanId(planId);
//                relationInterfaceResultDto.setSuitId(suitId);
//                relationInterfaceResultDto.setCaseId(caseId);
//                relationInterfaceResultDto.setInterfaceId(interfaceId);
//                relationInterfaceResultDto.setRequestData(params);
//                relationInterfaceResultDto.setResponseData(response.getBody().toString());
//                relationInterfaceResultDto.setResult(result);
//                relationInterfaceResultDto.setReportId(reportId);
//                relationInterfaceResultService.save(relationInterfaceResultDto);
////            }
//
//        }
//    }

    @Override
    public void execute() {
        httpRequest();
    }

    @Override
    public void executeWithException() throws Exception {

    }

    @Override
    public void executeWithException(Long reportId) throws Exception {

    }

}
