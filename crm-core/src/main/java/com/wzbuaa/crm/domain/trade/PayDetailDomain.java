package com.wzbuaa.crm.domain.trade;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.wzbuaa.crm.domain.BaseEntity;
import com.wzbuaa.crm.domain.crm.MemberDomain;

import framework.util.EnumUtils;


/**
 * 收支明细
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "crm_pay_detail")
public class PayDetailDomain extends BaseEntity<Long> {

	//pay支付,refund退款,recharge充值
	public enum DetailType {
		pay("支付"), 
		refund("退款"), 
		recharge("充值");
		
		private String name;

		private DetailType(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}
	}
	
	private String paymentNo;// 流水号
	private DetailType detailType;// 类型
	private BigDecimal before_amount = BigDecimal.ZERO;// 收支金额
	private BigDecimal amount = BigDecimal.ZERO;// 收支金额
	private BigDecimal after_amount = BigDecimal.ZERO;// 收支金额
	private BigDecimal balance;// 余额
	private String remark;// 备注
	private String orderNo;//订单编号
	private OrderDomain order;//订单
	private String operator;//操作员
	private MemberDomain member;//会员
	private String dealId;//第三方支付平台支付编号
	private String paymentTypeName;//支付方式名称
	
	@Transient
	public String getDetailInfo(){
		String detail =  EnumUtils.enumProp2I18nMap(DetailType.class).get(detailType.name());
		if(StringUtils.isNotBlank(remark)){
			detail = detail + "-" + remark;
		}
		return detail;
	}
	
	@PrePersist
	public void prePersist(){
		if(order != null){
			orderNo = order.getOrderNo();
		}
	}
	
	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	@Enumerated(EnumType.STRING)
	public DetailType getDetailType() {
		return detailType;
	}

	public void setDetailType(DetailType detailType) {
		this.detailType = detailType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getBefore_amount() {
		return before_amount;
	}

	public void setBefore_amount(BigDecimal before_amount) {
		this.before_amount = before_amount;
	}

	public BigDecimal getAfter_amount() {
		return after_amount;
	}

	public void setAfter_amount(BigDecimal after_amount) {
		this.after_amount = after_amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="order_id")
	public OrderDomain getOrder() {
		return order;
	}

	public void setOrder(OrderDomain order) {
		this.order = order;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	public MemberDomain getMember() {
		return member;
	}

	public void setMember(MemberDomain member) {
		this.member = member;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public String getPaymentTypeName() {
		return paymentTypeName;
	}

	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}
