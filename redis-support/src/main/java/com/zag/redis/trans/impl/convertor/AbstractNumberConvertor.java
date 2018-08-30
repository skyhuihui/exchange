package com.zag.redis.trans.impl.convertor;

/**
 * @author lei
 * @date 2016/12/22
 */
public abstract class AbstractNumberConvertor<N extends Number> extends AbstractSimpleValueConvertor<N> {

	@Override
	protected String val2String(N val) {
		return val.toString();
	}
}
