package com.zag.core.util;

/**
 * 同步操作执行单元,配合同步工具使用{@link SyncUtil}
 * @author stone
 * @date 2017年7月24日
 * @reviewer 
 * @see com.SyncUtil.base.core.util.SyncUtils
 */
public interface SyncExecutionUnit {
	/**
	 * 操作是否可执行,在进入同步体前判断一次,进入后判断一次
	 * @author stone
	 * @date 2017年7月24日
	 * @return
	 */
	boolean isExecutable();

	/**
	 * 获取同步锁数据源,一般是id,数据源经过hash处理和取模运算后,拿到同步锁池中的锁,来进行同步操作
	 * 根据锁池的大小,降低碰撞概率
	 * @author stone
	 * @date 2017年7月24日
	 * @return 同步锁数据源,不能为null
	 */
	Object getSyncLockSource();

	/**
	 * 同步方法体,放置需要进行同步操作的逻辑
	 * @author stone
	 * @date 2017年7月24日
	 * @return
	 */
	
	<R> R execute();
}
