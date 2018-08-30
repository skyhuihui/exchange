package com.zag.support.web.assist;


import com.zag.core.enums.EnumerableValue;
import com.zag.core.enums.converter.BaseEnumValueConverter;

/**
 * 调用接口的客户端类型
 *
 * @author stone
 * @usage
 * @reviewer
 * @since 2017年8月2日
 */
public enum ClientType implements EnumerableValue {
    //ios app
    IOS(0, "ios app"),
    //安卓app
    ANDROID(1, "安卓app"),
    //微商城
    WECHAT(2, "微商城"),
    //浏览器
    WEB(3, "浏览器"),

    UNKNOWN(4, "位置");


    private int value;
    private String desc;
    ClientType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static class Converter extends BaseEnumValueConverter<ClientType> {
    }

}
