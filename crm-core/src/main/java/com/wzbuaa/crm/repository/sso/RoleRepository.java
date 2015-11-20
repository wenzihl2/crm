package com.wzbuaa.crm.repository.sso;

import java.util.List;

import com.wzbuaa.crm.domain.sso.Role;
import com.wzbuaa.crm.repository.BaseRepository;

/**
 * <p>User: zhenglong
 * <p>Date: 2015年5月25日
 * <p>Version: 1.0
 */
public interface RoleRepository extends BaseRepository<Role, Long> {
	
	public List<Role> findByIsSystem(boolean isSystem);
	
}
