package com.wzbuaa.crm.domain.crm;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import com.wzbuaa.crm.domain.BaseEntity;

/**
 * 用户账户
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "crm_account")
public class AccountDomain extends BaseEntity<Long> {

	public static final String IDCARD_PATTERN = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$|^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
	
	private String idCard;  // 身份证号
	private String username;      // 开户姓名
	private String organization;  // 办理机构名称
	private String cardNum;       // 卡号
	private String attribution;   // 所属机构
	private MemberDomain member;  // 会员
	private String code;		  //银行代码
	
	/**
	 * 尾号
	 * @return
	 */
	@Transient
	public String getLastNum(){
		if(StringUtils.isBlank(cardNum) || cardNum.length() < 4){
			return cardNum;
		}
		return cardNum.substring(cardNum.length() - 4, cardNum.length());
	}

	@NotEmpty(message = "{not.null}")
	@Pattern(regexp = IDCARD_PATTERN, message = "{account.idcard.not.valid}")
	public String getIdCard() {
		return idCard;
	}


	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}


	@NotEmpty(message = "{not.null}")
	public String getUserName() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	@NotEmpty(message = "{not.null}")
	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	@NotEmpty(message = "{not.null}")
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

	@ManyToOne(fetch = FetchType.LAZY)
	public MemberDomain getMember() {
		return member;
	}

	public void setMember(MemberDomain member) {
		this.member = member;
	}

	@NotEmpty(message = "{not.null}")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
