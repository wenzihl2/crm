package com.wzbuaa.crm.domain.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.wzbuaa.crm.domain.AbstractEntity;
import com.wzbuaa.crm.repository.hibernate.type.JsonMap;
import com.wzbuaa.crm.repository.hibernate.type.JsonMapUserType;
import com.wzbuaa.crm.repository.support.annotation.EnableQueryCache;

/**
 * 插件
 */
@SuppressWarnings("serial")
@Entity
@TypeDef(
        name = "PluginConfig",
        typeClass = JsonMapUserType.class
)
@Table(name = "base_plugin")
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PluginDomain extends AbstractEntity<String> {
	
	public enum PluginType {
		upload, payment;
	}
	
	/**
     * 用户会话id ===> uid
     */
    private String id;
	private String name;//名称
	private String version;//插件版本
	private String author;//作者
	private String description;//备注
	private JsonMap<String, Object> configparam; // 配置
	private PluginType type;
	private Boolean isEnabled = Boolean.FALSE;//安装状态 (1: 安装， 2: 没安装)
	
	@Id
    @GeneratedValue(generator = "assigned")
    @GenericGenerator(name = "assigned", strategy = "assigned")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getAuthor() {
		return author;
	}

	@Enumerated(EnumType.STRING)
	public PluginType getType() {
		return type;
	}

	public void setType(PluginType type) {
		this.type = type;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Type(type = "PluginConfig")
	public JsonMap<String, Object> getConfigparam() {
		return configparam;
	}

	public void setConfigparam(JsonMap<String, Object> configparam) {
		this.configparam = configparam;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}


}
