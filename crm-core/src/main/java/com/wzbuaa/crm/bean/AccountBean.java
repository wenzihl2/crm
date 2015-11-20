package com.wzbuaa.crm.bean;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

@XmlRootElement(name = "AccountBean")
public class AccountBean {
	private Long id;
	private String identityCard; //身份证号
	private String userName; // 开户姓名
	private String organization; // 办理机构名称
	private String cardNum; // 卡号
	private String attribution; // 所属机构
	private String code; // 银行代码

	public AccountBean() {
	}

	public AccountBean(Long id, String userName, String organization, String cardNum,
			String attribution, String code) {
		this.id = id;
		this.userName = userName;
		this.organization = organization;
		this.cardNum = cardNum;
		this.attribution = attribution;
		this.code = code;
	}
	
	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getLastCode(){
		if(StringUtils.isBlank(cardNum) || cardNum.length() < 4){
			return cardNum;
		}
		return cardNum.substring(cardNum.length() - 4, cardNum.length());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getAttribution() {
		return attribution;
	}

	public void setAttribution(String attribution) {
		this.attribution = attribution;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
