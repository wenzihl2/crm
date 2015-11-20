package com.wzbuaa.crm.service.sso;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;
import com.wzbuaa.crm.domain.sso.Role;
import com.wzbuaa.crm.repository.sso.RoleRepository;
import com.wzbuaa.crm.service.BaseService;

/**
 * Service实现类 - 角色
 */

@Service
public class RoleService extends BaseService<Role, Long> {
	
	private RoleRepository getRoleRepository() {
        return (RoleRepository) baseRepository;
    }
	
	public List<Role> findByIsSystem(boolean isSystem) {
		return getRoleRepository().findByIsSystem(isSystem);
	}
	
	/**
     * 获取可用的角色列表
     *
     * @param roleIds
     * @return
     */
    public Set<Role> findShowRoles(Set<Long> roleIds) {

        Set<Role> roles = Sets.newHashSet();

        //TODO 如果角色很多 此处应该写查询
        for (Role role : findAll()) {
            if (Boolean.TRUE.equals(role.getShow()) && roleIds.contains(role.getId())) {
                roles.add(role);
            }
        }
        return roles;
    }
}