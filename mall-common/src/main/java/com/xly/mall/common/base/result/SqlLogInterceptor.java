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

/**
 * 日志查询拦截器
 *
 * @author lxzl
 *
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
        @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
public class SqlLogInterceptor implements Interceptor {

    private static final ThreadLocal<String> sqlLogContextHolder = new ThreadLocal<String>();

    private static final long DEFAULT_SLOW_LIMIT = 1000l;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static boolean openLog;

    private static int logLength;

    private static long slowLimit;

    private static String ignorePattern;

    public static void setOpenLog(boolean openLog) {
        SqlLogInterceptor.openLog = openLog;
    }

    public static void setLogLength(int logLength) {
        SqlLogInterceptor.logLength = logLength;
    }

    public static void setSlowLimit(long slowLimit) {
        SqlLogInterceptor.slowLimit = slowLimit;
    }

    public static void setIgnorePattern(String ignorePattern) {
        SqlLogInterceptor.ignorePattern = ignorePattern;
    }

    public static String getExecuteSql() {
        String sql = sqlLogContextHolder.get();
        return sql;
    }

    public static void setExecuteSql(String sql) {
        sqlLogContextHolder.set(sql);
    }

    public static void clearSqlLogContext() {
        sqlLogContextHolder.remove();
    }

    // public static Boolean getDdlFlag() {
    // Boolean ddlFlag = ddlFlagContextHolder.get();
    // return ddlFlag;
    // }
    //
    // public static void setDdlFlag(Boolean ddlFlag) {
    // ddlFlagContextHolder.set(ddlFlag);
    // }
    //
    // public static void clearDdlFlagContext() {
    // ddlFlagContextHolder.remove();
    // }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //默认开启sql日志
        if (openLog) {
            long startTime = System.currentTimeMillis();
            long endTime = 0l;
            Object obj = null;
            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
            String sqlId = mappedStatement.getId();

            boolean sqlLogFlag = getSqlLogFlag(sqlId);
            if (sqlLogFlag) {
                try {
                    boolean ddlFlag = getDdlFlag();
                    String sqlLog1 = getExecuteMethodLog(ddlFlag,sqlId);
                    log.info(sqlLog1);
                    String sql = getSql(invocation, mappedStatement);
                    String sqlLog2 = getExecuteSqlLog(sql);
                    log.info(sqlLog2);
                    obj = invocation.proceed();
                } catch (Throwable t) {
                    obj = t.getClass().getCanonicalName() + ":" + t.getMessage();
                    throw t;
                } finally {
                    String result = "";
                    if (isJavaClass(obj)) {
                        result = JSON.toJSONString(obj);
                    }
                    endTime = (endTime == 0 ? System.currentTimeMillis() : endTime);
                    long cost = endTime - startTime;
                    long slowLimit = SqlLogInterceptor.slowLimit != 0l ? SqlLogInterceptor.slowLimit : DEFAULT_SLOW_LIMIT;
                    boolean slowQuery = (cost > slowLimit);
                    clearSqlLogContext();
                    String sqlLog3 = getExecuteCostLog(slowQuery,startTime, endTime, cost,result);
                    log.info(sqlLog3);
                }
            }
            return obj;
        } else {
            Object obj = invocation.proceed();
            return obj;
        }
    }

    private boolean getSqlLogFlag(String sqlId) {
        if (StringUtil.isBlank(ignorePattern)) {
            return true;
        }

        if (StringUtil.isBlank(sqlId)) {
            return true;
        }

        Pattern pattern = Pattern.compile(ignorePattern);
        Matcher matcher = pattern.matcher(sqlId);

        if (matcher.matches()) {
            return false;
        } else {
            return true;
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

        if (sql != null) {
            return true;
        } else {
            return false;
        }
    }

    private String getExecuteMethodLog( boolean ddlFlag,String method) {
        String ddlFlagLog = ddlFlag ? "(ddl)" : "";
        return String.format( ddlFlagLog + "method:%s",method );
    }
    private String getExecuteSqlLog( String sql) {
        return String.format( "【Execute Sql】%s",sql );
    }

    private String getExecuteCostLog(boolean slowQuery, long startTime, long endTime, long cost, String result) {
        String startTimeStr = DateUtil.formatDate(startTime, DateUtil.MAX_LONG_DATE_FORMAT_STR);
        String endTimeStr = DateUtil.formatDate(endTime, DateUtil.MAX_LONG_DATE_FORMAT_STR);
        String slowQueryLog = slowQuery ? "(slowQuery)" : "";
        result = StringUtil.isBlank(result) ? "" : "result:" + result;
        return String.format(
                "【Execute Cost:%dms】" + result + slowQueryLog + "(start:%s, end:%s)",
                cost, startTimeStr, endTimeStr);
    }
    /**
     * 判断是否是基本类型
     * @return
     */
    private static boolean isJavaClass(Object o) {
        if(o instanceof String ||
                o instanceof Integer ||
                o instanceof Long ||
                o instanceof Short ||
                o instanceof Character ||
                o instanceof Boolean ||
                o instanceof Byte ||
                o instanceof Float ||
                o instanceof  Double) {
            return true;
        }else if(o instanceof List){
            List lo = (List)o;
            if(lo!=null&&lo.size()>0){
                return isJavaClass(lo.get(0));
            }
        }
        return false;
    }
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

}
