package com.zag.core.web3_eth;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.core.web3_eth
 * @ClassName: ${TYPE_NAME}
 * @Description: 解密keystore
 * @Author: skyhuihui
 * @CreateDate: 2018/8/23 17:30
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/23 17:30
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public class DecryptWallet {
    private static Web3j web3j = Web3j.build(new HttpService(Web3Config.web3j));

    /**
     * 获得当前区块高度
     *
     */
    public static BigInteger getBlockNumber()  {
        EthBlockNumber ethBlockNumber = null;
        try {
            ethBlockNumber = web3j.ethBlockNumber().send();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BigInteger blockNumber = ethBlockNumber.getBlockNumber();
        return blockNumber;
    }

    /**
     * 判断用户当前余额是否大于 三次交易手续费
     *
     * @param address
     */
    public static Boolean gasCount(String address)  {
        BigInteger balance = getBalance(address);
        BigInteger gas = null;
        try {
            EthGasPrice ethGasPrice = web3j.ethGasPrice().send();
            BigInteger gasPrice = ethGasPrice.getGasPrice().multiply(new BigInteger("110")).divide(new BigInteger("100"));
            BigInteger gasLimit = BigInteger.valueOf(80000);
            gas = gasLimit.multiply(gasPrice);
        }catch (IOException e){
        }
        if(balance.compareTo(gas.multiply(BigInteger.valueOf(Long.parseLong("3"))))>0){
            return true;
        }
        return false;
    }
    /**
     * 获取余额
     *
     * @param address 钱包地址
     * @return 余额
     */
    private static BigInteger getBalance(String address) {
        BigInteger balance = null;
        try {
            EthGetBalance ethGetBalance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
            balance = ethGetBalance.getBalance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("address " + address + " balance " + balance + "wei");
        return balance;
    }

    /**
     * 解密keystore 得到私钥
     *
     * @param keystore
     * @param password
     */
    public static String decryptWallet(String keystore, String password) {
        String privateKey = null;
        ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
        try {
            WalletFile walletFile = objectMapper.readValue(keystore, WalletFile.class);
            ECKeyPair ecKeyPair = null;
            ecKeyPair = Wallet.decrypt(password, walletFile);
            privateKey = ecKeyPair.getPrivateKey().toString(16);
        } catch (CipherException e) {
            if ("Invalid password provided".equals(e.getMessage())) {
                System.out.println("密码错误");
            }
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    /**
     * 解密keystore 获得用户地址
     *
     * @param keystore
     */
    public static String decryptAddress(String keystore) {
        JSONObject jsStr = JSONObject.parseObject(keystore);
        return "0x"+jsStr.get("address").toString();
    }

    //获取地址nonce
    public static BigInteger getNonce(String address){
        BigInteger nonce;
        EthGetTransactionCount ethGetTransactionCount = null;
        try {
            ethGetTransactionCount = web3j.ethGetTransactionCount(address, DefaultBlockParameterName.PENDING).send();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (ethGetTransactionCount == null) return new BigInteger("0");
        nonce = ethGetTransactionCount.getTransactionCount();
        return nonce;
    }

    // 获得keysotrenonce
    public static BigInteger getKeyStoreNonce(String keystore){
        JSONObject jsStr = JSONObject.parseObject(keystore);
        return getNonce("0x"+jsStr.get("address").toString());
    }

    // 比较nonce 比较本地nonce 和 线上 nonce 如果小于线上 设置当前nonce为线上nonce
    public static BigInteger sortNonce(BigInteger nonce, BigInteger nonceNet){
        if(nonce.compareTo(nonceNet) < 0){
            nonce = nonceNet;
        }
        return nonce;
    }
}
