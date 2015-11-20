package com.wzbuaa.crm.service.crm;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.wzbuaa.crm.domain.crm.CreditsDetailDomain;
import com.wzbuaa.crm.domain.crm.MemberDomain;
import com.wzbuaa.crm.service.BaseService;

/**
 * 积分明细
 * @author zhenglong
 */
@Service
public class CreditsDetailService extends BaseService<CreditsDetailDomain, Long> {
	
	@Resource private MemberService memberService;
	@PersistenceContext EntityManager entityManager;
	
	public void modifyCredits(MemberDomain member, CreditsDetailDomain detail) {
		entityManager.lock(member, LockModeType.PESSIMISTIC_WRITE);
		Integer consumePoint = member.getConsumePoint();
		if(consumePoint == null){
			consumePoint = detail.getAmount();
		}else{
			consumePoint += detail.getAmount();
		}
		member.setConsumePoint(consumePoint);
		memberService.update(member);
		detail.setMember(member);
		detail.setRemaind(consumePoint);
		save(detail);
	}

//	@Override
//	public PagerBean getCreditsDetail(PagerBean pager, Long memberId) {
//		MemberDomain member = memberService.findOne(memberId);
//		if(member == null){
//			throw createWebApplicationException("会员不存在");
//		}
//		String hql = "select new com.crm.bean.CreditsDetailBean(c.createDate,c.remark,c.amount,c.remaind) from CreditsDetailDomain c where c.member.id=:memberId order by c.createDate desc";
//		
//		finder.setParam("memberId", memberId);
//		pager = findByPager(pager, finder, "c");
//		if(member.getCredits() == null){
//			pager.setSummary(BigDecimal.ZERO);
//		}else{
//			pager.setSummary(new BigDecimal(member.getCredits()));
//		}
//		return pager;
//	}

}
