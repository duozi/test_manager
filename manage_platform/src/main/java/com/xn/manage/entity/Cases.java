package com.xn.manage.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Cases 实体类
 * 
 * @author shawn
 * @date 2016-12-22
 */
public class Cases extends BaseEntity {

    /**
     * 主键列
     * 主键 
     */
    private Integer id;

    /**
     * 
     * 对应的方法id 
     */
    private Integer methodId;

    /**
     * 
     * case名称 
     */
    private String caseName;

    /**
     * 
     * case是否可用,1表示可用,0表示不可以用 
     */
    private Boolean caseStatus;

    /**
     * 
     * case的参数，用json表示 
     */
    private String param;

    /**
     * 
     * changeTime 
     */
    private Date changeTime;

    
    private List<DbAssert> dbAsserts = new ArrayList<DbAssert>();
    
    private List<DbOperation> dbOperations = new ArrayList<DbOperation>();
    
    private List<RedisOperation> redisOperations = new ArrayList<RedisOperation>();
    
    private List<ParameterAssert> parameterAsserts = new ArrayList<ParameterAssert>();
    
    private List<RedisAssert> redisAsserts = new ArrayList<RedisAssert>();

    
    private Method method;
   
	
	public Cases(){
	    // default constructor
	}
    
    public Cases(Integer id){
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setMethodId(Integer methodId) {
        this.methodId = methodId;
    }
    
    public Integer getMethodId() {
        return this.methodId;
    }
    
    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }
    
    public String getCaseName() {
        return this.caseName;
    }
    
    public void setCaseStatus(Boolean caseStatus) {
        this.caseStatus = caseStatus;
    }
    
    public Boolean getCaseStatus() {
        return this.caseStatus;
    }
    
    public void setParam(String param) {
        this.param = param;
    }
    
    public String getParam() {
        return this.param;
    }
    
    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }
    
    public Date getChangeTime() {
        return this.changeTime;
    }
    

    
    public void setDbAsserts(List<DbAssert> dbAsserts){
        this.dbAsserts = dbAsserts;
    }
    
    public List<DbAssert> getDbAsserts() {
        return dbAsserts;
    }
    
    public void setDbOperations(List<DbOperation> dbOperations){
        this.dbOperations = dbOperations;
    }
    
    public List<DbOperation> getDbOperations() {
        return dbOperations;
    }
    
    public void setRedisOperations(List<RedisOperation> redisOperations){
        this.redisOperations = redisOperations;
    }
    
    public List<RedisOperation> getRedisOperations() {
        return redisOperations;
    }
    
    public void setParameterAsserts(List<ParameterAssert> parameterAsserts){
        this.parameterAsserts = parameterAsserts;
    }
    
    public List<ParameterAssert> getParameterAsserts() {
        return parameterAsserts;
    }
    
    public void setRedisAsserts(List<RedisAssert> redisAsserts){
        this.redisAsserts = redisAsserts;
    }
    
    public List<RedisAssert> getRedisAsserts() {
        return redisAsserts;
    }

    
    public void setMethod(Method method){
        this.method = method;
    }
    
    public Method getMethod() {
        return method;
    }
}
