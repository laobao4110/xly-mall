package com.xly.mall.common.domain.base;

import org.apache.commons.lang.StringUtils;

public class PageQuery {
    private static final int MAX_PAGE_NO = 5000;
    private int pageNo;
    private int pageSize;
    private int start;
    private int end;
    private int totalCount;
    private String orderBy;
    private String orderType;
    private int maxPageNo;

    public PageQuery() {
        this.totalCount = -1;
    }

    public PageQuery(int pageNo, int pageSize) {
        this(pageNo, pageSize, 5000, (String)null, "asc");
    }

    public PageQuery(int pageNo, int pageSize, int maxPageNo) {
        this(pageNo, pageSize, maxPageNo, (String)null, "asc");
    }

    public PageQuery(int pageNo, int pageSize, int maxPageNo, String orderBy, String orderType) {
        this.totalCount = -1;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
        this.maxPageNo = maxPageNo;
        this.setOrderType(orderType);
        if (pageNo == 1) {
            this.start = 0;
        } else {
            this.start = (pageNo - 1) * pageSize;
        }

        this.end = this.start + pageSize - 1;
    }

    public PageQuery(int pageNo, int pageSize, String orderBy, String orderType) {
        this.totalCount = -1;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
        this.maxPageNo = 5000;
        this.setOrderType(orderType);
        if (pageNo == 1) {
            this.start = 0;
        } else {
            this.start = (pageNo - 1) * pageSize;
        }

        this.end = this.start + pageSize - 1;
    }

    public PageQuery(String orderBy, String orderType, int start, int pageSize) {
        this.totalCount = -1;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
        this.maxPageNo = 5000;
        this.setOrderType(orderType);
        if (start < 0) {
            start = 0;
        }

        this.start = start;
        this.pageNo = this.start % this.pageSize == 0 ? this.start / this.pageSize : this.start / this.pageSize + 1;
        this.end = this.start + pageSize - 1;
    }

    public PageQuery(int maxPageNo, String orderBy, String orderType, int start, int pageSize) {
        this.totalCount = -1;
        this.maxPageNo = maxPageNo;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
        this.setOrderType(orderType);
        if (start < 0) {
            start = 0;
        }

        this.start = start;
        this.pageNo = this.start % this.pageSize == 0 ? this.start / this.pageSize : this.start / this.pageSize + 1;
        this.end = this.start + pageSize - 1;
    }

    public int getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        if (this.maxPageNo == 0) {
            this.maxPageNo = 5000;
        }

        if (this.pageSize > this.maxPageNo) {
            this.pageSize = this.maxPageNo;
        }

        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.totalCount > 0 ? Math.min(this.end, this.totalCount - 1) : this.end;
    }

    public int getCount() {
        int count = this.getEnd() - this.getStart();
        return count < 0 ? 0 : count + 1;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public String getOrderBy() {
        return this.orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderType() {
        return this.orderType;
    }

    public void setOrderType(String orderType) {
        if (!StringUtils.isBlank(orderType)) {
            this.validateOrderType(orderType);
        }

        this.orderType = orderType;
    }

    private void validateOrderType(String orderType) {
        if (!"asc".equalsIgnoreCase(orderType) && !"desc".equalsIgnoreCase(orderType)) {
            throw new BusinessException("错误的排序类型，orderType：" + orderType);
        }
    }

    public String toString() {
        return "PageQuery [pageNo=" + this.pageNo + ", pageSize=" + this.pageSize + ", start=" + this.start + ", end=" + this.end + ", totalCount=" + this.totalCount + ", orderBy=" + this.orderBy + ", orderType=" + this.orderType + ", maxPageNo=" + this.maxPageNo + "]";
    }

    public static void main(String[] args) {
        printMsg(new PageQuery(1, 20));
        printMsg(new PageQuery(38, 20));
        PageQuery query = new PageQuery(38, 20);
        query.setTotalCount(750);
        printMsg(query);
        printMsg(new PageQuery("name", "asc", 81, 10));
        printMsg(new PageQuery("name", "asc", 80, 10));
    }

    private static void printMsg(PageQuery query) {
        System.out.println(query.toString() + "    count:" + query.getCount());
    }
}
