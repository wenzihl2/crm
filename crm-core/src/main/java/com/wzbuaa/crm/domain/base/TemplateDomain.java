package com.wzbuaa.crm.domain.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.wzbuaa.crm.domain.BaseEntity;

/**
 * 模板
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "base_template")
public class TemplateDomain extends BaseEntity<Long> {

	public enum TemplateType {
		email("邮件模板"), mobil("短信模板"), js("javascript模板"), print("打印模板");
		private final String info;

	    private TemplateType(String info) {
	        this.info = info;
	    }

	    public String getInfo() {
	        return info;
	    }
	    
	}
	
	private String name;// 模板名称
	private String identity; //模板标识符
	private String content;// 模板描述
	private TemplateType type;// 模板类型： 1.邮件模板 2.打印模板 3.页面模板 4.短信模板 5.JS模板
	private Long company; // 所属企业
	
	@Column(unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	@Column(length=1000)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	public TemplateType getType() {
		return type;
	}

	public void setType(TemplateType type) {
		this.type = type;
	}

	public Long getCompany() {
		return company;
	}

	public void setCompany(Long company) {
		this.company = company;
	}
}
