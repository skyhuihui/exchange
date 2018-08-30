package com.zag.redis.trans.impl.convertor;

import com.zag.redis.trans.ValueConvertor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author lei
 * @date 2016/12/23
 */
public final class BasicConvertor {

	private BasicConvertor(){}

	public static final Logger logger = LoggerFactory.getLogger(BasicConvertor.class);

	public static final ValueConvertor<Byte>  withByte = new AbstractNumberConvertor<Byte>(){
		@Override
		protected Byte parseValue(String data) {
			return Byte.valueOf(data);
		}
	};
	public static final ValueConvertor<Short>  withShort = new AbstractNumberConvertor<Short>(){
		@Override
		protected Short parseValue(String data) {
			return Short.valueOf(data);
		}
	};
	public static final ValueConvertor<Integer>  withInteger = new AbstractNumberConvertor<Integer>(){
		@Override
		protected Integer parseValue(String data) {
			return Integer.valueOf(data);
		}
	};
	public static final ValueConvertor<Long>  withLong = new AbstractNumberConvertor<Long>(){
		@Override
		protected Long parseValue(String data) {
			return Long.valueOf(data);
		}
	};
	public static final ValueConvertor<Double>  withDouble = new AbstractNumberConvertor<Double>(){
		@Override
		protected Double parseValue(String data) {
			return Double.valueOf(data);
		}
	};
	public static final ValueConvertor<Float>  withFloat = new AbstractNumberConvertor<Float>(){
		@Override
		protected Float parseValue(String data) {
			return Float.valueOf(data);
		}
	};
	public static final ValueConvertor<BigDecimal>  withBigDecimal = new AbstractSimpleValueConvertor<BigDecimal>(){
		@Override
		protected BigDecimal parseValue(String data) {
			return new BigDecimal(data).movePointLeft(8);
		}

		@Override
		protected String val2String(BigDecimal val) {
			return val.movePointRight(8).toString();
		}
//		@Override
//		protected BigDecimal parseValue(String data) {
//			return new BigDecimal(data);
//		}
	};
	public static final ValueConvertor<Date>  withDate = new AbstractSimpleValueConvertor<Date>(){
		@Override
		protected Date parseValue(String data) {
			return new Date(Long.valueOf(data));
		}

		@Override
		protected String val2String(Date val) {
			return val.getTime()+"";
		}
	};
	public static final ValueConvertor<java.sql.Date>  withSqlDate = new AbstractSimpleValueConvertor<java.sql.Date>(){
		@Override
		protected java.sql.Date parseValue(String data) {
			return new java.sql.Date(Long.valueOf(data));
		}

		@Override
		protected String val2String(java.sql.Date val) {
			return val.getTime()+"";
		}
	};
	public static final ValueConvertor<Timestamp>  withTimestamp = new AbstractSimpleValueConvertor<Timestamp>(){
		@Override
		protected Timestamp parseValue(String data) {
			return new Timestamp(Long.valueOf(data));
		}

		@Override
		protected String val2String(Timestamp val) {
			return val.getTime()+"";
		}
	};
	public static final ValueConvertor<Time>  withSqlTime = new AbstractSimpleValueConvertor<Time>(){
		@Override
		protected Time parseValue(String data) {
			return Time.valueOf(data);
		}
		@Override
		protected String val2String(Time val) {
			return val.toString();
		}
	};
	public static final ValueConvertor<Boolean>  withBoolean = new AbstractSimpleValueConvertor<Boolean>(){
		@Override
		protected Boolean parseValue(String data) {
			return Boolean.valueOf(data);
		}

		@Override
		protected String val2String(Boolean val) {
			return val.toString();
		}
	};
	public static final ValueConvertor<Character>  withCharacter = new AbstractSimpleValueConvertor<Character>(){
		@Override
		protected Character parseValue(String data) {
			if(data.length()!=1){
				logger.error("char 类型的值出错");
			}
			return data.charAt(0);
		}

		@Override
		protected String val2String(Character val) {
			return val.toString();
		}
	};
	public static final ValueConvertor<String>  withString = new AbstractSimpleValueConvertor<String>(){
		@Override
		protected String parseValue(String data) {
			return data;
		}

		@Override
		protected String val2String(String val) {
			return val;
		}
	};

}
