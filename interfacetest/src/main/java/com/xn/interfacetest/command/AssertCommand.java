package com.xn.interfacetest.command;
/**
 * Created by xn056839 on 2016/9/5.
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.xn.interfacetest.Exception.AssertNotEqualException;
import com.xn.interfacetest.api.RelationAssertResultService;
import com.xn.interfacetest.api.TestReportService;
import com.xn.interfacetest.dto.RelationAssertResultDto;
import com.xn.interfacetest.dto.TestReportDto;
import com.xn.interfacetest.model.KeyValueStore;
import com.xn.interfacetest.response.Assert;
import com.xn.interfacetest.response.AssertItem;
import com.xn.interfacetest.response.Response;
import com.xn.interfacetest.result.ReportResult;
import com.xn.interfacetest.util.SpringContextUtil;

import net.sf.json.JSONObject;


public class AssertCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(AssertCommand.class);
    private final static String separator = System.getProperty("line.separator", "\r\n");

    static RelationAssertResultService relationAssertResultService = (RelationAssertResultService) SpringContextUtil.getBean("relationAssertResultService");
    static TestReportService testReportService = (TestReportService) SpringContextUtil.getBean("testReportService");

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
        relationAssertResultDto.setAssertKey(key);
        relationAssertResultDto.setExpectValue(value);
        relationAssertResultDto.setExactValue(jsonObject.getString(key));
        relationAssertResultDto.setReportId(reportId);
        relationAssertResultDto.setParamsAssertId(assertId);
        relationAssertResultDto.setCaseId(assertItem.getCaseDto().getId());
        relationAssertResultDto.setInterfaceId(assertItem.getInterfaceDto().getId());
        //默认设置为断言通过
        relationAssertResultDto.setResult("match");

        //是否抛出异常
        Boolean exception = false;

        if (key.contains(".")) {
            String[] array = key.split("\\.", 2);
            if (jsonObject.containsKey(array[0])) {
                deepAssert(jsonObject.getJSONObject(array[0]), array[1], value, assertItem, reportId,assertId);
            } else {
                AssertItem item = new AssertItem(key, value, "校验字段不存在");
                assertItem.addDiff(item);
                relationAssertResultDto.setResult("not match");
                exception = true;
            }
        } else {
            Set set = jsonObject.keySet();
            if (set.contains(key)) {
                String returnValue = String.valueOf(jsonObject.get(key));
                if (value.equalsIgnoreCase("NONULL")) {
                    if (StringUtils.isEmpty(returnValue)||!returnValue.equals("null")||!returnValue.equals("{}")) {
                        AssertItem item = new AssertItem(key, value, returnValue);
                        assertItem.addDiff(item);
                        relationAssertResultDto.setResult("not match");
                        exception = true;
                    }
                } else {
                    if (!returnValue.equals(value)) {
                        AssertItem item = new AssertItem(key, value, returnValue);
                        assertItem.addDiff(item);
                        relationAssertResultDto.setResult("not match");
                        exception = true;
                    }
                }
            } else {
                AssertItem item = new AssertItem(key, value, "校验字段不存在");
                assertItem.addDiff(item);
//                ReportResult.getReportResult().assertAdd(assertItem);
                relationAssertResultDto.setResult("not match");
                exception = true;
            }
        }

        //保存断言结果
        if(null != reportId){
            relationAssertResultService.save(relationAssertResultDto);
        }

        if(exception){
            throw new AssertNotEqualException("assert is not Equal");
        }
    }

    public static void deepAssert(JSONObject jsonObject, String key, String value, Assert assertItem) throws AssertNotEqualException {
        if (key.contains(".")) {
            String[] array = key.split("\\.", 2);
            if (jsonObject.containsKey(array[0])) {
                deepAssert(jsonObject.getJSONObject(array[0]), array[1], value, assertItem);
            } else {
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
                        AssertItem item = new AssertItem(key, value, returnValue);
                        assertItem.addDiff(item);
                        throw new AssertNotEqualException("assert is not Equal");
                    }
                } else {
                    if (!returnValue.equals(value)) {
                        AssertItem item = new AssertItem(key, value, returnValue);
                        assertItem.addDiff(item);
                        throw new AssertNotEqualException("assert is not Equal");

                    }
                }
            } else {
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
    public void execute() throws Exception{
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
            //将该测试计划的结果保存为失败
            TestReportDto testReportDto = new TestReportDto();
            testReportDto.setId(reportId);
            testReportDto.setResult("fail");
            testReportService.update(testReportDto);
            ReportResult.failedPlus();
            logger.info("断言校验的时候reportResult的值：" +  ReportResult.getReportResult().toString());
            logger.error(assertItem.getInterfaceDto().getUrl() + "/" + assertItem.getInterfaceDto().getName() + "/" + assertItem.getCaseDto().getName() + "=====[assert error]");
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
