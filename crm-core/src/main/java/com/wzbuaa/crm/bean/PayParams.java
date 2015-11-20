package com.wzbuaa.crm.bean;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PayParams")
public class PayParams {
	
	private String tradeNo;//交易编号
	private String title;//交易标题
	private BigDecimal goodsAmount;//商品总额
	private BigDecimal deliveryAmount;//快递总额
	private BigDecimal totalAmount;//交易总额
	private Integer creditsLimit;//积分使用限制
	private String error;//错误信息
	private Boolean hadPay;//是否已支付
	private Boolean isMerge;//是否合并支付
	private String orderNo;//订单编号，若isMerge为true时，orderNo为null
	private String body;//交易内容
	private Boolean isGroupOrder;//是否团购
	private Boolean isFundOrder;//是否我的钱包
	
	private Integer credits;//使用积分
	private Integer creditsRate;//积分汇率

	public BigDecimal getCreditsAmount(){
		if(credits == null){
			return BigDecimal.ZERO;
		}
		return new BigDecimal(credits).divide(new BigDecimal(creditsRate), 2, BigDecimal.ROUND_DOWN);
	}
	
	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCreditsLimit() {
		return creditsLimit;
	}

	public void setCreditsLimit(Integer creditsLimit) {
		this.creditsLimit = creditsLimit;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Boolean getHadPay() {
		return hadPay;
	}

	public void setHadPay(Boolean hadPay) {
		this.hadPay = hadPay;
	}

	public BigDecimal getGoodsAmount() {
		return goodsAmount;
	}

	public void setGoodsAmount(BigDecimal goodsAmount) {
		this.goodsAmount = goodsAmount;
	}

	public BigDecimal getDeliveryAmount() {
		return deliveryAmount;
	}

	public void setDeliveryAmount(BigDecimal deliveryAmount) {
		this.deliveryAmount = deliveryAmount;
	}

	public Boolean getIsMerge() {
		return isMerge;
	}

	public void setIsMerge(Boolean isMerge) {
		this.isMerge = isMerge;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Integer getCredits() {
		return credits;
	}

	public void setCredits(Integer credits) {
		this.credits = credits;
	}

	public Integer getCreditsRate() {
		return creditsRate;
	}

	public void setCreditsRate(Integer creditsRate) {
		this.creditsRate = creditsRate;
	}

	public Boolean getIsGroupOrder() {
		return isGroupOrder;
	}

	public void setIsGroupOrder(Boolean isGroupOrder) {
		this.isGroupOrder = isGroupOrder;
	}

	public Boolean getIsFundOrder() {
		return isFundOrder;
	}

	public void setIsFundOrder(Boolean isFundOrder) {
		this.isFundOrder = isFundOrder;
	}

}
