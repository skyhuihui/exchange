package com.zag.transfrom.ex;

import com.google.common.base.Function;
import com.zag.db.mysql.po.web3.UserPo;
import com.zag.vo.ex.user.resp.UserRespVo;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.transfrom.ex
 * @ClassName: ${UserPoToFindRespVo}
 * @Description: PoTo
 * @Author: skyhuihui
 * @CreateDate: 2018/8/8 11:37
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/8 11:37
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public class UserPoToFindRespVo implements Function<UserPo, UserRespVo> {

    @Override
    public UserRespVo apply(UserPo userPo) {
        UserRespVo userRespVo = new UserRespVo();
        userRespVo.setId(userPo.getId());
        userRespVo.setArea(userPo.getArea());
        userRespVo.setHandIdCard(userPo.getHandIdCard());
        userRespVo.setIdCardBack(userPo.getIdCardBack());
        userRespVo.setIdCardFront(userPo.getIdCardFront());
        userRespVo.setMailbox(userPo.getMailbox());
        userRespVo.setName(userPo.getName());
        userRespVo.setPhone(userPo.getPhone());
        userRespVo.setType(userPo.getType());
        userRespVo.setUserExamineEnums(userPo.getUserExamineEnums());
        return userRespVo;
    }
}
