package com.wzbuaa.crm.service.crm;

import static com.wzbuaa.crm.domain.Constants.ACCOUNT_LIMIT;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wzbuaa.crm.domain.DictionaryType;
import com.wzbuaa.crm.domain.base.DictionaryDomain;
import com.wzbuaa.crm.domain.crm.AccountDomain;
import com.wzbuaa.crm.domain.crm.MemberDomain;
import com.wzbuaa.crm.exception.ApplicationException;
import com.wzbuaa.crm.repository.crm.AccountRepository;
import com.wzbuaa.crm.service.BaseService;
import com.wzbuaa.crm.service.base.DictionaryService;
import com.wzbuaa.crm.util.ShopUtil;

/**
 * 会员银行卡账户
 * @author zhenglong
 *
 */
@Service
public class AccountService extends BaseService<AccountDomain, Long> {
	
	@Resource private MemberService memberService;
	@Resource private DictionaryService dictionaryService;
	
	private AccountRepository getAccountRepository() {
		 return (AccountRepository) baseRepository;
	}
	
	public AccountDomain findById(Long id) {
		Long memberId = ShopUtil.getUserId();
		MemberDomain member = memberService.findOne(memberId);
		return getAccountRepository().findByIdAndMember(id, member);
	}
	
	public List<AccountDomain> findList() {
		Long memberId = ShopUtil.getUserId();
		MemberDomain member = memberService.findOne(memberId);
		return getAccountRepository().findByMember(member);
	}

	public AccountDomain save(AccountDomain account) {
		Long memberId = ShopUtil.getUserId();
		MemberDomain member = memberService.findOne(memberId);
		if(member.getAccountSet() != null && member.getAccountSet().size() >= ACCOUNT_LIMIT){
			throw new ApplicationException("最多只能添加" + ACCOUNT_LIMIT + "张银行卡!");
		}
		DictionaryDomain dictionary = dictionaryService.findByTypeAndCodeShare(DictionaryType.bank, account.getCode());
		account.setAttribution(dictionary.getName());
		account.setMember(member);
		return super.save(account);
	}
}
