package com.zag.redis.bean;

import com.zag.core.model.Identifiable;
import com.zag.core.util.DateUtil;
import com.zag.core.util.DebugUtil;
import com.zag.redis.trans.impl.TranslatorBuilder;
import com.zag.redis.trans.RedisObject;
import com.zag.redis.trans.Translator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

/**
 * 使用java bean 属性来做toMap和fromMap操作, 因此RO可以继承
 * 
 * 继承该类的RO必须严格按照java bean的方式定义属性(即提供getter,setter)
 */
public abstract class BaseRedisObject<ID extends Serializable> extends BaseRo implements Identifiable<ID>,RedisObject {

	protected Logger log = LoggerFactory.getLogger(getClass());

	private ID id;
	
	private Timestamp createTimestamp = DateUtil.now();
	private Timestamp updateTimestamp;


	private Translator translator = TranslatorBuilder.getDefaultTranslatorSingleton();


	public void fromMap(Map<byte[], byte[]> map) {
		translator.fillObject(this, map);
	}

	/**
	 * Include all basis data.
	 */
	public Map<byte[], byte[]> toMap() {
		return translator.toRedisData(this);
	}


	@Override
	public String toString() {
		return DebugUtil.toString(this);
	}

	@Override
	public ID getId() {
		return id;
	}

	@Override
	public void setId(ID id) {
		this.id = id;
	}

	public Timestamp getCreateTimestamp() {
		return createTimestamp;
	}

	public void setCreateTimestamp(Timestamp createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	public Timestamp getUpdateTimestamp() {
		return updateTimestamp;
	}

	public void setUpdateTimestamp(Timestamp updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}

}
