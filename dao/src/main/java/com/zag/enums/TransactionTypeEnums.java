package com.zag.enums;

import com.zag.core.enums.EnumerableValue;
import com.zag.core.enums.converter.BaseEnumValueConverter;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.enums
 * @ClassName: ${TYPE_NAME}
 * @Description: 余额变化类型（交易类型）
 * @Author: skyhuihui
 * @CreateDate: 2018/8/28 10:31
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/28 10:31
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public enum TransactionTypeEnums implements EnumerableValue {
    /**
     * 提现
     */
    Withdraw(0),

    /**
     * 充值
     */
    Recharge(1),

    /**
     * 转账
     */
    Transfer(2);

    private int value;


    TransactionTypeEnums(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static class Converter extends BaseEnumValueConverter<TransactionTypeEnums> {
    }
}