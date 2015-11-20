package com.wzbuaa.crm.domain.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.wzbuaa.crm.domain.BaseEntity;

/**
 * 实体类 - 日志配置
 */
@Entity
@Table(name="base_log_config")
public class S_logConfigDomain extends BaseEntity<Long> {

	private static final long serialVersionUID = -240939735880402675L;

	private String operationName;// 操作名称
	private String actClassName;// 需要进行日志记录的Action名称
	private String actMedName;// 需要进行日志记录的方法名称
	private String description;// 描述

	@Column(nullable = false, unique = true,name="operationName")
	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	@Column(length = 5000)
	public String getDescription() {
		return description;
	}


	@Column(nullable = false,name="actClassName")
	public String getActClassName() {
		return actClassName;
	}

	public void setActClassName(String actClassName) {
		this.actClassName = actClassName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(nullable = false,name="actMedName")
	public String getActMedName() {
		return actMedName;
	}

	public void setActMedName(String actMedName) {
		this.actMedName = actMedName;
	}
}