package com.wzbuaa.crm.service.cms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wzbuaa.crm.domain.cms.ArticleDomain;
import com.wzbuaa.crm.repository.cms.ArticleRepository;
import com.wzbuaa.crm.service.BaseService;

import framework.util.Collections3;


@Service
public class ArticleService extends BaseService<ArticleDomain, Long> {

	private ArticleRepository getArticleRepository() {
        return (ArticleRepository) baseRepository;
    }
	
	@Transactional(readOnly=true)
	public List<ArticleDomain> findList(final Long[] ids, final Long categoryId, final Boolean isPublication, final Boolean isRecommend,
			final Integer count, final List<Order> orderList) {
	    Specification<ArticleDomain> spec = new Specification<ArticleDomain>() {
			@Override
			public Predicate toPredicate(Root<ArticleDomain> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				Predicate[] p = new Predicate[list.size()]; 
				// 广告ID
			    if(ids != null && ids.length > 0){ 
			    	list.add(root.get("id").as(Long.class).in(Arrays.asList(ids))); 
				}
			    
			    // 栏目ID
			    if(categoryId != null){ 
			    	list.add(cb.equal(root.get("category").get("id").as(Long.class), categoryId));
				}
			    
			    // 是否发布
			    if(isPublication != null){
			    	list.add(cb.equal(root.get("isPublication").as(Boolean.class), isPublication));
				}
			    
			    // 是否推荐
			    if(isPublication != null){
			    	list.add(cb.equal(root.get("isRecommend").as(Boolean.class), isRecommend));
				}
			    
				//list.add(cb.equal(root.get("company"), SysUtil.getUser().companyId));
				//list.add(cb.equal(root.get("deleted"), false)); 
			    return cb.and(list.toArray(p));
			}
	    };
	    if(ids != null && ids.length > 0){ 
	    	return getArticleRepository().findAll(spec);
	    } else {
	    	if(Collections3.isNotEmpty(orderList)) {
	    		return getArticleRepository().findAll(spec, count, new Sort(orderList));
	    	} else {
	    		return getArticleRepository().findAll(spec, count);
	    	}
	    }
	}
	
}