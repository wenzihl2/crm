package com.wzbuaa.crm.service.cms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wzbuaa.crm.component.service.BaseMovableService;
import com.wzbuaa.crm.domain.cms.AdvertDomain;
import com.wzbuaa.crm.repository.cms.AdvertRepository;

import framework.util.Collections3;


@Service
public class AdvertService extends BaseMovableService<AdvertDomain, Long> {

	private AdvertRepository getAdvertRepository() {
        return (AdvertRepository) baseRepository;
    }
	
	@Transactional(readOnly=true)
	public List<AdvertDomain> findList(final Long[] ids, final Long positionId, final String position_code,
			final Integer count, final List<Order> orderList) {
	    Specification<AdvertDomain> spec = new Specification<AdvertDomain>() {
			@Override
			public Predicate toPredicate(Root<AdvertDomain> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				Predicate[] p = new Predicate[list.size()]; 
				// 广告ID
			    if(ids != null && ids.length > 0){ 
			    	list.add(root.get("id").as(Long.class).in(Arrays.asList(ids))); 
				}
			    
			    // 广告位ID
			    if(positionId != null){ 
			    	list.add(cb.equal(root.get("position").get("id").as(Long.class), positionId));
				}
			    
			    // 广告位编码
			    if(StringUtils.isNotEmpty(position_code)){
			    	list.add(cb.equal(root.get("position").get("code").as(String.class), position_code));
				}
			    
				//list.add(cb.equal(root.get("company"), SysUtil.getUser().companyId));
				//list.add(cb.equal(root.get("deleted"), false)); 
			    return cb.and(list.toArray(p));
			}
	    };
	    if(ids != null && ids.length > 0){ 
	    	return getAdvertRepository().findAll(spec);
	    } else {
	    	if(Collections3.isNotEmpty(orderList)) {
	    		return getAdvertRepository().findAll(spec, count, new Sort(orderList));
	    	} else {
	    		return getAdvertRepository().findAll(spec, count);
	    	}
	    }
	}
	
}