package com.wzbuaa.crm.domain.crm;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.wzbuaa.crm.domain.BaseEntity;
/**
 * 实体类 - 短息
 */
@SuppressWarnings("serial")
@Entity
@Table(name="crm_sms_message")
public class SmsMessageDomain extends BaseEntity<Long> {
	/**
	 *短信状态:  UNSEND(未发送), SEND(已发送), SUCCESS(发送成功),FIALURE(发送失败)
	 */
	public enum MessageStatus {
		UNSEND, SEND, SUCCESS,FIALURE;
	}
	
	public enum MessageType {
		passwordRecover, // 密码找回
		mobileVerify,  // 手机验证
		goodsNotify,  // 商品购买
		goodsShip,  // 商品发货
		orderPay,  // 订单支付
		groupBond, // 
		passwordNotify, // 本店密码修改
		payPasswordNotify, // 支付密码修改
		emailVerify; // 邮箱验证
	}
	
	private String smsNumber;     // 短信号码
	private String smsContent;    // 短信内容
	private Date   sendTime;      // 发送时间
	private String sn;            // 流水号
	private String description;   // 备注
	private MessageStatus status; // 状态
	private MessageType type;     // 短信类型

	
	public String getSmsNumber() {
		return smsNumber;
	}

	public void setSmsNumber(String smsNumber) {
		this.smsNumber = smsNumber;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Enumerated(EnumType.STRING)
	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}
	

}
