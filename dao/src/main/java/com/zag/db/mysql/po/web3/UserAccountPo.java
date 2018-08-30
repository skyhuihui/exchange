package com.zag.db.mysql.po.web3;

import com.zag.support.jpa.po.BaseEntity;

import javax.persistence.*;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.db.mysql.po.web3
 * @ClassName: ${UserAccountPo}
 * @Description: 用户区块链账户表
 * @Author: skyhuihui
 * @CreateDate: 2018/8/23 11:04
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/23 11:04
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
@Entity
@Table(name = "ex_user_account")
public class UserAccountPo extends BaseEntity {

    /** 用户 */
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserPo userPo;

    @Column(columnDefinition = "varchar(500) COMMENT '以太坊本地账户地址'")
    private String ethAccount;

    @Column(columnDefinition = "varchar(500) COMMENT '以太坊地址'")
    private String address;

    public UserPo getUserPo() {
        return userPo;
    }

    public void setUserPo(UserPo userPo) {
        this.userPo = userPo;
    }

    public String getEthAccount() {
        return ethAccount;
    }

    public void setEthAccount(String ethAccount) {
        this.ethAccount = ethAccount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
