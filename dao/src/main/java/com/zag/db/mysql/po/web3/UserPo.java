package com.zag.db.mysql.po.web3;

import com.zag.enums.UserExamineEnums;
import com.zag.enums.UserTypeEnums;
import com.zag.support.jpa.po.BaseEntity;

import javax.persistence.*;
import java.util.List;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.db.mysql.po.web3
 * @ClassName: UserPo
 * @Description: 用户表
 * @Author: skyhuihui
 * @CreateDate: 2018/8/8 10:01
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/8 10:01
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
@Entity
@Table(name = "ex_user")
public class UserPo extends BaseEntity {

    @Column(columnDefinition = "varchar(20) COMMENT '手机号'")
    private String phone;

    @Column(columnDefinition = "varchar(30) COMMENT '邮箱'")
    private String mailbox;

    @Column(columnDefinition = "varchar(50) COMMENT '用户名'")
    private String name;

    @Column(columnDefinition = "varchar(50) COMMENT '密码'")
    private String password;

    @Column(columnDefinition = "Integer(10) COMMENT '是否实名认证'")
    private UserTypeEnums type = UserTypeEnums.DISABLE;

    @Column(columnDefinition = "Integer(10) COMMENT '审核状态'")
    private UserExamineEnums userExamineEnums = UserExamineEnums.Unaudited;

    @Column(columnDefinition = "Integer COMMENT '国家(+86)'")
    private Integer area;

    @Column(columnDefinition = "varchar(500) COMMENT '身份证背面'")
    private String idCardBack;

    @Column(columnDefinition = "varchar(500) COMMENT '身份证正面'")
    private String idCardFront;

    @Column(columnDefinition = "varchar(500) COMMENT '手持身份证照片'")
    private String handIdCard;

    /** 用户对应币种 （由于对余额处理比较频繁，不采用多对多，根据个人习惯来吧） */
    @OneToMany(mappedBy = "userPo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CurrencyUserPo> currencyUserPos;

    /** 用户对应币种余额变动记录  */
    @OneToMany(mappedBy = "userPo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransactionPo> transactionPos;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserTypeEnums getType() {
        return type;
    }

    public void setType(UserTypeEnums type) {
        this.type = type;
    }

    public UserExamineEnums getUserExamineEnums() {
        return userExamineEnums;
    }

    public void setUserExamineEnums(UserExamineEnums userExamineEnums) {
        this.userExamineEnums = userExamineEnums;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getIdCardBack() {
        return idCardBack;
    }

    public void setIdCardBack(String idCardBack) {
        this.idCardBack = idCardBack;
    }

    public String getIdCardFront() {
        return idCardFront;
    }

    public void setIdCardFront(String idCardFront) {
        this.idCardFront = idCardFront;
    }

    public String getHandIdCard() {
        return handIdCard;
    }

    public void setHandIdCard(String handIdCard) {
        this.handIdCard = handIdCard;
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

