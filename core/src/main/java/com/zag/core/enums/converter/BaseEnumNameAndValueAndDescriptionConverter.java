package com.zag.core.enums.converter;

import com.zag.core.enums.EnumerableNameAndValueAndDescription;

import javax.persistence.AttributeConverter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * 枚举Converter
 * Created by lei on 12/19/16.
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class BaseEnumNameAndValueAndDescriptionConverter<T extends EnumerableNameAndValueAndDescription>
        implements AttributeConverter<EnumerableNameAndValueAndDescription, Integer> {

    private Class<T> tClass = null;

    private Class<T> getTargetClass() {

        if (tClass == null) {
            Type[] actualTypeArguments = ((ParameterizedType) this.getClass().getGenericSuperclass())
                    .getActualTypeArguments();
            tClass = actualTypeArguments == null || actualTypeArguments.length == 0 ? null : (Class<T>)
                    actualTypeArguments[0];
        }
        return tClass;
    }

    @Override
    public Integer convertToDatabaseColumn(EnumerableNameAndValueAndDescription attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public T convertToEntityAttribute(Integer dbData) {

        Class<? extends EnumerableNameAndValueAndDescription> fieldClass = getTargetClass();
        if (fieldClass == null || !fieldClass.isEnum()) {
            return null;
        }
        for (EnumerableNameAndValueAndDescription enumConstant : fieldClass.getEnumConstants()) {
            if (Objects.equals(enumConstant.getValue(), dbData)) {
                return (T) enumConstant;
            }
        }
        return null;
    }
}
