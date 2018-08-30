package com.zag.support.web.assist;

/**
 * 实现该接口的requestVo将在BaseController处理完数据后调用postInitializing()方法
 *
 * @author lei
 * @usage
 * @reviewer
 * @since 2017年8月16日
 */
public interface PostInitialization {

    void postInitializing();
}
