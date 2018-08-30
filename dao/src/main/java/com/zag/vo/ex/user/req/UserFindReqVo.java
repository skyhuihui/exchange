package com.zag.vo.ex.user.req;

import com.zag.vo.BaseRequestVo;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.vo.ex.user.req
 * @ClassName: ${UserFindReqVo}
 * @Description: 手机号/邮箱登录
 * @Author: skyhuihui
 * @CreateDate: 2018/8/8 11:12
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/8 11:12
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public class UserFindReqVo extends BaseRequestVo {

    //邮箱号
    private String mailbox;

    //手机号
    private String phone;

    //密码
    private String password;

    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
