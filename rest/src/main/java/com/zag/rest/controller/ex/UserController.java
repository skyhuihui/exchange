package com.zag.rest.controller.ex;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.google.common.base.Strings;
import com.zag.core.register.MailUtil;
import com.zag.core.register.SMSUtil;
import com.zag.rest.controller.BaseController;
import com.zag.rest.util.JwtToken;
import com.zag.service.ex.user.UserService;
import com.zag.support.web.handler.JSONResult;
import com.zag.vo.ex.user.req.*;
import com.zag.vo.ex.user.resp.UserRespVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.rest.controller.ex
 * @ClassName: UserController
 * @Description: 用户controller
 * @Author: skyhuihui
 * @CreateDate: 2018/8/8 10:35
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/8 10:35
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
@RestController
@RequestMapping(value = "app/api/v1/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    private JwtToken jwtToken = new JwtToken();

    /**
     * 发送手机验证码
     * @author skyhuihui
     * @version V1.0
     * @return JSONResult
     */
    @ResponseBody
    @RequestMapping(value = "sendSms")
    public JSONResult sendSms(HttpServletRequest request) throws ClientException {
        UserSend reqVo = super.buildVo(request, UserSend.class);
        HttpSession session = request.getSession();
        int code = (int)((Math.random()*9+1)*100000);
        if(!Strings.isNullOrEmpty(reqVo.getPhone())){
            if(Objects.nonNull(session.getAttribute(reqVo.getPhone()))){
                session.removeAttribute(reqVo.getPhone());
            }
            SendSmsResponse response = SMSUtil.sendSms(reqVo.getPhone(), code);
            logger.info("发送短信手机号为："+reqVo.getPhone()+" 短信接口返回的数据----------------" + "Code=" + response.getCode()
           + "Message=" + response.getMessage() + "RequestId=" + response.getRequestId() + "BizId=" + response.getBizId());
            session.setAttribute(reqVo.getPhone(), code);
            return new JSONResult(0,"发送成功");
        }else {
            return new JSONResult(-1,"发送失败，手机号不能为空");
        }
    }

    /**
     * 发送邮箱验证码
     * @author skyhuihui
     * @version V1.0
     * @return JSONResult
     */
    @ResponseBody
    @RequestMapping(value = "sendMail")
    public JSONResult sendMail(HttpServletRequest request) throws Exception {
        UserSend reqVo = super.buildVo(request, UserSend.class);
        HttpSession session = request.getSession();
        int code = (int)((Math.random()*9+1)*100000);
        if(!Strings.isNullOrEmpty(reqVo.getMail())){
            if(Objects.nonNull(session.getAttribute(reqVo.getMail()))){
                session.removeAttribute(reqVo.getMail());
            }
            MailUtil.sendActiveMail(reqVo.getMail(), code);
            logger.info("发送邮箱验证邮箱号为："+reqVo.getMail() +"验证码为"+ code);
            session.setAttribute(reqVo.getMail(), code);
            return new JSONResult(0,"发送成功");
        }else {
            return new JSONResult(-1,"发送失败，邮箱号不能为空");
        }
    }

    /**
     * 邮箱注册
     * @author skyhuihui
     * @version V1.0
     * @return JSONResult
     */
    @ResponseBody
    @RequestMapping(value = "insertMailUser")
    public JSONResult insertMailArticle(HttpServletRequest request) {
        UserAddMailReqVo reqVo = super.buildVo(request, UserAddMailReqVo.class);
        HttpSession session = request.getSession();
        logger.info("邮箱注册：session: "+session.getAttribute(reqVo.getMailbox())+"  发送验证码: "+reqVo.getCode());
        if(Strings.isNullOrEmpty(reqVo.getCode())) {
            return new JSONResult(-1, "验证码不能为空");
        }
        if(Objects.isNull(session.getAttribute(reqVo.getMailbox()))){
            return new JSONResult(-1,"验证码已过期");
        }
        if(!session.getAttribute(reqVo.getMailbox()).toString().equals(reqVo.getCode())){
            return new JSONResult(-1,"验证码不正确");
        }
        UserRespVo userRespVo = userService.insertMailUser(reqVo);
        if(Objects.nonNull(userRespVo)){
            return new JSONResult(userRespVo);
        }else {
            return new JSONResult(-1,"用户已存在");
        }
    }

    /**
     * 手机号注册
     * @author skyhuihui
     * @version V1.0
     * @return JSONResult
     */
    @ResponseBody
    @RequestMapping(value = "insertPhoneUser")
    public JSONResult insertPhoneArticle(HttpServletRequest request) {
        UserAddPhoneReqVo reqVo = super.buildVo(request, UserAddPhoneReqVo.class);
        HttpSession session = request.getSession();
        logger.info("手机号注册：session: "+session.getAttribute(reqVo.getPhone())+"  发送验证码: "+reqVo.getCode());
        if(Strings.isNullOrEmpty(reqVo.getCode())) {
            return new JSONResult(-1, "验证码不能为空");
        }
        if(Objects.isNull(session.getAttribute(reqVo.getPhone()))){
            return new JSONResult(-1,"验证码已过期");
        }
        if(!session.getAttribute(reqVo.getPhone()).toString().equals(reqVo.getCode())){
            return new JSONResult(-1,"验证码不正确");
        }
        UserRespVo respVo = userService.insertPhoneUser(reqVo);
        if(Objects.nonNull(respVo)){
            return new JSONResult(respVo);
        }else {
            return new JSONResult(-1,"用户已存在");
        }
    }

    /**
     * 手机号/邮箱 登录
     * @author skyhuihui
     * @version V1.0
     * @return JSONResult
     */
    @ResponseBody
    @RequestMapping(value = "findUser")
    public JSONResult findUser(HttpServletRequest request) {
        UserFindReqVo reqVo = super.buildVo(request, UserFindReqVo.class);
        return new JSONResult(userService.findUser(reqVo));
    }

    /**
     *  用户绑定邮箱/手机号  一个邮箱手机号只能用一次
     * @author skyhuihui
     * @version V1.0
     * @return JSONResult
     */
    @ResponseBody
    @RequestMapping(value = "addPhoneOrMail")
    public JSONResult addPhoneOrMail(HttpServletRequest request) {
        if(!jwtToken.jwtToken(request.getHeader("jwtToken"))){
            return new JSONResult(-1, "登陆信息已过期请重新登录");
        }
        UserAddPhoneOrMailReqVo reqVo = super.buildVo(request, UserAddPhoneOrMailReqVo.class);
        HttpSession session = request.getSession();
        logger.info("绑定手机号/邮箱：session: "+session.getAttribute(reqVo.getPhone())+"  发送验证码: "+reqVo.getCode());
        if(Strings.isNullOrEmpty(reqVo.getCode())||(Objects.isNull(session.getAttribute(reqVo.getPhone()))&&Objects.isNull(session.getAttribute(reqVo.getMail())))) {
            return new JSONResult(-1, "验证码不能为空或验证码已过期");
        }
        if(StringUtils.isNotEmpty(reqVo.getMail())){
            if(!session.getAttribute(reqVo.getMail()).toString().equals(reqVo.getCode())){
                return new JSONResult(-1,"验证码不正确");
            }
        }else{
            if(!session.getAttribute(reqVo.getPhone()).toString().equals(reqVo.getCode())){
                return new JSONResult(-1,"验证码不正确");
            }
        }
        return new JSONResult(userService.addPhoneOrMail(reqVo));
    }

}
