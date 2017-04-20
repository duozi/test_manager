package com.xn.interfacetest.command;
/**
 * Created by xn056839 on 2016/9/5.
 */

import static com.xn.interfacetest.command.AssertCommand.convertKeyValueStoreToMap;
import static com.xn.interfacetest.command.AssertCommand.deepAssert;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.xn.interfacetest.Exception.AssertNotEqualException;
import com.xn.interfacetest.model.KeyValueStore;
import com.xn.interfacetest.response.Assert;
import com.xn.interfacetest.response.AssertItem;
import com.xn.interfacetest.result.ReportResult;
import com.xn.interfacetest.util.RedisUtil;

import net.sf.json.JSONObject;


public class RedisAssertCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(RedisAssertCommand.class);
    private final static String separator = System.getProperty("line.separator", "\r\n");
    RedisUtil redisUtil = new RedisUtil(null,null);
    private List<KeyValueStore> redisParams;
    private Assert assertItem;
    private String key;
    private String redisMethod;

    public void setKey(String key) {
        this.key = key;
    }

    public void setRedisMethod(String redisMethod) {
        this.redisMethod = redisMethod;
    }

    public void setRedisParams(List<KeyValueStore> redisParams) {
        this.redisParams = redisParams;
    }

    public void setAssertItem(Assert assertItem) {
        this.assertItem = assertItem;
    }

    private String getValue(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return redisUtil.get(key);
    }

    private Long getTime(String key) {
        if (StringUtils.isEmpty(key)) {
            return Long.valueOf(-3);
        }
        return redisUtil.getTime(key);
    }

    private void doExecuteRedisAssert(List<KeyValueStore> redisParams) throws AssertNotEqualException {
        if (redisParams != null && redisParams.size() > 0) {
            String exact = null;
            Map<String, String> expectation = convertKeyValueStoreToMap(redisParams);
            try {

                if (redisMethod.equalsIgnoreCase("getValue")) {
                    String redisValue = getValue(key);
                    exact = redisValue;
                    logger.info("redis assert getValue method command<{}> is starting... ", expectation);
                    redisVerifyValue(expectation, redisValue);
                } else if (redisMethod.equalsIgnoreCase("getTime")) {
                    Long time = getTime(key);
                    exact = String.valueOf(time);
                    logger.info("redis assert getTime method command<{}> is starting... ", expectation);
                    redisVerifyTime(expectation, time);
                }

            } catch (AssertNotEqualException e) {
                assertItem.setResult("failed");
                String message = assertItem.getInterfaceName()+"/"+assertItem.getMethodName()+"/"+assertItem.getCaseName()+"====assert redis step invoke has error,expect=" + expectation + separator + "result=" + exact;
                logger.error(message, e);
                throw e;

            }
        }

    }

    private void redisVerifyTime(Map<String, String> expectation, Long exactTime) throws AssertNotEqualException {


        Long time = Long.valueOf(expectation.get("time"));
        if (time != exactTime) {
            ReportResult.failedPlus();
            AssertItem item = new AssertItem("redis.getTime", expectation.get("time"), String.valueOf(exactTime));
            assertItem.addDiff(item);
            throw new AssertNotEqualException("assert is not Equal");
        }
    }

    private void redisVerifyValue(Map<String, String> expected, String redisValue) throws AssertNotEqualException {
        if (expected.containsKey("valueEqual")) {
            String value = expected.get("valueEqual");
            if(value==null&&redisValue==null){
                return;
            }
            if (value==null||redisValue==null||!value.equals(redisValue)) {
                ReportResult.failedPlus();
                AssertItem item = new AssertItem(key, value, redisValue);
                assertItem.addDiff(item);
                throw new AssertNotEqualException("assert is not Equal");
            }else{
                return;
            }

        }
        if (!expected.isEmpty()) {
            if (redisValue != null) {

                JSONObject jsonObject = JSONObject.fromObject(redisValue);

                for (String key : expected.keySet()) {
                    String value = expected.get(key);
                    deepAssert(jsonObject, key, value, assertItem);
                }
            }
        }

    }

    @Override
    public void execute() {


    }

    @Override
    public void executeWithException() throws Exception {
        doExecuteRedisAssert(redisParams);
    }

    @Override
    public void executeWithException(Long reportId) throws Exception {

    }


}
