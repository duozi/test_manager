package com.xn.manage.entity;

import java.util.Date;

/**
 * ParameterAssert 实体类
 * 
 * @author shawn
 * @date 2016-12-22
 */
public class ParameterAssert extends BaseEntity {

    /**
     * 主键列
     * 主键 
     */
    private Integer id;

    /**
     * 
     * 所属的计划id 
     */
    private Integer caseId;

    /**
     * 
     * 参数校验是否可用，1表示可用，0表示不可用 
     */
    private Boolean parameterAssertStatus;

    /**
     * 
     * 参数校验内容，用json表示 
     */
    private String parameterAssertContent;

    /**
     * 
     * changeTime 
     */
    private Date changeTime;


    
    private Cases cases;
   
	
	public ParameterAssert(){
	    // default constructor
	}
    
    public ParameterAssert(Integer id){
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }
    
    public Integer getCaseId() {
        return this.caseId;
    }
    
    public void setParameterAssertStatus(Boolean parameterAssertStatus) {
        this.parameterAssertStatus = parameterAssertStatus;
    }
    
    public Boolean getParameterAssertStatus() {
        return this.parameterAssertStatus;
    }
    
    public void setParameterAssertContent(String parameterAssertContent) {
        this.parameterAssertContent = parameterAssertContent;
    }
    
    public String getParameterAssertContent() {
        return this.parameterAssertContent;
    }
    
    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }
    
    public Date getChangeTime() {
        return this.changeTime;
    }
    


    
    public void setCases(Cases cases){
        this.cases = cases;
    }
    
    public Cases getCases() {
        return cases;
    }
}
