package com.wzbuaa.crm.domain.crm;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.wzbuaa.crm.component.entity.Companyable;
import com.wzbuaa.crm.domain.BaseEntity;

/**
 * 邮件通知
 * @author zhenglong
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "crm_email_msg")
public class EmailMsgDomain extends BaseEntity<Long> implements Companyable {

	// 消息类型: 注册，忘记密码,会员邀请, 修改
	public enum EmailMsgType {
		Register, PasswordForget, Invite, Modify, PasswordNotify, EmailNotify, MobileNotify, PayPasswordNofity;
	}

	private String toEmail; // 发送的邮件地址
	private MemberDomain member; // 发送者
	private EmailMsgType messageType; // 消息类型
	private String confirmation; // 16位随机验证码
	private Boolean isConfirm = Boolean.FALSE; // 是否验证
	private Long validTimeRang; // 有效时常，如果为null，则表示无失效时间
	private Long company; // 所属企业
	
	public EmailMsgDomain() {
		super();
	}

	public EmailMsgDomain(MemberDomain member, String toEmail, EmailMsgType messageType, String confirmation) {
		super();
		this.member = member;
		this.toEmail = toEmail;
		this.messageType = messageType;
		this.confirmation = confirmation;
	}

	@Transient
	public boolean isValid() {
		//表示无失效时间控制
		if(this.getValidTimeRang() == null) {
			return true;
		} else if ((this.getCreateDate().getTime() + validTimeRang) >= new Date().getTime()) {
			return true;
		}
		return false;
		
	}
	
	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	@Enumerated(EnumType.STRING)
	public EmailMsgType getMessageType() {
		return messageType;
	}

	public void setMessageType(EmailMsgType messageType) {
		this.messageType = messageType;
	}

	public String getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}

	public Boolean getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(Boolean isConfirm) {
		this.isConfirm = isConfirm;
	}

	public Long getValidTimeRang() {
		return validTimeRang;
	}

	public void setValidTimeRang(Long validTimeRang) {
		this.validTimeRang = validTimeRang;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	public MemberDomain getMember() {
		return member;
	}

	public void setMember(MemberDomain member) {
		this.member = member;
	}

	public Long getCompany() {
		return company;
	}

	public void setCompany(Long company) {
		this.company = company;
	}

}