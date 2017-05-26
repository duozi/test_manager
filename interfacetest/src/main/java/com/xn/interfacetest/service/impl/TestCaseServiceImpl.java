/*
 * Copyright (c) 2014-2015, Yunnex and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.xn.interfacetest.service.impl;

import com.xn.common.base.CommonResult;
import com.xn.common.utils.*;
import com.xn.common.utils.FileUtil;
import com.xn.interfacetest.Enum.*;
import com.xn.interfacetest.api.*;
import com.xn.interfacetest.command.*;
import com.xn.interfacetest.dao.TestCaseMapper;
import com.xn.interfacetest.dto.*;
import com.xn.interfacetest.entity.RelationCaseDatabase;
import com.xn.interfacetest.entity.TestCase;
import com.xn.interfacetest.model.AssertKeyValueVo;
import com.xn.interfacetest.model.KeyValueStore;
import com.xn.interfacetest.response.Assert;
import com.xn.interfacetest.result.ReportResult;
import com.xn.interfacetest.singleton.InitThreadPool;
import com.xn.interfacetest.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.apache.poi.ss.usermodel.CellType.*;

/**
 * TestCase Service实现
 * 
 * @author Carol
 * @date 2017-02-14
 */
@Service
@Transactional
public class TestCaseServiceImpl implements TestCaseService {
    private static final Logger logger = LoggerFactory.getLogger(TestCaseServiceImpl.class);

    private static final String STRING_NAME = "java.lang.String";

    private static final String INTEGER_NAME = "java.lang.Integer";

    private static final String SHORT_NAME = "java.lang.Short";

    private static final String BYTE_NAME = "java.lang.Byte";

    private static final String LONG_NAME = "java.lang.Long";

    private static final String FLOAT_NAME = "java.lang.Float";

    private static final String DOUBLE_NAME = "java.lang.Double";

    private static final String CHARACTER_NAME = "java.lang.Character";

    private static final String BOOLEAN_NAME = "java.lang.Boolean";

    /**
     *  Dao
     */
    @Autowired
    private TestCaseMapper testCaseMapper;

    @Autowired
    private TestInterfaceService testInterfaceService;

    @Autowired
    private TestParamsService testParamsService;

    @Autowired
    private RelationCaseRedisService relationCaseRedisService;

    @Autowired
    private  RelationCaseDatabaseService relationCaseDatabaseService;

    @Autowired
    private  DataAssertService dataAssertService;

    @Autowired
    private  ParamsAssertService paramsAssertService;

    @Autowired
    private TestReportService testReportService;

    @Autowired
    private TestEnvironmentService testEnvironmentService;

    @Autowired
    private RelationServiceEnvironmentService relationServiceEnvironmentService;

    @Autowired
    private TestDatabaseConfigService testDatabaseConfigService;

    @Autowired
    private RelationCaseParamsService relationCaseParamsService;

    @Autowired
    private TestJarMethodService testJarMethodService;

    @Override
    @Transactional(readOnly = true)
    public TestCaseDto get(Object condition)
	{  
        TestCase testCase = testCaseMapper.get(condition);
        TestCaseDto testCaseDto = BeanUtils.toBean(testCase,TestCaseDto.class);
	    return testCaseDto;  
	}  

