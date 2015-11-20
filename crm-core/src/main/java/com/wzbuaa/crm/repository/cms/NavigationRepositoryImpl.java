package com.wzbuaa.crm.repository.cms;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.wzbuaa.crm.domain.cms.NavigationDomain.NavigationPosition;

/**
 * <p>User: zhenglong
 * <p>Date: 2015年6月10日
 * <p>Version: 1.0
 */
public class NavigationRepositoryImpl {
	
	@PersistenceContext
    private EntityManager em;
	
	public int findMaxPriority(NavigationPosition position, Long parentId) {
		StringBuffer hql = new StringBuffer("select max(bean.priority) from NavigationDomain bean where bean.position=:position");
		if(parentId != null) {
			hql.append(" and bean.parentId=:parentId");
		}
		TypedQuery<Integer> q = em.createQuery(hql.toString(), Integer.class);
		q.setParameter("position", position);
		if(parentId != null) {
			q.setParameter("parentId", parentId);
		}
		Integer max = q.getSingleResult();
		return max == null ? 0 : max;
	}
}
