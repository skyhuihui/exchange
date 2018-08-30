package com.zag.core.page;

import java.io.Serializable;

/**
 * @author stone
 * @date 2016/12/19
 */
public class PageableRequest implements Serializable {

    private static final long serialVersionUID = 9084950607751477752L;

    private int page = 0;

    private int size = 10;

    public PageableRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public PageableRequest() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
