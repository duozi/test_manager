package com.xn.autotest.enums;

/**
 * Created by xn056839 on 2016/12/27.
 */

public enum ResultMsgEnum {
    SUCCESS(0, "成功"),
    FAILED(1, "失败"),
    FAILURE_DATABASE(2, "数据库异常"),
    PARAMS_ERROR(3, "参数错误"),
    UPDATE_FAILED(4, "更新失败"),
    SAVE_FAILED(5, "保存失败"),
    DEL_FAILED(6,"删除失败"),
    EXECUTE_FAILED(7,"执行失败");

    private Integer returnCode;

    private String returnMsg;

    private ResultMsgEnum(Integer returnCode, String returnMsg) {
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
    }

    public Integer getReturnCode() {
        return returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public static ResultMsgEnum getEnum(Integer errorCode) {
        ResultMsgEnum[] arr = ResultMsgEnum.values();
        for (ResultMsgEnum tmp : arr) {
            if (errorCode == tmp.getReturnCode()) {
                return tmp;
            }
        }
        return SUCCESS;
    }
}
