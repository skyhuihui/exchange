package com.zag.core.enums;

/**
 * 枚举上层接口扩展(描述信息) 只有枚举才应该继承本接口
 * 配合BaseEnumNameAndValueAndDescriptionConverter使用
 */
public interface EnumerableNameAndValueAndDescription {

    String getName();

    int getValue();

    String getDescription();
}
