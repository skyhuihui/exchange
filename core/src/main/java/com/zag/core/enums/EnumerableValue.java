package com.zag.core.enums;

/**
 * 枚举int的上层接口,只有枚举才应该继承本接口
 * 配合BaseEnumValueConverter使用
 * 
 * @author stone 2017年5月5日
 */
public interface EnumerableValue{
	
	int getValue();
	
}
