package com.zag.db.mysql.po.web3;

import com.zag.support.jpa.po.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ProjectName: web3-zag
 * @Package: com.zag.db.mysql.po
 * @ClassName: ArticlePo
 * @Description: Article表 字段：id，用户地址，数据，时间
 * @Author: skyhuihui
 * @CreateDate: 2018/8/2 16:17
 * @UpdateUser: skyhuihui
 * @UpdateDate: 2018/8/2 16:17
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
@Entity
@Table(name = "web3_article")
public class ArticlePo extends BaseEntity {

    @Column(columnDefinition = "varchar(50) COMMENT '账户'")
    private String account;

    @Column(columnDefinition = "varchar(1000) COMMENT '信息'")
    private String content;

    @Column(columnDefinition = "varchar(20) COMMENT '时间戳'")
    private String time;

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
