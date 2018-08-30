package com.zag.vo.ex.user.req;

import com.zag.vo.BaseRequestVo;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.vo.ex.user.req
 * @ClassName: ${UserAddPhoneReqVo}
 * @Description: 手机号注册
 * @Author: skyhuihui
 * @CreateDate: 2018/8/8 10:49
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/8 10:49
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public class UserAddPhoneReqVo extends BaseRequestVo {

    //手机号
    private String phone;

    //姓名
    private String name;

    //密码
    private String password;

    //地区 （+86）
    private Integer area;

    //验证码
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

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }
}
