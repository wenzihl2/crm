package com.wzbuaa.crm.domain.sso.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.wzbuaa.crm.domain.BaseEntity;

/**
 * 分组与 用户/组织机构关系表
 * <p/>
 * 将用户/组织机构放一张表目的是提高查询性能
 * <p/>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-4-19 下午3:44
 * <p>Version: 1.0
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "sso_group_relation")
//@EnableQueryCache
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GroupRelationDomain extends BaseEntity<Long> {

    private Long groupId;

    private Long deptId;

    /**
     * 关联的单个用户
     */
    private Long userId;

    /**
     * 关联的 区间user id 作为单个关联的一种优化
     * 和user二者选一
     * [startUserId, endUserId]闭区间
     */
    private Long startUserId;
    private Long endUserId;

    @Column(name = "group_id")
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
    
    @Column(name = "dept_id")
    public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	@Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "start_user_id")
    public Long getStartUserId() {
        return startUserId;
    }

    public void setStartUserId(Long startUserId) {
        this.startUserId = startUserId;
    }

    @Column(name = "end_user_id")
    public Long getEndUserId() {
        return endUserId;
    }

    public void setEndUserId(Long endUserId) {
        this.endUserId = endUserId;
    }
}
