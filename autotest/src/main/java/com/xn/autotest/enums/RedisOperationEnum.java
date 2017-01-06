package com.xn.autotest.enums;

/**
 * Created by xn056839 on 2016/12/27.
 */

public enum RedisOperationEnum {
    NULL(0),
    SET(1),
    GET(2),
    DEL(3),
    EXPIRE(4);


    private Integer type;



    private RedisOperationEnum(Integer type) {
        this.type = type;

    }

    public Integer getType() {
        return type;
    }



    public static RedisOperationEnum getEnum(Integer type) {
        RedisOperationEnum[] arr = RedisOperationEnum.values();
        for (RedisOperationEnum tmp : arr) {
            if (type == tmp.getType()) {
                return tmp;
            }
        }
        return NULL;
    }
}
