package com.xly.mall.common.base.db;


import com.xly.mall.common.base.SystemException;
import com.xly.mall.common.base.result.SqlLogInterceptor;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class DynamicDataSource extends AbstractRoutingDataSource {
    private static final String DDL_DB_CLASS = "com.xly.mall.common.base.db.DdlDbInterceptor";
    private static final String DDL_TABLE_CLASS = "com.xly.mall.common.base.db.DdlTableInterceptor";
    private static final String SQL_LOG_CLASS = "com.xly.mall.common.base.result.SqlLogInterceptor";
    private static final String DEFAULT_DDL_FILE = "ddl-config.xml";
    private static Map<String, String> dataSourceDbMap;
    private static Map<String, DdlDb> dbDataSourceMap;
    private Map<Object, Object> dataSourceMap;
    private String defaultTargetDataSourceKey;
    private String currentDataSource;
    private String ddlFile;
    private boolean ddlFlag;
    private boolean readWriteSeparateFlag = false;
    private boolean openLog = false;
    private int logLength;
    private String ignorePattern;
    private long slowLimit = 1000L;

    public DynamicDataSource() {
    }

    public Map<String, String> getDataSourceDbMap() {
        return dataSourceDbMap;
    }

    public void setDataSourceDbMap(Map<String, String> dataSourceDbMap) {
        dataSourceDbMap = dataSourceDbMap;
    }

    public Map<String, DdlDb> getDbDataSourceMap() {
        return dbDataSourceMap;
    }

    public void setDbDataSourceMap(Map<String, DdlDb> dbDataSourceMap) {
        dbDataSourceMap = dbDataSourceMap;
    }

    public void setDefaultTargetDataSourceKey(String defaultTargetDataSourceKey) {
        this.defaultTargetDataSourceKey = defaultTargetDataSourceKey;
    }

    public void setDataSourceMap(Map<Object, Object> dataSourceMap) {
        this.dataSourceMap = dataSourceMap;
    }

    public void setDdlFile(String ddlFile) {
        this.ddlFile = ddlFile;
    }

    public void setDdlFlag(boolean ddlFlag) {
        this.ddlFlag = ddlFlag;
    }

    public void setReadWriteSeparateFlag(boolean readWriteSeparateFlag) {
        this.readWriteSeparateFlag = readWriteSeparateFlag;
    }

    public void setOpenLog(boolean openLog) {
        this.openLog = openLog;
    }

    public void setLogLength(int logLength) {
        this.logLength = logLength;
    }

    public void setIgnorePattern(String ignorePattern) {
        this.ignorePattern = ignorePattern;
    }

    public void setSlowLimit(long slowLimit) {
        this.slowLimit = slowLimit;
    }

    protected Object determineCurrentLookupKey() {
        String dataSource = DataSourceSwitcher.getDataSourceType();
        if (dataSource == null) {
            dataSource = this.defaultTargetDataSourceKey;
        }

        if (dataSource == null) {
            throw new SystemException("Can not find a dataSource.");
        } else {
            this.currentDataSource = dataSource;
            return dataSource;
        }
    }

    public static String getDataSourceByDb(String db, boolean isSlave) {
        return DdlInterceptor.getDataSourceByDb(db, isSlave);
    }

    public String getCurrentDataSource() {
        return this.currentDataSource;
    }

    public Set<String> getDataSources() {
        return dataSourceDbMap.keySet();
    }

    public DataSource getDataSource(String dataSourceKey) {
        DataSource dataSource = (DataSource)this.dataSourceMap.get(dataSourceKey);
        return dataSource;
    }

    public void initSqlLog() {
        if (this.openLog) {
            SqlLogInterceptor.setOpenLog(this.openLog);
            SqlLogInterceptor.setSlowLimit(this.slowLimit);
            SqlLogInterceptor.setLogLength(this.logLength);
            SqlLogInterceptor.setIgnorePattern(this.ignorePattern);
            InterceptorUtil.setSqlSessionFactoryBean((SqlSessionFactoryBean)SpringContextHolder.applicationContext.getBean(SqlSessionFactoryBean.class));
            InterceptorUtil.dynamicAddInterceptor("com.xly.mall.common.base.result.SqlLogInterceptor");
        }

    }

    public void initDdlConfig() {
        if (this.ddlFlag) {
            try {
                DdlInterceptor.setDdlFlag(this.ddlFlag);
                DdlInterceptor.setReadWriteSeparateFlag(this.readWriteSeparateFlag);
                InterceptorUtil.setSqlSessionFactoryBean((SqlSessionFactoryBean)SpringContextHolder.applicationContext.getBean(SqlSessionFactoryBean.class));
                InterceptorUtil.dynamicAddInterceptor("com.xly.mall.common.base.DdlDbInterceptor");
                InterceptorUtil.dynamicAddInterceptor("com.xly.mall.common.base.DdlTableInterceptor");
                String ddlFile = StringUtils.isNotBlank(this.ddlFile) ? this.ddlFile : "ddl-config.xml";
                URL url = Thread.currentThread().getContextClassLoader().getResource(ddlFile);
                if (url != null) {
                    SAXReader saxReader = new SAXReader();
                    Document document = null;
                    if (this.isJarUrl(url)) {
                        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(ddlFile);
                        document = saxReader.read(is);
                    } else {
                        document = saxReader.read(url);
                    }

                    Element root = document.getRootElement();
                    Element tables = root.element("tables");
                    List<?> nodes = tables.elements("table");
                    Iterator<?> iterator = nodes.iterator();
                    if (DdlInterceptor.ddlConfigMap == null) {
                        DdlInterceptor.ddlConfigMap = new HashMap();
                    }

                    if (DdlInterceptor.dataSourceDbMap == null || DdlInterceptor.dataSourceDbMap.isEmpty() || DdlInterceptor.dbDataSourceMap == null || DdlInterceptor.dbDataSourceMap.isEmpty()) {
                        this.buildDdlMap();
                    }

                    ArrayList ddlTableList = null;

                    while(true) {
                        String table;
                        Integer tableNum;
                        Integer dbNum;
                        do {
                            do {
                                if (!iterator.hasNext()) {
                                    DdlInterceptor.tablePattern = DdlInterceptor.getTablePattern(DdlInterceptor.ddlConfigMap.keySet());
                                    return;
                                }

                                ddlTableList = new ArrayList();
                                Element e = (Element)iterator.next();
                                table = e.attributeValue("table").trim();
                                String column = e.attributeValue("column") == null ? null : e.attributeValue("column").trim();
                                tableNum = e.attributeValue("tableNum") == null ? null : Integer.valueOf(e.attributeValue("tableNum").trim());
                                String db = e.attributeValue("db") == null ? null : e.attributeValue("db").trim();
                                dbNum = e.attributeValue("dbNum") == null ? null : Integer.valueOf(e.attributeValue("dbNum").trim());
                                DdlInterceptor.ddlConfigMap.put(table, new DdlConfig(table, column, tableNum, db, dbNum));
                            } while(tableNum == null);
                        } while(tableNum.intValue() <= 1);

                        for(int i = 1; i <= tableNum.intValue(); ++i) {
                            if (dbNum != null && dbNum.intValue() != 1) {
                                if (dbNum.intValue() > tableNum.intValue()) {
                                    throw new SystemException("Ddl config error, tableNum should max than dbNum");
                                }

                                ddlTableList.add(new DdlTable(table + "_" + (i - 1), (i - 1) / (tableNum.intValue() / dbNum.intValue()) + 1 - 1));
                            } else {
                                ddlTableList.add(new DdlTable(table + "_" + (i - 1), Integer.valueOf(0)));
                            }
                        }

                        if (DdlInterceptor.consistenHashUtilMap == null) {
                            DdlInterceptor.consistenHashUtilMap = new HashMap();
                        }

                        DdlInterceptor.consistenHashUtilMap.put(table, new ConsistenHashUtil(ddlTableList));
                    }
                }
            } catch (Exception var17) {
                throw new SystemException("InitDdlConfig error", var17);
            }
        }

    }

    private boolean isJarUrl(URL url) {
        return url.toString().indexOf("jar:file") != -1 || url.toString().indexOf("jar!") != -1;
    }

    private void buildDdlMap() {
        DdlInterceptor.dataSourceDbMap = this.getDataSourceDbMap();
        DdlInterceptor.dbDataSourceMap = this.getDbDataSourceMap();
    }
}
