package com.jung.finance.bean;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/3/20. 上午12:09
 *
 *
 */
public class Counter {
    private int pageSize;

    private int total;

    private int pageCount;

    private int pageIndex;

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return this.total;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

}