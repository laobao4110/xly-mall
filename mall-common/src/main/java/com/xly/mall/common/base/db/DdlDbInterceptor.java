package com.xly.mall.common.base.db;

import com.xly.mall.common.base.result.SqlParserUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

@Intercepts({@Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class}
), @Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
)})
public class DdlDbInterceptor extends DdlInterceptor implements Interceptor {
    public DdlDbInterceptor() {
    }

    public Object intercept(Invocation invocation) throws Throwable {
        if (ddlFlag) {
            MappedStatement mappedStatement = (MappedStatement)invocation.getArgs()[0];
            Object parameter = invocation.getArgs()[1];
            DynamicDataSource dynamicDataSource = (DynamicDataSource)mappedStatement.getConfiguration().getEnvironment().getDataSource();
            BoundSql boundSql = mappedStatement.getBoundSql(parameter);
            String interceptSql = boundSql.getSql();
            List<String> tableList = this.getTableList(interceptSql);
            boolean ddlFlag = this.getDdlFlag(interceptSql, tableList);
            String ddlDataSource = null;
            String currentDataSource;
            if (ddlFlag) {
                currentDataSource = SqlParserUtil.handleSql(interceptSql, mappedStatement, boundSql);
                ddlDataSource = this.getDdlDataSource(currentDataSource, interceptSql, tableList, dynamicDataSource);
            }

            if (StringUtils.isBlank(ddlDataSource)) {
                ddlDataSource = getCurrentDataSource(dynamicDataSource);
            }

            if (SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType()) && getReadWriteSeparate(readWriteSeparateFlag)) {
                ddlDataSource = this.getSlaveDataSourceByMasterDataSource(ddlDataSource);
            }

            if (StringUtils.isNotBlank(ddlDataSource)) {
                currentDataSource = getCurrentDataSource(dynamicDataSource);
                if (!ddlDataSource.equals(currentDataSource)) {
                    DataSourceSwitcher.setDataSourceTypeInContext(ddlDataSource);
                    Object obj = invocation.proceed();
                    DataSourceSwitcher.setDataSourceTypeInContext(currentDataSource);
                    return obj;
                }
            }
        }

        Object obj = invocation.proceed();
        return obj;
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
    }

    private String getDdlDataSource(String sql, String interceptSql, List<String> tableList, DynamicDataSource dynamicDataSource) {
        String ddlDb = null;
        String dataSource = null;
        Iterator<String> iterator = tableList.iterator();
        Set ddlTableSet = ddlConfigMap.keySet();

        while(iterator.hasNext() && ddlDb == null) {
            String table = (String)iterator.next();
            if (ddlTableSet.contains(table)) {
                DdlConfig ddlConfig = (DdlConfig)ddlConfigMap.get(table);
                if (ddlConfig != null) {
                    String column = ddlConfig.getColumn();
                    if (StringUtils.isBlank(column)) {
                        ddlDb = ddlConfig.getDb();
                    } else {
                        String db = ddlConfig.getDb();
                        Integer dbNum = ddlConfig.getDbNum();
                        if (!StringUtils.isBlank(db) || dbNum != null && dbNum.intValue() != 1) {
                            if (dbNum != null && dbNum.intValue() != 1) {
                                ConsistenHashUtil<DdlTable> consistenHashUtil = (ConsistenHashUtil)consistenHashUtilMap.get(table);
                                if (consistenHashUtil != null) {
                                    Object columnValue = null;

                                    try {
                                        columnValue = this.getColumnValue(sql, column);
                                    } catch (Exception var19) {
                                        this.log.error("Get columnValue error", var19);
                                    }

                                    if (columnValue != null) {
                                        DdlTable ddlTable = (DdlTable)consistenHashUtil.get(columnValue);
                                        if (ddlTable != null) {
                                            Integer ddlDbNum = ddlTable.getDdlDbNum();
                                            if (ddlDbNum != null && ddlDbNum.intValue() != 0) {
                                                if (StringUtils.isBlank(db)) {
                                                    String currentDb = getCurrentDb(dynamicDataSource);
                                                    if (StringUtils.isNotBlank(currentDb)) {
                                                        ddlDb = currentDb + "_" + ddlDbNum;
                                                    }
                                                } else {
                                                    ddlDb = db + "_" + ddlDbNum;
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                ddlDb = db;
                            }
                        }
                    }
                }
            }
        }

        if (StringUtils.isNotBlank(ddlDb)) {
            dataSource = getDataSourceByDb(ddlDb, false);
        }

        return dataSource;
    }

    public static void main(String[] args) {
        String a = null;
        String b = "b";
        if (b.equals(a)) {
            System.out.println("y");
        } else {
            System.out.println("n");
        }

    }
}
