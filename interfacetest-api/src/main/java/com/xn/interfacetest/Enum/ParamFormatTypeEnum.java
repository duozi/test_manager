package com.xn.interfacetest.Enum;


public enum ParamFormatTypeEnum {
    NORMAL("普通类型", 1),ENCRYPT("用jar包加密类型", 2);
    // 成员变量
    private String name;
    private int id;

    // 构造方法
    private ParamFormatTypeEnum(String name, int id) {
        this.name = name;
        this.id = id;
    }

    // 普通方法
    public static String getName(int id) {
        for (ParamFormatTypeEnum c : ParamFormatTypeEnum.values()) {
            if (c.getId() == id) {
                return c.name;
            }
        }
        return null;
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id; 
    }

    public void setId(int id) {
        this.id = id;
    }
}
