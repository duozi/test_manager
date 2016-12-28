package com.xn.manage.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Interface 实体类
 * 
 * @author Carol
 * @date 2016-12-22
 */
public class Interface extends BaseEntity {

    /**
     * 主键列
     * 主键 
     */
    private Integer id;

    /**
     * 
     * 对应的系统id 
     */
    private Integer systemId;

    /**
     * 
     * 接口名称 
     */
    private String interfaceName;

    /**
     * 
     * 接口的类型,0表示http,1表示dubbo 
     */
    private Boolean interfaceType;

    /**
     * 
     * 接口是否可用,1表示可用,0表示不可以用 
     */
    private Boolean interfaceStatus;

    /**
     * 
     * changeTime 
     */
    private Date changeTime;

    
    private List<DubboServiceProperties> dubboServicePropertiess = new ArrayList<DubboServiceProperties>();
    
    private List<HttpServiceProperties> httpServicePropertiess = new ArrayList<HttpServiceProperties>();
    
    private List<Method> methods = new ArrayList<Method>();

    
    private System system;
   
	
	public Interface(){
	    // default constructor
	}
    
    public Interface(Integer id){
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }
    
    public Integer getSystemId() {
        return this.systemId;
    }
    
    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }
    
    public String getInterfaceName() {
        return this.interfaceName;
    }
    
    public void setInterfaceType(Boolean interfaceType) {
        this.interfaceType = interfaceType;
    }
    
    public Boolean getInterfaceType() {
        return this.interfaceType;
    }
    
    public void setInterfaceStatus(Boolean interfaceStatus) {
        this.interfaceStatus = interfaceStatus;
    }
    
    public Boolean getInterfaceStatus() {
        return this.interfaceStatus;
    }
    
    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }
    
    public Date getChangeTime() {
        return this.changeTime;
    }
    

    
    public void setDubboServicePropertiess(List<DubboServiceProperties> dubboServicePropertiess){
        this.dubboServicePropertiess = dubboServicePropertiess;
    }
    
    public List<DubboServiceProperties> getDubboServicePropertiess() {
        return dubboServicePropertiess;
    }
    
    public void setHttpServicePropertiess(List<HttpServiceProperties> httpServicePropertiess){
        this.httpServicePropertiess = httpServicePropertiess;
    }
    
    public List<HttpServiceProperties> getHttpServicePropertiess() {
        return httpServicePropertiess;
    }
    
    public void setMethods(List<Method> methods){
        this.methods = methods;
    }
    
    public List<Method> getMethods() {
        return methods;
    }

    
    public void setSystem(System system){
        this.system = system;
    }
    
    public System getSystem() {
        return system;
    }
}
