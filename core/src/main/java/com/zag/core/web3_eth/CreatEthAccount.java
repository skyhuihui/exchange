package com.zag.core.web3_eth;

import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.core.web3_eth
 * @ClassName: ${创建eth账号}
 * @Description: java类作用描述
 * @Author: 作者姓名
 * @CreateDate: 2018/8/23 10:51
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/23 10:51
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public class CreatEthAccount {

    /**
     * 创建钱包
     * @param path //保存路径
     * @param password //密码
     * */
    public static String saveEthAccount(String path, String password) {
        String wallet_file="";
        try {
            File tempFile = new File(path);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
             wallet_file = WalletUtils.generateLightNewWalletFile(password, tempFile);
            System.out.println("钱包输出："+wallet_file);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
        }
        return  wallet_file;
    }

}
