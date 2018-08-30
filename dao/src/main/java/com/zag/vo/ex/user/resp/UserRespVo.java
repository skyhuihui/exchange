package com.zag.vo.ex.user.resp;

import com.zag.enums.UserExamineEnums;
import com.zag.enums.UserTypeEnums;
import com.zag.vo.BaseRequestVo;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.vo.ex.user.resp
 * @ClassName: ${UserRespVo}
 * @Description: 用户登录返回
 * @Author: skyhuihui
 * @CreateDate: 2018/8/8 11:17
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/8 11:17
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public class UserRespVo extends BaseRequestVo {

    private Long id;

    private String phone;

    private String mailbox;

    private String name;

    private UserTypeEnums type;

    private UserExamineEnums userExamineEnums;

    private Integer area;

    private String idCardBack;

    private String idCardFront;

    private String handIdCard;

    private String jwtToken;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public UserTypeEnums getType() {
        return type;
    }

    @Override
    public void setType(UserTypeEnums type) {
        this.type = type;
    }

    @Override
    public UserExamineEnums getUserExamineEnums() {
        return userExamineEnums;
    }

    @Override
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
}
