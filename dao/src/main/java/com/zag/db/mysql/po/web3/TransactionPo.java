package com.zag.db.mysql.po.web3;

import com.zag.enums.TransactionTypeEnums;
import com.zag.support.jpa.po.BaseEntity;

import javax.persistence.*;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.db.mysql.po.web3
 * @ClassName: ${TransactionPo}
 * @Description: 交易记录
 * @Author: skyhuihui
 * @CreateDate: 2018/8/28 10:26
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/28 10:26
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
@Entity
@Table(name = "ex_transcation")
public class TransactionPo  extends BaseEntity {
    /** 币种 */
    @ManyToOne
    @JoinColumn(name = "currencyall_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CurrencyAllPo currencyAllPo;

    /** 所属用户 */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserPo userPo;

    @Column(columnDefinition = "varchar(500) COMMENT '交易hash'")
    private String hash;

    private Double amont;

    @Column(columnDefinition = "Integer(10) COMMENT '交易类型'")
    private TransactionTypeEnums transactionTypeEnums;

    public CurrencyAllPo getCurrencyAllPo() {
        return currencyAllPo;
    }

    public void setCurrencyAllPo(CurrencyAllPo currencyAllPo) {
        this.currencyAllPo = currencyAllPo;
    }

    public UserPo getUserPo() {
        return userPo;
    }

    public void setUserPo(UserPo userPo) {
        this.userPo = userPo;
    }

    public Double getAmont() {
        return amont;
    }

    public void setAmont(Double amont) {
        this.amont = amont;
    }

    public TransactionTypeEnums getTransactionTypeEnums() {
        return transactionTypeEnums;
    }

    public void setTransactionTypeEnums(TransactionTypeEnums transactionTypeEnums) {
        this.transactionTypeEnums = transactionTypeEnums;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
