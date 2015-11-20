package com.wzbuaa.crm.bean;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DepositDrawBean")
public class DepositDrawBean {
	
	private String name;// 开户姓名
	private String bankName;// 开户行
	private String cardNo;// 银行卡号
	private BigDecimal applyAmount;// 申请提现金额
	private BigDecimal realAmount;// 实际可提金额
	private SystemID systemId;// 来源
	private String remark;// 备注
	private String applyName;// 申请人姓名
	private Long memberId;//会员id
	private Long accountId;//银行卡id
	private String payPassword;//支付密码
	private DrawType drawType;//提现类型

	public DepositDrawBean() {
	}

	public DepositDrawBean(String name, String bankName, String cardNo,
			BigDecimal applyAmount, BigDecimal realAmount, SystemID systemId,
			String remark, String applyName) {
		super();
		this.name = name;
		this.bankName = bankName;
		this.cardNo = cardNo;
		this.applyAmount = applyAmount;
		this.realAmount = realAmount;
		this.systemId = systemId;
		this.remark = remark;
		this.applyName = applyName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public BigDecimal getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	public BigDecimal getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(BigDecimal realAmount) {
		this.realAmount = realAmount;
	}
	public SystemID getSystemId() {
		return systemId;
	}

	public void setSystemId(SystemID systemId) {
		this.systemId = systemId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getApplyName() {
		return applyName;
	}

	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public DrawType getDrawType() {
		return drawType;
	}

	public void setDrawType(DrawType drawType) {
		this.drawType = drawType;
	}

}
