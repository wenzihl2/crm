package com.wzbuaa.crm.repository.base;

import com.wzbuaa.crm.domain.base.TemplateDomain;
import com.wzbuaa.crm.domain.base.TemplateDomain.TemplateType;
import com.wzbuaa.crm.repository.BaseRepository;

/**
 * 
 * <p>User: zhenglong
 * <p>Date: 2015年09月11日
 * <p>Version: 1.0
 */
public interface TemplateRepository extends BaseRepository<TemplateDomain, Long> {

	
	public TemplateDomain findByNameAndCompany(String name, Long company);
	
	public TemplateDomain findByTypeAndIdentityAndCompany(TemplateType type, String name, Long company);
	
	public TemplateDomain findByTypeAndIdentity(TemplateType type, String name);
}