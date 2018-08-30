package com.zag.vo.web3.req;

import com.zag.constant.Constant;
import com.zag.vo.BaseRequestVo;

/**
 * @ProjectName: web3-zag
 * @Package: com.zag.vo.web3.req
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: 作者姓名
 * @CreateDate: 2018/8/3 15:27
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/3 15:27
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public class ArticleAddReqVo  extends BaseRequestVo {
    //账户
    private String account;

    //信息
    private String content;

    //time
    private String time;

    //页码
    private Integer page= Constant.DEFAULT_PAGE;

    //数位
    private Integer size = Constant.DEFAULT_PAGE_SIZE;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
