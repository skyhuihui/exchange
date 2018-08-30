package com.zag.rest.base;

public interface Constants {

    String DATA = "data_map";

    String DATA_STR = "data_str";

    String SCENE_PROD = "production";

    //图片验证码hash,key开头标记
    String IMAGE_CODE = "global:imageVerifyCode:";

    //短信验证码hash,key开头标记
    String SMS_CODE = "global:sms:";

    //网络环境常量
    String APN_WIFI = "wifi";
    String APN_WAN = "wan";


    //使用场景定义
    String SCENE_DEV = "dev";
    String SCENE_TEST = "test";
    /**
     * Rest接口md5数据 在httprequest attribute中的存放key值
     */
    String MD5_HTTP_ATTR_NAME = "com.gridy.rest.md5";

    /**
     * Rest接口post数据参数名
     */
    String POST_DATA_PARAM_NAME = "data";

    /**
     * 从httpReqest中指定签名开关, 优先级高于配置, 目录 combineApi接口使用,
     * 如果未指定该字段, 从配置读取
     */
    String SIGN_FLAG_REQ_ATTR = "com.gridy.rest.signFlag";

    String DEFAULT_TOKEN = "20161919";

    /**
     * 当前页码 的参数名
     */
    String CUR_PAGE = "curPage";

}