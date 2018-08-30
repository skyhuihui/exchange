package com.zag.vo.web3.resp;

import java.io.Serializable;

/**
 * @ProjectName: web3-zag
 * @Package: com.zag.vo.web3.resp
 * @ClassName: ArticleRespVo
 * @Description: java类作用描述
 * @Author: skyhuihui
 * @CreateDate: 2018/8/2 16:58
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/2 16:58
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public class ArticleRespVo implements Serializable {

    private Long id;

    private String account;

    private String content;

    private String time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
