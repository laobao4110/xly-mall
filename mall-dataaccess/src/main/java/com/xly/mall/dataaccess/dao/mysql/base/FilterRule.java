package com.xly.mall.dataaccess.dao.mysql.base;

public class FilterRule {
    private String key;
    private String comparator;
    private Object value;

    public FilterRule() {
    }

    public FilterRule(String key, String comparator, Object value) {
        this.key = key;
        this.comparator = comparator;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getComparator() {
        return this.comparator;
    }

    public void setComparator(String comparator) {
        this.comparator = comparator;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
