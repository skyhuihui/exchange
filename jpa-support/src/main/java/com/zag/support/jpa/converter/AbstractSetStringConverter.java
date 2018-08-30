package com.zag.support.jpa.converter;

import com.google.common.base.Converter;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.AttributeConverter;
import org.apache.commons.lang3.StringUtils;

/**
 * 按照固定规则将set和string相互转换
 *
 * @author yikun.mao
 * @usage
 * @reviewer
 * @since 2017年8月23日
 */
public abstract class AbstractSetStringConverter<E> extends Converter<Set<E>, String> implements AttributeConverter<Set<E>, String> {

    public static final String separator = ",";

    public AbstractSetStringConverter() {
    }

    /**
     * 年轻人不要使用这个方法,让系统去用
     */
    @Override
    @Deprecated
    public String convertToDatabaseColumn(Set<E> set) {
        return this.toString(set);
    }

    /**
     * 年轻人不要使用这个方法,让系统去用
     */
    @Override
    @Deprecated
    public Set<E> convertToEntityAttribute(String dbData) {
        if (StringUtils.isBlank(dbData)) return new HashSet<>();
        return this.toSet(dbData);
    }

    public String toString(Set<E> set) {
        if (set == null) return "";
        Set<String> stringSet = Sets.newHashSet();
        for (E e : set) {
            String str = element2String(e);
            if (str != null) {
                stringSet.add(str);
            }
        }
        return Joiner.on(separator).join(set);
    }

    public Set<E> toSet(String string) {
        if (StringUtils.isBlank(string)) return Collections.emptySet();
        List<String> elements = Splitter.on(separator).splitToList(string);
        return Sets.newHashSet(Lists.transform(elements, this::string2Element));
    }

    protected abstract E string2Element(String data);

    protected String element2String(E ele) {
        if (ele != null) return ele.toString();
        return null;
    }

    @Override
    protected String doForward(Set<E> a) {
        return this.toString(a);
    }

    @Override
    protected Set<E> doBackward(String b) {
        return this.toSet(b);
    }
}
