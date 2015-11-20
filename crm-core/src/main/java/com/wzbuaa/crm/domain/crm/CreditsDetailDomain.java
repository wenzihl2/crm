package com.wzbuaa.crm.domain.crm;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.wzbuaa.crm.domain.BaseEntity;
import com.wzbuaa.crm.domain.crm.CreditsSetDomain.CreditsGetType;

/**
 * 积分明细
 * @author zhenglong
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "crm_credits_detail")
public class CreditsDetailDomain extends BaseEntity<Long> {

	private Integer amount;// 数量
	private CreditsGetType creditsGetType;// 明细类别
	private String remark;// 来源/用途备注
	private Integer remaind;//余额
	private MemberDomain member;//会员
	private String operator;//操作员
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	@Enumerated(EnumType.STRING)
	public CreditsGetType getCreditsGetType() {
		return creditsGetType;
	}

	public void setCreditsGetType(CreditsGetType creditsGetType) {
		this.creditsGetType = creditsGetType;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	public MemberDomain getMember() {
		return member;
	}

	public void setMember(MemberDomain member) {
		this.member = member;
	}

	public Integer getRemaind() {
		return remaind;
	}

	public void setRemaind(Integer remaind) {
		this.remaind = remaind;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

}
