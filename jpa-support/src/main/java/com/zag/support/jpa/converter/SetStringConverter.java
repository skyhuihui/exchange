package com.zag.support.jpa.converter;

/**
 * 将set或string按照固定规则相互转换
 *
 * @author yikun.mao
 * @usage
 * @reviewer
 * @since 2017年8月23日
 */
public class SetStringConverter extends AbstractSetStringConverter<String> {
    public static final SetStringConverter INSTANCE = new SetStringConverter();

    public SetStringConverter() {
    }

    @Override
    protected String string2Element(String data) {
        return data;
    }
}
