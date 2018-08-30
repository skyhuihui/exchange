package com.zag.vo;

import javax.validation.constraints.NotNull;

/**
 * @author stone
 * @usage
 * @reviewer
 * @since 2017年8月6日
 */
public class IdRequestVo extends BaseRequestVo {
    private static final long serialVersionUID = 8145491923251311486L;

    @NotNull(message = "id不能为null")
    private Long id;

    public IdRequestVo() {
    }

    public IdRequestVo(Long id) {
        this();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

