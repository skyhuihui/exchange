package com.zag.vo.ex.user.req;

import com.zag.enums.UserTypeEnums;
import com.zag.vo.BaseRequestVo;

import javax.persistence.Column;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.vo.ex.user.req
 * @ClassName: ${UserAddReqVo}
 * @Description: 添加新用户(用户注册 邮箱)
 * @Author: skyhuihui
 * @CreateDate: 2018/8/8 10:43
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/8 10:43
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public class UserAddMailReqVo extends BaseRequestVo {

    //邮箱号
    private String mailbox;

    //姓名
    private String name;

    //密码
    private String password;

    //验证码
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
