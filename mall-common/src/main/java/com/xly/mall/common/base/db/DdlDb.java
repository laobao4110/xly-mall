package com.xly.mall.common.base.db;

import java.util.List;

public class DdlDb {
    private String dbName;
    private String masterDataSourceKey;
    private List<String> slaveDataSourceKeyList;

    public DdlDb(String dbName, String masterDataSourceKey, List<String> slaveDataSourceKeyList) {
        this.dbName = dbName;
        this.masterDataSourceKey = masterDataSourceKey;
        this.slaveDataSourceKeyList = slaveDataSourceKeyList;
    }

    public String getDbName() {
        return this.dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getMasterDataSourceKey() {
        return this.masterDataSourceKey;
    }

    public void setMasterDataSourceKey(String masterDataSourceKey) {
        this.masterDataSourceKey = masterDataSourceKey;
    }

    public List<String> getSlaveDataSourceKeyList() {
        return this.slaveDataSourceKeyList;
    }

    public void setSlaveDataSourceKeyList(List<String> slaveDataSourceKeyList) {
        this.slaveDataSourceKeyList = slaveDataSourceKeyList;
    }

    public String toString() {
        return "DdlDb [dbName=" + this.dbName + ", masterDataSourceKey=" + this.masterDataSourceKey + ", slaveDataSourceKeyList=" + this.slaveDataSourceKeyList + "]";
    }
}
