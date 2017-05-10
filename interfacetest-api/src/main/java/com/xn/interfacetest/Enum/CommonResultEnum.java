package com.xn.interfacetest.Enum;

/**
 * Created by xn056839 on 2017/2/22.
 */

public enum CommonResultEnum {
    SUCCESS(0, "成功"),
    ERROR(1, "异常"),
    FAILED(2, "失败");
    private Integer returnCode;

    private String returnMsg;

    private CommonResultEnum(Integer returnCode, String returnMsg) {
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
    }

    public static CommonResultEnum getEnum(Integer errorCode) {
        CommonResultEnum[] arr = CommonResultEnum.values();
        for (CommonResultEnum tmp : arr) {
            if (errorCode == tmp.getReturnCode()) {
                return tmp;
            }
        }
        return SUCCESS;
    }

    public Integer getReturnCode() {
        return returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }
}
