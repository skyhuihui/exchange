package com.rrb.redis.test;

import com.zag.redis.annotation.FieldSortedSet;
import com.zag.redis.annotation.Ro;
import com.zag.redis.annotation.RoSortedSet;
import com.zag.redis.bean.BaseRedisObject;

@Ro(key = "test:test")
@RoSortedSet(key = "all", score = "ts")
public class TestRo extends BaseRedisObject<String> {

	private String id;
	private String name;

	@FieldSortedSet(key = "categoryId", score = "createTime")
	private Long categoryId;

	@FieldSortedSet(key = "categoryId", score = "createTime")
	private java.sql.Timestamp createTime;

	private Long ts = 123456L;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public java.sql.Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.sql.Timestamp createTime) {
		this.createTime = createTime;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public Long getTs() {
		return ts;
	}

	public void setTs(Long ts) {
		this.ts = ts;
	}


}
