package com.zag.db.mysql.po.web3;

import com.zag.support.jpa.po.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.db.mysql.po.web3
 * @ClassName: ${currency}
 * @Description: 交易所支持的 核心钱包 （eth,btc , usdt ，eos......）
 * @Author: skyhuihui
 * @CreateDate: 2018/8/27 18:03
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/27 18:03
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
@Entity
@Table(name = "ex_currency")
public class CurrencyPo extends BaseEntity {

    @Column(columnDefinition = "varchar(20) COMMENT '币种名称'")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
