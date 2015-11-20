package com.wzbuaa.crm.repository.crm;

import com.wzbuaa.crm.domain.crm.MemberDomain;
import com.wzbuaa.crm.repository.BaseRepository;

/**
 *	积分规则
 * <p>User: zhenglong
 * <p>Date: 2015年5月25日
 * <p>Version: 1.0
 */
public interface MemberRepository extends BaseRepository<MemberDomain, Long> {
	
	public MemberDomain findByUsername(String username);
	
	public MemberDomain findByEmail(String email);
	
	public MemberDomain findByMobile(String mobile);
	
}
