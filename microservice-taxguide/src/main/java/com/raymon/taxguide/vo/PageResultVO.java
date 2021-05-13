package com.raymon.taxguide.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageResultVO<T> implements Serializable {

    private static final long serialVersionUID = -6661476124068094221L;

    private int pageNo;
    private int pageSize;
    private long total;
    List<T> result = new ArrayList<T>();

    public PageResultVO(int pageNo, int pageSize, long total, List<T> t) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.total = total;
        this.result = t;
    }

    public PageResultVO() {
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

}
