package com.xn.interfacetest.Enum;


public enum OperationTypeEnum {
	PREPARE("数据准备", 1),CLEAR("数据清除", 2);
    // 成员变量
    private String name;
    private int id;

    // 构造方法
    private OperationTypeEnum(String name, int id) {
        this.name = name;
        this.id = id;
    }

    // 普通方法
    public static String getName(int id) {
        for (OperationTypeEnum c : OperationTypeEnum.values()) {
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
