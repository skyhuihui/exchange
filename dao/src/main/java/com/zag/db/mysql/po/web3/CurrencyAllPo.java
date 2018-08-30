package com.zag.db.mysql.po.web3;

import com.zag.support.jpa.po.BaseEntity;

import javax.persistence.*;
import java.util.List;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.db.mysql.po.web3
 * @ClassName: ${CurrencyAllPo}
 * @Description: 所有币种
 * @Author: skyhuihui
 * @CreateDate: 2018/8/27 18:09
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/27 18:09
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
@Entity
@Table(name = "ex_currency_all")
public class CurrencyAllPo extends BaseEntity {

    /** 所属币种 */
    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CurrencyPo currencyPo;

    @Column(columnDefinition = "Integer COMMENT '几位小数点（数字）'")
    private Integer decimals;

    @Column(columnDefinition = "varchar(200) COMMENT '币地址'")
    private String address;

    @Column(columnDefinition = "varchar(50) COMMENT '币全名'")
    private String name;

    @Column(columnDefinition = "varchar(50) COMMENT '币简称'")
    private String symbol;

    /** 币种对应的用户 （由于对余额处理比较频繁，不采用多对多，根据个人习惯来吧） */
    @OneToMany(mappedBy = "currencyAllPo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CurrencyUserPo> currencyUserPos;

    /** 用户对应币种余额变动记录  */
    @OneToMany(mappedBy = "currencyAllPo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransactionPo> transactionPos;

    public CurrencyPo getCurrencyPo() {
        return currencyPo;
    }

    public void setCurrencyPo(CurrencyPo currencyPo) {
        this.currencyPo = currencyPo;
    }

    public Integer getDecimals() {
        return decimals;
    }

    public void setDecimals(Integer decimals) {
        this.decimals = decimals;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public List<CurrencyUserPo> getCurrencyUserPos() {
        return currencyUserPos;
    }

    public void setCurrencyUserPos(List<CurrencyUserPo> currencyUserPos) {
        this.currencyUserPos = currencyUserPos;
    }

    public List<TransactionPo> getTransactionPos() {
        return transactionPos;
    }

    public void setTransactionPos(List<TransactionPo> transactionPos) {
        this.transactionPos = transactionPos;
    }
}
