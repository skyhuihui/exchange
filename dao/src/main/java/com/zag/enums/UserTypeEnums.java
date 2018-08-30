package com.zag.enums;

import com.zag.core.enums.EnumerableValue;
import com.zag.core.enums.converter.BaseEnumValueConverter;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.enums
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: 作者姓名
 * @CreateDate: 2018/8/8 10:09
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/8 10:09
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public enum UserTypeEnums implements EnumerableValue {

    /**
     * 禁用
     */
    DISABLE(0),
    /**
     * 启用
     */
    ENABLE(1);

    private int value;


    UserTypeEnums(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public boolean isEnable() {
        return this.value == ENABLE.getValue();
    }

    public static class Converter extends BaseEnumValueConverter<UserTypeEnums> {
    }
}
