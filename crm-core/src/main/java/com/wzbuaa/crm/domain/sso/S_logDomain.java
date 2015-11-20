package com.wzbuaa.crm.domain.sso;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.wzbuaa.crm.component.entity.Companyable;
import com.wzbuaa.crm.domain.BaseEntity;
/**
 * 实体类 - 日志
 */

@Entity
@Table(name="sso_log")
public class S_logDomain extends BaseEntity<Long> implements Companyable  {

	private static final long serialVersionUID = -4494144902110236826L;

	private String operationName;// 操作名称
	private String operator;// 操作员
	private String actionClassName;// 操作Action名称
	private String actionMethodName;// 操作方法名称
	private String ip;// IP
	private String info;// 日志信息
	private Long company;
	
	@Column(nullable = false, name="operationName")
	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	@Column(nullable = false)
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public Long getCompany() {
		return company;
	}

	public void setCompany(Long company) {
		this.company = company;
	}

	@Column(nullable = false,  name="actionClassName")
	public String getActionClassName() {
		return actionClassName;
	}

	public void setActionClassName(String actionClassName) {
		this.actionClassName = actionClassName;
	}

	@Column(nullable = false,name="actionMethodName")
	public String getActionMethodName() {
		return actionMethodName;
	}

	public void setActionMethodName(String actionMethodName) {
		this.actionMethodName = actionMethodName;
	}

	@Column(nullable = false)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(length = 5000)
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}