package com.zag.core.util;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 性能计时器
 * @author stone
 * @since 2017年10月13日
 * @usage 
 * @reviewer
 */
public class Timers{
	private StopWatch sw = new StopWatch();
//	private List<Long> time = Lists.newArrayList();
	private Map<String,Long> map = Maps.newLinkedHashMap();
	private long preTime = 0;
	private boolean enabled = true;
	private static final Logger logger = LoggerFactory.getLogger(Timers.class);
	private Timers(){
	}
	
	public static Timers createAndBegin(boolean enabled){
		Timers timers = new Timers();
		timers.enabled = enabled;
		if(!enabled)return timers;
		timers.sw.start();
		return timers;
	}
	public void record(String tag){
		if(!enabled)return;
		sw.split();
		long splitTime = sw.getSplitTime();
		long sub = splitTime - preTime;
//		time.add(sub);
		map.put(tag, sub);
		preTime=splitTime;
	}
	private Map<String,Long> out(){
		if(!enabled)return map;
		if (!sw.isStopped()) {
			sw.stop();
		}
		long all = sw.getTime();
		map.put("总计用时", all);
		return map;
	}
	public void print(String tag){
		if(!enabled)return;
		logger.debug(tag +" {}", this.out());
	}
}