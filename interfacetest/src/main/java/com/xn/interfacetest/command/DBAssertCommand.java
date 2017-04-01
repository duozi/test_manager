package com.xn.interfacetest.command;
/**
 * Created by xn056839 on 2016/9/5.
 */

import com.xn.interfacetest.Exception.AssertNotEqualException;
import com.xn.interfacetest.dto.RelationAssertResultDto;
import com.xn.interfacetest.entity.RelationAssertResult;
import com.xn.interfacetest.response.Assert;
import com.xn.interfacetest.response.AssertItem;
import com.xn.interfacetest.result.ReportResult;
import com.xn.interfacetest.service.RelationAssertResultService;
import com.xn.interfacetest.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class DBAssertCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(DBAssertCommand.class);
    private final static String separator = System.getProperty("line.separator", "\r\n");

    static ApplicationContext ctx=new ClassPathXmlApplicationContext("/spring/spring-context.xml");
    static RelationAssertResultService relationAssertResultService=(RelationAssertResultService) ctx.getBean("relationAssertResultService");


    private Assert assertItem;
    private String sql;
    private String expectCount;
    private String name;
    private Long id;

    public void setName(String name) {
        this.name = name;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void setExpectCount(String expectCount) {
        this.expectCount = expectCount;
    }

    public void setAssertItem(Assert assertItem) {
        this.assertItem = assertItem;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private void doExecuteDBAssert(Long reportId) throws AssertNotEqualException {
        if (sql != null) {
            String exact = "";

            try {

                exact = DBUtil.getCountFromDB(name,sql);

                logger.info("DB assert getCount method command<{}> is starting... ", "sql=" + sql + ",count=" + expectCount);
                DBVerify(exact,reportId);


            } catch (AssertNotEqualException e) {
                assertItem.setResult("failed");
                String message = assertItem.getInterfaceName()+"/"+assertItem.getMethodName()+"/"+assertItem.getCaseName()+"====assert DB step invoke has error,sql="+sql+separator+"expect count=" + expectCount + separator + "exact count=" + exact;
                logger.error(message, e);
                throw e;

            }
        }

    }

    private void DBVerify(String exactCount, Long reportId) throws AssertNotEqualException {

        if (!exactCount.equals(expectCount)) {
            ReportResult.failedPlus();
            AssertItem item = new AssertItem("DB.getCount", expectCount, exactCount);
            assertItem.addDiff(item);
            //保存断言结果
            RelationAssertResultDto relationAssertResultDto = new RelationAssertResultDto();
            relationAssertResultDto.setDbAssertId(id);
            relationAssertResultDto.setReportId(reportId);
            relationAssertResultDto.setResult("assert is not Equal");
            relationAssertResultService.save(relationAssertResultDto);
            throw new AssertNotEqualException("assert is not Equal");
        }
    }


    @Override
    public void execute(Long caseId, Long interfaceId, Long planId,Long reportId) {

    }

    @Override
    public void execute()  {


    }

    @Override
    public void executeWithException(Long reportId) throws Exception {
        doExecuteDBAssert(reportId);
    }

    @Override
    public void executeWithException() throws Exception {

    }


}
