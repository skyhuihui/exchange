package com.zag.service.ex.account;

import java.io.IOException;
import java.math.BigInteger;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.service.ex.account
 * @ClassName: ${AccountTxlistService}
 * @Description: 检测账户充币
 * @Author: skyhuihui
 * @CreateDate: 2018/8/29 13:52
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/29 13:52
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public interface AccountTxlistService {

    /**
     * 检测充币
     * @author skyhuihui
     * @version V1.0
     * @return void
     */
    void txEthErc20List() throws IOException;

    /**
     * 检测eth充币
     * @author skyhuihui
     * @version V1.0
     * @return void
     */
    void txEthList() throws IOException;
}
