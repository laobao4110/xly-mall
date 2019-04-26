package com.xly.mall.common.base.db;

public class DdlTable {
    private String ddlTableName;
    private Integer ddlDbNum;

    public DdlTable(String ddlTableName, Integer ddlDbNum) {
        this.ddlTableName = ddlTableName;
        this.ddlDbNum = ddlDbNum;
    }

    public String getDdlTableName() {
        return this.ddlTableName;
    }

    public void setDdlTableName(String ddlTableName) {
        this.ddlTableName = ddlTableName;
    }

    public Integer getDdlDbNum() {
        return this.ddlDbNum;
    }

    public void setDdlDbNum(Integer ddlDbNum) {
        this.ddlDbNum = ddlDbNum;
    }

    public int hashCode() {
        boolean prime = true;
        int result = 1;
        result = 31 * result + (this.ddlDbNum == null ? 0 : this.ddlDbNum.hashCode());
        result = 31 * result + (this.ddlTableName == null ? 0 : this.ddlTableName.hashCode());
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
            DdlTable other = (DdlTable)obj;
            if (this.ddlDbNum == null) {
                if (other.ddlDbNum != null) {
                    return false;
                }
            } else if (!this.ddlDbNum.equals(other.ddlDbNum)) {
                return false;
            }

            if (this.ddlTableName == null) {
                if (other.ddlTableName != null) {
                    return false;
                }
            } else if (!this.ddlTableName.equals(other.ddlTableName)) {
                return false;
            }

            return true;
        }
    }

    public String toString() {
        return "DdlTable [ddlTableName=" + this.ddlTableName + ", ddlDbNum=" + this.ddlDbNum + "]";
    }
}
