package com.zag.service.ex.user;


import com.zag.vo.ex.user.req.UserAddMailReqVo;
import com.zag.vo.ex.user.req.UserAddPhoneOrMailReqVo;
import com.zag.vo.ex.user.req.UserAddPhoneReqVo;
import com.zag.vo.ex.user.req.UserFindReqVo;
import com.zag.vo.ex.user.resp.UserRespVo;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.service.ex.user
 * @ClassName: UserService
 * @Description: 用户service
 * @Author: skyhuihui
 * @CreateDate: 2018/8/8 10:37
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/8 10:37
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public interface UserService {

    /**
     * 添加用户邮箱注册
     * @author skyhuihui
     * @version V1.0
     * @param reqVo
     * @return void
     */
    UserRespVo insertMailUser(UserAddMailReqVo reqVo);

    /**
     * 添加用户手机号注册
     * @author skyhuihui
     * @version V1.0
     * @param reqVo
     * @return void
     */
    UserRespVo insertPhoneUser(UserAddPhoneReqVo reqVo);

    /**
     * 查询用户 登录
     * @author skyhuihui
     * @version V1.0
     * @param reqVo
     * @return UserRespVo
     */
    UserRespVo findUser(UserFindReqVo reqVo);

    /**
     * 查询用户
     * @author skyhuihui
     * @version V1.0
     * @param id
     * @return UserRespVo
     */
    UserRespVo findUserById(Long id);

    /**
     * 用户绑定邮箱/手机号  一个邮箱手机号只能用一次
     * @author skyhuihui
     * @version V1.0
     * @param reqVo
     * @return UserRespVo
     */
    UserRespVo addPhoneOrMail(UserAddPhoneOrMailReqVo reqVo);
}
