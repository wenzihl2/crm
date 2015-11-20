package com.wzbuaa.crm.repository.sso;

import com.google.common.collect.Sets;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Set;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-2 下午3:31
 * <p>Version: 1.0
 */
public class AuthRepositoryImpl {

    @PersistenceContext
    private EntityManager em;


    public Set<Long> findRoleIds(Long userId, Set<Long> groupIds, Set<Long> deptIds, Set<Long> jobIds, Set<Long[]> deptJobIds) {

        boolean hasGroupIds = groupIds.size() > 0;
        boolean hasDeptIds = deptIds.size() > 0;
        boolean hasJobIds = jobIds.size() > 0;
        boolean hasDeptJobIds = deptJobIds.size() > 0;

        StringBuilder hql = new StringBuilder("select roleIds from Auth where ");
        hql.append(" (userId=:userId) ");

        if (hasGroupIds) {
            hql.append(" or ");
            hql.append(" (groupId in (:groupIds)) ");
        }

        if (hasDeptIds) {
            hql.append(" or ");
            hql.append(" (( deptId in (:deptIds) and jobId=0 )) ");
        }

        if (hasJobIds) {
            hql.append(" or ");
            hql.append(" (( deptId=0 and jobId in (:jobIds) )) ");
        }
        if (hasDeptJobIds) {
            int i = 0, l = deptJobIds.size();
            while (i < l) {
                hql.append(" or ");
                hql.append(" ( deptId=:deptId_" + i + " and jobId=:jobId_" + i + " ) ");
                i++;
            }
        }

        Query q = em.createQuery(hql.toString());

        q.setParameter("userId", userId);

        if (hasGroupIds) {
            q.setParameter("groupIds", groupIds);
        }

        if (hasDeptIds) {
            q.setParameter("deptIds", deptIds);
        }

        if (hasJobIds) {
            q.setParameter("jobIds", jobIds);
        }
        if (hasDeptJobIds) {
            int i = 0;
            for (Long[] deptJobId : deptJobIds) {
                q.setParameter("deptId_" + i, deptJobId[0]);
                q.setParameter("jobId_" + i, deptJobId[1]);
                i++;
            }
        }

        List<Set<Long>> roleIdSets = (List<Set<Long>>) q.getResultList();

        Set<Long> roleIds = Sets.newHashSet();
        for (Set<Long> set : roleIdSets) {
            roleIds.addAll(set);
        }

        return roleIds;
    }
}
