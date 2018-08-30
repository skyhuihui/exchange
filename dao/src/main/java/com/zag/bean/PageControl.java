package com.zag.bean;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

/**
 * 分页对象
 *
 * @author stone
 */
public class PageControl implements Serializable {

    private static int PAGESIZE = 50;

    /**
     * 每页对象数
     */
    private int pageSize = PAGESIZE;

    /**
     * 页数
     */
    private int pageCount = 0;

    /**
     * 对象数
     */
    private int count = 0;

    /**
     * 当前页
     */
    private int currentPage = 1;

    /**
     * 当前页开始的记录的位置
     */
    private int begin = 0;

    /**
     * 当前页结束的记录的位置
     */
    private int end = 0;

    /**
     * 结果
     */
    private List list = null;

    private String appendParams = "";



    /**
     * 当前快近所在页面
     */
    private int currentSkip = 1;

    private int skipSize = 5;

    public PageControl() {

    }

    public PageControl(int defaultPageSize) {
        this.pageSize = defaultPageSize;
    }

    public PageControl(int defaultPageSize, int page) {
        this.pageSize = defaultPageSize;
        this.currentPage = page;
    }

    public PageControl(HttpServletRequest request) {
        init(request);
    }


    public PageControl(HttpServletRequest request, int defaultPageSize) {
        this.pageSize = defaultPageSize;
        init(request);
    }

    public PageControl(HttpServletRequest request, int defaultPageSize, int page) {
        this.pageSize = defaultPageSize;
        this.currentPage = page;
        init(request);
    }

    protected void init(HttpServletRequest request) {
        currentPage = 1;
        String page = request.getParameter("page");
        String s_pageSize = request.getParameter("pageSize");
        if (page != null) {
            try {
                currentPage = Integer.valueOf(page).intValue();
                if (currentPage <= 0) {
                    currentPage = 1;
                }
            } catch (Exception e) {
            }
        }
        if (s_pageSize != null) {
            try {
                if (Integer.valueOf(s_pageSize).intValue() > 0) {
                    this.pageSize = Integer.valueOf(s_pageSize).intValue();
                }
            } catch (Exception e) {
            }
        }
        try {
            appendParams = request.getQueryString();
        } catch (Exception e) {
        }

        if (appendParams != null) {
            appendParams = appendParams.replaceAll("page=" + getCurrentPage(), "");
            appendParams = appendParams.replaceAll("&pageSize=" + getPageSize(), "");
            appendParams = appendParams.replaceAll("&pageSize=" + getPageSize(), "");
            appendParams = appendParams.replaceAll("[&]+", "&");
            appendParams = StringUtils.removeEnd(appendParams, "&");
        } else {
            appendParams = "";
        }

        request.setAttribute("pageControl", this);
    }


    /**
     * 返回当前页
     *
     * @return 当前页
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * 得到页数
     *
     * @return　页数
     */
    public int getPageCount() {
        return pageCount;
    }

    /**
     * 得到每页记录条数
     *
     * @return 每页记录条数
     */
    public int getPageSize() {
        return this.pageSize;
    }

    /**
     * 得到记录条数
     *
     * @return 记录条数
     */
    public int getCount() {
        return count;
    }

    /**
     * 得到当前页的记录开始位置
     *
     * @return 开始位置
     */
    public int getBegin() {
        return begin;
    }

    /**
     * 得到记录的结束位置
     *
     * @return 结束位置
     */
    public int getEnd() {
        return end;
    }

    public List getList() {
        return list;
    }

    public String getAppendParams() {
        return appendParams;
    }

    /**
     * 设置记录条数
     *
     * @param totalCount int
     */
    public void setCount(int totalCount) {
        if (totalCount <= 0) {
            return;
        }
        this.count = totalCount;
        this.pageCount = totalCount / pageSize + ((totalCount % pageSize == 0) ? 0 : 1);
        if (currentPage > pageCount) {
            currentPage = pageCount;
        }
        if (currentPage <= 0) {
            currentPage = 1;
        }

        begin = (currentPage - 1) * pageSize;
        end = currentPage * pageSize;
        if (end >= totalCount) {
            end = totalCount;
        }

        currentSkip = (currentPage / skipSize) * skipSize + 1;
        if (currentPage % skipSize == 0) {
            currentSkip = currentSkip - skipSize;
        }

    }

    /**
     * 是否可以到第一页
     *
     * @return boolean
     */
    public boolean getCanGoFirst() {
        return (this.currentPage > 1);
    }

    /**
     * 是否可以到前一页
     *
     * @return boolean
     */
    public boolean getCanGoPrevious() {
        return (this.currentPage > 1);
    }

    /**
     * 是否可以到下一页
     *
     * @return boolean
     */
    public boolean getCanGoNext() {
        return (this.currentPage < this.pageCount);
    }

    /**
     * 是否可以到最后一页
     *
     * @return boolean
     */
    public boolean getCanGoLast() {
        return (this.currentPage < this.pageCount);
    }

    /**
     * 得到前一页页码
     *
     * @return int
     */
    public int getPrevious() {
        return this.currentPage > 1 ? this.currentPage - 1 : 1;
    }

    /**
     * 得到下一页页码
     *
     * @return int
     */
    public int getNext() {
        return this.currentPage < this.pageCount ? this.currentPage + 1 : this.pageCount;
    }

    /**
     * 填充对象
     *
     * @param objectList List
     */
    public void setAll(List objectList) {
        if (objectList != null) {
            this.setCount(objectList.size());
            list = objectList.subList(this.getBegin(), this.getEnd());
        } else {
            this.setCount(0);
        }
    }

    /**
     * @param list List
     */
    public void setList(List list) {
        this.list = list;
    }

    public void setAppendParams(String appendParams) {
        this.appendParams = appendParams;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * 是否可以向前快进
     *
     * @return boolean
     */
    public boolean getCanSkipPrevious() {
        return getSkipPrevious() > 0;
    }

    /**
     * 得到向前快近的页码
     *
     * @return int
     */
    public int getSkipPrevious() {
        return this.currentSkip - skipSize;
    }

    /**
     * 是否可以向后快进
     *
     * @return boolean
     */
    public boolean getCanSkipNext() {
        return (getSkipNext() <= this.pageCount);
    }

    /**
     * 得到向后快近的页码
     *
     * @return int
     */
    public int getSkipNext() {
        return this.currentSkip + skipSize;
    }

    /**
     * 得到当前显示的页码
     *
     * @return int[]
     */
    public int[] getCurrentSkipPageNumbers() {
        int count = skipSize;
        if (currentSkip + skipSize > pageCount) {
            count = pageCount - currentSkip + 1;
        }
        int[] Result = new int[count];
        for (int i = 0; i < count; i++) {
            Result[i] = currentSkip + i;
        }
        return Result;
    }

    public void reCount(int page, int count) {
        this.currentPage = page;
        this.setCount(count);
    }

}
