package com.zag.core.baidu.map;


import com.zag.core.util.HttpClientUtil;

/**
 * @author stone
 * @date 2017年09月12日
 * @reviewer
 * @see
 */
public class BaiduMapUtil {

    public static String getLocationDecode(Double latitude, Double longitude) {
        String requestUrl = String.format(BaiduMapConfig.LOCATION_DECODE_URL, BaiduMapConfig.BAIDU_MAP_AK, latitude + "," + longitude);
        return HttpClientUtil.doGet(requestUrl);
    }
}
