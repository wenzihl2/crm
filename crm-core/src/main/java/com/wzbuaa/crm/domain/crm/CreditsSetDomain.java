package com.wzbuaa.crm.domain.crm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.wzbuaa.crm.domain.BaseEntity;

/**
 * 积分赠送规则
 * 
 * @author fangchen
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "crm_credits_set")
public class CreditsSetDomain extends BaseEntity<Long> {

	/**
	 *	积分获取方式
	 */
	public enum CreditsGetType {
		regist, signIn, invite, review, buy
	}
	
	private String name;// 规则名称
	private CreditsGetType getType;// 获取类型
	private Integer rankPoint; // 等级积分
	private Integer consumePoint; // 消费积分
	private Date startDate; // 积分开始时间
	private Date endDate; // 积分结束时间
	private Boolean show = Boolean.FALSE; // 是否开启

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Temporal(TemporalType.DATE)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Enumerated(EnumType.STRING)
	public CreditsGetType getGetType() {
		return getType;
	}

	public void setGetType(CreditsGetType getType) {
		this.getType = getType;
	}

	public Integer getRankPoint() {
		return rankPoint;
	}

	public void setRankPoint(Integer rankPoint) {
		this.rankPoint = rankPoint;
	}

	public Integer getConsumePoint() {
		return consumePoint;
	}

	public void setConsumePoint(Integer consumePoint) {
		this.consumePoint = consumePoint;
	}

	@Column(name="is_show")
	public Boolean getShow() {
		return show;
	}

	public void setShow(Boolean show) {
		this.show = show;
	}

}
