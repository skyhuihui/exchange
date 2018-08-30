package com.zag.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zag.core.asserts.BusinessAsserts;
import com.zag.core.util.DebugUtil;
import com.zag.enums.UserExamineEnums;
import com.zag.enums.UserTypeEnums;
import com.zag.exception.Exceptions;
import com.zag.support.web.assist.*;

import java.math.BigDecimal;

/**
 * @author stone
 * @usage
 * @reviewer
 * @since 2017年8月6日
 */
public class BaseRequestVo implements IPAddressAware, IRequestVo, ClientTypeAware {

    private static final long serialVersionUID = -775142169239478866L;

    @JsonIgnore
    private GlobalParams globalParams;
    @JsonIgnore
    private String ip;
    @JsonIgnore
    private String md5;
    @JsonIgnore
    private ClientType clientType;
    /**
     * 注意:只有当app在全局参数提交了userId时,id才会有值,该值在<br>
     * 如果使用jackson以外的json序列化,记得屏蔽对userId的输出
     */
    @JsonIgnore
    private Long userId;

    @JsonIgnore
    private String name;

    @JsonIgnore
    private UserTypeEnums type;

    @JsonIgnore
    private UserExamineEnums userExamineEnums;

    @Override
    public GlobalParams getGlobalParams() {
        return globalParams;
    }

    @Override
    public void setGlobalParams(GlobalParams globalParams) {
        this.globalParams = globalParams;
    }

    @JsonIgnore
    public Long getUserId() {
        BusinessAsserts.notNull(userId, Exceptions.Global.ID_NOT_EXIST, "您还没有登录");
        return userId;
    }

    @JsonIgnore
    public Long getUserIdNotAssert() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    @Override
    public String getIp() {
        return ip;
    }

    @Override
    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Override
    public String toString() {
        return DebugUtil.toString(this);
    }

    @Override
    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserTypeEnums getType() {
        return type;
    }

    public void setType(UserTypeEnums type) {
        this.type = type;
    }

    public UserExamineEnums getUserExamineEnums() {
        return userExamineEnums;
    }

    public void setUserExamineEnums(UserExamineEnums userExamineEnums) {
        this.userExamineEnums = userExamineEnums;
    }
}
