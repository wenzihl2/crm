package com.wzbuaa.crm.domain.trade;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.wzbuaa.crm.bean.PayStatus;
import com.wzbuaa.crm.bean.RecordType;
import com.wzbuaa.crm.domain.BaseEntity;
import com.wzbuaa.crm.domain.crm.MemberDomain;

/**
 * 订单支付记录
 * @author fangchen
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "crm_order_pay")
public class OrderPayDomain extends BaseEntity<Long> {

	private String tradeNo;// 交易编号
	private Boolean isPay;//是否支付
	private Integer credits;// 积分
	private BigDecimal deposit;// 余额
	private BigDecimal cash;//现金
	private Date expired;//过期时间
	private OrderDomain order;//合并支付订单
	private Boolean isSend;//是否发货，用于担保交易接口
	private MemberDomain member;//会员
	private PayStatus payStatus;//交易状态
	private String payType;//付款方式，用于手机支付
	
	@PreRemove
	public void preRemove(){
		if(order != null){
			order.setOrderPay(null);
		}
	}
	
	/**
	 * 获取交易记录详情页面
	 * @return
	 */
	@Transient
	public String getDetailUrl(){
		if(order != null){
			return order.getDetailUrl();
		}
		return null;
	}

	/**
	 * 获取交易记录详情页面
	 * @return
	 */
	@Transient
	public String getShowUrl(){
		if(isShopOrder()){
			return "order/result.jhtml?tradeNo=" + tradeNo;
		}
		return "";
	}
	
	/**
	 * 获取交易信息
	 * @return
	 */
	@Transient
	public String getBody() {
		if(order != null){
			return order.getTitle();
		}
		return null;
	}

	/**
	 * 是否团购交易
	 * @return
	 */
	@Transient
	public boolean isShopOrder(){
		if(order == null){
			return false;
		}
		if(order.getRecordType() != RecordType.shop){
			return false;
		}
		return true;
	}

	/**
	 * 获取单笔支付下的订单编号
	 * @return
	 */
	@Transient
	public String getOrderNo(){
		if(order == null){
			return null;
		}
		return order.getOrderNo();
	}
	
	@Column(nullable=false)
	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Integer getCredits() {
		return credits;
	}

	public void setCredits(Integer credits) {
		this.credits = credits;
	}

	public BigDecimal getDeposit() {
		return deposit;
	}

	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
	}

	public BigDecimal getCash() {
		return cash;
	}

	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}

	public Date getExpired() {
		return expired;
	}

	public void setExpired(Date expired) {
		this.expired = expired;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	public OrderDomain getOrder() {
		return order;
	}

	public void setOrder(OrderDomain order) {
		this.order = order;
	}

	public Boolean getIsSend() {
		return isSend;
	}

	public void setIsSend(Boolean isSend) {
		this.isSend = isSend;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	public MemberDomain getMember() {
		return member;
	}

	public void setMember(MemberDomain member) {
		this.member = member;
	}

	public Boolean getIsPay() {
		return isPay;
	}

	public void setIsPay(Boolean isPay) {
		this.isPay = isPay;
	}

	@Enumerated(EnumType.STRING)
	public PayStatus getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

}
