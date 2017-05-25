package com.xn.authority.constants;

/**
 * Created by chenhening on 2017/5/12.
 */
public interface RightConstants {

    /**
     * 需要过滤的权限
     */
    public interface FilterResource {

        String RIGHT = "right";

        String RIGHTUSER = "rightuser";// 账号管理

        String RIGHTROLE = "rightrole";// 角色管理

    }

    /**
     * 启用状态
     */
    public enum RoleStatus {

        Y("开启"), N("禁用");
        private String name;

        private RoleStatus(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * 账号类型
     */
    public enum AccountTypeStatus {
        MASTER(1,"管理员"),SUB(0,"普通操作员");

        private Integer id;

        private String name;

        private AccountTypeStatus(Integer id,String name){
            this.id = id;
            this.name = name;
        }
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
