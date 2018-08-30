package com.zag.redis.util;

import java.io.Serializable;

import org.springframework.expression.Expression;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.zag.redis.bean.BaseRedisObject;

/**
 * 属性注解 方法注解辅助类
 * 
 * @author stone(by lei)
 * @date 2017年8月12日
 * @reviewer
 * @param <T>
 */
public class SortedSetAssist<T extends BaseRedisObject<ID>, ID extends Serializable> {

	private String fieldName;
	private String prefix;
	private Expression keyExpression;
	private Expression scoreExpression;

	public SortedSetAssist() {
		super();
	}

	public SortedSetAssist(String fieldName, String prefix, Expression keyExpression, Expression scoreExpression) {
		super();
		this.prefix = prefix;
		this.fieldName = fieldName;
		this.keyExpression = keyExpression;
		this.scoreExpression = scoreExpression;
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

	public Expression getScoreExpression() {
		return scoreExpression;
	}

	public void setScoreExpression(Expression scoreExpression) {
		this.scoreExpression = scoreExpression;
	}

	/**
	 * 前缀加上属性名称加上表达式值KEY
	 * @author stone(by lei)
	 * @date 2017年8月12日
	 * @return
	 */
	public String getKey(T ro) {
		return this.prefix + ":" + ExpressionUtil.getKey(ro, keyExpression);
	}

	/**
	 * 获取权重
	 * @author stone(by lei)
	 * @date 2017年8月12日
	 * @param ro
	 * @return
	 */
	public long getScore(T ro) {
		return ExpressionUtil.getScore(new StandardEvaluationContext(ro), scoreExpression);
	}

}
