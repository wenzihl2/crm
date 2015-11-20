package com.wzbuaa.crm.bean;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 用于订单导入
 * 
 * @author zhenglong
 * 
 */
@XmlRootElement(name="OrderBean")
public class OrderBean {

	private String orderNo; // 订单编号
	private String title;// 订单编号
	private String body;// 订单详情
	private RecordType recordType;//订单类型
	private BigDecimal amount; // 订单金额
	private BigDecimal discount; // 折扣
	private SystemID systemId; // 系统编号
	private BigDecimal deposit;// 余额
	private BigDecimal cash; // 现金
	private Integer credits; // 积分
	private BigDecimal fund;//我的钱包

	private String pasword;// 密码
	private String operater;// 操作员
	private Long memberId;//crm会员ID
	private String mobile;//手机号码 
	private String notifyUrl;//通知地址
	private String showUrl;//通知地址
	private String detailUrl;//详情地址

	private BigDecimal deliveryPrice;//快递费
	private Integer expiredMinutes;//失效时间（单位：分钟）
	private String groupNo;//交易记录归属的订单编号
	private PaymentStatus paymentStatus;//支付类型
	
	private String receiver;// 收款人
	private String bank;// 开户行
	private String cardNo;// 银行卡号
	private Long supplier_id;//商家id
	private String supplier_name;//商家名称
	private Boolean returnToDeposit;//退款到余额
	
	private String logistics_name;//物流名称
	private String invoice_no;//物流单号

	public OrderBean() {
		super();
	}

	public BigDecimal getRemaind(){
		BigDecimal total = amount;
		if(discount != null){
			amount = amount.subtract(discount);
		}
		return total;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public SystemID getSystemId() {
		return systemId;
	}

	public void setSystemId(SystemID systemId) {
		this.systemId = systemId;
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

	public Integer getCredits() {
		return credits;
	}

	public void setCredits(Integer credits) {
		this.credits = credits;
	}

	public String getPasword() {
		return pasword;
	}

	public void setPasword(String pasword) {
		this.pasword = pasword;
	}

	public String getOperater() {
		return operater;
	}

	public void setOperater(String operater) {
		this.operater = operater;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public BigDecimal getDeliveryPrice() {
		return deliveryPrice;
	}

	public void setDeliveryPrice(BigDecimal deliveryPrice) {
		this.deliveryPrice = deliveryPrice;
	}

	public Integer getExpiredMinutes() {
		return expiredMinutes;
	}

	public void setExpiredMinutes(Integer expiredMinutes) {
		this.expiredMinutes = expiredMinutes;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Long getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(Long supplier_id) {
		this.supplier_id = supplier_id;
	}

	public String getSupplier_name() {
		return supplier_name;
	}

	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}

	public RecordType getRecordType() {
		return recordType;
	}

	public void setRecordType(RecordType recordType) {
		this.recordType = recordType;
	}

	public BigDecimal getFund() {
		return fund;
	}

	public void setFund(BigDecimal fund) {
		this.fund = fund;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public Boolean getReturnToDeposit() {
		return returnToDeposit;
	}

	public void setReturnToDeposit(Boolean returnToDeposit) {
		this.returnToDeposit = returnToDeposit;
	}

	public String getShowUrl() {
		return showUrl;
	}

	public void setShowUrl(String showUrl) {
		this.showUrl = showUrl;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLogistics_name() {
		return logistics_name;
	}

	public void setLogistics_name(String logistics_name) {
		this.logistics_name = logistics_name;
	}

	public String getInvoice_no() {
		return invoice_no;
	}

	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
	}

}
