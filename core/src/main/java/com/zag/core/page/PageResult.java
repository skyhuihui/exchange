package com.zag.core.page;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.List;

/**
 * @author stone
 * @date 2016/12/22
 */
public class PageResult<E> implements Serializable {

    private static final long serialVersionUID = 1048029186252168978L;

    private int currentPage = 0;

    private int size = 10;

    private List<E> result;

    private int total;

    private boolean hasNext;

    public PageResult(int currentPage, int size, long total, List<E> result) {
        Preconditions.checkArgument(result != null);
        this.currentPage = currentPage;
        this.size = size;
        this.result = result;
        this.total = (int) total;
    }

    //用于 dubbo 序列化,平时不要使用
    public PageResult() {
    }

    public List<E> getResult() {
        return result;
    }

    public void setResult(List<E> result) {
        this.result = result;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }
}
