package com.wzbuaa.crm.domain.sso;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.wzbuaa.crm.component.entity.Companyable;
import com.wzbuaa.crm.domain.BaseEntity;
import com.wzbuaa.crm.repository.support.annotation.EnableQueryCache;

/**
 * 实体类 - 角色
 */
@SuppressWarnings("serial")
@Entity
@Table(name="sso_role")
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role extends BaseEntity<Long> implements Companyable {

	private String name;// 角色名称
	private String value;// 角色标识
	private String description;// 描述
	private Long company; // 所属企业
	private Boolean show = Boolean.FALSE;
	private Boolean isSystem = Boolean.FALSE;
	/**
     * 用户 组织机构 工作职务关联表
     */
    private List<RoleResourcePermission> resourcePermissions = new ArrayList<RoleResourcePermission>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCompany() {
		return company;
	}

	public void setCompany(Long company) {
		this.company = company;
	}

	@Column(name = "is_show")
	public Boolean getShow() {
		return show;
	}

	public void setShow(Boolean show) {
		this.show = show;
	}

	@Column(name = "is_system")
	public Boolean getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(Boolean isSystem) {
		this.isSystem = isSystem;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = RoleResourcePermission.class, mappedBy = "role", orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    @Basic(optional = true, fetch = FetchType.EAGER)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)//集合缓存
    @OrderBy
	public List<RoleResourcePermission> getResourcePermissions() {
		return resourcePermissions;
	}

	public void setResourcePermissions(
			List<RoleResourcePermission> resourcePermissions) {
		this.resourcePermissions = resourcePermissions;
	}

	@Transient
	public void addResourcePermission(RoleResourcePermission roleResourcePermission) {
        roleResourcePermission.setRole(this);
        getResourcePermissions().add(roleResourcePermission);
    }

}