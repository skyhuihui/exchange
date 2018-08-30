package com.zag.vo.ex.user.req;

import com.zag.vo.BaseRequestVo;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.vo.ex.user.req
 * @ClassName: ${UserSend}
 * @Description: 用户填写手机号/邮箱 获的验证码
 * @Author: skyhuihui
 * @CreateDate: 2018/8/8 17:14
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/8 17:14
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public class UserSend extends BaseRequestVo {

    private String phone;

    private String mail;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}