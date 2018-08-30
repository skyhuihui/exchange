package com.zag.redis.trans.impl;

import com.google.common.collect.Lists;
import com.zag.redis.trans.*;
import com.zag.redis.trans.impl.convertor.EnumConvertor;
import com.zag.redis.trans.impl.convertor.BasicConvertor;
import com.zag.redis.trans.impl.convertor.BeanConvertor;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author lei
 * @date 2016/12/22
 */
public class TranslatorBuilder {

	private DataTransformer dataTransformer;

	private List<Bucket> convertorBuckets = Lists.newArrayList();

	private BeanRegistry beanRegistry;

	private ConvertorRegistry convertorRegistry;

	private static Translator defaultTranslator;

	public static TranslatorBuilder createBuilder(){
		return new TranslatorBuilder().addDefaultConvertor();
	}

	/**
	 * 获取默认 translator 单例
	 * @return
	 */
	public synchronized static Translator getDefaultTranslatorSingleton(){
		if(defaultTranslator==null){
			defaultTranslator = TranslatorBuilder.createBuilder().build();
		}
		return defaultTranslator;
	}


	public TranslatorBuilder addValueConvertor(Class clazz, ValueConvertor valueConvertor){
		convertorBuckets.add(new Bucket(clazz,valueConvertor));
		return this;
	}

	public TranslatorBuilder addValueConvertor(ConvertorMatcher convertorMatcher, ValueConvertor valueConvertor){
		convertorBuckets.add(new Bucket(convertorMatcher,valueConvertor));
		return this;
	}

	public <T extends Translator> Translator build(){
		DefaultTranslator dt = new DefaultTranslator();
		if(convertorRegistry==null){
			convertorRegistry = new DefaultConvertorRegistry();
		}

		if(beanRegistry==null){
			DefaultBeanRegistry beanRegistry0 = new DefaultBeanRegistry();
			beanRegistry0.setConvertorRegistry(convertorRegistry);
			beanRegistry = beanRegistry0;
		}
		if(dataTransformer==null){
			dataTransformer = new DefaultDataTransformer();
		}
		this.addValueConvertor(RedisObject.class, new BeanConvertor(this.convertorRegistry,this.beanRegistry));


		for (Bucket convertorBucket : convertorBuckets) {
			if(convertorBucket.clazz != null){
				convertorRegistry.registerConvertor(convertorBucket.clazz,convertorBucket.valueConvertor);
			}else{
				convertorRegistry.registerConvertor(convertorBucket.convertorMatcher,convertorBucket.valueConvertor);
			}
		}

		dt.setBeanRegistry(beanRegistry);
		dt.setConvertorRegistry(convertorRegistry);
		dt.setDataTransformer(dataTransformer);
		return dt;
	}

	private TranslatorBuilder addDefaultConvertor(){
		this
			.addValueConvertor(Boolean.class,       BasicConvertor.withBoolean  )
			.addValueConvertor(boolean.class,       BasicConvertor.withBoolean  )
			.addValueConvertor(Byte.class,          BasicConvertor.withByte     )
			.addValueConvertor(byte.class,          BasicConvertor.withByte     )
			.addValueConvertor(Short.class,         BasicConvertor.withShort    )
			.addValueConvertor(short.class,         BasicConvertor.withShort    )
			.addValueConvertor(Integer.class,       BasicConvertor.withInteger  )
			.addValueConvertor(int.class,           BasicConvertor.withInteger  )
			.addValueConvertor(Long.class,          BasicConvertor.withLong     )
			.addValueConvertor(long.class,          BasicConvertor.withLong     )
			.addValueConvertor(Character.class,     BasicConvertor.withCharacter)
			.addValueConvertor(char.class,          BasicConvertor.withCharacter)
			.addValueConvertor(Double.class,        BasicConvertor.withDouble   )
			.addValueConvertor(double.class,        BasicConvertor.withDouble   )
			.addValueConvertor(Float.class,         BasicConvertor.withFloat    )
			.addValueConvertor(float.class,         BasicConvertor.withFloat    )
			.addValueConvertor(BigDecimal.class,    BasicConvertor.withBigDecimal)
			.addValueConvertor(String.class,        BasicConvertor.withString   )
			.addValueConvertor(Date.class,          BasicConvertor.withDate     )
			.addValueConvertor(java.sql.Date.class, BasicConvertor.withSqlDate  )
			.addValueConvertor(Timestamp.class,     BasicConvertor.withTimestamp)
			.addValueConvertor(Time.class,          BasicConvertor.withSqlTime  )
			.addValueConvertor(EnumConvertor.MARCHER, new EnumConvertor())
			.addValueConvertor(BeanConvertor.MATCHER, new BeanConvertor(convertorRegistry,beanRegistry))


		;

		return this;
	}

	private static class Bucket{
		Class clazz;
		ValueConvertor valueConvertor;
		ConvertorMatcher convertorMatcher;

		public Bucket(Class clazz, ValueConvertor valueConvertor) {
			this.clazz = clazz;
			this.valueConvertor = valueConvertor;
		}

		public Bucket(ConvertorMatcher convertorMatcher, ValueConvertor valueConvertor) {
			this.valueConvertor = valueConvertor;
			this.convertorMatcher = convertorMatcher;
		}
	}
}
