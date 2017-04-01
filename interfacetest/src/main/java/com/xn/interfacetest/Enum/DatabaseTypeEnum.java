package com.xn.interfacetest.Enum;


public enum DatabaseTypeEnum {
	MYSQL("Mysql" ,1),POSTGRES("Postgres", 2),ORACLE("Oracle", 3),SQLSERVER("SqlServer",4),DB2("DB2",5);
    // 成员变量
    private String name;
    private int id;

    // 构造方法
    private DatabaseTypeEnum(String name, int id) {
        this.name = name;
        this.id = id;
    }

    // 普通方法
    public static String getName(int id) {
        for (DatabaseTypeEnum c : DatabaseTypeEnum.values()) {
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
