package com.wzbuaa.crm.domain.base;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.wzbuaa.crm.domain.BaseEntity;
import com.wzbuaa.crm.domain.SnCreateType;

@SuppressWarnings("serial")
@Entity
@Table(name = "base_sncreater")
public class SnCreaterDomain extends BaseEntity<Long> {

	public enum UpdateCycle {
		No, EveryDay, EveryMonth, EveryYear;
	}

	private String name; // 名称
	private SnCreateType code; // 编码
	private String qz; // 前缀
	private Boolean qyrq; // 启用日期
	private String rqgs; // 日期格式
	private Long dqxh; // 当前序号
	private UpdateCycle gxzq; // 更新周期
	private Integer ws; // 序号位数
	private Long company;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Enumerated(EnumType.STRING)
	public SnCreateType getCode() {
		return code;
	}

	public void setCode(SnCreateType code) {
		this.code = code;
	}

	public String getQz() {
		return qz;
	}

	public void setQz(String qz) {
		this.qz = qz;
	}

	public Boolean getQyrq() {
		return qyrq;
	}

	public void setQyrq(Boolean qyrq) {
		this.qyrq = qyrq;
	}

	public String getRqgs() {
		return rqgs;
	}

	public void setRqgs(String rqgs) {
		this.rqgs = rqgs;
	}

	public Long getDqxh() {
		return dqxh;
	}

	public void setDqxh(Long dqxh) {
		this.dqxh = dqxh;
	}

	@Enumerated(EnumType.STRING)
	public UpdateCycle getGxzq() {
		return gxzq;
	}

	public void setGxzq(UpdateCycle gxzq) {
		this.gxzq = gxzq;
	}

	public Integer getWs() {
		return ws;
	}

	public void setWs(Integer ws) {
		this.ws = ws;
	}

	public Long getCompany() {
		return company;
	}

	public void setCompany(Long company) {
		this.company = company;
	}

}