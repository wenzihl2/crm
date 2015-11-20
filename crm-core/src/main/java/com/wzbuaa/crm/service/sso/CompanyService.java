package com.wzbuaa.crm.service.sso;

import org.springframework.stereotype.Service;

import com.wzbuaa.crm.domain.sso.S_companyDomain;
import com.wzbuaa.crm.repository.sso.CompanyRepository;
import com.wzbuaa.crm.service.BaseService;

/**
 * Service实现类 - 企业
 */
@Service
public class CompanyService extends BaseService<S_companyDomain, Long> {

	 private CompanyRepository getCompanyRepository() {
		 return (CompanyRepository) baseRepository;
	 }
	 
}