package com.xn.authority.model;

public class Result {

    private boolean success = true;

    private String code;

    private String message;
    
    private Object attach;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getAttach() {
        return attach;
    }

    public void setAttach(Object attach) {
        this.attach = attach;
    }
    
    public void error(String message) {
        this.success = false;
        this.message = message;
    }

}
