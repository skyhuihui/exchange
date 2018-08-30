package com.zag.vo.ex.account;

import com.zag.vo.BaseRequestVo;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.vo.ex.account
 * @ClassName: ${WithdrawReqVo}
 * @Description: 用户提币请求数据
 * @Author: skyhuihui
 * @CreateDate: 2018/8/24 15:37
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/24 15:37
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public class EthWithdrawReqVo extends BaseRequestVo {

    public String account;

    public Double amount;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
