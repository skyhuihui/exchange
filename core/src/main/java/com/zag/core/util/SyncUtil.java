package com.zag.core.util;

public class SyncUtil {
	private final int lockStoreSize;
	private final Object[] lockStore;
	
	/**
	 * 创建指定大小的同步锁仓库
	 * @author stone
	 * @date 2017年7月24日
	 * @param lockStoreSize
	 */
	public SyncUtil(int lockStoreSize){
		this.lockStoreSize = lockStoreSize;
		lockStore = new Object[lockStoreSize];
		for (int i = 0; i < lockStore.length; i++) {
			lockStore[i] = new Object();
		}
	}
	/**
	 * 默认构造器, 同步锁仓库大小为64
	 * @author stone
	 * @date 2017年7月24日
	 */
	public SyncUtil(){
		this(64);
	}
	/**
	 * 根据同步锁数据源,进行哈希和取模之后从仓库中获取对应的锁
	 * @author stone
	 * @date 2017年7月24日
	 * @param source
	 * @return
	 */
	private Object getSyncLock(Object source){
		if(source==null) throw new RuntimeException("source 不能为null");
		return lockStore[Math.abs(source.hashCode() % lockStoreSize)];
	}
	
	/**
	 * 执行方法体
	 * @author stone
	 * @date 2017年7月24日
	 * @param unit 执行单元
	 * @return 执行单元的execute方法返回的对象, 如果没有执行,返回null
	 */
	public <T> T syncExecute(SyncExecutionUnit unit){
		Object lock = getSyncLock(unit.getSyncLockSource());
		if(unit.isExecutable()){
			synchronized (lock) {
				if(unit.isExecutable()){ 
					return unit.execute();
				}
			}
		}
		return null;
	}
}
