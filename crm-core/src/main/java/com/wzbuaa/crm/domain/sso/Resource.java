package com.wzbuaa.crm.domain.sso;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;

import com.wzbuaa.crm.component.entity.Treeable;
import com.wzbuaa.crm.domain.BaseEntity;
import com.wzbuaa.crm.repository.support.annotation.EnableQueryCache;

@SuppressWarnings("serial")
@Entity
@Table(name = "sso_resource")
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Resource extends BaseEntity<Long> implements Treeable<Long> {

	public enum ResourceType {
		Menu, Operate;
	}
	
	private String name;// 模块名称
	/**
	 * 资源标识符 用于权限匹配的 如sys:resource
	 */
	private String identity;
	/**
	 * 点击后前往的地址 菜单才有
	 */
	private String url;
	/**
	 * 图标
	 */
	private String icon;
	private String parentIds;
	private Long parentId = 0l;
	private Integer priority;
	private ResourceType type = ResourceType.Menu;
	private String html;
	/**
	 * 是否有叶子节点
	 */
	private boolean hasChildren;
	/**
	 * 是否显示
	 */
	private Boolean show = Boolean.FALSE;

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

	@Enumerated(EnumType.STRING)
	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Column(length=500)
	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	/**
     * 是否有叶子节点
     */
    @Formula(value = "(select count(*) from sso_resource f_t where f_t.parent_id = id)")
	public boolean isHasChildren() {
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

	@Override @Transient
	public String getSeparator() {
		return "/";
	}

	@Override @Transient
	public String makeSelfAsNewParentIds() {
		return getParentIds() + getId() + getSeparator();
	}

	@Override @Transient
	public boolean isRoot() {
		if (getParentId() != null && getParentId() == 0) {
            return true;
        }
        return false;
	}

	@Override @Transient
	public boolean isLeaf() {
		if (isRoot()) {
            return false;
        }
        if (isHasChildren()) {
            return false;
        }

        return true;
	}

	@Override @Transient
	public boolean getHasChildren() {
		return false;
	}

	/**
     * 根节点默认图标 如果没有默认 空即可
     *
     * @return
     */
	@Override @Transient
	public String getRootDefaultIcon() {
		return "ztree_setting";
	}

	/**
     * 树枝节点默认图标 如果没有默认 空即可
     *
     * @return
     */
    @Override @Transient
	public String getBranchDefaultIcon() {
		return "ztree_folder";
	}

    /**
     * 树叶节点默认图标 如果没有默认 空即可
     *
     * @return
     */
	@Override @Transient
	public String getLeafDefaultIcon() {
		return "ztree_file";
	}

}
