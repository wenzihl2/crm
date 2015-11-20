package com.wzbuaa.crm.service.cms;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wzbuaa.crm.component.service.BaseTreeableService;
import com.wzbuaa.crm.domain.cms.NavigationDomain;
import com.wzbuaa.crm.domain.cms.NavigationDomain.NavigationPosition;
import com.wzbuaa.crm.repository.cms.NavigationRepository;


@Service
public class NavigationService extends BaseTreeableService<NavigationDomain, Long> {

	private NavigationRepository getNavigationRepository() {
        return (NavigationRepository) baseRepository;
    }
	
	public List<NavigationDomain> findByPosition(NavigationPosition position) {
		return getNavigationRepository().findByPosition(position);
	}
	
	public int findMaxPriority(NavigationPosition position, Long parentId) {
		return getNavigationRepository().findMaxPriority(position, parentId);
	}
	
}