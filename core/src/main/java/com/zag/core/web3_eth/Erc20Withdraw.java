package com.zag.core.web3_eth;

import com.zag.core.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ChainId;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.core.web3_eth
 * @ClassName: ${Erc20Transaction}
 * @Description: erc20代币提现 热钱包转到用户钱包
 * @Author: skyhuihui
 * @CreateDate: 2018/8/23 17:00
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/23 17:00
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public class Erc20Withdraw {

    private static Web3j web3j = Web3j.build(new HttpService(Web3Config.web3j));
    public static Logger logger = LoggerFactory.getLogger(Erc20Withdraw.class);
    public static BigInteger nonce=BigInteger.ZERO;
    /**
     * 代币提币
     */
    public static String Erc20TokenTransaction(String contractAddress, String toAddress, double amount, int decimals) throws IOException {

        //获得keysotre文件
        String keystore = FileUtil.readToString(Web3Config.hotWallet);
        String privateKey = DecryptWallet.decryptWallet(keystore, );//  你自己设置的密码
        BigInteger nonceNet = DecryptWallet.getKeyStoreNonce(keystore);
        // 比较nonce
        nonce = DecryptWallet.sortNonce(nonce, nonceNet);
        //gasPraice 手动设置
        //BigInteger gasPrice = Convert.toWei(BigDecimal.valueOf(10), Convert.Unit.GWEI).toBigInteger();
        EthGasPrice ethGasPrice = web3j.ethGasPrice().send();
        BigInteger gasPrice = ethGasPrice.getGasPrice().multiply(new BigInteger("110")).divide(new BigInteger("100"));
        //gasLimit
        BigInteger gasLimit = BigInteger.valueOf(80000);
        BigInteger value = BigInteger.ZERO;
        //token转账参数
        String methodName = "transfer";
        List<Type> inputParameters = new ArrayList<>();
        List<TypeReference<?>> outputParameters = new ArrayList<>();
        Address tAddress = new Address(toAddress);
        Uint256 tokenValue = new Uint256(BigDecimal.valueOf(amount).multiply(BigDecimal.TEN.pow(decimals)).toBigInteger());
        inputParameters.add(tAddress);
        inputParameters.add(tokenValue);
        TypeReference<Bool> typeReference = new TypeReference<Bool>() {
        };
        outputParameters.add(typeReference);
        Function function = new Function(methodName, inputParameters, outputParameters);
        String data = FunctionEncoder.encode(function);
        byte chainId = ChainId.NONE;
        String signedData;
        String str="nonce:"+nonce+" gasPrice"+gasPrice+"   gasLimit:"+gasLimit+"   接受地址："+toAddress+"   总量："+tokenValue;
        logger.info(str);
        try {
            signedData = Erc20Withdraw.signTransaction(nonce, gasPrice, gasLimit, contractAddress, value, data, chainId, privateKey);
            if (signedData != null) {
                EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(signedData).send();
                nonce = nonce.add(BigInteger.ONE);
                System.out.println(ethSendTransaction.getTransactionHash());
                return ethSendTransaction.getTransactionHash();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 签名交易
     */
    public static String signTransaction(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
                                         BigInteger value, String data, byte chainId, String privateKey) throws IOException {
        byte[] signedMessage;
        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce,
                gasPrice,
                gasLimit,
                to,
                value,
                data);

        if (privateKey.startsWith("0x")) {
            privateKey = privateKey.substring(2);
        }
        System.out.println("参数：gasPrice:"+gasPrice+"   gaslimit:"+gasLimit+"   nonce:"+nonce+"   value"+value+"   data:"+data);
        ECKeyPair ecKeyPair = ECKeyPair.create(new BigInteger(privateKey, 16));
        Credentials credentials = Credentials.create(ecKeyPair);

        if (chainId > ChainId.NONE) {
            signedMessage = TransactionEncoder.signMessage(rawTransaction, chainId, credentials);
        } else {
            signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        }

        String hexValue = Numeric.toHexString(signedMessage);
        return hexValue;
    }
}
