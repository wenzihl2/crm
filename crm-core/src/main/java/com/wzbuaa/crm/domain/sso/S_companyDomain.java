package com.wzbuaa.crm.domain.sso;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.wzbuaa.crm.domain.BaseEntity;
import com.wzbuaa.crm.domain.base.DictionaryDomain;

/**
 * 企业
 * 
 * @author zhenglong
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "sso_company")
public class S_companyDomain extends BaseEntity<Long> {

	private String code; // 编码
	private String name; // 企业名称
	private String short_name; // 企业简称
	private DictionaryDomain industry; // 行业
	private DictionaryDomain area; // 省份
	private String address; // 地址
	private String telephone; // 电话
	private String fax; // 传真
	private Date reg_date; // 注册时间
	private Date expiry_date; // 失效时间
	private String config; // 企业配置

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShort_name() {
		return short_name;
	}

	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	public DictionaryDomain getIndustry() {
		return industry;
	}

	public void setIndustry(DictionaryDomain industry) {
		this.industry = industry;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	public DictionaryDomain getArea() {
		return area;
	}

	public void setArea(DictionaryDomain area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

	public Date getExpiry_date() {
		return expiry_date;
	}

	public void setExpiry_date(Date expiry_date) {
		this.expiry_date = expiry_date;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

}
