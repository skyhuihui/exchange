package com.zag.support.jpa.converter;

import com.google.common.base.Converter;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 将实体中的List<String>和数据库中的text相互转换	    	<----最初的作用<br>
 * 按照固定的规则(逗号分隔值)将list和string做相互转化	    <----现在的作用<br>
 * 弊端:
 * 在实体中写List<String>,并使用本转换器转换的属性,无法用@Modifying和@Query单独写jpql更新,只能查出原有对象再更新
 *
 * @author lei 2017年5月5日
 */
public abstract class AbstractListStringConverter<E> extends Converter<List<E>, String> implements AttributeConverter<List<E>, String>, Serializable {
    //	private static final String separator = "ಥ_ಥ";
    public static final String separator = ",";
    private static final long serialVersionUID = -7609608603310118266L;

    public AbstractListStringConverter() {
    }

    /**
     * 年轻人不要使用这个方法,让系统去用
     */
    @Override
    @Deprecated
    public String convertToDatabaseColumn(List<E> list) {
        return this.toString(list);
    }

    /**
     * 年轻人不要使用这个方法,让系统去用
     */
    @Override
    @Deprecated
    public List<E> convertToEntityAttribute(String dbData) {
        if (StringUtils.isBlank(dbData)) return new ArrayList<>();
        return this.toList(dbData);
    }

    public String toString(List<E> list) {
        if (list == null) return "";
        List<String> stringList = Lists.newArrayList();
        list.forEach(e -> {
            String str = element2String(e);
            if (str != null) {
                stringList.add(str);
            }
        });
        return Joiner.on(separator).join(stringList);
    }

    public List<E> toList(String string) {
        if (StringUtils.isBlank(string)) return Collections.emptyList();
        List<String> list = Splitter.on(separator).splitToList(string);
        List<E> result = Lists.newArrayList();
        for (String s : list) {
            result.add(string2Element(s));
        }
        return result;
    }

    @Override
    protected String doForward(List<E> a) {
        return toString(a);
    }

    @Override
    protected List<E> doBackward(String b) {
        return toList(b);
    }

    protected abstract E string2Element(String data);

    protected String element2String(E ele) {
        if (ele != null) return ele.toString();
        return null;
    }

}
