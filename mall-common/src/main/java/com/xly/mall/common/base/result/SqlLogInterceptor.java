package com.xly.mall.common.base.result;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Intercepts({@Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class}
), @Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
)})
public class SqlLogInterceptor implements Interceptor {
    private static final ThreadLocal<String> sqlLogContextHolder = new ThreadLocal();
    private static final long DEFAULT_SLOW_LIMIT = 1000L;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static boolean openLog;
    private static int logLength;
    private static long slowLimit;
    private static String ignorePattern;

    public SqlLogInterceptor() {
    }

    public static void setOpenLog(boolean openLog) {
        openLog = openLog;
    }

    public static void setLogLength(int logLength) {
        logLength = logLength;
    }

    public static void setSlowLimit(long slowLimit) {
        slowLimit = slowLimit;
    }

    public static void setIgnorePattern(String ignorePattern) {
        ignorePattern = ignorePattern;
    }

    public static String getExecuteSql() {
        String sql = (String)sqlLogContextHolder.get();
        return sql;
    }

    public static void setExecuteSql(String sql) {
        sqlLogContextHolder.set(sql);
    }

    public static void clearSqlLogContext() {
        sqlLogContextHolder.remove();
    }

    public Object intercept(Invocation invocation) throws Throwable {
        if (!openLog) {
            Object obj = invocation.proceed();
            return obj;
        } else {
            long startTime = System.currentTimeMillis();
            long endTime = 0L;
            Object obj = null;
            MappedStatement mappedStatement = (MappedStatement)invocation.getArgs()[0];
            String sqlId = mappedStatement.getId();
            boolean sqlLogFlag = this.getSqlLogFlag(sqlId);
            if (sqlLogFlag) {
                boolean var27 = false;

                try {
                    var27 = true;
                    boolean ddlFlag = this.getDdlFlag().booleanValue();
                    String sqlLog1 = this.getExecuteMethodLog(ddlFlag, sqlId);
                    this.log.info(sqlLog1);
                    String sql = this.getSql(invocation, mappedStatement);
                    String sqlLog2 = this.getExecuteSqlLog(sql);
                    this.log.info(sqlLog2);
                    obj = invocation.proceed();
                    var27 = false;
                } catch (Throwable var28) {
                    obj = var28.getClass().getCanonicalName() + ":" + var28.getMessage();
                    throw var28;
                } finally {
                    if (var27) {
                        String result = "";
                        if (isJavaClass(obj)) {
                            result = JSON.toJSONString(obj);
                        }

                        endTime = endTime == 0L ? System.currentTimeMillis() : endTime;
                        long cost = endTime - startTime;
                        long slowLimit = 0;
                        slowLimit = slowLimit != 0L ? slowLimit : 1000L;
                        boolean slowQuery = cost > slowLimit;
                        clearSqlLogContext();
                        String sqlLog3 = this.getExecuteCostLog(slowQuery, startTime, endTime, cost, result);
                        this.log.info(sqlLog3);
                    }
                }

                String result = "";
                if (isJavaClass(obj)) {
                    result = JSON.toJSONString(obj);
                }

                endTime = endTime == 0L ? System.currentTimeMillis() : endTime;
                long cost = endTime - startTime;
                long slowLimit = 0;
                slowLimit = slowLimit != 0L ? slowLimit : 1000L;
                boolean slowQuery = cost > slowLimit;
                clearSqlLogContext();
                String sqlLog3 = this.getExecuteCostLog(slowQuery, startTime, endTime, cost, result);
                this.log.info(sqlLog3);
            }

            return obj;
        }
    }

    private boolean getSqlLogFlag(String sqlId) {
        if (StringUtil.isBlank(ignorePattern)) {
            return true;
        } else if (StringUtil.isBlank(sqlId)) {
            return true;
        } else {
            Pattern pattern = Pattern.compile(ignorePattern);
            Matcher matcher = pattern.matcher(sqlId);
            return !matcher.matches();
        }
    }

    private String getSql(Invocation invocation, MappedStatement mappedStatement) {
        String sql = getExecuteSql();
        if (sql == null) {
            Object parameter = invocation.getArgs()[1];
            BoundSql boundSql = mappedStatement.getBoundSql(parameter);
            String interceptSql = boundSql.getSql();
            sql = SqlParserUtil.handleSql(interceptSql, mappedStatement, boundSql);
        }

        return sql;
    }

    private Boolean getDdlFlag() {
        String sql = getExecuteSql();
        return sql != null ? true : false;
    }

    private String getExecuteMethodLog(boolean ddlFlag, String method) {
        String ddlFlagLog = ddlFlag ? "(ddl)" : "";
        return String.format(ddlFlagLog + "method:%s", method);
    }

    private String getExecuteSqlLog(String sql) {
        return String.format("【Execute Sql】%s", sql);
    }

    private String getExecuteCostLog(boolean slowQuery, long startTime, long endTime, long cost, String result) {
        String startTimeStr = DateUtil.formatDate(startTime, "yyyy-MM-dd HH:mm:ss.SSS");
        String endTimeStr = DateUtil.formatDate(endTime, "yyyy-MM-dd HH:mm:ss.SSS");
        String slowQueryLog = slowQuery ? "(slowQuery)" : "";
        result = StringUtil.isBlank(result) ? "" : "result:" + result;
        return String.format("【Execute Cost:%dms】" + result + slowQueryLog + "(start:%s, end:%s)", cost, startTimeStr, endTimeStr);
    }

    private static boolean isJavaClass(Object o) {
        if (!(o instanceof String) && !(o instanceof Integer) && !(o instanceof Long) && !(o instanceof Short) && !(o instanceof Character) && !(o instanceof Boolean) && !(o instanceof Byte) && !(o instanceof Float) && !(o instanceof Double)) {
            if (o instanceof List) {
                List lo = (List)o;
                if (lo != null && lo.size() > 0) {
                    return isJavaClass(lo.get(0));
                }
            }

            return false;
        } else {
            return true;
        }
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
    }
}
