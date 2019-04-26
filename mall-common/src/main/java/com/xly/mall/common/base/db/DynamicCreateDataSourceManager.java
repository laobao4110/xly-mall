package com.xly.mall.common.base.db;

import javax.sql.DataSource;
import java.util.*;
import java.util.Map.Entry;

public class DynamicCreateDataSourceManager {
    private DynamicDataSource dynamicDataSource;
    private List<DataSourceProxy> dataSourceProxyList;

    public DynamicCreateDataSourceManager() {
    }

    public void setDynamicDataSource(DynamicDataSource dynamicDataSource) {
        this.dynamicDataSource = dynamicDataSource;
    }

    public void setDataSourceProxyList(List<DataSourceProxy> dataSourceProxyList) {
        this.dataSourceProxyList = dataSourceProxyList;
    }

    public void initCreateDataSource() {
        this.registerDataSource();
    }

    private void registerDataSource() {
        Map<Object, Object> targetDataSources = new HashMap();
        Map<String, DdlDb> dbDataSourceMap = new HashMap();
        Map<String, String> dataSourceDbMap = new HashMap();
        DataSource defaultTargetDataSource = null;
        String defaultTargetDataSourceKey = null;
        List<DataSourceProxy> dataSourceProxyList = new ArrayList();
        if (this.dataSourceProxyList != null && !this.dataSourceProxyList.isEmpty()) {
            dataSourceProxyList = this.dataSourceProxyList;
        } else {
            Map<String, DataSourceProxy> dataSourceProxyMap = SpringContextHolder.applicationContext.getBeansOfType(DataSourceProxy.class);
            if (dataSourceProxyMap != null && !dataSourceProxyMap.isEmpty()) {
                Iterator i$ = dataSourceProxyMap.entrySet().iterator();

                while(i$.hasNext()) {
                    Entry<String, DataSourceProxy> en = (Entry)i$.next();
                    ((List)dataSourceProxyList).add(en.getValue());
                }
            }
        }

        Iterator i$ = ((List)dataSourceProxyList).iterator();

        while(i$.hasNext()) {
            DataSourceProxy dataSourceProxy = (DataSourceProxy)i$.next();
            String dataSourceKey = dataSourceProxy.getDataSourceKey();
            DataSource dataSource = dataSourceProxy.getDataSource();
            String dbName = dataSourceProxy.getDbName();
            boolean isDefault = dataSourceProxy.getIsDefault();
            Map<String, DataSource> slaveDataSourceMap = dataSourceProxy.getSlaveDataSourceMap();
            if (isDefault) {
                defaultTargetDataSource = dataSource;
                defaultTargetDataSourceKey = dataSourceKey;
            }

            if (slaveDataSourceMap != null && !slaveDataSourceMap.isEmpty()) {
                List<String> slaveDataSourceKeyList = new ArrayList();
                Iterator i = slaveDataSourceMap.entrySet().iterator();

                while(i.hasNext()) {
                    Entry<String, DataSource> sen = (Entry)i.next();
                    String slaveDataSourceKey = (String)sen.getKey();
                    DataSource slaveDataSource = (DataSource)sen.getValue();
                    slaveDataSourceKeyList.add(slaveDataSourceKey);
                    targetDataSources.put(slaveDataSourceKey, slaveDataSource);
                    dataSourceDbMap.put(slaveDataSourceKey, dbName);
                }

                dbDataSourceMap.put(dbName, new DdlDb(dbName, dataSourceKey, slaveDataSourceKeyList));
            } else {
                dbDataSourceMap.put(dbName, new DdlDb(dbName, dataSourceKey, (List)null));
            }

            dataSourceDbMap.put(dataSourceKey, dbName);
            targetDataSources.put(dataSourceKey, dataSource);
        }

        this.dynamicDataSource.setDbDataSourceMap(dbDataSourceMap);
        this.dynamicDataSource.setDataSourceDbMap(dataSourceDbMap);
        this.dynamicDataSource.setTargetDataSources(targetDataSources);
        this.dynamicDataSource.setDataSourceMap(targetDataSources);
        this.dynamicDataSource.setDefaultTargetDataSource(defaultTargetDataSource);
        this.dynamicDataSource.setDefaultTargetDataSourceKey(defaultTargetDataSourceKey);
        DdlInterceptor.dataSourceDbMap = dataSourceDbMap;
        DdlInterceptor.dbDataSourceMap = dbDataSourceMap;
        this.dynamicDataSource.initDdlConfig();
        this.dynamicDataSource.initSqlLog();
        this.dynamicDataSource.afterPropertiesSet();
    }
}

