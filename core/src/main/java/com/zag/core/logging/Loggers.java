package com.zag.core.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * logstash日志记录器
 * @author stone
 * @since 2017年8月16日
 * @usage 
 * @reviewer
 */
public enum Loggers {

	SERVICE{
		final Logger SERVICE_LOGGER = LoggerFactory.getLogger("logstash.service");
		Logger getLogger(){return SERVICE_LOGGER;}
	},
	DAO{
		final Logger DAO_LOGGER = LoggerFactory.getLogger("logstash.dao");
		Logger getLogger(){return DAO_LOGGER;}
	},
	SYNC{
		final Logger SYNC_LOGGER = LoggerFactory.getLogger("logstash.sync");
		Logger getLogger() {return SYNC_LOGGER;}
	},
	MVC{
		final Logger MVC_LOGGER = LoggerFactory.getLogger("logstash.mvc");
		Logger getLogger(){return MVC_LOGGER;}
	},
	ASSERTS{
		final Logger ASSERTS_LOGGER = LoggerFactory.getLogger("logstash.asserts");
		Logger getLogger(){return ASSERTS_LOGGER;}
	}
	;
	
	abstract Logger getLogger();
	
	public void trace(LogstashLog log){
		this.getLogger().trace("{}",log);
	}
	public void debug(LogstashLog log){
		this.getLogger().debug("{}",log);
	}
	public void info(LogstashLog log){
		this.getLogger().info("{}",log);
	}
	public void warn(LogstashLog log){
		this.getLogger().warn("{}",log);
	}
	public void error(LogstashLog log){
		this.getLogger().error("{}",log);
	}
	public boolean isTraceEnabled(){
		return this.getLogger().isTraceEnabled();
	}
	public boolean isDebugEnabled(){
		return this.getLogger().isDebugEnabled();
	}
	public boolean isInfoEnabled(){
		return this.getLogger().isInfoEnabled();
	}
	public boolean isWarnEnabled(){
		return this.getLogger().isWarnEnabled();
	}
	public boolean isErrorEnabled(){
		return this.getLogger().isErrorEnabled();
	}

}
