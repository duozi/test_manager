/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.xn.common.utils;

import com.alibaba.fastjson.JSON;
import java.io.Serializable;
import java.util.Map;

public class PageInfo implements Serializable {
    private static final long serialVersionUID = -1047949160793143855L;
    protected int pageSize = 10;

    protected int currentPage = 1;

    protected int totalPages = 0;

    protected int totalRows = 0;

    protected int pageStartRow = 0;

    protected int pageEndRow = 0;

    protected boolean pagination = false;

    boolean hasNextPage = false;

    boolean hasPreviousPage = false;
    Object obj;
    private String params;
    private boolean needCount = true;

    public PageInfo(int rows, int pageSize) {
        init(rows, pageSize);
    }

    public PageInfo(int currentPage) {
        this.currentPage = currentPage;
        this.pagination = true;
    }

    public PageInfo() {}

    public void init(int rows, int pageSize) {
        this.pageSize = pageSize;

        this.totalRows = rows;

        if (this.totalRows % pageSize == 0)
            this.totalPages = (this.totalRows / pageSize);
        else
            this.totalPages = (this.totalRows / pageSize + 1);
    }

    public void init(int rows, int pageSize, int currentPage) {
        this.pageSize = pageSize;

        this.totalRows = rows;

        if (this.totalRows % pageSize == 0)
            this.totalPages = (this.totalRows / pageSize);
        else {
            this.totalPages = (this.totalRows / pageSize + 1);
        }
        if (currentPage != 0)
            gotoPage(currentPage);
    }

    public void putTo(Map<String, Object> map) {
        map.put("page", this);
    }

    private void calculatePage() {
        if (this.currentPage - 1 > 0)
            this.hasPreviousPage = true;
        else {
            this.hasPreviousPage = false;
        }

        if (this.currentPage >= this.totalPages)
            this.hasNextPage = false;
        else {
            this.hasNextPage = true;
        }

        if (this.currentPage * this.pageSize < this.totalRows) {
            this.pageEndRow = (this.currentPage * this.pageSize);
            this.pageStartRow = (this.pageEndRow - this.pageSize);
        } else {
            this.pageEndRow = this.totalRows;
            this.pageStartRow = (this.pageSize * (this.totalPages - 1));
        }
    }

    public void gotoPage(int page) {
        if ((this.currentPage > this.totalPages) && (this.currentPage > 1))
            this.currentPage = Math.max(1, this.totalPages);
        else {
            this.currentPage = Math.max(1, page);
        }

        calculatePage();
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public boolean isHasNextPage() {
        return this.hasNextPage;
    }

    public boolean isHasPreviousPage() {
        return this.hasPreviousPage;
    }

    public int getPageEndRow() {
        return this.pageEndRow;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public int getPageStartRow() {
        return this.pageStartRow;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public int getTotalRows() {
        return this.totalRows;
    }

    public void setTotalPages(int i) {
        this.totalPages = i;
    }

    public void setCurrentPage(int i) {
        this.currentPage = i;
    }

    public void setHasNextPage(boolean b) {
        this.hasNextPage = b;
    }

    public void setHasPreviousPage(boolean b) {
        this.hasPreviousPage = b;
    }

    public void setPageEndRow(int i) {
        this.pageEndRow = i;
    }

    public PageInfo setPageSize(int i) {
        this.pageSize = i;
        return this;
    }

    public void setPageStartRow(int i) {
        this.pageStartRow = i;
    }

    public void setTotalRows(int i) {
        this.totalRows = i;
    }

    public boolean isPagination() {
        return this.pagination;
    }

    public PageInfo setPagination(boolean pagination) {
        this.pagination = pagination;
        return this;
    }

    public String getParams() {
        return this.params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String toString() {
        return JSON.toJSONString(this);
    }

    public boolean isNeedCount() {
        return this.needCount;
    }

    public void setNeedCount(boolean needCount) {
        this.needCount = needCount;
    }
}