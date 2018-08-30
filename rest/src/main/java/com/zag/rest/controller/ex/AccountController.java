package com.zag.rest.controller.ex;

import com.zag.rest.controller.BaseController;
import com.zag.rest.util.JwtToken;
import com.zag.service.ex.account.AccountService;
import com.zag.service.ex.account.AccountTxlistService;
import com.zag.support.web.handler.JSONResult;
import com.zag.vo.BaseRequestVo;
import com.zag.vo.ex.account.Erc20WithdrawReqVo;
import com.zag.vo.ex.account.EthWithdrawReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.rest.controller.ex
 * @ClassName: ${AccountController}
 * @Description: 用户区块链账户控制器
 * @Author: skyhuihui
 * @CreateDate: 2018/8/24 14:21
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/24 14:21
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
@RestController
@RequestMapping(value = "app/api/v1/account")
public class AccountController extends BaseController {

    private JwtToken jwtToken = new JwtToken();
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountTxlistService accountTxlistService;

    /**
     * 获取用户以太坊地址
     * @author skyhuihui
     * @version V1.0
     * @return JSONResult
     */
    @ResponseBody
    @RequestMapping(value = "getEthAccount")
    public JSONResult getEthAccount(HttpServletRequest request) {
        if(!jwtToken.jwtToken(request.getHeader("jwtToken"))){
            return new JSONResult(-1, "登陆信息已过期请重新登录");
        }
        BaseRequestVo reqVo = super.buildVo(request, BaseRequestVo.class);
        return new JSONResult(accountService.findUserAccount(reqVo));
    }

    /**
     * 用户提现 以太币
     * @author skyhuihui
     * @version V1.0
     * @return JSONResult
     */
    @ResponseBody
    @RequestMapping(value = "ethWithdraw")
    public JSONResult ethWithdraw(HttpServletRequest request) throws IOException {
        if(!jwtToken.jwtUserType(request.getHeader("jwtToken"))){
            return new JSONResult(-1, "请实名认证");
        }
        EthWithdrawReqVo reqVo = super.buildVo(request, EthWithdrawReqVo.class);
        return new JSONResult(accountService.updateUserEthAccount(reqVo));
    }

    /**
     * 用户提现 erc20代币
     * @author skyhuihui
     * @version V1.0
     * @return JSONResult
     */
    @ResponseBody
    @RequestMapping(value = "erc20Withdraw")
    public JSONResult erc20Withdraw(HttpServletRequest request) throws IOException {
        if(!jwtToken.jwtUserType(request.getHeader("jwtToken"))){
            return new JSONResult(-1, "请实名认证");
        }
        Erc20WithdrawReqVo reqVo = super.buildVo(request, Erc20WithdrawReqVo.class);
        return new JSONResult(accountService.updateUserErc20Account(reqVo));
    }

    /**
     * 检测用户充值
     * @author skyhuihui
     * @version V1.0
     * @return void
     */
    @Scheduled(cron="0 0/5 * * * ?")
    public void txList() throws IOException {
        accountTxlistService.txEthErc20List();
        accountTxlistService.txEthList();
    }
}
