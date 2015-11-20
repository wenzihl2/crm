package com.wzbuaa.crm.service.crm;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.wzbuaa.crm.domain.crm.CreditsSetDomain;
import com.wzbuaa.crm.repository.crm.CreditsSetRepository;
import com.wzbuaa.crm.service.BaseService;

/**
 * 积分赠送规则
 * @author fangchen
 *
 */
@Service
public class CreditsSetService extends BaseService<CreditsSetDomain, Long> {
	
	@Resource private MemberService memberService;
	
	private CreditsSetRepository getCreditsSetRepository() {
		 return (CreditsSetRepository) baseRepository;
	}

	public void realteInviterRule(Long[] ids, Long id) {
	}

	public void realteRule(Long[] ids, Long id) {
	}

}
