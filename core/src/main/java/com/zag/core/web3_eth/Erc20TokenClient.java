package com.zag.core.web3_eth;

import com.zag.core.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
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
import java.util.concurrent.ExecutionException;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.core.web3_eth
 * @ClassName: ${Erc20TokenClient}
 * @Description: erc20代币处理
 * @Author: skyhuihui
 * @CreateDate: 2018/8/28 13:51
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/28 13:51
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public class Erc20TokenClient {

    public static Logger logger = LoggerFactory.getLogger(Erc20TokenClient.class);
    private static Web3j web3j = Web3j.build(new HttpService(Web3Config.web3j));
    private static String emptyAddress = "0x0000000000000000000000000000000000000000";

    public static void main(String[] args) {
        System.out.println(getTokenDecimals("0xd5c0f4e10801717f683f546fad3e4481d2b324ba"));
    }
    /**
     * 查询代币精度
     *
     * @param contractAddress
     * @return
     */
    public static int getTokenDecimals(String contractAddress) {
        String methodName = "decimals";
        String fromAddr = emptyAddress;
        int decimal = 0;
        List<Type> inputParameters = new ArrayList<>();
        List<TypeReference<?>> outputParameters = new ArrayList<>();

        TypeReference<Uint8> typeReference = new TypeReference<Uint8>() {
        };
        outputParameters.add(typeReference);

        Function function = new Function(methodName, inputParameters, outputParameters);

        String data = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createEthCallTransaction(fromAddr, contractAddress, data);

        EthCall ethCall;
        try {
            ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
            List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
            decimal = Integer.parseInt(results.get(0).getValue().toString());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return decimal;
    }

    /**
     * 代币转账
     */
    public static String erc20TokenTransaction(String keystore, String contractAddress, String toAddress, double amount, int decimals) throws IOException {

        String privateKey = DecryptWallet.decryptWallet(keystore, );//  你自己设置的密码
        BigInteger nonceNet = DecryptWallet.getKeyStoreNonce(keystore);
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
        String str="nonce:"+nonceNet+" gasPrice"+gasPrice+"   gasLimit:"+gasLimit+"   接受地址："+toAddress+"   总量："+tokenValue;
        logger.info(str);
        try {
            signedData = Erc20Withdraw.signTransaction(nonceNet, gasPrice, gasLimit, contractAddress, value, data, chainId, privateKey);
            if (signedData != null) {
                EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(signedData).send();
                System.out.println("hash:"+ethSendTransaction.getTransactionHash());
                return ethSendTransaction.getTransactionHash();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
