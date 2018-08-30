package com.zag.core.web3_eth;

import com.zag.core.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.core.web3_eth
 * @ClassName: ${TYPE_NAME}
 * @Description: 以太币提现 热钱包转到用户钱包
 * @Author: skyhuihui
 * @CreateDate: 2018/8/23 17:01
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/23 17:01
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public class EthWithdraw {

    private static Web3j web3j = Web3j.build(new HttpService(Web3Config.web3j));
    public static Logger logger = LoggerFactory.getLogger(EthWithdraw.class);
    public static BigInteger nonce = BigInteger.ZERO;
    /**
     * eth提币
     */
    public static String ethTokenTransaction(String toAddress, double amount) throws IOException {
        //获得keysotre文件
        String keystore = FileUtil.readToString(Web3Config.hotWallet);
        String privateKey = DecryptWallet.decryptWallet(keystore, );//  你自己设置的密码
        BigInteger nonceNet = DecryptWallet.getKeyStoreNonce(keystore);
        // 比较nonce
        nonce = DecryptWallet.sortNonce(nonce, nonceNet);
        //gasPraice 手动设置
        //BigInteger gasPrice = Convert.toWei(BigDecimal.valueOf(2), Convert.Unit.GWEI).toBigInteger();
        EthGasPrice ethGasPrice = web3j.ethGasPrice().send();
        BigInteger gasPrice = ethGasPrice.getGasPrice().multiply(new BigInteger("110")).divide(new BigInteger("100"));
        //gasLimit
        BigInteger gasLimit = BigInteger.valueOf(21000);
        //转账人私钥
        Credentials credentials = Credentials.create(privateKey);
        //创建交易，这里是转0.5个以太币
        BigInteger value = Convert.toWei(Double.toString(amount), Convert.Unit.ETHER).toBigInteger();
        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
                nonce, gasPrice, gasLimit, toAddress, value);
        //签名Transaction，这里要对交易做签名
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signedMessage);
        //发送交易
        EthSendTransaction ethSendTransaction = null;
        try {
            ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        String transactionHash = ethSendTransaction.getTransactionHash();
        nonce = nonce.add(BigInteger.ONE);
        //获得到transactionHash后就可以到以太坊的网站上查询这笔交易的状态了
        String str="nonce:"+nonce+" gasPrice: "+gasPrice+"   gasLimit:"+gasLimit+"   接受地址："+toAddress+"   总量："+value+"  Hash:"+transactionHash;
        logger.info(str);
        return transactionHash;
    }

    /**
     * eth  转账
     */
    public static String ethTransactionToAddress(String keystore, String toAddress, double amount) throws IOException {
        //获得keysotre文件
        String privateKey = DecryptWallet.decryptWallet(keystore, );//  你自己设置的密码
        BigInteger nonceNet = DecryptWallet.getKeyStoreNonce(keystore);
        //gasPraice 手动设置
        //BigInteger gasPrice = Convert.toWei(BigDecimal.valueOf(2), Convert.Unit.GWEI).toBigInteger();
        EthGasPrice ethGasPrice = web3j.ethGasPrice().send();
        BigInteger gasPrice = ethGasPrice.getGasPrice().multiply(new BigInteger("110")).divide(new BigInteger("100"));
        //gasLimit
        BigInteger gasLimit = BigInteger.valueOf(21000);
        //转账人私钥
        Credentials credentials = Credentials.create(privateKey);
        //创建交易，这里是转0.5个以太币
        BigInteger value = Convert.toWei(Double.toString(amount), Convert.Unit.ETHER).toBigInteger();
        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
                nonceNet, gasPrice, gasLimit, toAddress, value);
        //签名Transaction，这里要对交易做签名
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signedMessage);
        //发送交易
        EthSendTransaction ethSendTransaction = null;
        try {
            ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        String transactionHash = ethSendTransaction.getTransactionHash();
        //获得到transactionHash后就可以到以太坊的网站上查询这笔交易的状态了
        String str="nonce:"+nonceNet+" gasPrice: "+gasPrice+"   gasLimit:"+gasLimit+"   接受地址："+toAddress+"   总量："+value+"  Hash:"+transactionHash;
        logger.info(str);
        return str;
    }
}
