package com.xn.autotest.enums;

/**
 * Created by xn056839 on 2016/12/27.
 */

public enum OperationTypeEnum {
    NULL(0),
    BEFORE_CLASS(1),
    AFTER_CALaASS(2),
    BEFORE(3),
    AFTER (4);


    private Integer type;



    private OperationTypeEnum(Integer type) {
        this.type = type;

    }

    public Integer getType() {
        return type;
    }



    public static OperationTypeEnum getEnum(Integer type) {
        OperationTypeEnum[] arr = OperationTypeEnum.values();
        for (OperationTypeEnum tmp : arr) {
            if (type == tmp.getType()) {
                return tmp;
            }
        }
        return NULL;
    }
}
