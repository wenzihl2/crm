package com.wzbuaa.crm.repository.base;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.wzbuaa.crm.domain.DictionaryType;

/**
 * <p>User: zhenglong
 * <p>Date: 2015年6月10日
 * <p>Version: 1.0
 */
public class DictionaryRepositoryImpl {
	
	@PersistenceContext
    private EntityManager em;
	
	public int findMaxPriority(DictionaryType type, Long parentId) {
		StringBuffer hql = new StringBuffer("select max(bean.priority) from DictionaryDomain bean where bean.type=:type");
		if(parentId != null) {
			hql.append(" and bean.parent.id=:parentId");
		}
		TypedQuery<Integer> q = em.createQuery(hql.toString(), Integer.class);
		q.setParameter("type", type);
		if(parentId != null) {
			q.setParameter("parentId", parentId);
		}
		Integer max = q.getSingleResult();
		return max == null ? 0 : max;
	}
}
