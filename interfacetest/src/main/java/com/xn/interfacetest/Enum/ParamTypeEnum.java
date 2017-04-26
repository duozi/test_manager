package com.xn.interfacetest.Enum;


public enum ParamTypeEnum {
	SZ("数字",0),BR("布尔", 1),SJ("时间", 2),ST("实体类", 3),QJBL("全局变量", 4),ZFC("字符串", 5),KONG("空", 6);
    // 成员变量
    private String name;
    private int id;

    // 构造方法
    private ParamTypeEnum(String name, int id) {
        this.name = name;
        this.id = id;
    }

    // 普通方法
    public static String getName(int id) {
        for (ParamTypeEnum c : ParamTypeEnum.values()) {
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
