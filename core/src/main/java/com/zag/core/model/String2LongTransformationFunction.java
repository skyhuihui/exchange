package com.zag.core.model;

import com.google.common.base.Function;

/**
 * 字符串->long值转换器,字符串必须为一个合法整形
 * @author stone
 * @since 2017年9月8日
 * @usage 
 * @reviewer
 */
public class String2LongTransformationFunction implements Function<String, Long>{

	@Override
	public Long apply(String input) {
		return Long.valueOf(input);
	}

}
