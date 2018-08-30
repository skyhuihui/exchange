package com.zag.db.mysql.po.web3;

import com.zag.support.jpa.po.BaseEntity;

import javax.persistence.*;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.db.mysql.po.web3
 * @ClassName: ${CurrencyUserPo}
 * @Description: 用户对应的币 所拥有数量
 * @Author: skyhuihui
 * @CreateDate: 2018/8/27 18:17
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/27 18:17
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
@Entity
@Table(name = "ex_currency_user")
public class CurrencyUserPo extends BaseEntity {

    /** 币种 */
    @ManyToOne
    @JoinColumn(name = "currencyall_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CurrencyAllPo currencyAllPo;

    /** 所属用户 */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserPo userPo;

    private Double amont;

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
}
