package com.wzbuaa.crm.domain.base;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.wzbuaa.crm.domain.BaseEntity;
import com.wzbuaa.crm.repository.support.annotation.EnableQueryCache;

import framework.util.Collections3;

/**
 * 查询表单配置
 * <p>User: zhenglong
 * <p>Date: 2015年6月11日
 * <p>Version: 1.0
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "base_maintain_table")
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TableDomain extends BaseEntity<Long> {

	private String name; // 表名称
	private String classPath; // 类路径
	private String keyword;//关键词
	private List<TableFieldDomain> fields;
	private Long companyId;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassPath() {
		return classPath;
	}

	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}

	@OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy="table", orphanRemoval=true)
	@OrderBy("priority asc")
	@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)//集合缓存
	public List<TableFieldDomain> getFields() {
		return fields;
	}

	public void setFields(List<TableFieldDomain> fields) {
		this.fields = fields;
	}
	
	@Transient
	public void addFields(List<TableFieldDomain> fields) {
		if(this.fields == null) {
			this.fields = new ArrayList<TableFieldDomain>();
		}
		if (Collections3.isNotEmpty(fields)) {
			this.fields.clear();
			for(TableFieldDomain field : fields) {
				field.setTable(this);
			}
			this.fields.addAll(fields);
		}
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
}
