package com.zag.support.jpa.converter;

/**
 * 将实体中的List<String>和数据库中的text相互转换	    	<----最初的作用<br>
 * 按照固定的规则(逗号分隔值)将list和string做相互转化	    <----现在的作用<br>
 * 弊端:
 * 在实体中写List<String>,并使用本转换器转换的属性,无法用@Modifying和@Query单独写jpql更新,只能查出原有对象再更新
 *
 * @author lei 2017年5月5日
 */
public class ListStringConverter extends AbstractListStringConverter<String> {
    public static final ListStringConverter INSTANCE = new ListStringConverter();
    private static final long serialVersionUID = -3786469606707789991L;

    public ListStringConverter() {
    }

    @Override
    protected String string2Element(String data) {
        return data;
    }

}
