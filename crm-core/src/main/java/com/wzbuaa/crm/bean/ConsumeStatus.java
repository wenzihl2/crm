package com.wzbuaa.crm.bean;

/**
 * 消费状态 (冻结、完成、退款)
 * @author fangchen
 *
 */
public enum ConsumeStatus {
	Create("等待付款"), Freeze("金额冻结"), Over("交易成功"), Close("交易关闭");
	private String name;

	private ConsumeStatus(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
