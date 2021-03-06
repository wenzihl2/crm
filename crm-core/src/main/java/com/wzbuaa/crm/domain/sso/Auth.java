package com.wzbuaa.crm.domain.sso;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.google.common.collect.Sets;
import com.wzbuaa.crm.domain.BaseEntity;
import com.wzbuaa.crm.repository.hibernate.type.CollectionToStringUserType;
import com.wzbuaa.crm.repository.support.annotation.EnableQueryCache;

/**
 * 组织机构 工作职位  用户  角色 关系表
 * 1、授权的五种情况
 * 只给组织机构授权 (orgnizationId=? and jobId=0)
 * 只给工作职务授权 (orgnizationId=0 and jobId=?)
 * 给组织机构和工作职务都授权 (orgnizationId=? and jobId=?)
 * 给用户授权  (userId=?)
 * 给组授权 (groupId=?)
 * <p/>
 * 因此查询用户有没有权限 就是
 * where (deptId=? and jobId=0) or (deptId = 0 and jobId=?) or (deptId=? and jobId=?) or (userId=?) or (groupId=?)
 * <p/>
 * <p/>
 * 2、为了提高性能
 * 放到一张表
 * 此处不做关系映射（这样需要配合缓存）
 * <p/>
 * 3、如果另一方是可选的（如只选组织机构 或 只选工作职务） 那么默认0 使用0的目的是为了也让走索引
 * <p/>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-4-24 下午2:14
 * <p>Version: 1.0
 */
@TypeDef(
        name = "SetToStringUserType",
        typeClass = CollectionToStringUserType.class,
        parameters = {
                @Parameter(name = "separator", value = ","),
                @Parameter(name = "collectionType", value = "java.util.HashSet"),
                @Parameter(name = "elementType", value = "java.lang.Long")
        }
)
@Entity
@Table(name = "sso_auth")
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Auth extends BaseEntity<Long> {

    /**
     * 组织机构
     */
    private Long deptId = 0L;

    /**
     * 工作职务
     */
    private Long jobId = 0L;

    /**
     * 用户
     */
    private Long userId = 0L;

    /**
     * 组
     */
    private Long groupId = 0L;
    private Set<Long> roleIds;
    private AuthType type;

    @Column(name = "dept_id")
	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	@Column(name = "job_id")
	public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Type(type = "SetToStringUserType")
    @Column(name = "role_ids")
    public Set<Long> getRoleIds() {
        if (roleIds == null) {
            roleIds = Sets.newHashSet();
        }
        return roleIds;
    }

    public void setRoleIds(Set<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public void addRoleId(Long roleId) {
        getRoleIds().add(roleId);
    }


    public void addRoleIds(Set<Long> roleIds) {
        getRoleIds().addAll(roleIds);
    }

    @Enumerated(EnumType.STRING)
    public AuthType getType() {
        return type;
    }

    public void setType(AuthType type) {
        this.type = type;
    }

    @Column(name = "group_id")
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

}