    @Override
    @Transactional(readOnly = true)
    public long count(TestCaseDto condition) {
        return testCaseMapper.count(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestCaseDto> list(TestCaseDto condition) {
        List<TestCase> list = testCaseMapper.list(condition);
        List<TestCaseDto> dtoList = CollectionUtils.transform(list, TestCaseDto.class);
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestCaseDto> list(Map<String,Object> condition) {
        List<TestCase> list = testCaseMapper.list(condition);
        List<TestCaseDto> dtoList = CollectionUtils.transform(list, TestCaseDto.class);
        return dtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResult<TestCaseDto> page(Map<String,Object> condition){
        return PageResult.wrap((PageInfo) condition.get("page"), list(condition));
    }

    @Override
    public TestCaseDto save(TestCaseDto testCaseDto) {
        TestCase testCase = BeanUtils.toBean(testCaseDto,TestCase.class);
        if(null == testCaseDto.getId()){
            testCaseMapper.save(testCase);
        } else {
            testCaseMapper.update(testCase);
        }

        testCaseDto.setId(testCase.getId());
        return testCaseDto;
    }

    @Override
    public int save(List<TestCaseDto> testCaseDtos) {
        if (testCaseDtos == null || testCaseDtos.isEmpty()) {
            return 0;
        }
        List<TestCase> testCases = CollectionUtils.transform(testCaseDtos, TestCase.class);
        return testCaseMapper.saveBatch(testCases);
    }

    @Override
    public int update(TestCaseDto testCaseDto) {
        TestCase testCase = BeanUtils.toBean(testCaseDto,TestCase.class);
        return testCaseMapper.update(testCase);
    }
    
    @Override
    public int deleteByPK(Long id) {
        return testCaseMapper.deleteByPK(id);
    }

    @Override
    public int delete(TestCaseDto testCaseDto) {
        TestCase testCase = BeanUtils.toBean(testCaseDto,TestCase.class);
        return testCaseMapper.delete(testCase);
    }
    
    @Override
    public int deleteBatchByPK(List<Long> ids) {
        return testCaseMapper.deleteBatchByPK(ids);
    }
    
    @Override
    public int deleteBatch(List<TestCaseDto> testCases) {
        return 0;
    }

    @Override
    public int updatePart(TestCaseDto testCaseDto) {
        TestCase testCase = BeanUtils.toBean(testCaseDto,TestCase.class);
        return testCaseMapper.updatePart(testCase);
    }

    @Override
    public PageResult<TestCaseDto> listByParams(Map<String, Object> params) {
        List<TestCase> list = testCaseMapper.listByParams(params);
        List<TestCaseDto> dtoList = CollectionUtils.transform(list, TestCaseDto.class);
        for(TestCaseDto testCaseDto: dtoList ){
            if(null != testCaseDto.getInterfaceId()){
                TestInterfaceDto testInterfaceDto = testInterfaceService.get(testCaseDto.getInterfaceId());
                testCaseDto.setInterfaceDto(testInterfaceDto);
            }
        }
        return PageResult.wrap((PageInfo) params.get("page"), dtoList);
    }

    @Override
    public List<TestCaseDto> listBySuitId(Long suitId) {
        List<TestCase> list = testCaseMapper.listBySuitId(suitId);
        List<TestCaseDto> dtoList = CollectionUtils.transform(list, TestCaseDto.class);
        return dtoList;
    }


    @Override
    public List<TestCaseDto> listAllBySuitList(List<TestSuitDto> testSuitDtoList) {
        List<TestCase> list = testCaseMapper.listAllBySuitList(testSuitDtoList);
        List<TestCaseDto> dtoList = CollectionUtils.transform(list, TestCaseDto.class);
        return dtoList;
    }

    @Override
    public void copyCase(Map<String, Object> params) {
        Long caseId = Long.parseLong((String)params.get("caseId"));

        //复制基础信息
       TestCase testCase = this.copyBaseInfo(caseId,(String) params.get("caseNum"));

        //被选择了要复制的部门的标志才会置为1
        testCase.setParamsAssert(0);
        logger.info("是否复制响应参数的断言" + params.get("dataAssert"));
        if("1".equals(params.get("dataAssert"))&& testCase.getParamsAssert() == 1){
            //复制响应参数的断言
            this.copyParamsAssert(caseId,testCase.getId());
            testCase.setParamsAssert(1);
        }

        logger.info("是否复制数据清除" + params.get("dataClear"));
        testCase.setDataClear(0);
        if("1".equals(params.get("dataClear"))  && testCase.getDataClear() == 1){
            //复制数据清除
            this.copyDataClear(caseId,testCase.getId());
            testCase.setDataClear(1);
        }

        logger.info("是否复制数据准备" + params.get("dataPrepare") );
        testCase.setDataPrepare(0);
        if("1".equals(params.get("dataPrepare"))  && testCase.getDataPrepare() == 1){
            //复制数据准备
            this.copyDataPrepare(caseId,testCase.getId());
            testCase.setDataPrepare(1);
        }
        testCaseMapper.update(testCase);

        logger.info("是否复制请求参数" + (params.get("dataParams")) );
        if("1".equals(params.get("dataParams"))){
            //复制请求参数
            this.copyDataParams(testCase,caseId,testCase.getId());
        }
    }

    @Override
    public List<TestCaseDto> getByCaseNum(String number) {
        List<TestCase> testCaseList = testCaseMapper.getByCaseNum(number);
        List<TestCaseDto> dtoList = CollectionUtils.transform(testCaseList, TestCaseDto.class);
        return dtoList;
    }

    @Override
    public List<TestCaseDto> listBySuitIdOrderByInterfaceId(Long suitId) {
        List<TestCase> testCaseList = testCaseMapper.listBySuitIdOrderByInterfaceId( suitId);
        List<TestCaseDto> dtoList = CollectionUtils.transform(testCaseList, TestCaseDto.class);
        if(null != dtoList && dtoList.size() > 0){
            for(TestCaseDto caseDto: dtoList){
                //查询接口信息
                caseDto.setInterfaceDto(testInterfaceService.get(caseDto.getInterfaceId()));
            }
        }
        return dtoList;
    }

    @Override
    public List<TestCaseDto> listAllOrderByInterface() {
        List<TestCase> testCaseList = testCaseMapper.listAllOrderByInterface();
        List<TestCaseDto> dtoList = CollectionUtils.transform(testCaseList, TestCaseDto.class);
        if(null != dtoList && dtoList.size() > 0){
            for(TestCaseDto caseDto: dtoList){
                //查询接口信息
                caseDto.setInterfaceDto(testInterfaceService.get(caseDto.getInterfaceId()));
            }
        }
        return dtoList;
    }

    @Override
    public StringBuffer dealWithExcelFile( String path) throws Exception {
        //解析文件
        return readExcel(path);
    }

    private StringBuffer readExcel(String path) throws Exception{
        // 对读取Excel表格标题测试
        InputStream excelFile = new FileInputStream(path);

        //定义一个字符串保存没有保存成功的用例，用以提示
        StringBuffer failCaseNumbers = new StringBuffer("");
        try {
            Workbook  wb = WorkbookFactory.create(new File(path));
            Sheet sheet = wb.getSheetAt(0);
            // 得到总行数
            int rowNum = sheet.getLastRowNum() + 1;
            logger.info("共计行数rowNum：" + rowNum);

            //取第1行
            Row row = sheet.getRow(0);
            int colNum = row.getPhysicalNumberOfCells(); //取第一行的列数作为列数
            logger.info("共计列数colNum：" + colNum);

            // 正文内容应该从第2行开始,第一行为表头的标题
            for (int i = 1; i < rowNum; i++) {
                TestCaseDto caseDto = new TestCaseDto();
                logger.info("开始插入第｛｝行用例" + i);
                row = sheet.getRow(i);
                //取每一个格子的值
                //第一个格子---用例编号
                String number = getCellFormatValue(row.getCell(0)) + "";
                //校验用例编号
                if(!checkCaseNumberUnique(number)){
                    failCaseNumbers.append(number).append("（用例编号与系统中已存在的用例重复）,");
                    continue;
                }
                caseDto.setNumber(number);

                //第二个格子---用例名称
                caseDto.setName(getCellFormatValue(row.getCell(1)) + "");

                //第三个格子---用例描述
                caseDto.setDescription(getCellFormatValue(row.getCell(2)) + "");

                //第四个格子---接口id,接口id为空或者接口id不存在的时候直接返回
                if(StringUtils.isBlank(getCellFormatValue(row.getCell(3)) + "") || !checkInterfaceIdExist(Long.parseLong(getCellFormatValue(row.getCell(3)) + ""))){
                    failCaseNumbers.append(number).append("（系统中不存在当前接口id）,");
                    continue;
                }
                caseDto.setInterfaceId(Long.parseLong(getCellFormatValue(row.getCell(3)) + ""));

                //第5个格子---自定义参数
                caseDto.setCustomParams(getCellFormatValue(row.getCell(4)) + "");

                //第6个格子---自定义参数类型
                caseDto.setCustomParamsType(AppendParamEnum.getIdByName(getCellFormatValue(row.getCell(5)) + ""));

                //10-用例类型
                if("SINGLE".equals(getCellFormatValue(row.getCell(9))) || "MUTIPLE".equals(getCellFormatValue(row.getCell(9)))){
                    caseDto.setType(getCellFormatValue(row.getCell(9)) + "");
                } else {
                    failCaseNumbers.append(number).append("（用例类型不合法，只能为\"MUTIPLE\"或者\"SINGLE\"）,");
                    continue;
                }


                caseDto  = this.save(caseDto);
                logger.info("保存用例：" + caseDto.toString());
                //第7个格子---参数断言
                String assertJson = getCellFormatValue(row.getCell(6)) + "";
                try {
                    //保存参数断言
                    saveParamsAsserts(assertJson,caseDto);
                }catch (Exception e){
                    logger.error("保存断言出现异常：" , e);
                    failCaseNumbers.append(number).append("(用例保存成功，但是断言保存异常,请进入用例详情重新配置),");
                }
                //8-数据准备
                String prepareStr = getCellFormatValue(row.getCell(7)) + "";
                if(StringUtils.isNotBlank(prepareStr)){
                    try {
                        saveDataOperate(prepareStr,caseDto.getId(),OperationTypeEnum.PREPARE.getId());
                        caseDto.setDataPrepare(1);
                        //更新用例
                        update(caseDto);
                    }catch (Exception e){
                        logger.error("保存sql出现异常：" , e);
                        failCaseNumbers.append(number).append("(用例保存成功，但是数据准备保存异常,请进入用例详情重新配置),");
                    }
                }

                //9-数据清除
                String clearStr = getCellFormatValue(row.getCell(8)) + "";
                if(StringUtils.isNotBlank(prepareStr)){
                    try {
                        saveDataOperate(clearStr,caseDto.getId(),OperationTypeEnum.CLEAR.getId());
                        caseDto.setDataClear(1);
                        //更新用例
                        update(caseDto);
                    }catch (Exception e){
                        logger.error("保存sql出现异常：" , e);
                        failCaseNumbers.append(number).append("(用例保存成功，但是数据清除保存异常,请进入用例详情重新配置),");
                    }
                }

            }
        } catch (FileNotFoundException e) {
            logger.error("用例excel文件未找到：" , e);
            throw e;
        } catch (IOException e) {
            logger.error("解析用例excel文件异常："  ,e);
            throw e;
        }finally {
            return failCaseNumbers;
        }
    }

    //保存用例的数据处理信息
    private void saveDataOperate(String operationStr, Long caseId, int operateType) throws Exception{
        logger.info("数据处理的类型是：" + OperationTypeEnum.getName(operateType) + ",读取到的值是：" + operationStr);
        //operationStr = "车行易数据库配置:select * from test_case;车行易数据库配置:select * from test_case;"
        String[] sqlStrArray = operationStr.split(";|；");
        for(String sqlStr : sqlStrArray){
            String[] sqlArray = sqlStr.split(":|：");
            RelationCaseDatabaseDto relationCaseDataBase = new RelationCaseDatabaseDto();
            relationCaseDataBase.setCaseId(caseId);
            relationCaseDataBase.setOperateType(operateType);
            relationCaseDataBase.setDatabaseName(sqlArray[0]);
            relationCaseDataBase.setSqlStr(sqlArray[1]);
            relationCaseDataBase.setType(2);//用例数据处理
            relationCaseDatabaseService.save(relationCaseDataBase);
        }

    }

    private boolean checkInterfaceIdExist(Long interfaceId) {
        TestInterfaceDto testInterfaceDto = testInterfaceService.get(interfaceId);
        if(null != testInterfaceDto){
            return true;
        }
        return false;
    }

    private void saveParamsAsserts(String assertJson, TestCaseDto caseDto) throws Exception{
        logger.info("读取到的断言json是：" + assertJson);
        //判断是否为空
        if (StringUtils.isNotBlank(assertJson)) {
            JSONObject jsonObject = JSONObject.fromObject(assertJson);
            Set set = jsonObject.entrySet();
            Iterator i = set.iterator();
            while (i.hasNext()){
                ParamsAssertDto paramsAssertDto = new ParamsAssertDto();
                paramsAssertDto.setCaseId(caseDto.getId());
                paramsAssertDto.setAssertParam(i.next().toString().split("=")[0]);
                paramsAssertDto.setRightValue(i.next().toString().split("=")[1]);
                paramsAssertService.save(paramsAssertDto);
            }
        }
    }

    private boolean checkCaseNumberUnique(String number) {
        List<TestCaseDto> testCaseList = this.getByCaseNum(number);
        if(null != testCaseList && testCaseList.size()>0){
            return false;
        }
        return true;
    }

    /**
     * 根据Cell类型设置数据
     * @param cell
     * @return
     */
    private Object getCellFormatValue(Cell cell) {
        DataFormatter formatter = new DataFormatter();
        switch (cell.getCellTypeEnum()) {
            case STRING:
                return cell.getRichStringCellValue().getString();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    return Math.round(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }

    }

    private void copyDataParams(TestCase testCase, Long caseId, Long newCaseId) {
        //判断是自定义参数还是配置的参数
        if(testCase.getParamsType() == ParamsGroupTypeEnum.KEY.getId()){
            //不是自定义参数就需要复制参数表的该用例的字段
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("caseId",caseId);
            params.put("idDelete",0);
            List<RelationCaseParamsDto> relationCaseParamsDtoList = relationCaseParamsService.list(params);
            if(null != relationCaseParamsDtoList && relationCaseParamsDtoList.size() > 0 ){
                logger.info("有参数｛｝个" + relationCaseParamsDtoList.size());
                for(RelationCaseParamsDto relationCaseParams : relationCaseParamsDtoList){
                    relationCaseParams.setId(null);
                    relationCaseParams.setIsDelete(0);
                    relationCaseParams.setCaseId(newCaseId);
                    relationCaseParamsService.save(relationCaseParams);
                }
            }
        }

    }

    //数据准备
    private void copyDataPrepare(Long caseId, Long newCaseId) {
        copyDataOperate( caseId,  newCaseId, OperationTypeEnum.PREPARE.getId());
    }

    //数据清除
    private void copyDataClear(Long caseId, Long newCaseId) {
        copyDataOperate( caseId,  newCaseId, OperationTypeEnum.CLEAR.getId());
    }

    //复制响应参数断言
    private void copyParamsAssert(Long caseId, Long newCaseId) {
        List<ParamsAssertDto> paramsAssertDtoList = paramsAssertService.getByCaseId(caseId);
        if(null != paramsAssertDtoList && paramsAssertDtoList.size() > 0){
            logger.info("有参数断言｛｝个" + paramsAssertDtoList.size());
            for(ParamsAssertDto paramsAssertDto:paramsAssertDtoList){
                paramsAssertDto.setId(null);
                paramsAssertDto.setCaseId(newCaseId);
                paramsAssertDto.setIsDelete(0);
                paramsAssertService.save(paramsAssertDto);
            }
        }
    }

    //复制基础信息
    private TestCase copyBaseInfo(Long caseId,String caseNum) {
        TestCase testCase = testCaseMapper.get(caseId);
        //把id设置为空，就可以新增一个参数值都一样的用例了
        testCase.setId(null);
        testCase.setIsDelete(0);
        testCase.setStatus(0);
        testCase.setNumber(caseNum);
        testCaseMapper.save(testCase);//保存完了就有用例id写入了testCase
        logger.info("复制之后新的用例信息：" + testCase.toString());
        return testCase;
    }

    //复制数据处理
    private void copyDataOperate(Long caseId, Long newCaseId,Integer operateType){
        //数据库
        List<RelationCaseDatabaseDto> relationCaseDatabaseList = relationCaseDatabaseService.getByCaseIdAndOperateType(caseId,operateType);
        if(null != relationCaseDatabaseList && relationCaseDatabaseList.size() > 0) {
            logger.info("有数据库操作｛｝个" + relationCaseDatabaseList.size());
            for(RelationCaseDatabaseDto relationCaseDatabaseDto : relationCaseDatabaseList){
                relationCaseDatabaseDto.setId(null);
                relationCaseDatabaseDto.setIsDelete(0);
                relationCaseDatabaseDto.setCaseId(newCaseId);
                relationCaseDatabaseService.save(relationCaseDatabaseDto);
            }
        }

        //redis
        List<RelationCaseRedisDto> relationCaseRedisList = relationCaseRedisService.getByCaseIdAndOperateType(caseId,operateType);
        if(null != relationCaseRedisList && relationCaseRedisList.size() > 0) {
            logger.info("有redis操作｛｝个" + relationCaseRedisList.size());
            for(RelationCaseRedisDto relationCaseRedisDto : relationCaseRedisList){
                relationCaseRedisDto.setId(null);
                relationCaseRedisDto.setIsDelete(0);
                relationCaseRedisDto.setCaseId(newCaseId);
                relationCaseRedisService.save(relationCaseRedisDto);
            }
        }
    }

    @Override
    public void excuteCaseList(List<TestCaseDto> testCaseDtoList, TestEnvironmentDto testEnvironmentDto, Long planId, TestReportDto testReportDto, TestSuitDto suitDto) throws Exception{
        ReportResult.getReportResult().setTotal(ReportResult.getReportResult().getTotal() + testCaseDtoList.size());
        excute( testCaseDtoList,testEnvironmentDto,planId, testReportDto,suitDto);

    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    private void excute(final List<TestCaseDto> testCaseDtoList, final TestEnvironmentDto testEnvironmentDto, final Long planId, final TestReportDto testReportDto, final TestSuitDto suitDto){
        logger.info("==========线程池执行测试用例========");
        //创建一个线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(10);;
        //遍历执行测试用例
        for(int i = 0; i < testCaseDtoList.size(); i++){
            final int finalI = i;
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        logger.info("========执行线程" + finalI);
                        excuteCase( testCaseDtoList.get(finalI),testEnvironmentDto,planId,testReportDto,suitDto);
                    } catch (Exception e) {
                        logger.error("多线程执行用例异常：",e);
                    }

                }
            });
        }



        try {
            logger.info("sleep-----"+1000);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.info("InterruptedException-----"+e.getMessage());
        }

        threadPool.shutdown();
        while (true) {
            if (threadPool.isTerminated()) {
                break;
            }
        }
    }
    @Override
    public void testRun(Long caseId, Long environmentId) throws Exception{
        //判断是dubbo接口还是http接口
        logger.info("==========联调测试用例========");
        TestInterfaceDto testInterfaceDto = testInterfaceService.getByCaseId(caseId);
        if(null != testInterfaceDto){
            if(InterfaceTypeEnum.HTTP.getId() == testInterfaceDto.getType()){
                testRunHttp(testInterfaceDto,caseId,environmentId);
            } else if(InterfaceTypeEnum.DUBBO.getId() == testInterfaceDto.getType()){
                testRunDubbo(testInterfaceDto,caseId,environmentId);
            }
        }
    }

    @Override
    public List<TestCaseDto> getByCaseIds(String caseIds) {
        String[] ids = caseIds.split(",|，");
        List<TestCase> testCaseList = testCaseMapper.getByCaseIds(ids);
        List<TestCaseDto> dtoList = CollectionUtils.transform(testCaseList, TestCaseDto.class);
        return dtoList;
    }

    @Override
    public void changeStatusList(int status, List<TestCaseDto> testCaseDtoList) {
        testCaseMapper.changeStatusList( status, testCaseDtoList);
    }

    /**
     * dubbo接口联调
     * @param testInterfaceDto
     * @param caseId
     * @param environmentId
     */
    private void testRunDubbo(TestInterfaceDto testInterfaceDto, Long caseId, Long environmentId) {
        logger.info("==========联调测试用例DUBBO========");
        TestCaseDto testCaseDto = this.get(caseId);
        TestEnvironmentDto testEnvironmentDto = testEnvironmentService.get(environmentId);
        this.excuteDubbo(testCaseDto,testInterfaceDto,testEnvironmentDto,null,null);
    }

    /**
     * http接口联调
     * @param testInterfaceDto
     * @param caseId
     * @param environmentId
     */
    private void testRunHttp(TestInterfaceDto testInterfaceDto, Long caseId, Long environmentId)throws Exception {
        logger.info("==========联调测试用例HTTP========");
        TestCaseDto testCaseDto = this.get(caseId);
        TestEnvironmentDto testEnvironmentDto = testEnvironmentService.get(environmentId);
        this.excuteHttp(testCaseDto,testInterfaceDto,testEnvironmentDto,null,null,null);
    }

    /**
     * 执行测试用例
     * @param caseDto 用例
     * @param testEnvironmentDto 执行环境
     * @param testReportDto
     */
//    @Transactional(propagation = TransactionDefinition.ISOLATION_READ_UNCOMMITTED)
    private  void excuteCase(TestCaseDto caseDto, TestEnvironmentDto testEnvironmentDto, Long planId, TestReportDto testReportDto, TestSuitDto suitDto) throws Exception {
        logger.info("执行测试用例：" +  caseDto.getId() + "-" + caseDto.getName());
        logger.info("开始执行测试用例的时候reportResult的值：" +  ReportResult.getReportResult().toString());

        //查询用例所属接口的基本配置
        logger.info("==========查询用例id=" + caseDto.getId() + "所属接口信息========");
        TestInterfaceDto interfaceDto = testInterfaceService.getByCaseId(caseDto.getId());
        if(null != interfaceDto){
            if(InterfaceTypeEnum.DUBBO.getId() == interfaceDto.getType()){
                //dubbo接口
                this.excuteDubbo(caseDto,interfaceDto,testEnvironmentDto,planId,testReportDto);
            } else if(InterfaceTypeEnum.HTTP.getId() == interfaceDto.getType()){
                //http接口
                this.excuteHttp(caseDto,interfaceDto,testEnvironmentDto,planId,testReportDto,suitDto);
            }
        }

    }

    /**
     * http接口的用例执行方法
     * @param caseDto
     * @param interfaceDto
     * @param testEnvironmentDto
     */
    private void excuteHttp(TestCaseDto caseDto, TestInterfaceDto interfaceDto, TestEnvironmentDto testEnvironmentDto,Long planId,TestReportDto testReportDto,TestSuitDto suitDto) throws Exception{
        Long caseId = caseDto.getId(); //用例id
        Long interfaceId = interfaceDto.getId();//接口id
        Long environmentId = testEnvironmentDto.getId();//环境id
        Long reportId = null != testReportDto?testReportDto.getId():null;
        logger.info("==========http接口用例执行:用例id｛｝接口id｛｝环境id｛｝========" + caseId + "," + interfaceId + "," + environmentId);
        //初始化数据库，先把所有的数据库连接
        boolean flag = DBUtil.getDBInit(environmentId,caseId);

        if(!flag){
            logger.info("数据库初始化失败！" );
            return;
        }

        TestCaseCommand testCaseCommand = new TestCaseCommand();
        //进行执行用例之前的初始化工作
        testCaseCommand.setBeforeCommand(getBeforeCommand(testEnvironmentDto,caseDto));
        //数据清除
        testCaseCommand.setAfterCommand(getAfterCommand(testEnvironmentDto,caseDto));
        //断言初始化
        testCaseCommand.setAssertCommand(getAssertCommand(caseDto, interfaceDto,reportId));

        //========================发送http请求初始化===============================
        //取超时时间---默认200000
        String timeout = "200000";
        //请求方式get/post
        String requestType = RequestTypeEnum.getName(interfaceDto.getRequestType());
        //协议类型
        String propType = HttpTypeEnum.getName(interfaceDto.getProtocolType());
        //取http接口的url
        String url = interfaceDto.getUrl();
        //contentType
        String contentType = interfaceDto.getContentType();
        logger.info("contentType:" + contentType);
        //http请求的服务器地址
        RelationServiceEnvironmentDto relationServiceEnvironmentDto = relationServiceEnvironmentService.getByCaseAndEnvironment(interfaceDto.getServiceId(),environmentId);
        if(null != relationServiceEnvironmentDto){
            url = propType + "://" + relationServiceEnvironmentDto.getIpAddress() + ":" + relationServiceEnvironmentDto.getHttpPort() + "/" + url;
        }
        logger.info("url:" + url);
        //请求参数处理9
        String paramsStr  = formatParams(caseDto,contentType,interfaceDto);
        logger.info("paramsStr:" + paramsStr);

        testCaseCommand.setCaseCommand(getCaseCommand(requestType,url,paramsStr,contentType,timeout,propType, caseDto, interfaceDto, planId, reportId, suitDto));

        //执行测试用例
        testCaseCommand.execute();

        if (flag) {
            DBUtil.DBClose();
        }
    }

    //初始化用例的执行
    private CaseCommand getCaseCommand(String requestType,String url,String paramsStr,String contentType,String timeout,String propType,
                                       TestCaseDto caseDto,TestInterfaceDto interfaceDto,Long planId,Long reportId,TestSuitDto suitDto) {
        return new HttpCaseCommand(requestType,url,paramsStr,contentType,timeout,propType, caseDto, interfaceDto, planId, reportId, suitDto);
    }

    //初始化字段校验
    private AssertCommand getAssertCommand(TestCaseDto caseDto, TestInterfaceDto interfaceDto,Long reportId) {
        Assert assertItem = new Assert(interfaceDto,caseDto);
        List<AssertKeyValueVo> paralist = new ArrayList();
       // List<KeyValueStore> redislist = new ArrayList();

        List<Command> dbAssertCommandList = new ArrayList();
        ParaAssertCommand paraAssertCommand=null;
        DBAssertCommand dbAssertCommand = null;

        //获取断言信息
        //1.数据库断言
        List<DataAssertDto> dataAssertDtoList = dataAssertService.getByCaseId(caseDto.getId());
        if(null != dataAssertDtoList && dataAssertDtoList.size() > 0){
            for(DataAssertDto dataAssert:dataAssertDtoList){
                dbAssertCommand =  new DBAssertCommand();
                //通过数据库配置名称拿到具体的数据库名称
                TestDatabaseConfigDto testDatabaseConfigDto = testDatabaseConfigService.getByName(dataAssert.getDatabaseName());
                if(null != testDatabaseConfigDto){
                    logger.info("通过数据库配置名拿到的数据库配置：" +testDatabaseConfigDto.toString());
                    dbAssertCommand.setName(testDatabaseConfigDto.getDatabaseName());
                }
                dbAssertCommand.setSql(dataAssert.getSqlStr());
                dbAssertCommand.setAssertItem(assertItem);
                dbAssertCommand.setId(dataAssert.getId());
                dbAssertCommandList.add(dbAssertCommand);
                logger.info("数据库断言内容：" + dbAssertCommand.toString());
            }
        }

//        //2.redis断言-----暂时不做
//        List<Command> redisAssertCommandList = new ArrayList();
//        List<RedisAssertDto> redisAssertDtoList = redisAssertService.getByCaseId(caseDto.getId());

        //3.响应字段断言
        List<ParamsAssertDto> paramsAssertDtoList = paramsAssertService.getByCaseId(caseDto.getId());
        if(null != paramsAssertDtoList && paramsAssertDtoList.size() > 0){
            for(ParamsAssertDto paramsDto : paramsAssertDtoList){
                AssertKeyValueVo keyValueStore = new AssertKeyValueVo(paramsDto.getAssertParam(),paramsDto.getRightValue(),paramsDto.getId());
                paralist.add(keyValueStore);
                logger.info("响应字段断言内容：" + keyValueStore.toString());
            }
            paraAssertCommand = new ParaAssertCommand(paralist);
            paraAssertCommand.setAssertItem(assertItem);
        }
        AssertCommand assertCommand = new AssertCommand(paraAssertCommand, null, dbAssertCommandList, assertItem,reportId);
        return assertCommand;

    }

    //初始化执行用例之后要做的事情
    private List<Command> getAfterCommand(TestEnvironmentDto testEnvironmentDto, TestCaseDto testcaseDto) {
        List<Command> list = new ArrayList();
        Long caseId = testcaseDto.getId(); //用例id
        Long environmentId = testEnvironmentDto.getId();//环境id

        //初始化redis
        RedisUtil redisUtil = new RedisUtil(caseId,environmentId);

        //数据准备
        if(null != testcaseDto.getDataClear() && testcaseDto.getDataClear() > 0) {
            //数据准备---查询数据库准备
            List<RelationCaseDatabaseDto> relationCaseDatabaseDtoList = relationCaseDatabaseService.getByCaseIdAndOperateType(caseId, OperationTypeEnum.CLEAR.getId());
            if (null != relationCaseDatabaseDtoList && relationCaseDatabaseDtoList.size() > 0) {
                for (RelationCaseDatabaseDto relationCaseDatbase : relationCaseDatabaseDtoList) {
                    //执行数据准备的sql
                    logger.info("==========数据准备执行sql：" + relationCaseDatbase.getSqlStr());
                    //通过数据库配置名称拿到具体的数据库名称
                    TestDatabaseConfigDto testDatabaseConfigDto = testDatabaseConfigService.getByName(relationCaseDatbase.getDatabaseName());
                    if(null != testDatabaseConfigDto) {
                        logger.info("通过数据库配置名拿到的数据库配置：" +testDatabaseConfigDto.toString());
                        list.add(new DBCommand(testDatabaseConfigDto.getDatabaseName(), relationCaseDatbase.getSqlStr()));
                    }
                }
            }
            //数据准备---redis准备
            List<RelationCaseRedisDto> relationCaseRedisDtoList = relationCaseRedisService.getByCaseIdAndOperateType(caseId, OperationTypeEnum.CLEAR.getId());
            if(null != relationCaseRedisDtoList && relationCaseRedisDtoList.size() > 0 ){
                for(RelationCaseRedisDto relationCaseRedisDto : relationCaseRedisDtoList){
                    RedisCommand redisCommand = new RedisCommand();
                    redisCommand.setKey(relationCaseRedisDto.getKey());
                    redisCommand.setMethodName(RedisOperationTypeEnum.getName(relationCaseRedisDto.getRedisOperateType()));
                    redisCommand.setTime(relationCaseRedisDto.getTime());
                    redisCommand.setValue(relationCaseRedisDto.getValue());
                    list.add(redisCommand);
                }
            }
        }
        return list;
    }

    //初始化执行用例之前要做的事情
    private List<Command> getBeforeCommand(TestEnvironmentDto testEnvironmentDto,TestCaseDto testcaseDto) {
        List<Command> list = new ArrayList();

        Long caseId = testcaseDto.getId(); //用例id
        Long environmentId = testEnvironmentDto.getId();//环境id

        //初始化redis
        RedisUtil redisUtil = new RedisUtil(caseId,environmentId);

        //数据准备
        if(null != testcaseDto.getDataPrepare() && testcaseDto.getDataPrepare() > 0) {
            //数据准备---查询数据库准备
            List<RelationCaseDatabaseDto> relationCaseDatabaseDtoList = relationCaseDatabaseService.getByCaseIdAndOperateType(caseId, OperationTypeEnum.PREPARE.getId());
            if (null != relationCaseDatabaseDtoList && relationCaseDatabaseDtoList.size() > 0) {
                for (RelationCaseDatabaseDto relationCaseDatbase : relationCaseDatabaseDtoList) {
                    //执行数据准备的sql
                    logger.info("==========数据准备执行sql：" + relationCaseDatbase.getSqlStr());
                    //通过数据库配置名称拿到具体的数据库名称
                    TestDatabaseConfigDto testDatabaseConfigDto = testDatabaseConfigService.getByName(relationCaseDatbase.getDatabaseName());
                    if(null != testDatabaseConfigDto){
                        list.add(new DBCommand(testDatabaseConfigDto.getDatabaseName(), relationCaseDatbase.getSqlStr()));
                    }
                }
            }
            //数据准备---redis准备
            List<RelationCaseRedisDto> relationCaseRedisDtoList = relationCaseRedisService.getByCaseIdAndOperateType(caseId, OperationTypeEnum.PREPARE.getId());
            if(null != relationCaseRedisDtoList && relationCaseRedisDtoList.size() > 0 ){
                for(RelationCaseRedisDto relationCaseRedisDto : relationCaseRedisDtoList){
                    RedisCommand redisCommand = new RedisCommand();
                    redisCommand.setKey(relationCaseRedisDto.getKey());
                    redisCommand.setMethodName(RedisOperationTypeEnum.getName(relationCaseRedisDto.getRedisOperateType()));
                    redisCommand.setTime(relationCaseRedisDto.getTime());
                    redisCommand.setValue(relationCaseRedisDto.getValue());
                    list.add(redisCommand);
                }
            }
        }

      return list;
    }

    private String formatParams(TestCaseDto caseDto, String contentType,TestInterfaceDto interfaceDto) {
        //参数,如果没有配置自定义类型就说明是配置了参数,如果配置了就取自定义参数
        StringBuffer paramsStr = new StringBuffer("");
        if(null != caseDto.getParamsType() && ParamsGroupTypeEnum.KEY.getId() == caseDto.getParamsType()){
            //获取参数列表
            List<ParamDto> testParamsDtoList = testParamsService.listByCaseIdFromRelation(caseDto.getId());
            if(contentType.equals("application/json")){
                logger.info("非自定义参数，转json，共"+testParamsDtoList.size() + "个参数");
                //将参数转化为json字符串类型
                if(null != testParamsDtoList && testParamsDtoList.size() > 0){
                    JSONObject jsonObject = new JSONObject();
                    Iterator iterator = testParamsDtoList.iterator();
                    //时间戳类型则转化为时间戳---保证是同一个时间戳
                    Date date = new Date();

                    while(iterator.hasNext()){
                        ParamDto param = (ParamDto) iterator.next();
                        logger.info("参数：" + param.toString());
                        String value = param.getValue();
                        //如果是需要加密的参数就调用
                        if(param.getFormatType() == ParamFormatTypeEnum.ENCRYPT.getId()){
                            //是加密的参数就先进行加密,加密的参数的值是加密的方法名
                            value = encrypt(interfaceDto.getJarPath(),interfaceDto,caseDto.getId(),param.getMethodName(),date);
                            logger.info("加密参数是：｛｝加密之后的值是：｛｝" + param.getName() + "," + value);
                        } else if(value.equals("timestamp")){
                            //时间戳类型则转化为时间戳
                            value = (date.getTime()) + "";
                        }
                        jsonObject.put(param.getName(),value);
                    }
                    paramsStr = paramsStr.append(jsonObject.toString());
                    logger.info("加密之后的所有参数："+paramsStr);
                }

            } else {
                logger.info("非自定义参数，转&拼接参数："+testParamsDtoList.size() + "个参数");
                //将参数转化为字符串类型
                if(null != testParamsDtoList && testParamsDtoList.size() > 0){
                    Iterator iterator = testParamsDtoList.iterator();
                    //时间戳类型则转化为时间戳---保证是同一个时间戳
                    Date date = new Date();
                    while(iterator.hasNext()){
                        ParamDto param = (ParamDto) iterator.next();
                        String value = param.getValue();
                        //如果是需要加密的参数就调用
                        if(param.getFormatType() == ParamFormatTypeEnum.ENCRYPT.getId()){
                            //是加密的参数就先进行加密,加密的参数的值是加密的方法名
                            value = encrypt(interfaceDto.getJarPath(),interfaceDto,caseDto.getId(),param.getMethodName(),date);
                            logger.info("加密参数是：｛｝加密之后的值是：｛｝" + param.getName() + "," + value);
                        }else if(value.equals("timestamp")){
                            //时间戳类型则转化为时间戳
                            value = (date.getTime()) + "";
                        }
                        paramsStr.append(param.getName() + "=" + param.getValue());
                        paramsStr.append("&");
                    }
                    paramsStr = new StringBuffer(paramsStr.substring(0,paramsStr.lastIndexOf("&")));
                }
            }
        } else if(null != caseDto.getParamsType() && ParamsGroupTypeEnum.CUSTOM.getId() == caseDto.getParamsType()){
            //不处理加密
            logger.info("自定义参数："+caseDto.getCustomParams());
            paramsStr = new StringBuffer(caseDto.getCustomParams());
        }

        return paramsStr.toString();
    }

    public String encrypt(String path, TestInterfaceDto interfaceDto,Long caseId,String methodName,Date date){
        //查询出接口对应的加密方法和参数列表
        TestJarMethodDto testJarMethodDto = testJarMethodService.getByMethodNameAndInterfaceId(methodName,interfaceDto.getId());
        if(null == testJarMethodDto){
            return null;
        }

        StringBuffer value = new StringBuffer("");
        //取出需要用到的类、方法名称、参数关系
        try {
            String className = testJarMethodDto.getClassName();
            String paramsTypes =testJarMethodDto.getParamsTypes();//如：String,int,double
            String paramsValues =testJarMethodDto.getParamsValues();//如：?{appid},123,4556或者appid=?{appid}&req=123,ddddd

            logger.info("开始加密的方法中初始化参数类型------");
            //将参数类型加入参数列表
            String[] typesArray = paramsTypes.split(",|，");
            Class<?>[] types = new Class[typesArray.length];
            for(int i=0;i<types.length;i++){
                //将参数类型替换成完整的参数类型名称
                String type = getTypeFullName(typesArray[i]);
                logger.info("加密的方法中初始化参数类型------" +type);
                types[i] = Class.forName(type);
            }

            StringBuffer paramsValuesNew = new StringBuffer("");
            logger.info("开始加密的方法中初始化参数值------，将问号参数值指定具体的值");
            String[] values = paramsValues.split(",|，");
            for(String oValue : values){
                //处理组合参数,例如：appId=?&nonce=56412&reqId=timestamp&timestamp=timestamp
                logger.info("参数值：" + oValue);
                //多个参数和值的组合组成一个jar包的参数---appid=?{appid}&req=123
                if(oValue.contains("&")){
                    String[] valueItemArray = oValue.split("&");
                    for(int i =0;i<valueItemArray.length;i++ ){
                        String itemValue = valueItemArray[i];
                        logger.info("目前处理的参数和值：" + itemValue);
                        //处理不指明参数值的参数
                        if(itemValue.contains("?")){
                            String[] valueItem = itemValue.split("=");//拿到的值会是valueItem[0] = "appid"，valueItem[1] = "?{appid}"
                            //对valueItem[1] = "?{appid}"解析出参数名称
                            String valueName = valueItem[1].substring(2,valueItem[1].length()-1);
                            //拿到用例中的该参数的值
                            RelationCaseParamsDto relationCaseParams = relationCaseParamsService.getByCaseIdAndParamName(valueName,caseId,0);
                            if(null != relationCaseParams){
                                //paramsValues = paramsValues.replace(itemValue,valueItem[0] + "=" + relationCaseParams.getValue());
                                paramsValuesNew.append(valueItem[0]).append("=").append(relationCaseParams.getValue());
                            }
                        }else {
                            paramsValuesNew.append(itemValue);
                        }
                        //如果是最后一个参数，那么就不加“&”
                        if(i < valueItemArray.length-1){
                            paramsValuesNew.append("&");
                        }
                    }
                } else if(oValue.contains("?")){
                    //单个参数，并且取当前用例中的值做jar包的参数----例如：?{appid}
                    //参数名称
                    String valueName = oValue.substring(2,oValue.length()-1);;

                    //拿到用例中的该参数的值
                    RelationCaseParamsDto relationCaseParams = relationCaseParamsService.getByCaseIdAndParamName(valueName,caseId,0);
                    if(null != relationCaseParams){
                        //paramsValues = paramsValues.replace(oValue, relationCaseParams.getValue());
                        paramsValuesNew.append(relationCaseParams.getValue());
                    } else {
                        //为空说明，这个参数没有值，那么传空
                        //paramsValues = paramsValues.replace(oValue, "");
                        paramsValuesNew.append("");
                    }
                } else {
                    paramsValuesNew.append(oValue);
                }
                paramsValuesNew.append(",");
            }

            logger.info("新的参数串为：" + paramsValuesNew.toString());
            //将参数中的时间戳替换为真的时间戳
            String timestamp = "=" + date.getTime();
            //替换了不固定值的参数值之后再对参数进行转换
            Object[] valusObject = paramsValuesNew.toString().replaceAll("=timestamp",timestamp).split(",|，");
            //调用jar包进行加密
            value = JarUtil.signature (path, className, methodName,types, valusObject);
        } catch (Exception e) {
            logger.error("类型未找到：" + e);
        }
        return (null == value?null:value.toString());
    }

    private static String getTypeFullName(String s) {
        if(s.equalsIgnoreCase("string")){
            return STRING_NAME;
        } else if(s.equalsIgnoreCase("integer") || s.equalsIgnoreCase("int")){
            return INTEGER_NAME;
        } else if(s.equalsIgnoreCase("short")){
            return SHORT_NAME;
        } else if(s.equalsIgnoreCase("byte")){
            return BYTE_NAME;
        } else if(s.equalsIgnoreCase("long")){
            return LONG_NAME;
        } else if(s.equalsIgnoreCase("double")){
            return DOUBLE_NAME;
        } else if(s.equalsIgnoreCase("float")){
            return FLOAT_NAME;
        } else if(s.equalsIgnoreCase("boolean")){
            return BOOLEAN_NAME;
        } else {
            return STRING_NAME;
        }
    }

    /**
     * dubbo接口的用例执行方法
     * @param caseDto
     * @param interfaceDto
     * @param testEnvironmentDto
     * @param testReportDto
     */
    private void excuteDubbo(TestCaseDto caseDto, TestInterfaceDto interfaceDto, TestEnvironmentDto testEnvironmentDto, Long planId, TestReportDto testReportDto) {
        logger.info("==========dubbo接口用例执行:用例id｛｝接口id｛｝环境id｛｝========" + caseDto.getId() + "," + interfaceDto.getId() + "," + testEnvironmentDto.getId());



    }


    public static void main(String[] s) {
        JSONObject jsonObject = JSONObject.fromObject("{\"userName\":\"test\",\"password\":\"123456\"}");
        Set set = jsonObject.entrySet();
        Iterator i = set.iterator();
        while (i.hasNext()){
            System.out.println(i.next().toString().split("=")[0]);
        }
        System.out.println(set.size());
    }
}
