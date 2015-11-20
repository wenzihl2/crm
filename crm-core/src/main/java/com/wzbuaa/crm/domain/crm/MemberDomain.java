package com.wzbuaa.crm.domain.crm;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wzbuaa.crm.bean.MemberBean;
import com.wzbuaa.crm.bean.QuestionType;
import com.wzbuaa.crm.bean.SystemID;
import com.wzbuaa.crm.domain.BaseEntity;
import com.wzbuaa.crm.domain.sso.user.UserStatus;

/**
 * 会员
 * 
 * @author zhenglong
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "crm_member")
public class MemberDomain extends BaseEntity<Long> {

	public static final String USERNAME_PATTERN = "^[\\u4E00-\\u9FA5\\uf900-\\ufa2d_a-zA-Z][\\u4E00-\\u9FA5\\uf900-\\ufa2d\\w]{1,19}$";
    public static final String EMAIL_PATTERN = "^((([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+(\\.([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+)*)|((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f]|\\x21|[\\x23-\\x5b]|[\\x5d-\\x7e]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(\\\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(\\x22)))@((([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.?";
    public static final String MOBILE_PHONE_NUMBER_PATTERN = "^0{0,1}(13[0-9]|15[0-9]|14[0-9]|18[0-9])[0-9]{8}$";
    public static final int USERNAME_MIN_LENGTH = 2;
    public static final int USERNAME_MAX_LENGTH = 20;
    public static final int PASSWORD_MIN_LENGTH = 5;
    public static final int PASSWORD_MAX_LENGTH = 50;
	
	private String idcard; // 身份证
	private String phone; // 电话号码
	private String mobile; // 手机号码
	private Boolean emailChecked;// 是否启用手机
	private String email; // 邮件
	private Boolean mobileChecked;// 是否启用邮箱
	private String name;// 真实姓名
	private String username; // 登陆账号
	private String password;//密码
	private String pay_passwd;// 支付密码
	private String salt; //加密因子
	private UserStatus status = UserStatus.normal; // 用户的使用状态
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birth; // 生日
	private int gender; // 性别

	private String address; // 地址
	private String zip_code; // 邮编
	private Boolean isEnablepay_passwd = Boolean.FALSE;//是否启用支付密码

	private Integer rankPoint = 0; // 等级积分
	private Integer consumePoint = 0;// 会员卡消费积分
	private BigDecimal deposit = BigDecimal.ZERO;// 会员余额
	private Integer freeze_consumePoint = 0;// 冻结积分
	private BigDecimal freeze_deposit = BigDecimal.ZERO;// 冻结余额
	// 安全性
	private Boolean isEnableSecurity = Boolean.FALSE;// 是否启安全问题，true：是 ； false：否
	private Integer useFailureCount = 0;// 连续使用失败的次数
	private Boolean isAccountLocked = Boolean.FALSE; // 帐号是否锁定,用于连续使用失败useFailureCount次数后，锁定帐号
	private String registerIp;// 注册IP
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date loginDate;// 最后登录日期
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lockedDate;// 账号锁定日期
	private Integer payPwdFailCount = 0;//支付密码错误次数
	private Boolean isPayLocked = Boolean.FALSE;//支付锁定
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date payLockedDate;// 账号锁定日期
	@JsonIgnore
	private Set<AccountDomain> accountSet;// 账户信息

	//密保
	@JsonIgnore
	private QuestionType question1;
	private String answer1;
	@JsonIgnore
	private QuestionType question2;
	private String answer2;
	@JsonIgnore
	private QuestionType question3;
	private String answer3;
	
	private SystemID systemId = SystemID.CRM;//会员来源
	private Long company;
	private String tencentId;//qq第三方登陆信息
	private String alipayId;//支付宝第三方登陆信息
	private String wxid;//微信第三方登陆信息
	
	@Enumerated(EnumType.STRING)
	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	/**
	 * 获取隐藏后的用户名
	 * @return
	 */
	@Transient
	public String getSecretName(){
		return username.substring(0, 6) + "******" + username.substring(11);
	}
	
	/**
	 * 获取隐藏后的手机号
	 * @return
	 */
	@Transient
	public String getSecretMobile(){
		return mobile.substring(0, 3) + "*****" + mobile.substring(8);
	}

	@Transient
	public MemberBean getMemberBean() {
		MemberBean memberBean = new MemberBean(getId(), username, name, 
				email, mobile, password, deposit, rankPoint, consumePoint, accountSet == null?0:accountSet.size());
		return memberBean;
	}
	
	/**
	 * 安全等级
	 * @return
	 */
	@Transient
	@JsonIgnore
	public String getSafeRank(){
		if(emailChecked != null && emailChecked && mobileChecked != null && mobileChecked){
			return "3";
		} else if((emailChecked != null && emailChecked) || (mobileChecked != null && mobileChecked)) {
			return "2";
		}
		return "1";
	}
	
	public Boolean getEmailChecked() {
		return emailChecked;
	}

	public void setEmailChecked(Boolean emailChecked) {
		this.emailChecked = emailChecked;
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

	public Integer getFreeze_consumePoint() {
		return freeze_consumePoint;
	}

	public void setFreeze_consumePoint(Integer freeze_consumePoint) {
		this.freeze_consumePoint = freeze_consumePoint;
	}

	public Boolean getMobileChecked() {
		return mobileChecked;
	}

	public void setMobileChecked(Boolean mobileChecked) {
		this.mobileChecked = mobileChecked;
	}

	@Column(updatable = false, nullable = false, unique = true)
	@NotNull(message = "{not.null}")
    @Pattern(regexp = USERNAME_PATTERN, message = "{user.username.not.valid}")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	@NotEmpty(message = "{not.null}")
    @Pattern(regexp = EMAIL_PATTERN, message = "{user.email.not.valid}")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getZip_code() {
		return zip_code;
	}

	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}

	@Column(length=32)
	@Length(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH, message = "{user.password.not.valid}")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
     * 生成新的种子
     */
    public void randomSalt() {
        setSalt(RandomStringUtils.randomAlphanumeric(10));
    }

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Integer getUseFailureCount() {
		return useFailureCount;
	}

	public void setUseFailureCount(Integer useFailureCount) {
		this.useFailureCount = useFailureCount;
	}

	public Boolean getIsAccountLocked() {
		return isAccountLocked;
	}

	public void setIsAccountLocked(Boolean isAccountLocked) {
		this.isAccountLocked = isAccountLocked;
	}

	public Date getLockedDate() {
		return lockedDate;
	}

	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}

	@NotEmpty(message = "{not.null}")
    @Pattern(regexp = MOBILE_PHONE_NUMBER_PATTERN, message = "{user.mobile.phone.number.not.valid}")
    @Column(name = "mobile")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public BigDecimal getDeposit() {
		return deposit;
	}

	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
	}

	public BigDecimal getFreeze_deposit() {
		return freeze_deposit;
	}

	public void setFreeze_deposit(BigDecimal freeze_deposit) {
		this.freeze_deposit = freeze_deposit;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
	public Set<AccountDomain> getAccountSet() {
		return accountSet;
	}

	public void setAccountSet(Set<AccountDomain> accountSet) {
		this.accountSet = accountSet;
	}

	public String getPay_passwd() {
		return pay_passwd;
	}

	public void setPay_passwd(String pay_passwd) {
		this.pay_passwd = pay_passwd;
	}

	public Boolean getIsEnableSecurity() {
		return isEnableSecurity;
	}

	public void setIsEnableSecurity(Boolean isEnableSecurity) {
		this.isEnableSecurity = isEnableSecurity;
	}
	
	public QuestionType getQuestion1() {
		return question1;
	}


	public void setQuestion1(QuestionType question1) {
		this.question1 = question1;
	}

	public String getAnswer1() {
		return answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	public QuestionType getQuestion2() {
		return question2;
	}

	public void setQuestion2(QuestionType question2) {
		this.question2 = question2;
	}

	public String getAnswer2() {
		return answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	public QuestionType getQuestion3() {
		return question3;
	}

	public void setQuestion3(QuestionType question3) {
		this.question3 = question3;
	}

	public String getAnswer3() {
		return answer3;
	}

	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}

	public Boolean getIsEnablepay_passwd() {
		return isEnablepay_passwd;
	}

	public void setIsEnablepay_passwd(Boolean isEnablepay_passwd) {
		this.isEnablepay_passwd = isEnablepay_passwd;
	}

	public Integer getPayPwdFailCount() {
		return payPwdFailCount;
	}

	public void setPayPwdFailCount(Integer payPwdFailCount) {
		this.payPwdFailCount = payPwdFailCount;
	}

	public Boolean getIsPayLocked() {
		return isPayLocked;
	}

	public void setIsPayLocked(Boolean isPayLocked) {
		this.isPayLocked = isPayLocked;
	}

	public Date getPayLockedDate() {
		return payLockedDate;
	}

	public void setPayLockedDate(Date payLockedDate) {
		this.payLockedDate = payLockedDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegisterIp() {
		return registerIp;
	}

	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public Long getCompany() {
		return company;
	}

	public void setCompany(Long company) {
		this.company = company;
	}

	public String getTencentId() {
		return tencentId;
	}

	public void setTencentId(String tencentId) {
		this.tencentId = tencentId;
	}

	public String getAlipayId() {
		return alipayId;
	}

	public void setAlipayId(String alipayId) {
		this.alipayId = alipayId;
	}

	public String getWxid() {
		return wxid;
	}

	public void setWxid(String wxid) {
		this.wxid = wxid;
	}

	@Enumerated(EnumType.STRING)
	public SystemID getSystemId() {
		return systemId;
	}

	public void setSystemId(SystemID systemId) {
		this.systemId = systemId;
	}
	
}
