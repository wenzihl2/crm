package com.wzbuaa.crm.domain.trade;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.wzbuaa.crm.domain.BaseEntity;

@Entity
@Table(name = "crm_statements")
public class StatementsDomain extends BaseEntity<Long> {

	private static final long serialVersionUID = -1340317298714183232L;

	private String statementsNo;//结算编号
	private BigDecimal amount;// 结算金额
	private String receiver;// 收款人
	private String bank;// 开户行
	private String cardNo;// 银行卡号
	private String supplier_name;// 商家名称
	private String location_name;// 门店名称
	private String location_id;// 门店id
	private Date dealDate;// 处理时间

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

	public String getLocation_name() {
		return location_name;
	}

	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}

	public String getLocation_id() {
		return location_id;
	}

	public void setLocation_id(String location_id) {
		this.location_id = location_id;
	}

	public Date getDealDate() {
		return dealDate;
	}

	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}

	public String getSupplier_name() {
		return supplier_name;
	}

	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}

	public String getStatementsNo() {
		return statementsNo;
	}

	public void setStatementsNo(String statementsNo) {
		this.statementsNo = statementsNo;
	}

}
