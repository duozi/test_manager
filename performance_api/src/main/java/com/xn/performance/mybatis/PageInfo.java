package com.xn.performance.mybatis;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by xn056839 on 2017/4/5.
 */



public class PageInfo implements Serializable {

    private static final long serialVersionUID = -1047949160793143855L;

    protected int pageSize = 10; // 每页默认10条数据

    protected int currentPage = 1; // 当前页

    protected int totalPages = 0; // 总页数

    protected int totalRows = 0; // 总数据数

    protected int pageStartRow = 0; // 每页的起始行数

    protected int pageEndRow = 0; // 每页显示数据的终止行数

    protected boolean pagination = false; // 是否分页

    boolean hasNextPage = false; // 是否有下一页

    boolean hasPreviousPage = false; // 是否有前一页

    Object obj; // 参数对象与返回对象

    private String params; // 请求参数的字符串形式，用于分页组件url拼接

    /**
     * 是否需要统计总数量,默认true
     */
    private boolean needCount = true;

    // List<Object> resultList; // 返回的结果
    // Map<String,Object> param; //查询入参

    public PageInfo(int rows, int pageSize) {
        this.init(rows, pageSize);
    }

    public PageInfo(int currentPage) {
        this.currentPage = currentPage;
        this.pagination = true;
    }

    public PageInfo() {

    }

    /**
     * 初始化分页参数:需要先设置totalRows
     */

    public void init(int rows, int pageSize) {

        this.pageSize = pageSize;

        this.totalRows = rows;

        if ((totalRows % pageSize) == 0) {
            totalPages = totalRows / pageSize;
        } else {
            totalPages = totalRows / pageSize + 1;
        }

    }

    public void init(int rows, int pageSize, int currentPage) {

        this.pageSize = pageSize;

        this.totalRows = rows;

        if ((totalRows % pageSize) == 0) {
            totalPages = totalRows / pageSize;
        } else {
            totalPages = totalRows / pageSize + 1;
        }
        if (currentPage != 0)
            gotoPage(currentPage);
    }

    public void putTo(Map<String, Object> map) {
        map.put("page", this);
    }

    /**
     * 计算当前页的取值范围：pageStartRow和pageEndRow
     */
    private void calculatePage() {
        if ((currentPage - 1) > 0) {
            hasPreviousPage = true;
        } else {
            hasPreviousPage = false;
        }

        if (currentPage >= totalPages) {
            hasNextPage = false;
        } else {
            hasNextPage = true;
        }

        if (currentPage * pageSize < totalRows) { // 判断是否为最后一页
            pageEndRow = currentPage * pageSize;
            pageStartRow = pageEndRow - pageSize;
        } else {
            pageEndRow = totalRows;
            pageStartRow = pageSize * (totalPages - 1);
        }

    }

    /**
     * 直接跳转到指定页数的页面
     *
     * @param page
     */
    public void gotoPage(int page) {
        if (currentPage > totalPages && currentPage > 1) {
            currentPage = Math.max(1, totalPages); // 当前页最小为1
        } else {
            currentPage = Math.max(1, page); // 当前页最小为1
        }

        calculatePage();

    }

    /**
     * @return
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * @param i
     */
    public void setCurrentPage(int i) {
        currentPage = i;
    }

    /**
     * @return
     */
    public boolean isHasNextPage() {
        return hasNextPage;
    }

    /**
     * @param b
     */
    public void setHasNextPage(boolean b) {
        hasNextPage = b;
    }

    /**
     * @return
     */
    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    /**
     * @param b
     */
    public void setHasPreviousPage(boolean b) {
        hasPreviousPage = b;
    }

    /**
     * @return
     */
    public int getPageEndRow() {
        return pageEndRow;
    }

    /**
     * @param i
     */
    public void setPageEndRow(int i) {
        pageEndRow = i;
    }

    /**
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param i
     */
    public PageInfo setPageSize(int i) {
        pageSize = i;
        return this;
    }

    /**
     * @return
     */
    public int getPageStartRow() {
        return pageStartRow;
    }

    /**
     * @param i
     */
    public void setPageStartRow(int i) {
        pageStartRow = i;
    }

    /**
     * @return
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * @param i
     */
    public void setTotalPages(int i) {
        totalPages = i;
    }

    /**
     * @return
     */
    public int getTotalRows() {
        return totalRows;
    }

    /**
     * @param i
     */
    public void setTotalRows(int i) {
        totalRows = i;
    }

    public boolean isPagination() {
        return pagination;
    }

    public PageInfo setPagination(boolean pagination) {
        this.pagination = pagination;
        return this;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public boolean isNeedCount() {
        return needCount;
    }

    /**
     * <pre>
     * 是否需要统计总记录数,默认为true.
     * 如果设置为false，将不会执行count sql，同时，pageInfo中的totalRows、totalPages 将会为0，hasNextPage也将一直为false
     *
     * </pre>
     *
     * @param needCount 默认为true
     * @date 2016年9月8日
     * @author Ternence
     */
    public void setNeedCount(boolean needCount) {
        this.needCount = needCount;
    }

}
