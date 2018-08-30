package com.zag.support.jpa.po;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * po基类,id不会自动生成
 *
 * @author lei
 * @usage
 * @reviewer
 * @since 2017年8月2日
 */
@SuppressWarnings("serial")
@MappedSuperclass
public class BaseEntity extends BasePo<Long> implements Serializable {

    @Id
    private Long id;

    private Timestamp createTimestamp = new Timestamp(System.currentTimeMillis());

    @UpdateTimestamp
    private Timestamp updateTimestamp;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Timestamp createTime) {
        this.createTimestamp = createTime;
    }

    public Timestamp getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Timestamp updateTime) {
        this.updateTimestamp = updateTime;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "#" + this.getId();
    }

}
