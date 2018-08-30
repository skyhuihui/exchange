package com.zag.service.ex.account;

import com.zag.enums.TransactionTypeEnums;
import com.zag.vo.BaseRequestVo;
import com.zag.vo.ex.account.Erc20WithdrawReqVo;
import com.zag.vo.ex.account.EthWithdrawReqVo;

import java.io.IOException;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.service.ex.account
 * @ClassName: ${AccountService}
 * @Description: 账户处理
 * @Author: skyhuihui
 * @CreateDate: 2018/8/24 14:40
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/24 14:40
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public interface AccountService {

    /**
     * 获得用户以太坊地址
     * @author skyhuihui
     * @version V1.0
     * @param reqVo
     * @return String
     */
    String findUserAccount(BaseRequestVo reqVo);

    /**
     * 以太币提现
     * @author skyhuihui
     * @version V1.0
     * @param reqVo
     * @return String
     */
    String updateUserEthAccount(EthWithdrawReqVo reqVo) throws IOException;

    /**
     * erc20代币提现
     * @author skyhuihui
     * @version V1.0
     * @param reqVo
     * @return String
     */
    String updateUserErc20Account(Erc20WithdrawReqVo reqVo) throws IOException;

    /**
     * 交易记录保存进数据库（充值，提现等）
     * @author skyhuihui
     * @version V1.0
     * @return void
     */
    void insertTransaction(TransactionTypeEnums typeEnums, Double amount , String address, String tokenAddress, String hash);
}
