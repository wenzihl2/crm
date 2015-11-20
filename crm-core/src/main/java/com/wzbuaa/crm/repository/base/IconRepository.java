package com.wzbuaa.crm.repository.base;

import com.wzbuaa.crm.domain.base.IconDomain;
import com.wzbuaa.crm.repository.BaseRepository;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:00
 * <p>Version: 1.0
 */
public interface IconRepository extends BaseRepository<IconDomain, Long> {
	
	IconDomain findByIdentity(String identity);
	
}
