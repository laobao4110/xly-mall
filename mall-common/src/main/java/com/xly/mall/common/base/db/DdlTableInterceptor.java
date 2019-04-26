package com.xly.mall.common.base.db;

import com.xly.mall.common.base.result.SqlLogInterceptor;
import com.xly.mall.common.base.result.SqlParserUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;

import java.sql.Connection;
import java.util.*;

@Intercepts({@Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class}
)})
public class DdlTableInterceptor extends DdlInterceptor implements Interceptor {
    public DdlTableInterceptor() {
    }

    public Object intercept(Invocation invocation) throws Throwable {
        if (ddlFlag) {
            RoutingStatementHandler handler = (RoutingStatementHandler)invocation.getTarget();
            BoundSql boundSql = handler.getBoundSql();
            String interceptSql = boundSql.getSql();
            List<String> tableList = this.getTableList(interceptSql);
            boolean ddlFlag = this.getDdlFlag(interceptSql, tableList);
            if (ddlFlag) {
                StatementHandler delegate = (StatementHandler)ReflectUtil.getFieldValue(handler, "delegate");
                MappedStatement mappedStatement = (MappedStatement)ReflectUtil.getFieldValue(delegate, "mappedStatement");
                String standardSql = SqlParserUtil.handleSql(interceptSql, mappedStatement, boundSql);
                Map<String, String> ddlSqlMap = this.getDdlSqlMap(standardSql, interceptSql, tableList);
                String ddlSql = (String)ddlSqlMap.get("ddlSql");
                String ddlStandardSql = (String)ddlSqlMap.get("ddlStandardSql");
                if (StringUtils.isNotBlank(ddlSql)) {
                    ReflectUtil.setFieldValue(boundSql, "sql", ddlSql);
                    SqlLogInterceptor.setExecuteSql(ddlStandardSql);
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

    private Map<String, String> getDdlSqlMap(String sql, String interceptSql, List<String> tableList) {
        Map<String, String> ddlSqlMap = new HashMap();
        String ddlSql = interceptSql;
        String ddlStandardSql = sql;
        Iterator<String> iterator = tableList.iterator();
        Set ddlTableSet = ddlConfigMap.keySet();

        while(iterator.hasNext()) {
            String table = (String)iterator.next();
            if (ddlTableSet.contains(table)) {
                DdlConfig ddlConfig = (DdlConfig)ddlConfigMap.get(table);
                if (ddlConfig != null) {
                    String column = ddlConfig.getColumn();
                    if (!StringUtils.isBlank(column)) {
                        ConsistenHashUtil<DdlTable> consistenHashUtil = (ConsistenHashUtil)consistenHashUtilMap.get(table);
                        if (consistenHashUtil != null) {
                            Object columnValue = null;

                            try {
                                columnValue = this.getColumnValue(sql, ddlConfig.getColumn());
                            } catch (Exception var18) {
                                this.log.error("Get columnValue error", var18);
                            }

                            if (columnValue != null) {
                                DdlTable ddlTable = (DdlTable)consistenHashUtil.get(columnValue);
                                String ddlTableName = ddlTable.getDdlTableName();
                                String regex1 = "\\s+" + table + "\\s+";
                                String regex2 = "\\s+" + table + "\\.";
                                ddlSql = ddlSql.replaceAll(regex1, " " + ddlTableName + " ");
                                ddlSql = ddlSql.replaceAll(regex2, " " + ddlTableName + ".");
                                ddlStandardSql = ddlStandardSql.replaceAll(regex1, " " + ddlTableName + " ");
                                ddlStandardSql = ddlStandardSql.replaceAll(regex2, " " + ddlTableName + ".");
                            }
                        }
                    }
                }
            }
        }

        ddlSqlMap.put("ddlSql", ddlSql);
        ddlSqlMap.put("ddlStandardSql", ddlStandardSql);
        return ddlSqlMap;
    }
}

