package com.wzbuaa.crm.domain.base;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.wzbuaa.crm.domain.BaseEntity;
import com.wzbuaa.crm.domain.trade.PaymentLogDomain;
import com.wzbuaa.crm.repository.hibernate.type.JsonMap;
import com.wzbuaa.crm.repository.hibernate.type.JsonMapUserType;

/**
 * 支付方式
 */
@SuppressWarnings("serial")
@TypeDef(
        name = "paymentConfig",
        typeClass = JsonMapUserType.class
)
@Entity
@Table(name = "base_payment_type")
public class PaymentTypeDomain extends BaseEntity<Long> {

	private String name;// 支付方式名称
	protected String type; // 支付方式
	private PluginDomain plugin;// 关联的支付组件
	private JsonMap<String, Object> config;
	private String description;// 介绍
	private Set<PaymentLogDomain> log;//支付日志

	@Column(unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "plugin_id")
	public PluginDomain getPlugin() {
		return plugin;
	}

	public void setPlugin(PluginDomain plugin) {
		this.plugin = plugin;
	}

	@Type(type = "paymentConfig")
	public JsonMap<String, Object> getConfig() {
		return config;
	}

	public void setConfig(JsonMap<String, Object> config) {
		this.config = config;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "payType")
	public Set<PaymentLogDomain> getLog() {
		return log;
	}

	public void setLog(Set<PaymentLogDomain> log) {
		this.log = log;
	}

}
