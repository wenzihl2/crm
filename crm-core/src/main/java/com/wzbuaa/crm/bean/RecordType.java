package com.wzbuaa.crm.bean;

public enum RecordType {
	shop("商城订单"),
	refund("订单退款"),
	recharge("余额充值");

	private String name;

	private RecordType(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
