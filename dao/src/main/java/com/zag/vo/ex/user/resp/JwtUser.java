package com.zag.vo.ex.user.resp;

import com.zag.enums.UserExamineEnums;
import com.zag.enums.UserTypeEnums;

import java.io.Serializable;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.vo.ex.user.resp
 * @ClassName: ${JwtUser}
 * @Description: jwt参数
 * @Author: skyhuihui
 * @CreateDate: 2018/8/9 11:26
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/9 11:26
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public class JwtUser implements Serializable {

    public JwtUser() {
    }

    public JwtUser(Long id, String name, UserTypeEnums type, UserExamineEnums userExamineEnums) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.userExamineEnums = userExamineEnums;
    }

    private Long id;

    private String name;

    private UserTypeEnums type;

    private UserExamineEnums userExamineEnums;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
