package com.wzbuaa.crm.repository.base;

import org.springframework.data.jpa.repository.Query;

import com.wzbuaa.crm.domain.base.PluginDomain;
import com.wzbuaa.crm.repository.BaseRepository;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:00
 * <p>Version: 1.0
 */
public interface PluginRepository extends BaseRepository<PluginDomain, String> {
	
	@Query("from PluginDomain where id=?1 and isEnabled=true")
	public PluginDomain findEnableComponent(String componentId);
}
