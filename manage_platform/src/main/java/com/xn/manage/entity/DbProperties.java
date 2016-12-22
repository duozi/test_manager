
package com.xn.manage.entity;

import java.util.Date;

/**
 * DbProperties 实体类
 * 
 * @author shawn
 * @date 2016-12-22
 */
public class DbProperties extends BaseEntity {


    /**
     * 主键列
     * 主键 
     */
    private Integer id;

    /**
     * 
     * 数据库名称 
     */
    private String dbName;

    /**
     * 
     * 所属的计划id 
     */
    private Integer planeId;

    /**
     * 
     * 数据库配置是否可用，1表示可用，0表示不可用 
     */
    private Boolean dbStatus;

    /**
     * 
     * 连接数据库url 
     */
    private String url;

    /**
     * 
     * 连接数据库用户名 
     */
    private String userName;

    /**
     * 
     * 连接数据库密码 
     */
    private String pwd;

    /**
     * 
     * changeTime 
     */
    private Date changeTime;

    /**
     * 
     * 数据库驱动 
     */
    private String dbDriver;


    
    private Plan plan;
   
	
	public DbProperties(){
	    // default constructor
	}
    
    public DbProperties(Integer id){
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
    
    public String getDbName() {
        return this.dbName;
    }
    
    public void setPlaneId(Integer planeId) {
        this.planeId = planeId;
    }
    
    public Integer getPlaneId() {
        return this.planeId;
    }
    
    public void setDbStatus(Boolean dbStatus) {
        this.dbStatus = dbStatus;
    }
    
    public Boolean getDbStatus() {
        return this.dbStatus;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getUserName() {
        return this.userName;
    }
    
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
    public String getPwd() {
        return this.pwd;
    }
    
    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }
    
    public Date getChangeTime() {
        return this.changeTime;
    }
    
    public void setDbDriver(String dbDriver) {
        this.dbDriver = dbDriver;
    }
    
    public String getDbDriver() {
        return this.dbDriver;
    }
    


    
    public void setPlan(Plan plan){
        this.plan = plan;
    }
    
    public Plan getPlan() {
        return plan;
    }
}
