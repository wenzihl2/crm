package com.wzbuaa.crm.domain.trade;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import static com.wzbuaa.crm.domain.Constants.ORDER_EXPIRED_HOURS;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.alibaba.fastjson.JSONArray;
import com.wzbuaa.crm.bean.ConsumeStatus;
import com.wzbuaa.crm.bean.PaymentStatus;
import com.wzbuaa.crm.bean.RecordType;
import com.wzbuaa.crm.bean.SystemID;
import com.wzbuaa.crm.domain.BaseEntity;
import com.wzbuaa.crm.domain.base.PaymentTypeDomain;
import com.wzbuaa.crm.domain.crm.MemberDomain;
import com.wzbuaa.crm.domain.sso.user.S_userDomain;
import com.wzbuaa.crm.util.SettingUtils;

/**
 * 交易记录 实体类
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "crm_order")
public class OrderDomain extends BaseEntity<Long> {

	private ConsumeStatus consumeStatus; // 消费状态 freezeDedected(已扣)
	private String orderNo; // 交易记录编号
	private String title;// 交易记录标题
	private String body;// 交易记录详情
	private String notifyUrl;//通知地址
	private String showUrl;//支付结果页面
	private String detailUrl;//详情地址
	private BigDecimal amount = BigDecimal.ZERO; // 单笔订单金额
	private BigDecimal discount = BigDecimal.ZERO; // 优惠(可用于会员卡优惠)
	private SystemID systemId; // 系统编号
	private RecordType recordType; // 业务类型
	private MemberDomain member; // 会员
	private Boolean isRefund = Boolean.FALSE; // 是否已退款
	private BigDecimal deliveryPrice = BigDecimal.ZERO; // 快递费

	private Integer credits = 0;//积分
	private BigDecimal deposit = BigDecimal.ZERO;//余额
	private BigDecimal cash = BigDecimal.ZERO; // 现金
	private BigDecimal fund = BigDecimal.ZERO;//我的钱包
	private PaymentStatus paymentStatus; //支付类型
	private PaymentTypeDomain paymentType;//支付方式
	private String paymentTypeName;//支付方式名称
	private Date expired; //过期时间
	private Set<OrderDomain> children; //子记录(退款记录)
	private OrderDomain parent; //父记录(消费记录)
	private S_userDomain operator; //操作员，目前用于门店付款
	private String refundOperator; //退款操作员
	private String refundBatchNo; //退款批次号
	private RemitDomain remit; //退款单
	private OrderPayDomain orderPay;//订单支付记录
	
	private Long supplier_id; //商家id
	private String supplier_name; //商家名称
	private Date turnoverTime; //支付时间
	private String tradeNo; //付款编号

	public OrderDomain() {
		super();
	}

	public OrderDomain(String orderNo, SystemID systemId,
			RecordType recordType, MemberDomain member) {
		super();
		this.orderNo = orderNo;
		this.systemId = systemId;
		this.recordType = recordType;
		this.member = member;
	}
	
	@PrePersist
	public void init() {
		if(expired == null && recordType != RecordType.refund){
			expired = DateUtils.addHours(new Date(), ORDER_EXPIRED_HOURS);
		}
	}
	
	/**
	 * 是否需要验证支付密码
	 * @return
	 */
	@Transient
	public boolean validPayPwd(){
		if(deposit != null && deposit.signum() == 1){
			return true;
		}
		if(credits != null && credits > 0){
			return true;
		}
		if(fund != null && fund.signum() == 1){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取剩余需要支付的金额
	 * @return
	 */
	@Transient
	public BigDecimal getRemaind(){
		BigDecimal total = amount;
		if(discount != null){
			total = total.subtract(discount);
		}
		if(credits != null){
			BigDecimal count = new BigDecimal(credits).divide(new BigDecimal(SettingUtils.get().getCreditsRate()), 2, BigDecimal.ROUND_HALF_UP);
			total = total.subtract(count);
		}
		if(fund != null){
			total = total.subtract(fund);
		}
		if(deposit != null){
			total = total.subtract(deposit);
		}
		return total;
	}
	
	/**
	 * 除去运费后的总额
	 * @return
	 */
	@Transient
	public BigDecimal getTotalWithoutDelivery(){
		if(deliveryPrice == null){
			return amount;
		}
		return amount.subtract(deliveryPrice);
	}

	/**
	 * 获取订单信息
	 * @return
	 */
	@Transient
	public JSONArray getPayInfo(){
		if(StringUtils.isBlank(body)){
			return new JSONArray();
		}
		return JSONArray.parseArray(body);
	}
	
	public ConsumeStatus getConsumeStatus() {
		return consumeStatus;
	}

	public void setConsumeStatus(ConsumeStatus consumeStatus) {
		this.consumeStatus = consumeStatus;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public SystemID getSystemId() {
		return systemId;
	}

	public void setSystemId(SystemID systemId) {
		this.systemId = systemId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	public MemberDomain getMember() {
		return member;
	}

	public void setMember(MemberDomain member) {
		this.member = member;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getCash() {
		return cash;
	}

	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}

	public RecordType getRecordType() {
		return recordType;
	}

	public void setRecordType(RecordType recordType) {
		this.recordType = recordType;
	}

	public BigDecimal getDeposit() {
		return deposit;
	}

	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_type_id")
	public PaymentTypeDomain getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentTypeDomain paymentType) {
		this.paymentType = paymentType;
	}

	public String getPaymentTypeName() {
		return paymentTypeName;
	}

	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}

	public Date getExpired() {
		return expired;
	}

	public void setExpired(Date expired) {
		this.expired = expired;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.REMOVE, mappedBy="parent")
	public Set<OrderDomain> getChildren() {
		return children;
	}

	public void setChildren(Set<OrderDomain> children) {
		this.children = children;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	public OrderDomain getParent() {
		return parent;
	}

	public void setParent(OrderDomain parent) {
		this.parent = parent;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	public S_userDomain getOperator() {
		return operator;
	}

	public void setOperator(S_userDomain operator) {
		this.operator = operator;
	}

	public String getRefundOperator() {
		return refundOperator;
	}

	public void setRefundOperator(String refundOperator) {
		this.refundOperator = refundOperator;
	}

	public String getRefundBatchNo() {
		return refundBatchNo;
	}

	public void setRefundBatchNo(String refundBatchNo) {
		this.refundBatchNo = refundBatchNo;
	}

	@OneToOne(fetch=FetchType.LAZY)
	public RemitDomain getRemit() {
		return remit;
	}

	public void setRemit(RemitDomain remit) {
		this.remit = remit;
	}

	public Integer getCredits() {
		return credits;
	}

	public void setCredits(Integer credits) {
		this.credits = credits;
	}

	public Boolean getIsRefund() {
		return isRefund;
	}

	public void setIsRefund(Boolean isRefund) {
		this.isRefund = isRefund;
	}

	public BigDecimal getDeliveryPrice() {
		return deliveryPrice;
	}

	public void setDeliveryPrice(BigDecimal deliveryPrice) {
		this.deliveryPrice = deliveryPrice;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	@Column(length=1000)
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public BigDecimal getFund() {
		return fund;
	}

	public void setFund(BigDecimal fund) {
		this.fund = fund;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public String getShowUrl() {
		return showUrl;
	}

	public void setShowUrl(String showUrl) {
		this.showUrl = showUrl;
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

	public Date getTurnoverTime() {
		return turnoverTime;
	}

	public void setTurnoverTime(Date turnoverTime) {
		this.turnoverTime = turnoverTime;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	public OrderPayDomain getOrderPay() {
		return orderPay;
	}

	public void setOrderPay(OrderPayDomain orderPay) {
		this.orderPay = orderPay;
	}

}
