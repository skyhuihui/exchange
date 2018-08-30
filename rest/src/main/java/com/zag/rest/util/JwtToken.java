package com.zag.rest.util;

import com.zag.enums.UserExamineEnums;
import com.zag.enums.UserTypeEnums;
import com.zag.service.ex.user.UserService;
import com.zag.vo.ex.user.resp.JwtUser;
import com.zag.vo.ex.user.resp.UserRespVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.rest.util
 * @ClassName: ${JwtToken}
 * @Description: Jwt验证
 * @Author: skyhuihui
 * @CreateDate: 2018/8/8 15:21
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/8 15:21
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public class JwtToken {

    /**
     * jwt验证过期时间
     * @author skyhuihui
     * @version V1.0
     * @return boolean
     */
    public Boolean jwtToken(String token){
        JwtUser jwtUser = JwtUnsign.unsign(token, JwtUser.class);
        if(Objects.nonNull(jwtUser)){
            //表示token合法
            return true;
        }else{
            //token不合法或者过期
            return false;
        }
    }

    /**
    * jwt验证权限 是否实名认证
    * @author skyhuihui
    * @version V1.0
    * @return boolean
    */
    public Boolean jwtUserType(String token){
        JwtUser jwtUser = JwtUnsign.unsign(token, JwtUser.class);
        if(Objects.nonNull(jwtUser)){
            System.out.println(jwtUser.getUserExamineEnums() +"   是否经过审核"+ UserExamineEnums.AuditSuccess );
            if(jwtUser.getUserExamineEnums().equals(UserExamineEnums.AuditSuccess)){
                return true;
            }
            return false;
        }else{
            //token不合法或者过期
            return false;
        }
    }

}
