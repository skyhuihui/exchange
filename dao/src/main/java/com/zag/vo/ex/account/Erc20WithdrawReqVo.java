package com.zag.vo.ex.account;

import com.zag.vo.BaseRequestVo;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.vo.ex.account
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: 作者姓名
 * @CreateDate: 2018/8/24 15:52
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/24 15:52
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public class Erc20WithdrawReqVo extends BaseRequestVo {

    public String account;

    public Double amount;

    public String contractAddress;

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

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
