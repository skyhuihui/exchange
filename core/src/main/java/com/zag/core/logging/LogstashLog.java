package com.zag.core.logging;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zag.core.exception.BusinessException;

import java.util.Arrays;
import java.util.Objects;

/**
 * 用于logstash的日志对象
 * @author stone
 * @since 2017年8月17日
 * @usage 
 * @reviewer
 */
@JsonInclude(Include.NON_NULL)
public class LogstashLog {
	private final LogType type;
	private final String signature;
	@JsonIgnore
	private final Throwable ex;
	private final String traces;
	private final String exType;
	private final String exmsg;
	private final String args;
	private final String returnVal;
	private final Object logContent;
	@JsonIgnore
	private static final ObjectMapper mapper = new ObjectMapper();

	public static enum LogType{
		//抛出一个异常
		ex,
		//抛出一个业务异常
		rrbex,
		//进入方法
		enter,
		//返回值
		returns,
		//处理中
		processing,
	}
	
	public static LogstashLog newEnterLog(Object...args) {
		return new LogstashLog(LogType.enter, null, args, null, null);
	}

	public static LogstashLog newReturnsLog(Object returnVal) {
		return new LogstashLog(LogType.returns, null, null, returnVal, null);
	}
	public static LogstashLog newMethodLog(String signature, Object returnVal, Object...args) {
		return new LogstashLog(signature, returnVal,null, args);
	}
	public static LogstashLog newMethodLog(String signature, Object returnVal, Throwable throwable, Object...args) {
		return new LogstashLog(signature, returnVal,throwable, args);
	}

	public static LogstashLog newExLog(Throwable ex) {
		if(ex instanceof BusinessException){
			//由于signature使用了栈帧层级,不能随意变更方法栈
			return new LogstashLog(LogType.rrbex, ex, null, null, null);
		}else{
			return new LogstashLog(LogType.ex, ex, null, null, null);
		}
	}
	public static LogstashLog newExLog(Throwable ex, StackTraceElement trace) {
		if(ex instanceof BusinessException){
			//由于signature使用了栈帧层级,不能随意变更方法栈
			return new LogstashLog(LogType.rrbex, ex, null, null, null, trace);
		}else{
			return new LogstashLog(LogType.ex, ex, null, null, null, trace);
		}
	}
	public static LogstashLog newRrbExLog(BusinessException ex) {
		return new LogstashLog(LogType.rrbex, ex, null, null, null);
	}

	public static LogstashLog newProcessingLog(Object logContent) {
		return new LogstashLog(LogType.processing, null, null, null, logContent);
	}
	
	public static LogstashLog newLog(String signature, Object logContent){
		return new LogstashLog(signature, logContent);
	}
	public static LogstashLog newLog(StackTraceElement ele, Object logContent){
		String signatureString =  ele.getClassName() +"."+ ele.getMethodName()+"(..)#line="+ele.getLineNumber();
		return new LogstashLog(signatureString, logContent);
	}

	private LogstashLog(String signature, Object returnVal, Throwable throwable, Object...args) {
		this.signature = signature;
		this.logContent = null;
		this.type = throwable == null ? LogType.processing : LogType.ex;
		this.ex = throwable;
		this.exType = throwable == null ? null : throwable.getClass().toString();
		this.exmsg = throwable == null ? null : throwable.getMessage();
		this.args = Arrays.toString(args);
		this.returnVal = Objects.toString(returnVal, null);
		this.traces = null;
	}
	private LogstashLog(String signature, Object logContent) {
		this.signature = signature;
		this.logContent = logContent;
		this.type = LogType.processing;
		this.ex = null;
		this.exType = null;
		this.exmsg = null;
		this.args = null;
		this.returnVal = null;
		this.traces = null;
	}
	
	private LogstashLog(LogType type, Throwable ex, Object args, Object returnVal, Object logContent) {
		/*
		 * [0] : 当前线程获取栈踪迹的方法getStackTrace() 
		 * [1] : 当前构造方法
		 * [2] : 四个静态new方法之一
		 * [3] : 真实方法栈帧
		 */
		this(type,ex,args,returnVal,logContent,Thread.currentThread().getStackTrace()[3]);
//		StackTraceElement ele = Thread.currentThread().getStackTrace()[3];
//		this.signature = ele.getClassName() +"."+ ele.getMethodName()+"(..)#line="+ele.getLineNumber();
//		this.type = type;
//		this.ex = ex;
//		if(ex !=null){
//			this.exmsg = ex.getMessage();
//			this.exType = ex.getClass().getName();
//		}else{
//			this.exType = null;
//			this.exmsg = null;
//		}
//		this.args = toString(args);
//		this.returnVal = toString(returnVal);
//		this.logContent = logContent;
	}
	private LogstashLog(LogType type, Throwable ex, Object args, Object returnVal, Object logContent, StackTraceElement trace) {
		super();
		StackTraceElement ele = trace;
		this.signature = ele.getClassName() +"."+ ele.getMethodName()+"(..)#line="+ele.getLineNumber();
		this.type = type;
		this.ex = ex;
		if(ex !=null){
			this.exmsg = ex.getMessage();
			this.exType = ex.getClass().getName();
			if(ex instanceof BusinessException){
				this.traces = null;
			}else{
				this.traces = traceToString(ex.getStackTrace());
			}
		}else{
			this.exType = null;
			this.exmsg = null;
			this.traces = null;
		}
		this.args = toString(args);
		this.returnVal = toString(returnVal);
		this.logContent = logContent;
	}

	public LogType getType() {
		return type;
	}

	public String getSignature() {
		return signature;
	}

	public Throwable getEx() {
		return ex;
	}

	public String getExmsg() {
		return exmsg;
	}

	public Object getArgs() {
		return args;
	}

	public Object getReturnVal() {
		return returnVal;
	}

	public Object getLogContent() {
		return logContent;
	}

	public String getExType() {
		return exType;
	}

	public String getTraces() {
		return traces;
	}

	@Override
	public String toString() {
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "序列化logstash log对象异常:"+e.getMessage();
		}
	}
	private String toString(Object obj){
//		return obj == null ? null : ToStringBuilder.reflectionToString(returnVal,DebugUtil.JSON_STYLE_FIXED);
		return obj == null ? null : obj.toString();
	}
	public static void main(String[] args) {
		System.out.println(newExLog(new Exception()));
	}
	private String traceToString(StackTraceElement[] traces){
		StringBuilder builder = new StringBuilder();
		for (StackTraceElement t : traces) {
			builder.append(t.toString()).append("\r\n");
		}
		return builder.toString();
	}
}
