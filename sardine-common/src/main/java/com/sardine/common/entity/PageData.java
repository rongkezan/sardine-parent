package com.sardine.common.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果集
 *
 * @author keith
 */
public class PageData<T> implements Serializable {

    private static final long serialVersionUID = -8940366960899264820L;

    /**
     * total record count
     */
    private long total;

    /**
     * page size
     */
    private long pageSize;

    /**
     * current page
     */
    private long current;

    /**
     * total pages
     */
    private long totalPage;

    /**
     * data records
     */
    private List<T> records;

    public PageData() {
    }

    public PageData(long total, long pageSize, long totalPage, long current, List<T> records) {
        this.total = total;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.current = current;
        this.records = records;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}
