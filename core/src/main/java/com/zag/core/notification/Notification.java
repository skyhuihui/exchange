package com.zag.core.notification;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * 推送消息POJO对象
 * Created by stone on 2016/03/26 10:25.
 */
public class Notification implements Serializable {

    public static final String NOTIFICATION_TITLE = "人人帮";

    /**
     * App类型
     */
    private AppType appType;

    /**
     * 推送的方式(发送给所有人/发送给单个用户)
     * P2P
     */
    private NotifyType notifyType = NotifyType.P2P;

    /**
     * 推送目标
     * NotifyType.BROADCAST 推送全站用户,无论是否传值 都推送
     * NotifyType.GROUP 推送指定组用户, target代表组唯一标示(即tags, 多个按逗号分隔)
     * NotifyType.P2P 推送指定用户, target代表deviceTokens
     * UserRo.lastToken;
     */
    private String target;

    /**
     * 推送的目标平台(IOS/ANDROID/MI)
     * userRo.lastUserAgent
     * NotificationChannel
     */
    private String platform;

    /**
     * 推送目标预留字段
     */
    private String deviceFree;

    /**
     * 推送方式(以弹出框的形式推送还是推送到APP内部)
     * ALERT
     */
    private NotificationSendType sendType = NotificationSendType.ALERT;

    /**
     * 推送消息的实体对象
     */
    private PushMessage pushMessage;


    /**
     * Apple App 唯一标识Token
     */
    private String deviceToken;

    public Notification() {
        super();
    }


    public Notification(AppType appType, NotifyType notifyType, String target, String platform, String deviceFree,
                        NotificationSendType sendType, PushMessage pushMessage, String deviceToken) {
        this.appType = appType;
        this.notifyType = notifyType;
        this.target = target;
        this.platform = platform;
        this.deviceFree = deviceFree;
        this.sendType = sendType;
        this.pushMessage = pushMessage;
        this.deviceToken = deviceToken;
    }

    public AppType getAppType() {
        return appType;
    }

    public void setAppType(AppType appType) {
        this.appType = appType;
    }

    public NotifyType getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(NotifyType notifyType) {
        this.notifyType = notifyType;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getDeviceFree() {
        return deviceFree;
    }

    public void setDeviceFree(String deviceFree) {
        this.deviceFree = deviceFree;
    }

    public PushMessage getPushMessage() {
        return pushMessage;
    }

    public void setPushMessage(PushMessage pushMessage) {
        this.pushMessage = pushMessage;
    }

    public NotificationSendType getSendType() {
        return sendType;
    }

    public void setSendType(NotificationSendType sendType) {
        this.sendType = sendType;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceToken() {
        return this.deviceToken;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

