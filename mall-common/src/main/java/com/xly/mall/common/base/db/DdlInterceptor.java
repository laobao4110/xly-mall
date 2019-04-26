package com.xly.mall.common.base.db;

import com.xly.mall.common.base.StringUtil;
import com.xly.mall.common.base.SystemException;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DdlInterceptor {
    protected final Logger log = LoggerFactory.getLogger(DdlInterceptor.class);
    public static final String TABLE_REGULAR_EXPRESSION_PREFIX = "(?:\\.|`|,|\\s)?";
    public static final String TABLE_REGULAR_EXPRESSION_SUFFIX = "(?:\\.|`|,|\\s|$)";
    public static Map<String, DdlConfig> ddlConfigMap;
    public static String tablePattern;
    public static Map<String, ConsistenHashUtil<DdlTable>> consistenHashUtilMap;
    public static Map<String, String> dataSourceDbMap;
    public static Map<String, DdlDb> dbDataSourceMap;
    protected CCJSqlParserManager parserManager = new CCJSqlParserManager();
    protected static boolean ddlFlag;
    protected static boolean readWriteSeparateFlag;

    public DdlInterceptor() {
    }

    public static void setDdlFlag(boolean ddlFlag) {
        ddlFlag = ddlFlag;
    }

    public static void setReadWriteSeparateFlag(boolean readWriteSeparateFlag) {
        readWriteSeparateFlag = readWriteSeparateFlag;
    }

    protected boolean getDdlFlag(String interceptSql, List<String> tableList) {
        Set<String> tableSet = ddlConfigMap.keySet();
        String patternString = "[\\s\\S]*(" + StringUtils.join(tableSet, "|") + ")[\\s\\S]*";
        Pattern pattern = Pattern.compile(patternString, 2);
        Matcher matcher = pattern.matcher(interceptSql);
        if (interceptSql != null && !"".equals(interceptSql) && matcher.matches()) {
            if (tableList == null || tableList.isEmpty()) {
                return false;
            }

            Iterator iterator = tableSet.iterator();

            while(iterator.hasNext()) {
                if (tableList.contains(iterator.next())) {
                    return true;
                }
            }
        }

        return false;
    }

    protected List<String> getTableList(String interceptSql) {
        String sql = interceptSql.replaceAll("[\\s]+", " ").trim();
        Statement statement = null;
        ArrayList tableList = null;

        try {
            statement = this.parserManager.parse(new StringReader(sql));
        } catch (JSQLParserException var9) {
            ;
        }

        if (statement != null) {
            if (statement instanceof Select) {
                Select select = (Select)statement;
                TableNameFinder tableNameFinder = new TableNameFinder();
                tableList = new ArrayList();
                tableList.addAll(tableNameFinder.getTableList(select));
            } else {
                Table table;
                if (statement instanceof Insert) {
                    Insert insert = (Insert)statement;
                    table = insert.getTable();
                    tableList = new ArrayList();
                    tableList.add(table.getName());
                } else if (statement instanceof Update) {
                    Update update = (Update)statement;
                    table = update.getTable();
                    tableList = new ArrayList();
                    tableList.add(table.getName());
                } else if (statement instanceof Delete) {
                    Delete delete = (Delete)statement;
                    table = delete.getTable();
                    tableList = new ArrayList();
                    tableList.add(table.getName());
                } else if (statement instanceof CreateTable) {
                    CreateTable createTable = (CreateTable)statement;
                    table = createTable.getTable();
                    tableList = new ArrayList();
                    tableList.add(table.getName());
                } else if (statement instanceof Drop) {
                    Drop drop = (Drop)statement;
                    String type = drop.getType();
                    if ("TABLE".equals(type)) {
                        tableList = new ArrayList();
                        tableList.add(drop.getName());
                    }
                } else if (statement instanceof Replace) {
                    Replace replace = (Replace)statement;
                    table = replace.getTable();
                    tableList = new ArrayList();
                    tableList.add(table.getName());
                } else if (statement instanceof Truncate) {
                    Truncate truncate = (Truncate)statement;
                    table = truncate.getTable();
                    tableList = new ArrayList();
                    tableList.add(table.getName());
                }
            }
        } else {
            Matcher matcher;
            String table;
            String patternString;
            Pattern pattern;
            if (sql.toUpperCase().indexOf("SHOW CREATE TABLE") != -1) {
                patternString = "SHOW CREATE TABLE\\s+(\\S+)";
                pattern = Pattern.compile(patternString, 2);
                matcher = pattern.matcher(sql);
                tableList = new ArrayList();

                while(matcher.find()) {
                    table = matcher.group(1).trim();
                    tableList.add(StringUtil.trimString(table, "`"));
                }
            } else if (sql.toUpperCase().indexOf("INSERT INTO") != -1) {
                patternString = "INSERT INTO\\s+(\\S+)\\s+";
                pattern = Pattern.compile(patternString, 2);
                matcher = pattern.matcher(sql);
                tableList = new ArrayList();

                while(matcher.find()) {
                    table = matcher.group(1).trim();
                    tableList.add(StringUtil.trimString(table, "`"));
                }
            }
        }

        return tableList;
    }

    protected Object getColumnValue(String sql, String columnName) {
        Statement statement = null;

        try {
            statement = this.parserManager.parse(new StringReader(sql));
        } catch (JSQLParserException var11) {
            this.log.warn("Parse sql exception, " + sql, var11);
        }

        if (statement != null) {
            if (statement instanceof Insert) {
                Insert insert = (Insert)statement;
                List<?> columnList = insert.getColumns();

                for(int i = 0; i < columnList.size(); ++i) {
                    Column column = (Column)columnList.get(i);
                    if (columnName.equals(column.getColumnName())) {
                        ItemsList itemsList = insert.getItemsList();
                        if (itemsList instanceof ExpressionList) {
                            ExpressionList expressionList = (ExpressionList)itemsList;
                            if (expressionList.getExpressions().get(i) != null) {
                                String columnValue = expressionList.getExpressions().get(i).toString();
                                return columnValue.replaceAll("^[\"|']|[\"|']$", "");
                            }

                            return null;
                        }
                    }
                }
            } else {
                String patternString = columnName + "\\s=\\s(.*?)[\\s|;]";
                Pattern pattern = Pattern.compile(patternString, 2);
                Matcher matcher = pattern.matcher(sql + " ");
                if (matcher.find()) {
                    String columnValue = matcher.group(1).trim();
                    return columnValue.replaceAll("^[\"|']|[\"|']$", "");
                }
            }
        }

        return null;
    }

    public static String getDataSourceByDb(String db, boolean isSlave) {
        if (dbDataSourceMap == null) {
            throw new SystemException("Can not find a db, because dbDataSourceMap is null.");
        } else {
            DdlDb ddlDb = (DdlDb)dbDataSourceMap.get(db);
            if (ddlDb == null) {
                throw new SystemException("Can not find a dataSource by db " + db + " from dbDataSourceMap.");
            } else {
                if (isSlave) {
                    List<String> slaveDataSourceKeyList = ddlDb.getSlaveDataSourceKeyList();
                    if (slaveDataSourceKeyList != null && !slaveDataSourceKeyList.isEmpty()) {
                        Random random = new Random();
                        int size = slaveDataSourceKeyList.size();
                        String dataSource = (String)slaveDataSourceKeyList.get(random.nextInt(size));
                        if (dataSource != null) {
                            return dataSource;
                        }
                    }
                }

                String dataSource = ddlDb.getMasterDataSourceKey();
                return dataSource;
            }
        }
    }

    public String getSlaveDataSourceByMasterDataSource(String masterDataSource) {
        if (StringUtils.isBlank(masterDataSource)) {
            return null;
        } else {
            String db = getDbByDataSource(masterDataSource);
            String slaveDataSource = getDataSourceByDb(db, true);
            return slaveDataSource;
        }
    }

    public static String getDbByDataSource(String dataSource) {
        if (dataSourceDbMap == null) {
            throw new SystemException("Can not find a db, because dataSourceDbMap is null.");
        } else {
            String db = (String)dataSourceDbMap.get(dataSource);
            if (db == null) {
                throw new SystemException("Can not find a db by dataSource=" + dataSource + " from dataSourceDbMap.");
            } else {
                return db;
            }
        }
    }

    public static boolean getReadWriteSeparate(boolean readWriteSeparateFlag) {
        Boolean readWriteSeparate = null;
        if (readWriteSeparateFlag) {
            readWriteSeparate = DataSourceSwitcher.getReadWriteSeparateFromContext();
            return readWriteSeparate == null ? true : readWriteSeparateFlag;
        } else {
            readWriteSeparate = DataSourceSwitcher.getReadWriteSeparateFromContext();
            return readWriteSeparate == null ? false : readWriteSeparateFlag;
        }
    }

    public static String getCurrentDataSource(DynamicDataSource dynamicDataSource) {
        String currentDataSource = dynamicDataSource.getCurrentDataSource();
        return currentDataSource;
    }

    public static String getCurrentDb(DynamicDataSource dynamicDataSource) {
        String currentDataSource = getCurrentDataSource(dynamicDataSource);
        return getDbByDataSource(currentDataSource);
    }

    public static String getTablePattern(Set<String> keySet) {
        if (keySet != null && !keySet.isEmpty()) {
            Iterator<String> iterator = keySet.iterator();
            String first = (String)iterator.next();
            if (!iterator.hasNext()) {
                return "(?:\\.|`|,|\\s)?(" + first + ")" + "(?:\\.|`|,|\\s|$)";
            } else {
                StringBuffer buf = new StringBuffer();
                buf.append("(?:\\.|`|,|\\s)?");
                buf.append("(");
                if (first != null) {
                    buf.append(first);
                }

                while(iterator.hasNext()) {
                    String table = (String)iterator.next();
                    buf.append("|");
                    if (table != null) {
                        buf.append(table);
                    }
                }

                buf.append(")");
                buf.append("(?:\\.|`|,|\\s|$)");
                return buf.toString();
            }
        } else {
            return "";
        }
    }
}
