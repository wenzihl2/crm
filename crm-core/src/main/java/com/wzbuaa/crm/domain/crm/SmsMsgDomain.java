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
 * 实体类 - 短息
 */
@SuppressWarnings("serial")
@Entity
@Table(name="crm_sms_msg")
public class SmsMsgDomain extends BaseEntity<Long> implements Companyable {
	/**
	 *短信状态:  UNSEND(未发送), SEND(已发送), SUCCESS(发送成功),FIALURE(发送失败)
	 */
	public enum MessageStatus {
		UNSEND, SEND, SUCCESS,FIALURE;
	}
	
	public enum SmsMsgType {
		register, // 用户注册
		passwordRecover, // 密码找回
		mobileVerify,  // 手机验证
		goodsNotify,  // 商品购买
		goodsShip,  // 商品发货
		orderPay,  // 订单支付
		groupBond, // 
		passwordNotify, // 密码修改
		mobileNotify, // 手机号修改
		emailNotify, // 邮箱修改
		payPasswordNotify, // 支付密码修改
		emailVerify; // 邮箱验证
	}
	
	private String sn;         // 流水号
	private String mobile;     // 短信号码
	private String content;    // 短信内容
	private String result; // 返回消息
	private Long validTimeRang; // 有效时常，如果为null，则表示无失效时间
	private String confirmation; // 4位随机验证码
	private MessageStatus status = MessageStatus.UNSEND; // 状态
	private SmsMsgType msgType;     // 短信类型
	private Boolean isConfirm = Boolean.FALSE; // 是否验证
	private MemberDomain member; // 发送者
	private Long company;

	public SmsMsgDomain(String sn, String mobile, String content,
			String confirmation, SmsMsgType msgType, MemberDomain member) {
		super();
		this.sn = sn;
		this.mobile = mobile;
		this.content = content;
		this.confirmation = confirmation;
		this.msgType = msgType;
		this.member = member;
	}
	
	@Transient
	public boolean isValid() {
		//表示无失效时间控制
		if(this.getStatus() == MessageStatus.SUCCESS && this.getValidTimeRang() == null) {
			return true;
		} else if (this.getStatus() == MessageStatus.SUCCESS && 
				(this.getCreateDate().getTime() + validTimeRang) > new Date().getTime()) {
			return true;
		}
		return false;
	}

	public SmsMsgDomain() {
		super();
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getValidTimeRang() {
		return validTimeRang;
	}

	public void setValidTimeRang(Long validTimeRang) {
		this.validTimeRang = validTimeRang;
	}

	public String getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	public MemberDomain getMember() {
		return member;
	}

	public void setMember(MemberDomain member) {
		this.member = member;
	}
	
	@Enumerated(EnumType.STRING)
	public MessageStatus getStatus() {
		return status;
	}

	public void setStatus(MessageStatus status) {
		this.status = status;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	@Enumerated(EnumType.STRING)
	public SmsMsgType getMsgType() {
		return msgType;
	}

	public void setMsgType(SmsMsgType msgType) {
		this.msgType = msgType;
	}

	public Boolean getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(Boolean isConfirm) {
		this.isConfirm = isConfirm;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Long getCompany() {
		return company;
	}

	public void setCompany(Long company) {
		this.company = company;
	}

}
