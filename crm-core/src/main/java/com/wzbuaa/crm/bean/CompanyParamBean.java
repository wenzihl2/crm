package com.wzbuaa.crm.bean;

import java.math.BigDecimal;

public class CompanyParamBean {

	private int chargetimes; // 默认付款周期数
	private BigDecimal chargeRate; // 默认付款比例
	private int gathertimes; // 默认收款周期数
	private BigDecimal gatherRate; // 默认收款比例
	private int pact_alarm; // 合同提醒时间
	private int custpool_c; // 客户池回收周期
	private boolean memberCardPay; // 会员卡支付
	private boolean saleAudit; // 销售订单启用审核
	private boolean saleAuditType; // 销售订单审核级次: false: 直接领导 true:直至顶级领导
	private boolean saletuiAudit; // 销售退货启用审核
	private boolean saletuiAuditType; // 销售退货审核级次: false: 直接领导 true:直至顶级领导

	public int getChargetimes() {
		return chargetimes;
	}

	public void setChargetimes(int chargetimes) {
		this.chargetimes = chargetimes;
	}

	public BigDecimal getChargeRate() {
		return chargeRate;
	}

	public void setChargeRate(BigDecimal chargeRate) {
		this.chargeRate = chargeRate;
	}

	public int getGathertimes() {
		return gathertimes;
	}

	public void setGathertimes(int gathertimes) {
		this.gathertimes = gathertimes;
	}

	public BigDecimal getGatherRate() {
		return gatherRate;
	}

	public void setGatherRate(BigDecimal gatherRate) {
		this.gatherRate = gatherRate;
	}

	public int getPact_alarm() {
		return pact_alarm;
	}

	public void setPact_alarm(int pact_alarm) {
		this.pact_alarm = pact_alarm;
	}

	public int getCustpool_c() {
		return custpool_c;
	}

	public void setCustpool_c(int custpool_c) {
		this.custpool_c = custpool_c;
	}

	public boolean getMemberCardPay() {
		return memberCardPay;
	}

	public void setMemberCardPay(boolean memberCardPay) {
		this.memberCardPay = memberCardPay;
	}

	public boolean getSaleAudit() {
		return saleAudit;
	}

	public void setSaleAudit(boolean saleAudit) {
		this.saleAudit = saleAudit;
	}

	public boolean getSaleAuditType() {
		return saleAuditType;
	}

	public void setSaleAuditType(boolean saleAuditType) {
		this.saleAuditType = saleAuditType;
	}

	public boolean getSaletuiAudit() {
		return saletuiAudit;
	}

	public void setSaletuiAudit(boolean saletuiAudit) {
		this.saletuiAudit = saletuiAudit;
	}

	public boolean getSaletuiAuditType() {
		return saletuiAuditType;
	}

	public void setSaletuiAuditType(boolean saletuiAuditType) {
		this.saletuiAuditType = saletuiAuditType;
	}

}
