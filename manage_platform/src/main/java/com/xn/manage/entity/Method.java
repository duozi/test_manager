package com.xn.manage.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Method 实体类
 * 
 * @author shawn
 * @date 2016-12-22
 */
public class Method extends BaseEntity {

    /**
     * 主键列
     * 主键 
     */
    private Integer id;

    /**
     * 
     * 对应的接口id 
     */
    private Integer interfaceId;

    /**
     * 
     * 方法名称 
     */
    private String methodName;

    /**
     * 
     * 方法是否可用,1表示可用,0表示不可以用 
     */
    private Boolean methodStatus;

    /**
     * 
     * changeTime 
     */
    private Date changeTime;

    
    private List<Cases> casess = new ArrayList<Cases>();

    
    private Interface interfaceEntity;
   
	
	public Method(){
	    // default constructor
	}
    
    public Method(Integer id){
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setInterfaceId(Integer interfaceId) {
        this.interfaceId = interfaceId;
    }
    
    public Integer getInterfaceId() {
        return this.interfaceId;
    }
    
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    
    public String getMethodName() {
        return this.methodName;
    }
    
    public void setMethodStatus(Boolean methodStatus) {
        this.methodStatus = methodStatus;
    }
    
    public Boolean getMethodStatus() {
        return this.methodStatus;
    }
    
    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }
    
    public Date getChangeTime() {
        return this.changeTime;
    }
    

    
    public void setCasess(List<Cases> casess){
        this.casess = casess;
    }
    
    public List<Cases> getCasess() {
        return casess;
    }

    
    public void setInterface(Interface interfaceEntity){
        this.interfaceEntity = interfaceEntity;
    }
    
    public Interface getInterfaceEntity() {
        return interfaceEntity;
    }
}
