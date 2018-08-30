package com.zag.service.ex.account;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.zag.core.util.FileUtil;
import com.zag.core.util.HttpUtil;
import com.zag.core.web3_eth.DecryptWallet;
import com.zag.core.web3_eth.Erc20TokenClient;
import com.zag.core.web3_eth.EthWithdraw;
import com.zag.db.mysql.po.web3.*;
import com.zag.db.mysql.repository.ex.CurrencyAllRepository;
import com.zag.db.mysql.repository.ex.CurrencyUserRepository;
import com.zag.db.mysql.repository.ex.TransactionRepository;
import com.zag.db.mysql.repository.ex.UserAccountRepository;
import com.zag.enums.TransactionTypeEnums;
import com.zag.service.AbstractBaseService;
import com.zag.service.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.service.ex.account
 * @ClassName: ${AccountTxlistServiceImpl}
 * @Description: 检测所有代币充币（eth，eos btc等）
 * @Author: skyhuihui
 * @CreateDate: 2018/8/29 13:52
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/29 13:52
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
@Service
public class AccountTxlistServiceImpl extends AbstractBaseService implements AccountTxlistService{

    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private CurrencyAllRepository currencyAllRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CurrencyUserRepository currencyUserRepository;

    Jedis jedis = new Jedis("localhost");

    @Override
    public void txEthList() throws IOException {
        // 获得块信息
        String blackNumber = jedis.get("ethblack");
        BigInteger black;
        if(Strings.isNullOrEmpty(blackNumber)){
            black = DecryptWallet.getBlockNumber();
            jedis.set("ethblack", black.toString());
            return;
        }
        black = new BigInteger(jedis.get("ethblack"));
        if (logger.isDebugEnabled()) {
            logger.debug("检测用户充币-------币种: 以太币 当前块区间(块加20)："+black);
        }
        ThreadPoolExecutor executor = new ThreadPoolExecutor(20, 20, 1000, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(20));
        // 保证每次查询20个块
        for(int i = 0; i<20 ; i++){
            AccountTxlistServiceImpl.EthTxList ethTxList = new AccountTxlistServiceImpl.EthTxList(Integer.toHexString(black.add(BigInteger.ONE).intValue()));
            black = black.add(BigInteger.ONE);
            executor.execute(ethTxList);
        }
        // 一次查两个区块 ， 下次开始应 从上次结束时大 一
        jedis.set("ethblack", black.toString());
    }

    //内部类 外部写接收不到 repository  检测以太坊  以太币
    class EthTxList extends Thread{
        private String blockNumber;

        public EthTxList(String block){
            this.blockNumber = block;
        }

