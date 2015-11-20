package com.wzbuaa.crm.bean;

import java.math.BigDecimal;

import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MemberBean")
public class MemberBean {
	private Long id; // 会员ID
	private String username;//用户名
	private String realName;//真实姓名
	private String email;//电子邮箱
	private String mobile;//手机
	private String passwd; // 会员密码
	private BigDecimal deposit;// 余额
	private Integer rankPoint; //等级积分
	private Integer consumePoint; // 消费积分
	private Integer bankCardNum;//银行卡数量

	public MemberBean() {
		super();
	}

	public MemberBean(Long id, String username, String realName, String email,
			String mobile, String passwd, BigDecimal deposit, 
			Integer rankPoint, Integer consumePoint, Integer bankCardNum) {
		super();
		this.id = id;
		this.username = username;
		this.realName = realName;
		this.email = email;
		this.mobile = mobile;
		this.passwd = passwd;
		this.deposit = deposit;
		this.rankPoint = rankPoint;
		this.consumePoint = consumePoint;
		this.bankCardNum = bankCardNum;
	}
	
	/**
	 * 获取隐藏后的用户名
	 * @return
	 */
	@Transient
	public String getSecretName(){
		return username.substring(0, 6) + "******" + username.substring(11);
	}
	
	public String getSecMobile(){
		return mobile.substring(0, 3) + "*****" + mobile.substring(8);
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public BigDecimal getDeposit() {
		return deposit;
	}

	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getBankCardNum() {
		return bankCardNum;
	}

	public void setBankCardNum(Integer bankCardNum) {
		this.bankCardNum = bankCardNum;
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

}
