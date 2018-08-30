package com.zag.core.notification;

import org.apache.commons.lang.StringUtils;

/**
 * Created by stone on 6/8/17.
 */
public class NotificationPlatform {

    public static final String MI = "mi";

    public static final String IOS = "ios";

    public static final String ANDROID = "android";

    public static final String ANDROID_IOS = "android_ios";

    /**
     * 是否Android平台
     * @param plaftorm
     * @return
     */
    public static Boolean isPlatformAndroid(String plaftorm) {
        plaftorm = StringUtils.trim(plaftorm);
        return StringUtils.equalsIgnoreCase(plaftorm, ANDROID) || StringUtils.equalsIgnoreCase(plaftorm, ANDROID_IOS);
    }

    /**
     * 是否Ios平台
     * @param plaftorm
     * @return
     */
    public static Boolean isPlatformIos(String plaftorm) {
        plaftorm = StringUtils.trim(plaftorm);
        return StringUtils.equalsIgnoreCase(plaftorm, IOS) || StringUtils.equalsIgnoreCase(plaftorm, ANDROID_IOS);
    }

    public static void main(String[] args) {
        System.out.println(isPlatformAndroid("android"));
        System.out.println(isPlatformIos("ios"));
    }


}