        @Override
        public void run()
        {
            String strURL = "https://api-ropsten.etherscan.io/api?module=proxy&action=eth_getBlockByNumber&tag=0x"+blockNumber+"&boolean=true&apikey=YourApiKeyToken";
            String json = HttpUtil.getURLContent(strURL);
            JSONObject jsStr = JSONObject.parseObject(json);
            String result = jsStr.getString("result");
            JSONObject resultJson = JSONObject.parseObject(result);
            JSONArray jsonArray = resultJson.getJSONArray("transactions");
            // 获得所有账号
            List<String> tokens = null;
            try {
                tokens = FileUtil.readToList("E:\\Wallet\\all\\all.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject ob = (JSONObject) jsonArray.get(i);
                // 获得转账余额
                String value = ob.getString("value");
                String to = ob.getString("to");
                if (value.equals("0x0") || Objects.isNull(ob) || Strings.isNullOrEmpty(to)) {
                    continue;
                }
                String hash = ob.getString("hash");
                // 所有账户地址 同 转账地址匹配
                for(int j = 0; j < tokens.size(); j++){
                    if(to.equals(tokens.get(j))){
                        BigInteger d = new BigInteger(value.substring(2), 16);
                        BigDecimal b = new BigDecimal(d.toString());
                        logger.info("充值账户："+to+"充值金额："+Double.parseDouble(b.divide(BigDecimal.TEN.pow(18)).toString())+"  充值币种：以太币");
                        // 保存进数据库
                        TransactionPo transactionPo = new TransactionPo();
                        IdService idService=new IdService(0);
                        transactionPo.setId(idService.nextId());
                        transactionPo.setTransactionTypeEnums(TransactionTypeEnums.Recharge);
                        transactionPo.setHash(hash);
                        transactionPo.setAmont(Double.parseDouble(b.divide(BigDecimal.TEN.pow(18)).toString()));
                        UserAccountPo userAccountPo = userAccountRepository.findByAddress(to);
                        if(Objects.nonNull(userAccountPo)){
                            transactionPo.setUserPo(userAccountPo.getUserPo());
                        }
                        CurrencyAllPo currencyAllPo = currencyAllRepository.findByaddress("0");
                        if(Objects.nonNull(currencyAllPo)){
                            transactionPo.setCurrencyAllPo(currencyAllPo);
                        }
                        transactionRepository.save(transactionPo);
                        UserPo userPo = new UserPo();
                        userPo.setId(transactionPo.getUserPo().getId());
                        // 用户账户 余额增加
                        CurrencyUserPo currencyUserPo = currencyUserRepository.findByUserPoAndCurrencyAllPo(userPo, currencyAllPo);
                        if(Objects.nonNull(currencyUserPo)){
                            currencyUserPo.setAmont(currencyUserPo.getAmont() + Double.parseDouble(b.divide(BigDecimal.TEN.pow(18)).toString()));
                            currencyUserRepository.save(currencyUserPo);
                        }else{
                            currencyUserPo = new CurrencyUserPo();
                            currencyUserPo.setId(idService.nextId());
                            currencyUserPo.setUserPo(userPo);
                            currencyUserPo.setCurrencyAllPo(currencyAllPo);
                            currencyUserPo.setAmont(Double.parseDouble(b.divide(BigDecimal.TEN.pow(18)).toString()));
                            currencyUserRepository.save(currencyUserPo);
                        }
                        // 余额收集进主账户
                        //获得用户 keystore 主账户地址
                        String keystore = FileUtil.readToString("E:\\Wallet\\"+userPo.getId()+"\\eth\\"+userAccountPo.getEthAccount());
                        String keystoreAddress = DecryptWallet.decryptAddress(keystore);
                        String one = FileUtil.readToString("E:\\Wallet\\0\\eth\\eth.json");
                        String oneAddress = DecryptWallet.decryptAddress(one);
                        // 计算手续费gas  （ 提币订单处理（修改用户余额）， redis black 高度  ）
                        if(!DecryptWallet.gasCount(DecryptWallet.decryptAddress(keystore))){
                            try {
                                EthWithdraw.ethTransactionToAddress(one, keystoreAddress, Double.parseDouble("0.001"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        // 转账到主账户
                        try {
                            EthWithdraw.ethTransactionToAddress(keystore,oneAddress, Double.parseDouble(b.divide(BigDecimal.TEN.pow(18)).toString()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void txEthErc20List() throws IOException {
        // 获得块信息
        String blackNumber = jedis.get("erc20black");
        BigInteger black;
        if(Strings.isNullOrEmpty(blackNumber)){
            black = DecryptWallet.getBlockNumber();
            jedis.set("erc20black", black.toString());
            return;
        }
        black = new BigInteger(jedis.get("erc20black"));
        // 加载所有币种
        List<String> tokens = FileUtil.readToList("E:\\Wallet\\contractAddress\\contractAddress.txt");
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 1000, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(10));
        for(int i = 0; i < tokens.size(); i++){
            if (logger.isDebugEnabled()) {
                logger.debug("检测用户充币-------币种: "+tokens.get(i)+" 当前块："+black);
            }
            AccountTxlistServiceImpl.EthErc20TxList ethErc20TxList = new AccountTxlistServiceImpl.EthErc20TxList(tokens.get(i),black.toString(), black.add(new BigInteger("20")).toString());
            executor.execute(ethErc20TxList);
        }
        // 一次查两个区块 ， 下次开始应 从上次结束时大 一
        black = black.add(new BigInteger("21"));
        jedis.set("erc20black", black.toString());
    }

    //内部类 外部写接收不到 repository  检测以太坊erc20代币
    class EthErc20TxList extends Thread{
        private String contractAddress;
        private String startblock;
        private String endblock;

        public EthErc20TxList(String address, String block, String endblock){
            this.contractAddress = address;
            this.startblock = block;
            this.endblock = endblock;
        }

        @Override
        public void run()
        {
            String strURL = "http://api-ropsten.etherscan.io/api?module=account&action=txlist&address="+contractAddress+"&startblock="+startblock+"&endblock="+endblock+"&sort=asc&apikey=YourApiKeyToken";
            String json = HttpUtil.getURLContent(strURL);
            JSONObject jsStr = JSONObject.parseObject(json);
            JSONArray jsonArray = jsStr.getJSONArray("result");
            // 获得代币精度
            int len = Erc20TokenClient.getTokenDecimals(contractAddress);
            // 获得所有账号
            List<String> tokens = null;
            try {
                tokens = FileUtil.readToList("E:\\Wallet\\all\\all.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject ob = (JSONObject) jsonArray.get(i);
                String account= "0x"+ob.getString("input").substring(ob.getString("input").length()-104, ob.getString("input").length()-64);
                String input = ob.getString("input").substring(ob.getString("input").length()-64);
                String hash = ob.getString("hash");
                // 所有账户地址 同 转账地址匹配
                for(int j = 0; j < tokens.size(); j++){
                    if(account.equals(tokens.get(j))){
                        BigInteger d = new BigInteger(input, 16);
                        BigDecimal b = new BigDecimal(d.toString());
                        logger.info("充值账户："+account+"充值金额："+Double.parseDouble(b.divide(BigDecimal.TEN.pow(len)).toString())+"  充值币种："+contractAddress);
                        // 保存进数据库
                        TransactionPo transactionPo = new TransactionPo();
                        IdService idService=new IdService(0);
                        transactionPo.setId(idService.nextId());
                        transactionPo.setTransactionTypeEnums(TransactionTypeEnums.Recharge);
                        transactionPo.setHash(hash);
                        transactionPo.setAmont(Double.parseDouble(b.divide(BigDecimal.TEN.pow(len)).toString()));
                        UserAccountPo userAccountPo = userAccountRepository.findByAddress(account);
                        if(Objects.nonNull(userAccountPo)){
                            transactionPo.setUserPo(userAccountPo.getUserPo());
                        }
                        CurrencyAllPo currencyAllPo = currencyAllRepository.findByaddress(contractAddress);
                        if(Objects.nonNull(currencyAllPo)){
                            transactionPo.setCurrencyAllPo(currencyAllPo);
                        }
                        transactionRepository.save(transactionPo);
                        UserPo userPo = new UserPo();
                        userPo.setId(transactionPo.getUserPo().getId());
                        // 用户账户 余额增加
                        CurrencyUserPo currencyUserPo = currencyUserRepository.findByUserPoAndCurrencyAllPo(userPo, currencyAllPo);
                        if(Objects.nonNull(currencyUserPo)){
                            currencyUserPo.setAmont(currencyUserPo.getAmont() + Double.parseDouble(b.divide(BigDecimal.TEN.pow(len)).toString()));
                            currencyUserRepository.save(currencyUserPo);
                        }else{
                            currencyUserPo = new CurrencyUserPo();
                            currencyUserPo.setId(idService.nextId());
                            currencyUserPo.setUserPo(userPo);
                            currencyUserPo.setCurrencyAllPo(currencyAllPo);
                            currencyUserPo.setAmont(Double.parseDouble(b.divide(BigDecimal.TEN.pow(len)).toString()));
                            currencyUserRepository.save(currencyUserPo);
                        }
                        // 余额收集进主账户
                        //获得用户 keystore 主账户地址
                        String keystore = FileUtil.readToString("E:\\Wallet\\"+userPo.getId()+"\\eth\\"+userAccountPo.getEthAccount());
                        String keystoreAddress = DecryptWallet.decryptAddress(keystore);
                        String one = FileUtil.readToString("E:\\Wallet\\0\\eth\\eth.json");
                        String oneAddress = DecryptWallet.decryptAddress(one);
                        // 计算手续费gas  （ 提币订单处理（修改用户余额）， redis black 高度  ）
                        if(!DecryptWallet.gasCount(DecryptWallet.decryptAddress(keystore))){
                            try {
                                EthWithdraw.ethTransactionToAddress(one, keystoreAddress, Double.parseDouble("0.001"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        // 转账到主账户
                        try {
                            Erc20TokenClient.erc20TokenTransaction(keystore, contractAddress, oneAddress, Double.parseDouble(b.divide(BigDecimal.TEN.pow(len)).toString()), len);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
