package com.zag.core.notification;


import com.zag.core.enums.EnumerableValue;
import com.zag.core.enums.converter.BaseEnumValueConverter;

/**
 * 推送跳转行为定义
 *
 * @author stone
 * @date 2017年10月08日
 * @reviewer
 * @see
 */
public enum TransmissionType implements EnumerableValue {

    REDIRECT_URL(1), REDIRECT_APP_VIEW(2);

    private int value;

    TransmissionType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static class Converter extends BaseEnumValueConverter<TransmissionType> {
    }
}
