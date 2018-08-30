package com.zag.core.web3_eth;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.core.web3_eth
 * @ClassName: ${web3Config}
 * @Description: infura 配置 无账号自己申请
 * @Author: skyhuihui
 * @CreateDate: 2018/8/23 17:04
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/23 17:04
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public class Web3Config {
    //infura环境   你自己的infura
    public static String web3j = ;
    //热钱包本地地址（提币）（收款）
    public static String hotWallet = "E:\\Wallet\\0\\eth\\eth.json";
    // 冷钱包账户地址 （收款）
    public static String coldWallet = "";
}
