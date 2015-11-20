package com.wzbuaa.crm.repository.cms;

import java.util.List;
import com.wzbuaa.crm.domain.cms.NavigationDomain;
import com.wzbuaa.crm.domain.cms.NavigationDomain.NavigationPosition;
import com.wzbuaa.crm.repository.BaseRepository;

/**
 * 导航管理
 * <p>User: zhenglong
 * <p>Date: 2015年11月09日
 * <p>Version: 1.0
 */
public interface NavigationRepository extends BaseRepository<NavigationDomain, Long> {
	
	public int findMaxPriority(NavigationPosition position, Long parentId);
	
	public List<NavigationDomain> findByPosition(NavigationPosition position);
	
}
