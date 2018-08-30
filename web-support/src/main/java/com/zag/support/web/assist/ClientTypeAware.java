package com.zag.support.web.assist;

/**
 * 请求vo 客户端类型感知 实现本接口的requestVo,
 * 在被BaseController处理时,
 * 会自动绑定当前请求者的clientType
 *
 * @author lei
 * @usage requestVo实现本接口
 * @reviewer
 * @since 2017年8月17日
 */
public interface ClientTypeAware {

    /**
     * 实现本接口的request vo通过此方法绑定请求者的client type
     *
     * @author lei
     * @date 2017年8月17日
     */
    void setClientType(ClientType clientType);

}
