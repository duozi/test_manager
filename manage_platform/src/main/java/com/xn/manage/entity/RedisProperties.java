
package com.xn.manage.entity;

import java.util.Date;

/**
 * RedisProperties 实体类
 * 
 * @author shawn
 * @date 2016-12-22
 */
public class RedisProperties extends BaseEntity {

    /**
     * 主键列
     * 主键 
     */
    private Integer id;

    /**
     * 
     * redis名称 
     */
    private String redisName;

    /**
     * 
     * 所属的计划id 
     */
    private Integer planeId;

    /**
     * 
     * 数据库配置是否可用，1表示可用，0表示不可用 
     */
    private Boolean redisStatus;

    /**
     * 
     * 连redis的host和port，用host:port表示,多个用，隔开 
     */
    private String redisHostPort;

    /**
     * 
     * 连接redis超时设置 
     */
    private String timeout;

    /**
     * 
     * redis重定向次数 
     */
    private String redirections;

    /**
     * 
     * changeTime 
     */
    private Date changeTime;


    
    private Plan plan;
   
	
	public RedisProperties(){
	    // default constructor
	}
    
    public RedisProperties(Integer id){
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setRedisName(String redisName) {
        this.redisName = redisName;
    }
    
    public String getRedisName() {
        return this.redisName;
    }
    
    public void setPlaneId(Integer planeId) {
        this.planeId = planeId;
    }
    
    public Integer getPlaneId() {
        return this.planeId;
    }
    
    public void setRedisStatus(Boolean redisStatus) {
        this.redisStatus = redisStatus;
    }
    
    public Boolean getRedisStatus() {
        return this.redisStatus;
    }
    
    public void setRedisHostPort(String redisHostPort) {
        this.redisHostPort = redisHostPort;
    }
    
    public String getRedisHostPort() {
        return this.redisHostPort;
    }
    
    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }
    
    public String getTimeout() {
        return this.timeout;
    }
    
    public void setRedirections(String redirections) {
        this.redirections = redirections;
    }
    
    public String getRedirections() {
        return this.redirections;
    }
    
    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }
    
    public Date getChangeTime() {
        return this.changeTime;
    }
    


    
    public void setPlan(Plan plan){
        this.plan = plan;
    }
    
    public Plan getPlan() {
        return plan;
    }
}
