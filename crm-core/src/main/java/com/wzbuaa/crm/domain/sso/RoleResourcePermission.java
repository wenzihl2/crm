package com.wzbuaa.crm.domain.sso;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import com.wzbuaa.crm.domain.BaseEntity;
import com.wzbuaa.crm.repository.hibernate.type.CollectionToStringUserType;
import com.wzbuaa.crm.repository.support.annotation.EnableQueryCache;

/**
 * 此处没有使用关联 是为了提高性能（后续会挨着查询资源和权限列表，因为有缓存，数据量也不是很大 所以性能不会差）
 * <p>User: Zhang Kaitao
 * <p>Date: 13-4-5 下午2:04
 * <p>Version: 1.0
 */

@SuppressWarnings("serial")
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
@Table(name = "sso_role_resource_permission")
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RoleResourcePermission extends BaseEntity<Long> {

    /**
     * 角色id
     */
    private Role role;
    
    /**
     * 资源id
     */
    private Long resourceId;
    /**
     * 权限id列表
     * 数据库通过字符串存储 逗号分隔
     */
    private Set<Long> permissionIds;

    public RoleResourcePermission() {
    }

    public RoleResourcePermission(Long id) {
        setId(id);
    }
    
    public RoleResourcePermission(Long resourceId, Set<Long> permissionIds) {
        this.resourceId = resourceId;
        this.permissionIds = permissionIds;
    }

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Column(name = "resource_id")
    public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	@Column(name = "resource_ids")
    @Type(type = "SetToStringUserType")
    public Set<Long> getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(Set<Long> permissionIds) {
		this.permissionIds = permissionIds;
	}

	@Override
    public String toString() {
        return "RoleResourcePermission{id=" + this.getId() +
                ",roleId=" + (role != null ? role.getId() : "null") +
                ", permissionIds=" + permissionIds +
                '}';
    }
}
