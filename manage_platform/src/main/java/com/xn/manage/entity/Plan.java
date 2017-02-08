package com.xn.manage.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Plan 实体类
 * 
 * @author Gaoshan
 * @date 2016-12-22
 */
public class Plan extends BaseEntity {

    /**
     * 主键列
     * 主键 
     */
    private Integer id;

    /**
     * 
     * 计划名称 
     */
    private String planName;

    /**
     * 
     * 计划的类型,0表示单接口,1表示串行接口 
     */
    private Boolean executeType;

    /**
     * 
     * 计划是否可用,1表示可用,0表示不可以用 
     */
    private Boolean planStatus;

    /**
     * 
     * changeTime 
     */
    private Date changeTime;

    /**
     * 
     * 计划是否发布的类型，0表示未发布，可以修改，1表示已发布不能修改 
     */
    private Boolean planType;

    /**
     * 
     * 计划下面的系统名，多个用，隔开 
     */
    private String systemName;

    
    private List<RedisProperties> redisPropertiess = new ArrayList<RedisProperties>();
    
    private List<System> systems = new ArrayList<System>();
    
    private List<DbProperties> dbPropertiess = new ArrayList<DbProperties>();

	
	public Plan(){
	    // default constructor
	}

    public Plan(Integer id,String planName){
        this.id = id;
        this.planName = planName;
    }

    public Plan(Integer id){
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getPlanName() {
        return this.planName;
    }
    
    public void setPlanName(String planName) {
        this.planName = planName;
    }
    
    public Boolean getExecuteType() {
        return this.executeType;
    }
    
    public void setExecuteType(Boolean executeType) {
        this.executeType = executeType;
    }
    
    public Boolean getPlanStatus() {
        return this.planStatus;
    }
    
    public void setPlanStatus(Boolean planStatus) {
        this.planStatus = planStatus;
    }
    
    public Date getChangeTime() {
        return this.changeTime;
    }
    
    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }
    
    public Boolean getPlanType() {
        return this.planType;
    }
    
    public void setPlanType(Boolean planType) {
        this.planType = planType;
    }
    
    public String getSystemName() {
        return this.systemName;
    }
    
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
    
    public List<RedisProperties> getRedisPropertiess() {
        return redisPropertiess;
    }
    
    public void setRedisPropertiess(List<RedisProperties> redisPropertiess){
        this.redisPropertiess = redisPropertiess;
    }
    
    public List<System> getSystems() {
        return systems;
    }
    
    public void setSystems(List<System> systems){
        this.systems = systems;
    }
    
    public List<DbProperties> getDbPropertiess() {
        return dbPropertiess;
    }
    
    public void setDbPropertiess(List<DbProperties> dbPropertiess){
        this.dbPropertiess = dbPropertiess;
    }

}
