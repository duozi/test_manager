package com.xn.interfacetest.Enum;


public enum AppendParamEnum {
	JSON("JSON", 0),XML("XML", 1),HTML("&", 3);
//    ,FILE("上传文件", 1);
    // 成员变量
    private String name;
    private int id;

    // 构造方法
    private AppendParamEnum(String name, int id) {
        this.name = name;
        this.id = id;
    }

    // 普通方法
    public static String getName(int id) {
        for (AppendParamEnum c : AppendParamEnum.values()) {
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

    public static int getIdByName(String name){
        for (AppendParamEnum c : AppendParamEnum.values()) {
            if (c.getName().equals(name.trim())) {
                return c.id;
            }
        }
        return 0;
    }
}
