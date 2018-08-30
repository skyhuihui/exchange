package com.zag.service.ex.account;

import com.alibaba.fastjson.JSONObject;
import com.zag.core.util.FileUtil;
import com.zag.core.web3_eth.Erc20TokenClient;
import com.zag.core.web3_eth.Erc20Withdraw;
import com.zag.core.web3_eth.EthWithdraw;
import com.zag.db.mysql.po.web3.*;
import com.zag.db.mysql.repository.ex.CurrencyAllRepository;
import com.zag.db.mysql.repository.ex.CurrencyUserRepository;
import com.zag.db.mysql.repository.ex.TransactionRepository;
import com.zag.db.mysql.repository.ex.UserAccountRepository;
import com.zag.enums.TransactionTypeEnums;
import com.zag.service.AbstractBaseService;
import com.zag.service.IdService;
import com.zag.vo.BaseRequestVo;
import com.zag.vo.ex.account.Erc20WithdrawReqVo;
import com.zag.vo.ex.account.EthWithdrawReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Objects;
/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.service.ex.account
 * @ClassName: ${AccountServiceImpl}
 * @Description: impl
 * @Author: skyhuihui
 * @CreateDate: 2018/8/24 14:40
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/24 14:40
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
@Service
public class AccountServiceImpl extends AbstractBaseService implements AccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private CurrencyAllRepository currencyAllRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CurrencyUserRepository currencyUserRepository;

    @Override
    public String findUserAccount(BaseRequestVo reqVo) {
        if (logger.isDebugEnabled()) {
            logger.debug("获取用户以太坊地址-------请求vo: {}", reqVo);
        }
        UserAccountPo userAccountPo = userAccountRepository.findByUserPo(reqVo.getUserId());
        logger.info(userAccountPo.getUserPo().getId()+"  参数  "+userAccountPo.getEthAccount());
        String keystore = FileUtil.readToString("E:\\Wallet\\"+userAccountPo.getUserPo().getId()+"\\eth\\"+userAccountPo.getEthAccount());
        JSONObject jsStr = JSONObject.parseObject(keystore);
        if (logger.isDebugEnabled()) {
            logger.debug("获取用户以太坊地址-------返回Vo: {}", "0x"+jsStr.get("address").toString());
        }
        return "0x"+jsStr.get("address").toString();
    }

    @Override
    public String updateUserEthAccount(EthWithdrawReqVo reqVo) throws IOException {
        if (logger.isDebugEnabled()) {
            logger.debug("用户提现eth-------请求vo: {}", reqVo);
        }
        CurrencyAllPo currencyAllPo = currencyAllRepository.findByaddress("0");
        if(Objects.isNull(currencyAllPo)){
            return null;
        }
        String hash = EthWithdraw.ethTokenTransaction(reqVo.getAccount(), reqVo.getAmount());
        String address = findUserAccount(reqVo);
        insertTransaction(TransactionTypeEnums.Withdraw, reqVo.getAmount(), address, "0", hash);
        UserPo userPo = new UserPo();
        userPo.setId(reqVo.getUserId());
        CurrencyUserPo currencyUserPo = currencyUserRepository.findByUserPoAndCurrencyAllPo(userPo, currencyAllPo);
        currencyUserPo.setAmont(currencyUserPo.getAmont() - reqVo.getAmount());
        currencyUserRepository.save(currencyUserPo);

        if (logger.isDebugEnabled()) {
            logger.debug("用户提现eth-------返回Vo: {}", hash);
        }
        return hash;
    }

    @Override
    public String updateUserErc20Account(Erc20WithdrawReqVo reqVo) throws IOException {
        if (logger.isDebugEnabled()) {
            logger.debug("用户提现erc20代币-------请求vo: {}", reqVo);
        }
        CurrencyAllPo currencyAllPo = currencyAllRepository.findByaddress(reqVo.contractAddress);
        // 获得代币精度
        int len = Erc20TokenClient.getTokenDecimals(reqVo.contractAddress);
        if(Objects.isNull(currencyAllPo)){
            return null;
        }
        String hash = Erc20Withdraw.Erc20TokenTransaction(reqVo.contractAddress, reqVo.getAccount(), reqVo.amount, len);
        String address = findUserAccount(reqVo);
        insertTransaction(TransactionTypeEnums.Withdraw, reqVo.getAmount(), address, reqVo.contractAddress, hash);
        UserPo userPo = new UserPo();
        userPo.setId(reqVo.getUserId());
        CurrencyUserPo currencyUserPo = currencyUserRepository.findByUserPoAndCurrencyAllPo(userPo, currencyAllPo);
        currencyUserPo.setAmont(currencyUserPo.getAmont() - reqVo.getAmount());
        currencyUserRepository.save(currencyUserPo);
        if (logger.isDebugEnabled()) {
            logger.debug("用户提现erc20代币-------返回Vo: {}", hash);
        }
        return hash;
    }

    // 交易记录 保存数据库(提币等)
    @Override
    public void insertTransaction(TransactionTypeEnums typeEnums, Double amount, String address, String tokenAddress, String hash){
        TransactionPo transactionPo = new TransactionPo();
        IdService idService=new IdService(0);
        transactionPo.setId(idService.nextId());
        transactionPo.setTransactionTypeEnums(typeEnums);
        transactionPo.setAmont(amount);
        transactionPo.setHash(hash);
        UserAccountPo userAccountPo = userAccountRepository.findByAddress(address);
        if(Objects.nonNull(userAccountPo)){
             transactionPo.setUserPo(userAccountPo.getUserPo());
        }
        CurrencyAllPo currencyAllPo = currencyAllRepository.findByaddress(tokenAddress);
        if(Objects.nonNull(currencyAllPo)){
            transactionPo.setCurrencyAllPo(currencyAllPo);
        }
        transactionRepository.save(transactionPo);
    }
}
