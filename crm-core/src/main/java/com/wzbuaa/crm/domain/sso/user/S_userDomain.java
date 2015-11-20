package com.wzbuaa.crm.domain.sso.user;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wzbuaa.crm.component.entity.Companyable;
import com.wzbuaa.crm.domain.BaseEntity;
import com.wzbuaa.crm.repository.support.annotation.EnableQueryCache;

/**
 * 用户实体类  @author zhenglong
 */
@SuppressWarnings("serial")
@Entity
@Table(name="sso_user")
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class S_userDomain extends BaseEntity<Long> implements Companyable {

	public static final String USERNAME_PATTERN = "^[\\u4E00-\\u9FA5\\uf900-\\ufa2d_a-zA-Z][\\u4E00-\\u9FA5\\uf900-\\ufa2d\\w]{1,19}$";
    public static final String EMAIL_PATTERN = "^((([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+(\\.([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+)*)|((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f]|\\x21|[\\x23-\\x5b]|[\\x5d-\\x7e]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(\\\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(\\x22)))@((([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.?";
    public static final String MOBILE_PHONE_NUMBER_PATTERN = "^0{0,1}(13[0-9]|15[0-9]|14[0-9]|18[0-9])[0-9]{8}$";
    public static final int USERNAME_MIN_LENGTH = 2;
    public static final int USERNAME_MAX_LENGTH = 20;
    public static final int PASSWORD_MIN_LENGTH = 5;
    public static final int PASSWORD_MAX_LENGTH = 50;
    
    private String name; // 职员名称
	private String username;//登陆账号
	private String password;//密码
	private String salt; //加密因子
	private String email; // 邮箱
	private String mobilePhoneNumber;
	private UserStatus status = UserStatus.normal; // 用户的使用状态
	private String loginid; //上次登录的sessionId
	private Integer loginCount; //登录的次数
	private Date lastLoginTime; //上次登录时间
	private String lastLoginIp; //上次登录的IP
	private Date lockedDate;// 账号锁定日期
	private Integer loginFailureCount = 0;//登录失败次数
	private Boolean isAccountLocked = false;// 账号是否锁定
	private Boolean isAccountExpired = false;// 账号是否过期
	private Boolean isCredentialsExpired = false;// 凭证是否过期
	//是否企业管理员Y: 是 N:不是。系统管理员新增的用户默认是企业管理员
	@JsonIgnore private Boolean isManager = false;
	/**
     * 是否是管理员
     */
    private Boolean admin = false;
	private Long company; // 所属企业
    private List<UserDeptJob> deptJobs;

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	/**
     * 生成新的种子
     */
    public void randomSalt() {
        setSalt(RandomStringUtils.randomAlphanumeric(10));
    }

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	@Enumerated(EnumType.STRING)
	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public String getSalt() {
		return salt;
	}


	public void setSalt(String salt) {
		this.salt = salt;
	}

	@NotEmpty(message = "{not.null}")
    @Pattern(regexp = EMAIL_PATTERN, message = "{user.email.not.valid}")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(updatable = false, nullable = false, unique = true)
	@NotNull(message = "{not.null}")
    @Pattern(regexp = USERNAME_PATTERN, message = "{user.username.not.valid}")
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@NotEmpty(message = "{not.null}")
    @Pattern(regexp = MOBILE_PHONE_NUMBER_PATTERN, message = "{user.mobile.phone.number.not.valid}")
    @Column(name = "mobile_phone_number")
	public String getMobilePhoneNumber() {
		return mobilePhoneNumber;
	}

	public void setMobilePhoneNumber(String mobilePhoneNumber) {
		this.mobilePhoneNumber = mobilePhoneNumber;
	}

	@Column(nullable = false,name="isAccountExpired")
	public Boolean getIsAccountExpired() {
		return isAccountExpired;
	}

	public void setIsAccountExpired(Boolean isAccountExpired) {
		this.isAccountExpired = isAccountExpired;
	}

	@Column(length=32)
	@Length(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH, message = "{user.password.not.valid}")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(nullable = false, length=1)
	public Boolean getIsManager() {
		return isManager;
	}

	public void setIsManager(Boolean isManager) {
		this.isManager = isManager;
	}

	@Column(nullable = false, length=1)
	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	@Column(name="loginCount")
	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	@Column(name="lastLoginTime")
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	/**
     * 用户 组织机构 工作职务关联表
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = UserDeptJob.class, mappedBy = "user", orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    @Basic(optional = true, fetch = FetchType.EAGER)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    //集合缓存引起的
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)//集合缓存
    @OrderBy()
	public List<UserDeptJob> getDeptJobs() {
		return deptJobs;
	}

	public void setDeptJobs(List<UserDeptJob> deptJobs) {
		this.deptJobs = deptJobs;
	}

	public Long getCompany() {
		return company;
	}

	public void setCompany(Long company) {
		this.company = company;
	}

	@Column(name="lastLoginIp")
	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	@Column(nullable = false,name="loginFailureCount")
	public Integer getLoginFailureCount() {
		return loginFailureCount;
	}

	public void setLoginFailureCount(Integer loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}

	@Column(name="lockedDate")
	public Date getLockedDate() {
		return lockedDate;
	}

	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}
	
	@Column(nullable = false,name="isCredentialsExpired")
	public Boolean getIsCredentialsExpired() {
		return isCredentialsExpired;
	}

	public void setIsCredentialsExpired(Boolean isCredentialsExpired) {
		this.isCredentialsExpired = isCredentialsExpired;
	}

	@Transient
	public boolean isAccountNonLocked() {
		return !this.isAccountLocked;
	}
	
	@Column(nullable = false,name="isAccountLocked")
	public Boolean getIsAccountLocked() {
		return isAccountLocked;
	}

	public void setIsAccountLocked(Boolean isAccountLocked) {
		this.isAccountLocked = isAccountLocked;
	}

	@Transient
	public boolean isAccountNonExpired() {
		return !this.isAccountLocked;
	}

	@Transient
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return !this.isCredentialsExpired;
	}
}
