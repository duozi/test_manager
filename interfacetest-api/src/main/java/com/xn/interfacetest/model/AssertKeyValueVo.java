package com.xn.interfacetest.model;

public class AssertKeyValueVo {
    String name;
    Object value;
    Long id;

    public AssertKeyValueVo(String name, Object value,Long id) {
        this.name = name;
        this.value = value;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AssertKeyValueVo{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", id=" + id +
                '}';
    }
}