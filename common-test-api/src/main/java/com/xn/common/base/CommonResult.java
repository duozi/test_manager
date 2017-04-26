package com.xn.common.base;/**
 * Created by xn056839 on 2016/12/27.
 */

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

public class CommonResult<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;

   public CommonResult() {
       this.code = 0;
       this.message = "操作成功";
    }

   public CommonResult(Integer code, String message) {
        this.code = code;
        this.message = message;

    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
