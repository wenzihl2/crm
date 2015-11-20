package com.wzbuaa.crm.domain.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;

import com.wzbuaa.crm.component.entity.Companyable;
import com.wzbuaa.crm.component.entity.Treeable;
import com.wzbuaa.crm.domain.BaseEntity;
import com.wzbuaa.crm.domain.DictionaryType;
import com.wzbuaa.crm.repository.support.annotation.EnableQueryCache;

/**
 * 实体类 - 数据字典
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "base_dictionary")
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DictionaryDomain extends BaseEntity<Long> implements Treeable<Long>, Companyable {
	
	private String name;/** 分类名 **/
	private Integer priority;
	private String parentIds;
    private Long parentId;
    /**
     * 图标
     */
    private String icon;
    /**
     * 是否有叶子节点
     */
    private boolean hasChildren;
	private DictionaryType type; /** 分类类别**/
	private String description;
    /**
     * 是否显示
     */
    private Boolean show = Boolean.FALSE;
	private String code;//编码
	private Long company;

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

	public Long getCompany() {
		return company;
	}

	public void setCompany(Long company) {
		this.company = company;
	}

	@Override
	public Integer getPriority() {
		return this.priority;
	}

	@Override
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Enumerated(EnumType.STRING)
	public DictionaryType getType() {
		return type;
	}

	public void setType(DictionaryType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "parent_ids")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	@Column(name = "parent_id")
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "is_show")
	public Boolean getShow() {
		return show;
	}

	public void setShow(Boolean show) {
		this.show = show;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	@Override
    @Transient
    public String getSeparator() {
        return "/";
    }

	@Override
    public String makeSelfAsNewParentIds() {
		if(getParentIds() == null) {
			this.setParentIds("0/");
		}
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
	
	@Formula(value = "(select count(*) from base_dictionary f_t where f_t.parent_id = id)")
    public boolean getHasChildren() {
        return hasChildren;
    }

	@Override
	@Transient
	public String getRootDefaultIcon() {
		return "ztree_root_open";
	}

	/**
     * 树枝节点默认图标 如果没有默认 空即可
     *
     * @return
     */
    @Override
    @Transient
    public String getBranchDefaultIcon() {
        return "ztree_branch";
    }

    /**
     * 树叶节点默认图标 如果没有默认 空即可
     *
     * @return
     */
    @Override
    @Transient
    public String getLeafDefaultIcon() {
        return "ztree_leaf";
    }
	
}