package com.xn.autotest.bean;

public class KeyValueStore {
    String name;
    Object value;

    public KeyValueStore(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

	@Override
	public String toString() {
		return "KeyValueStore [name=" + name + ", value=" + value + "]";
	}
    
    
}