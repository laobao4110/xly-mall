package com.xly.mall.common.base.db;

public class DataSourceSwitcher {
    private static final ThreadLocal<String> dbContextHolder = new ThreadLocal();
    private static final ThreadLocal<Boolean> dbContextReadWriteSeparate = new ThreadLocal();

    public DataSourceSwitcher() {
    }

    public static void setDataSourceTypeInContext(String dataSourceType) {
        dbContextHolder.set(dataSourceType);
        dbContextReadWriteSeparate.set(true);
    }

    public static void setDataSourceTypeForceMaster() {
        setDataSourceTypeForceMaster((String)null);
    }

    public static void setDataSourceTypeForceMaster(String dataSourceType) {
        if (dataSourceType != null) {
            dbContextHolder.set(dataSourceType);
        }

        dbContextReadWriteSeparate.set(false);
    }

    public static void setDataSourceTypeForceSlave() {
        setDataSourceTypeForceSlave((String)null);
    }

    public static void setDataSourceTypeForceSlave(String dataSourceType) {
        if (dataSourceType != null) {
            dbContextHolder.set(dataSourceType);
        }

        dbContextReadWriteSeparate.set(true);
    }

    public static String getDataSourceType() {
        return getDataSourceTypeFromContext();
    }

    public static String getDataSourceTypeFromContext() {
        String dataSourceType = (String)dbContextHolder.get();
        return dataSourceType;
    }

    public static Boolean getReadWriteSeparateFromContext() {
        Boolean readWriteSeparateFlag = (Boolean)dbContextReadWriteSeparate.get();
        return readWriteSeparateFlag;
    }

    public static void clearDataSourceType() {
        dbContextHolder.remove();
        dbContextReadWriteSeparate.remove();
    }
}
