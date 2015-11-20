package com.wzbuaa.crm.domain.sso.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.wzbuaa.crm.domain.BaseEntity;
import com.wzbuaa.crm.repository.support.annotation.EnableQueryCache;

/**
 * 分组超类
 * <p>User: Zhang Kaitao
 * <p>Date: 13-4-19 上午7:13
 * <p>Version: 1.0
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "sso_group")
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GroupDomain extends BaseEntity<Long> {
    /**
     * 分组名称
     */
    private String name;

    /**
     * 分组类型 如 用户分组/组织机构分组
     */
    private GroupType type;

    /**
     * 是否是默认分组
     */
    private Boolean defaultGroup = Boolean.FALSE;

    /**
     * 是否显示/可用
     */
    private Boolean show = Boolean.FALSE;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Enumerated(EnumType.STRING)
    public GroupType getType() {
        return type;
    }

    public void setType(GroupType type) {
        this.type = type;
    }

    @Column(name = "default_group")
    public Boolean getDefaultGroup() {
        return defaultGroup;
    }

    public void setDefaultGroup(Boolean defaultGroup) {
        this.defaultGroup = defaultGroup;
    }
    
    @Column(name = "is_show")
    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }

}
