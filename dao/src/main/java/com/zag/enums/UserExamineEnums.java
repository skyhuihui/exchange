package com.zag.enums;

import com.zag.core.enums.EnumerableValue;
import com.zag.core.enums.converter.BaseEnumValueConverter;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.enums
 * @ClassName: ${UserExamineEnums}
 * @Description: 用户审核
 * @Author: skyhuihui
 * @CreateDate: 2018/8/8 11:20
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/8 11:20
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public enum UserExamineEnums implements EnumerableValue {
    /**
     * 未审核
     */
    Unaudited(0),
    /**
     * 审核中
     */
    Audit(1),
    /**
     * 审核成功
     */
    AuditSuccess(2),
    /**
     * 审核失败
     */
    AuditFailure(3);

    private int value;


    UserExamineEnums(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static class Converter extends BaseEnumValueConverter<UserExamineEnums> {
    }
}
