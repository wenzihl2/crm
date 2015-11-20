package com.wzbuaa.crm.repository.sso;

import com.wzbuaa.crm.domain.sso.Resource;
import com.wzbuaa.crm.repository.BaseRepository;

public interface ResourceRepository extends BaseRepository<Resource, Long> {
	
	
	public Resource findByUrl(String url);
	
}
