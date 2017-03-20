package com.xn.manage.Enum;


public enum ExcuteTypeEnum {
    once("单次执行" ,1),circulation("循环执行", 2);
    // 成员变量
    private String name;
    private int id;

    // 构造方法
    private ExcuteTypeEnum(String name, int id) {
        this.name = name;
        this.id = id;
    }

    // 普通方法
    public static String getName(int id) {
        for (ExcuteTypeEnum c : ExcuteTypeEnum.values()) {
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
