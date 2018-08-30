package com.rrb.redis.test;

import java.util.Date;

public class Item {
	private String name;

	private int qty;

	private Long ts;
	
	private Date date;
	
	public Item(String name, int qty) {
		super();
		this.name = name;
		this.qty = qty;
		this.ts = System.currentTimeMillis();
		date = new java.sql.Timestamp(System.currentTimeMillis());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public Long getTs() {
		return ts;
	}

	public void setTs(Long ts) {
		this.ts = ts;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
