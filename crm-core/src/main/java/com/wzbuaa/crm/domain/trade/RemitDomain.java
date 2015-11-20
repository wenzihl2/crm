package com.wzbuaa.crm.domain.trade;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.wzbuaa.crm.bean.RemitDeal;
import com.wzbuaa.crm.bean.RemitStatus;
import com.wzbuaa.crm.domain.BaseEntity;
import com.wzbuaa.crm.domain.crm.MemberDomain;

/**
 * 汇款单
 * @author fangchen
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "crm_remit")
public class RemitDomain extends BaseEntity<Long> {
	
	/**
	 * 汇款类型
	 * refund 退款
	 * fundOutcome 我的钱包转出
	 * @author Administrator
	 *
	 */
	public enum RemitType {
		refund, fundOutcome
	}

	private String order_num;//交易记录号
	private String receiver;// 收款人
	private String bank;// 银行名称
	private String organization;//开户行
	private String cardNo;// 银行卡号
	private RemitType remitType;//汇款类型
	private BigDecimal amount;// 汇款金额
	private RemitStatus remitStatus;//汇款状态
	private RemitDeal remitDeal;//处理方式
	private Date finishDate;//完成时间
	private String remark;//备注
	private MemberDomain member;//会员

	public String getOrder_num() {
		return order_num;
	}

	public void setOrder_num(String order_num) {
		this.order_num = order_num;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	public MemberDomain getMember() {
		return member;
	}

	public void setMember(MemberDomain member) {
		this.member = member;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Enumerated(EnumType.STRING)
	public RemitType getRemitType() {
		return remitType;
	}

	public void setRemitType(RemitType remitType) {
		this.remitType = remitType;
	}

	public RemitStatus getRemitStatus() {
		return remitStatus;
	}

	public void setRemitStatus(RemitStatus remitStatus) {
		this.remitStatus = remitStatus;
	}

	public RemitDeal getRemitDeal() {
		return remitDeal;
	}

	public void setRemitDeal(RemitDeal remitDeal) {
		this.remitDeal = remitDeal;
	}

}
