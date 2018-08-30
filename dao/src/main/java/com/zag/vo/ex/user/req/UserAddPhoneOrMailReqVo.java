package com.zag.vo.ex.user.req;

import com.zag.vo.BaseRequestVo;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.vo.ex.user.req
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: 作者姓名
 * @CreateDate: 2018/8/9 15:50
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/9 15:50
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public class UserAddPhoneOrMailReqVo extends BaseRequestVo {

    private String phone;

    private String mail;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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
