package com.wzbuaa.crm.domain.trade;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.wzbuaa.crm.domain.BaseEntity;
import com.wzbuaa.crm.domain.base.PaymentTypeDomain;

/**
 * 支付日志
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "crm_payment_log")
public class PaymentLogDomain extends BaseEntity<Long> {

	private String orderNo;// 订单编号
	private String memberName;// 会员名称
	private BigDecimal orderAccount;// 订单金额
	private String bankId;// 银行代码
	private String bankDealId;// 银行交易号
	private String dealId;// 第三方交易号
	private Date dealTime;// 第三方交易时间
	private BigDecimal fee;// 第三方手续费
	private BigDecimal payAmount;// 实际支付金额
	private Boolean paySuccess;// 处理结果
	private String errorCode;// 错误代码
	private PaymentTypeDomain payType;//支付方式

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id")
	public PaymentTypeDomain getPayType() {
		return payType;
	}

	public void setPayType(PaymentTypeDomain payType) {
		this.payType = payType;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public BigDecimal getOrderAccount() {
		return orderAccount;
	}

	public void setOrderAccount(BigDecimal orderAccount) {
		this.orderAccount = orderAccount;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankDealId() {
		return bankDealId;
	}

	public void setBankDealId(String bankDealId) {
		this.bankDealId = bankDealId;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public Boolean getPaySuccess() {
		return paySuccess;
	}

	public void setPaySuccess(Boolean paySuccess) {
		this.paySuccess = paySuccess;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
