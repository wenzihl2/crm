package com.wzbuaa.crm.repository.crm;

import java.util.List;

import com.wzbuaa.crm.domain.crm.AccountDomain;
import com.wzbuaa.crm.domain.crm.MemberDomain;
import com.wzbuaa.crm.repository.BaseRepository;

/**
 *	会员银行账户
 * <p>User: zhenglong
 * <p>Date: 2015年11月10日
 * <p>Version: 1.0
 */
public interface AccountRepository extends BaseRepository<AccountDomain, Long> {
	
	public AccountDomain findByIdAndMember(Long id, MemberDomain member);
	
	public List<AccountDomain> findByMember(MemberDomain member);
}
