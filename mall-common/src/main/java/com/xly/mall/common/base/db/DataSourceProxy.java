package com.xly.mall.common.base.db;

import javax.sql.DataSource;
import java.util.Map;

public class DataSourceProxy {
    private DataSource dataSource;
    private String dbName;
    private String dataSourceKey;
    private boolean isDefault;
    private Map<String, DataSource> slaveDataSourceMap;

    public DataSourceProxy() {
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getDbName() {
        return this.dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDataSourceKey() {
        return this.dataSourceKey;
    }

    public void setDataSourceKey(String dataSourceKey) {
        this.dataSourceKey = dataSourceKey;
    }

    public boolean getIsDefault() {
        return this.isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Map<String, DataSource> getSlaveDataSourceMap() {
        return this.slaveDataSourceMap;
    }

    public void setSlaveDataSourceMap(Map<String, DataSource> slaveDataSourceMap) {
        this.slaveDataSourceMap = slaveDataSourceMap;
    }

    public String toString() {
        return "DataSourceProxy [dataSource=" + this.dataSource + ", dbName=" + this.dbName + ", dataSourceKey=" + this.dataSourceKey + ", isDefault=" + this.isDefault + ", slaveDataSourceMap=" + this.slaveDataSourceMap + "]";
    }
}

