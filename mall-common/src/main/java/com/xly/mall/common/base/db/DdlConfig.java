package com.xly.mall.common.base.db;

public class DdlConfig {
    private String table;
    private String column;
    private Integer tableNum;
    private String db;
    private Integer dbNum;
    private String isTransaction;

    public DdlConfig() {
    }

    public DdlConfig(String table, String column, Integer tableNum, String db, Integer dbNum) {
        this.table = table;
        this.column = column;
        this.tableNum = tableNum;
        this.db = db;
        this.dbNum = dbNum;
    }

    public DdlConfig(String table, String column, Integer tableNum, String db, Integer dbNum, String isTransaction) {
        this.table = table;
        this.column = column;
        this.tableNum = tableNum;
        this.db = db;
        this.dbNum = dbNum;
        this.isTransaction = isTransaction;
    }

    public String getTable() {
        return this.table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getColumn() {
        return this.column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Integer getTableNum() {
        return this.tableNum;
    }

    public void setTableNum(Integer tableNum) {
        this.tableNum = tableNum;
    }

    public String getDb() {
        return this.db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public Integer getDbNum() {
        return this.dbNum;
    }

    public void setDbNum(Integer dbNum) {
        this.dbNum = dbNum;
    }

    public String getIsTransaction() {
        return this.isTransaction;
    }

    public void setIsTransaction(String isTransaction) {
        this.isTransaction = isTransaction;
    }

    public int hashCode() {
        boolean prime = true;
        int result = 1;
        result = 31 * result + (this.db == null ? 0 : this.db.hashCode());
        result = 31 * result + (this.table == null ? 0 : this.table.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            DdlConfig other = (DdlConfig)obj;
            if (this.db == null) {
                if (other.db != null) {
                    return false;
                }
            } else if (!this.db.equals(other.db)) {
                return false;
            }

            if (this.table == null) {
                if (other.table != null) {
                    return false;
                }
            } else if (!this.table.equals(other.table)) {
                return false;
            }

            return true;
        }
    }

    public String toString() {
        return "DdlConfig [table=" + this.table + ", column=" + this.column + ", tableNum=" + this.tableNum + ", db=" + this.db + ", dbNum=" + this.dbNum + ", isTransaction=" + this.isTransaction + "]";
    }
}
