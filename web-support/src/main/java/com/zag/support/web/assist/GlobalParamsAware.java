/**
 *
 */
package com.zag.support.web.assist;

import java.io.Serializable;

/**
 * 全局参数接口
 */
public interface GlobalParamsAware extends Serializable {

    GlobalParams getGlobalParams();

    void setGlobalParams(GlobalParams globalParams);

}
