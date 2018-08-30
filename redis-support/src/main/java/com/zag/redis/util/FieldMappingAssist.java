package com.zag.redis.util;

import com.zag.redis.bean.BaseRedisObject;
import org.springframework.expression.Expression;

import java.io.Serializable;

/**
 * 属性注解 属性注解辅助类
 *
 * @author stone(by lei)
 * @date 2017年8月12日
 * @reviewer
 * @param <T>
 */
public class FieldMappingAssist<T extends BaseRedisObject<ID>, ID extends Serializable> {

	private String fieldName;
	private String prefix;
	private Expression keyExpression;

	public FieldMappingAssist() {
		super();
	}

	public FieldMappingAssist(String fieldName, String prefix, Expression keyExpression) {
		super();
		this.fieldName = fieldName;
		this.prefix = prefix;
		this.keyExpression = keyExpression;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Expression getKeyExpression() {
		return keyExpression;
	}

	public void setKeyExpression(Expression keyExpression) {
		this.keyExpression = keyExpression;
	}

	/**
	 * 属性名称加上表达式值KEY
	 * @author stone(by lei)
	 * @date 2017年8月12日
	 * @return
	 */
	public String getKey(T ro) {
		return this.prefix + ":" + ExpressionUtil.getKey(ro, keyExpression);
	}

}
