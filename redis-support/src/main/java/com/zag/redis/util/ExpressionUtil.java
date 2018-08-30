package com.zag.redis.util;

import org.springframework.expression.Expression;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * 表达式工具辅助类
 * @author stone(by lei)
 * @date 2017年8月12日
 * @reviewer 
 */
public class ExpressionUtil {

	/**
	 * 传递的是id或者时间
	 * @author stone(by lei)
	 * @date 2017年8月12日
	 * @param idOrDate
	 * @param expression
	 * @return
	 */
	public static long getScore(StandardEvaluationContext itemContext, Expression expression) {
		if (expression == null) {
			return System.currentTimeMillis();
		} else {
			Object obj = expression.getValue(itemContext);
//			System.out.println(obj.getClass().getName());
//			System.out.println(obj);
			if (obj instanceof java.util.Date) {
				return ((java.util.Date) obj).getTime();
			} if (obj instanceof java.sql.Date) {
				return ((java.sql.Date) obj).getTime();
			} if (obj instanceof java.sql.Timestamp) {
				return ((java.sql.Timestamp) obj).getTime();
			} else if (obj instanceof Number) {
				return ((Number) obj).longValue();
			} else {
				throw new IllegalArgumentException();
			}
		}
	}

	
	public static String getKey(Object ro, Expression expression) {
		return expression.getValue(ro).toString();
	}
}
