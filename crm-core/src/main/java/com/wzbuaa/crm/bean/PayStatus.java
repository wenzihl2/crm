package com.wzbuaa.crm.bean;

/**
 * 消费状态 (新建、冻结、完成)
 * @author fangchen
 *
 */
public enum PayStatus {
	
	Create("等待付款"), Freeze("金额冻结"), Finish("交易成功");
	
	private String name;

	private PayStatus(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
