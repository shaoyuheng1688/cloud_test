package com.raymon.frame.vo;

/**
 * 结果集 （接口返回的data为对象，用于GSON）
 *
 * @param <T>
 */
public class PageVO<T> {
    private T page;

    public T getPage() {
        return page;
    }

    public void setPage(T page) {
        this.page = page;
    }
}
