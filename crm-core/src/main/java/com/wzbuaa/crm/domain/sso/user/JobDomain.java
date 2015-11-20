package com.wzbuaa.crm.domain.sso.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;

import com.wzbuaa.crm.component.entity.Treeable;
import com.wzbuaa.crm.domain.BaseEntity;
import com.wzbuaa.crm.repository.support.annotation.EnableQueryCache;

/**
 * 工作职位
 * <p>User: zhenglong
 * <p>Date: 2015年5月24日
 * <p>Version: 1.0
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "sso_job")
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class JobDomain extends BaseEntity<Long> implements Treeable<Long>{

    /**
     * 标题
     */
    private String name;
    /**
     * 父路径
     */
    private Long parentId;

    private String parentIds;

    private Integer priority;

    /**
     * 图标
     */
    private String icon;

    /**
     * 是否有叶子节点
     */
    private boolean hasChildren;

    /**
     * 是否显示
     */
    private Boolean show = Boolean.FALSE;

    public JobDomain() {
    }

    public JobDomain(Long id) {
        setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public String makeSelfAsNewParentIds() {
        return getParentIds() + getId() + getSeparator();
    }

    @Override
    @Transient
    public String getSeparator() {
        return "/";
    }

	@Override
	public Integer getPriority() {
		return priority;
	}

	@Override
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

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

    public void setIcon(String icon) {
        this.icon = icon;
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

    @Formula(value = "(select count(*) from sso_job f_t where f_t.parent_id = id)")
    public boolean getHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    @Column(name = "is_show")
    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }


    /**
     * 根节点默认图标 如果没有默认 空即可
     *
     * @return
     */
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
