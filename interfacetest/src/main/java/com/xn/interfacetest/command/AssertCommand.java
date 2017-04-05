package com.xn.interfacetest.command;
/**
 * Created by xn056839 on 2016/9/5.
 */

import com.xn.interfacetest.Exception.AssertNotEqualException;
import com.xn.interfacetest.dto.RelationAssertResultDto;
import com.xn.interfacetest.entity.RelationAssertResult;
import com.xn.interfacetest.model.AssertKeyValueVo;
import com.xn.interfacetest.model.KeyValueStore;
import com.xn.interfacetest.response.Assert;
import com.xn.interfacetest.response.AssertItem;
import com.xn.interfacetest.response.Response;
import com.xn.interfacetest.result.ReportResult;
import com.xn.interfacetest.service.RelationAssertResultService;
import com.xn.interfacetest.service.TestDatabaseConfigService;
import com.xn.interfacetest.util.StringUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class AssertCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(AssertCommand.class);
    private final static String separator = System.getProperty("line.separator", "\r\n");

    static ApplicationContext ctx=new ClassPathXmlApplicationContext("/spring/spring-context.xml");
    static RelationAssertResultService relationAssertResultService=(RelationAssertResultService) ctx.getBean("relationAssertResultService");
    public Assert assertItem; //断言校验结果-----指定断言不符合的内容是什么
    private Command paramAssertCommand; //响应参数校验断言
    private List<Command> redisAssertCommandList; //redis数据断言
    private List<Command> DBAssertCommandList; //数据库断言
    private Long reportId; //结果报告的id

    public AssertCommand(Command paramAssertCommand, List<Command> redisAssertCommandList, List<Command> DBAssertCommandList, Assert assertItem,Long reportId) {
        this.paramAssertCommand = paramAssertCommand;
        this.assertItem = assertItem;
        this.redisAssertCommandList = redisAssertCommandList;
        this.DBAssertCommandList = DBAssertCommandList;
        this.reportId = reportId;
    }

    public static Map<String, String> convertKeyValueStoreToMap(List<KeyValueStore> params) {
        Map<String, String> result = new HashMap<String, String>();
        for (KeyValueStore kvs : params) {
            Object value = kvs.getValue();
            if (value instanceof Map) {
                result.putAll((Map) value);
            } else {
                result.put(kvs.getName(), (String) value);
            }
        }
        return result;
    }

    public static void deepAssert(JSONObject jsonObject, String key, String value, Assert assertItem, Long reportId,Long assertId) throws AssertNotEqualException {
        RelationAssertResultDto relationAssertResultDto = new RelationAssertResultDto();
        if (key.contains(".")) {
            String[] array = key.split("\\.", 2);
            if (jsonObject.containsKey(array[0])) {
                deepAssert(jsonObject.getJSONObject(array[0]), array[1], value, assertItem, reportId,assertId);
            } else {
                ReportResult.failedPlus();
                AssertItem item = new AssertItem(key, value, "校验字段不存在");
                assertItem.addDiff(item);
                //保存断言结果
                if(null != reportId){
                    relationAssertResultDto.setReportId(reportId);
                    relationAssertResultDto.setParamsAssertId(assertId);
                    relationAssertResultDto.setResult("assert is not Equal");
                }

                throw new AssertNotEqualException("assert is not Equal");
            }
        } else {
            Set set = jsonObject.keySet();
            if (set.contains(key)) {
                String returnValue = String.valueOf(jsonObject.get(key));
                if (value.equals("NONULL")) {
                    if (StringUtils.isEmpty(returnValue)||!returnValue.equals("null")||!returnValue.equals("{}")) {
                        ReportResult.failedPlus();

                        AssertItem item = new AssertItem(key, value, returnValue);
                        assertItem.addDiff(item);
                        //保存断言结果
                        if(null != reportId){
                            relationAssertResultDto.setReportId(reportId);
                            relationAssertResultDto.setParamsAssertId(assertId);
                            relationAssertResultDto.setResult("assert is not Equal");
                        }
                        throw new AssertNotEqualException("assert is not Equal");
                    }
                } else {
                    if (!returnValue.equals(value)) {
                        ReportResult.failedPlus();

                        AssertItem item = new AssertItem(key, value, returnValue);
                        assertItem.addDiff(item);

                        //保存断言结果
                        if(null != reportId){
                            relationAssertResultDto.setReportId(reportId);
                            relationAssertResultDto.setParamsAssertId(assertId);
                            relationAssertResultDto.setResult("assert is not Equal");
                        }

                        throw new AssertNotEqualException("assert is not Equal");

                    }
                }
            } else {
                ReportResult.failedPlus();
                AssertItem item = new AssertItem(key, value, "校验字段不存在");
                assertItem.addDiff(item);
//                ReportResult.getReportResult().assertAdd(assertItem);

                //保存断言结果
                if(null != reportId){
                    relationAssertResultDto.setReportId(reportId);
                    relationAssertResultDto.setParamsAssertId(assertId);
                    relationAssertResultDto.setResult("assert is not Equal");
                }

                throw new AssertNotEqualException("assert is not Equal");
            }
            if(null != relationAssertResultDto.getReportId()){
                relationAssertResultService.save(relationAssertResultDto);
            }

        }
    }

    public static void deepAssert(JSONObject jsonObject, String key, String value, Assert assertItem) throws AssertNotEqualException {
        if (key.contains(".")) {
            String[] array = key.split("\\.", 2);
            if (jsonObject.containsKey(array[0])) {
                deepAssert(jsonObject.getJSONObject(array[0]), array[1], value, assertItem);
            } else {
                ReportResult.failedPlus();
                AssertItem item = new AssertItem(key, value, "校验字段不存在");
                assertItem.addDiff(item);
                //
                throw new AssertNotEqualException("assert is not Equal");
            }
        } else {
            Set set = jsonObject.keySet();
            if (set.contains(key)) {
                String returnValue = String.valueOf(jsonObject.get(key));
                if (value.equals("NOTNULL")) {
                    if (StringUtils.isEmpty(returnValue)||!returnValue.equals("null")||!returnValue.equals("{}")) {
                        ReportResult.failedPlus();

                        AssertItem item = new AssertItem(key, value, returnValue);
                        assertItem.addDiff(item);
                        throw new AssertNotEqualException("assert is not Equal");
                    }
                } else {
                    if (!returnValue.equals(value)) {
                        ReportResult.failedPlus();

                        AssertItem item = new AssertItem(key, value, returnValue);
                        assertItem.addDiff(item);
                        throw new AssertNotEqualException("assert is not Equal");

                    }
                }
            } else {
                ReportResult.failedPlus();
                AssertItem item = new AssertItem(key, value, "校验字段不存在");
                assertItem.addDiff(item);
//                ReportResult.getReportResult().assertAdd(assertItem);
                throw new AssertNotEqualException("assert is not Equal");
            }


        }
    }

    public static void main(String[] args) throws AssertNotEqualException {
        String s="{\"batch_id\":{},\"error_msg\":\"成功\",\"error_no\":0}";
        JSONObject jsonObject= JSONObject.fromObject(s);
        deepAssert(jsonObject,"batch_id","NOTNULL",null);
    }

    public void setAssertItem(String request, Response response, String result) {
        assertItem.setRequest(request);
        assertItem.setResponse(response);
        assertItem.setResult(result);
    }

    @Override
    public void execute(Long caseId, Long interfaceId, Long planId,Long reportId) {

    }

    @Override
    public void execute() {

        try {
            String result = assertItem.getResult();
            if (result == null || !result.equals("error")) {
                if (paramAssertCommand != null) {
                    paramAssertCommand.executeWithException(reportId);
                }
                if (DBAssertCommandList != null && DBAssertCommandList.size() > 0) {
                    for (Command command : DBAssertCommandList) {
                        command.executeWithException(reportId);
                    }
                }
                if (redisAssertCommandList != null && redisAssertCommandList.size() > 0) {
                    for (Command command : redisAssertCommandList) {
                        command.executeWithException(reportId);
                    }


                }
            }
        } catch (Exception e) {
            logger.error(assertItem.getInterfaceName() + "/" + assertItem.getMethodName() + "/" + assertItem.getCaseName() + "=====[assert error]");
        } finally {
            ReportResult.getReportResult().assertAdd(assertItem);
        }
    }

    @Override
    public void executeWithException() throws Exception {

    }

    @Override
    public void executeWithException(Long reportId) throws Exception {

    }
}
