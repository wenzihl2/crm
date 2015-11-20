package com.wzbuaa.crm.domain.sso.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;

import com.wzbuaa.crm.component.entity.Companyable;
import com.wzbuaa.crm.component.entity.Treeable;
import com.wzbuaa.crm.domain.BaseEntity;
import com.wzbuaa.crm.repository.support.annotation.EnableQueryCache;

/**
 * 组织机构实体
 * @author zhenglong
 */
@SuppressWarnings("serial")
@Entity
@Table(name="sso_dept")
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeptDomain extends BaseEntity<Long> implements Treeable<Long>, Companyable {
	
	private String name; // 部门名称
	/**
     * 父路径
     */
    private Long parentId;
    /**
     * 图标
     */
    private String icon;
    private String parentIds;
	private String description;
	private Integer priority;
	private Long company; // 所属企业
	/**
     * 是否有叶子节点
     */
    private boolean hasChildren;
    /**
     * 是否显示
     */
    private Boolean show = Boolean.FALSE;
    
	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = false, length=30)
	public String getName() {
		return this.name;
	}

	@Column(name = "is_show")
    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }

	public Long getCompany() {
		return company;
	}

	public void setCompany(Long company) {
		this.company = company;
	}

	@Column(length=255)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getIcon() {
		if (!StringUtils.isEmpty(icon)) {
            return icon;
        }
        if (isRoot()) {
            return getRootDefaultIcon();
        }
        if (isLeaf()) {
            return getLeafDefaultIcon();
        }
        return getBranchDefaultIcon();
	}

	@Override
	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Column(name = "parent_id")
	public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Column(name = "parent_ids")
    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

	@Override
	@Transient
	public String getSeparator() {
		return "/";
	}

	@Override
	public String makeSelfAsNewParentIds() {
		return getParentIds() + getId() + getSeparator();
	}

	@Override
	@Transient
	public boolean isRoot() {
		if (getParentId() != null && getParentId() == 0) {
            return true;
        }
        return false;
	}

	@Override
	@Transient
	public boolean isLeaf() {
		if (isRoot()) {
            return false;
        }
        if (getHasChildren()) {
            return false;
        }

        return true;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}
	
	@Override
	@Formula(value = "(select count(*) from sso_dept f_t where f_t.parent_id = id)")
	public boolean getHasChildren() {
		return hasChildren;
	}

	@Override
	@Transient
	public String getRootDefaultIcon() {
		return "ztree_root_open";
	}

	@Override
	@Transient
	public String getBranchDefaultIcon() {
		return "ztree_branch";
	}

	@Override
	@Transient
	public String getLeafDefaultIcon() {
		return "ztree_leaf";
	}

}
