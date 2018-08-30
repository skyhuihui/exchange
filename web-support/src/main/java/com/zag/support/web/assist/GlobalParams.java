package com.zag.support.web.assist;

import java.io.Serializable;


/**
 * 全局参数
 */
public class GlobalParams implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2980565642013490335L;

    public GlobalParams() {
    }

    public GlobalParams(Long id, String name, String type, String userExamineEnums) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.userExamineEnums = userExamineEnums;
    }

    private Long id;

    private String name;

    private String type;

    private String userExamineEnums;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserExamineEnums() {
        return userExamineEnums;
    }

    public void setUserExamineEnums(String userExamineEnums) {
        this.userExamineEnums = userExamineEnums;
    }
}
