package com.wzbuaa.crm.bean;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MemberBeanComplex {

	private Long   memberId; // 网店用户信息在crm上的ID
	private String idcard; // 身份证
	private String phone; // 手机号码
	private String mobile; // 电话号码
	private String passwd;
	private byte[] upload;
	
	private String name; // 姓名
	private String edu; // 学历
	private Date birth; // 生日
	private String email; // 邮件
	private Integer gender; // 性别

	private Long areaid;
	private String address; // 地址
	private String zip_code; // 邮编
	private SystemID systemId;  //系统id
 	private BigDecimal point; //积分
	
	public MemberBeanComplex() {
		super();
	}

	public MemberBeanComplex(Long memberId, String idcard,
			String phone, String mobile, String passwd, byte[] upload,
			String name, String edu, Date birth, String email, Integer gender,
			Long areaid, String address, String zip_code, SystemID systemId,
			BigDecimal point) {
		super();
		this.memberId = memberId;
		this.idcard = idcard;
		this.phone = phone;
		this.mobile = mobile;
		this.passwd = passwd;
		this.upload = upload;
		this.name = name;
		this.edu = edu;
		this.birth = birth;
		this.email = email;
		this.gender = gender;
		this.areaid = areaid;
		this.address = address;
		this.zip_code = zip_code;
		this.systemId = systemId;
		this.point = point;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}


	public byte[] getUpload() {
		return upload;
	}

	public void setUpload(byte[] upload) {
		this.upload = upload;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEdu() {
		return edu;
	}

	public void setEdu(String edu) {
		this.edu = edu;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Long getAreaid() {
		return areaid;
	}

	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZip_code() {
		return zip_code;
	}

	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}

	public SystemID getSystemId() {
		return systemId;
	}

	public void setSystemId(SystemID systemId) {
		this.systemId = systemId;
	}

	public BigDecimal getPoint() {
		return point;
	}

	public void setPoint(BigDecimal point) {
		this.point = point;
	}

}
