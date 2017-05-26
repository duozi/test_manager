package com.xn.interfacetest.dto;

import com.xn.interfacetest.base.BaseDto;

/**
 * Created by xn058121 on 2017/3/28.
 */
public class ParamDto  extends BaseDto {
    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;


    private String name;

    private String value;

    /**
     *加密函数名
     *
     */
    private String methodName;

    private Integer formatType;

    private Long interfaceId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getFormatType() {
        return formatType;
    }

    public void setFormatType(Integer formatType) {
        this.formatType = formatType;
    }

    public Long getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(Long interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String toString() {
        return "ParamDto{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", methodName='" + methodName + '\'' +
                ", formatType=" + formatType +
                ", interfaceId=" + interfaceId +
                '}';
    }
}
