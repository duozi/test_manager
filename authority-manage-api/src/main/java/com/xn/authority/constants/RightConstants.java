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

}
