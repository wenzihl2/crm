package com.wzbuaa.crm.domain.trade;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.wzbuaa.crm.domain.BaseEntity;
import com.wzbuaa.crm.domain.base.PaymentTypeDomain;

/**
 * 支付接口结算数据
 * @author fangchen
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "crm_settle")
public class SettleDomain extends BaseEntity<Long> {

	private String tradeNo;// 交易编号
	private Date settleDate;// 结算日期
	private Date tradeDate;// 交易时间
	private String dealId;// 第三方交易号
	private BigDecimal amount;// 交易金额
	private BigDecimal fee;// 手续费
	private BigDecimal settleAmount;// 结算金额
	private PaymentTypeDomain paymentType;//付款方式
	private Boolean isCorrect;//是否正确

	public SettleDomain() {
		super();
	}

	public SettleDomain(String tradeNo, Date settleDate, Date tradeDate,
			String dealId, BigDecimal amount, BigDecimal fee,
			BigDecimal settleAmount, PaymentTypeDomain paymentType) {
		super();
		this.tradeNo = tradeNo;
		this.settleDate = settleDate;
		this.tradeDate = tradeDate;
		this.dealId = dealId;
		this.amount = amount;
		this.fee = fee;
		this.settleAmount = settleAmount;
		this.paymentType = paymentType;
		this.isCorrect = false;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Date getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}

	public Date getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public BigDecimal getSettleAmount() {
		return settleAmount;
	}

	public void setSettleAmount(BigDecimal settleAmount) {
		this.settleAmount = settleAmount;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	public PaymentTypeDomain getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentTypeDomain paymentType) {
		this.paymentType = paymentType;
	}

	public Boolean getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(Boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

}
