package com.wzbuaa.crm.domain.cms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Formula;

import com.wzbuaa.crm.component.entity.Stateable;
import com.wzbuaa.crm.component.entity.Stateable.ShowStatus;
import com.wzbuaa.crm.component.entity.Treeable;
import com.wzbuaa.crm.domain.BaseEntity;

/**
 * 实体类 - 导航
 * auther zhenglong 2015-11-09
 */

@SuppressWarnings("serial")
@Entity
@Table(name="cms_navigation")
public class NavigationDomain extends BaseEntity<Long> implements Treeable<Long>, Stateable<ShowStatus> {


	// 导航位置:顶部、中间、底部
	public enum NavigationPosition {
		left, top, middle, bottom
	}

	private String name;// 名称
	/**
     * 父路径
     */
    private Long parentId;
    /**
     * 图标
     */
    private String icon;
    private String parentIds;
	private NavigationPosition position;// 位置
	private String url;// 链接地址;
	private Boolean isBlankTarget = Boolean.FALSE;// 是否在新窗口中打开
	private Boolean isHot = Boolean.FALSE;//是否热门
	private Integer priority;// 排序
	private ShowStatus status = ShowStatus.hide;// 是否显示
	/**
     * 是否有叶子节点
     */
    private boolean hasChildren;
    
    @Column(nullable = false, length=30)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	public NavigationPosition getPosition() {
		return position;
	}

	public void setPosition(NavigationPosition position) {
		this.position = position;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getIsBlankTarget() {
		return isBlankTarget;
	}

	public void setIsBlankTarget(Boolean isBlankTarget) {
		this.isBlankTarget = isBlankTarget;
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

	@Column(name="is_hot")
	public Boolean getIsHot() {
		return isHot;
	}

	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}

	@Enumerated(EnumType.STRING)
	public ShowStatus getStatus() {
		return status;
	}

	public void setStatus(ShowStatus status) {
		this.status = status;
	}

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