package com.wzbuaa.crm.repository.sso;

import javax.persistence.Query;

import com.wzbuaa.crm.domain.sso.user.DeptDomain;
import com.wzbuaa.crm.domain.sso.user.JobDomain;
import com.wzbuaa.crm.repository.callback.DefaultSearchCallback;

import framework.entity.search.Searchable;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-5 下午6:17
 * <p>Version: 1.0
 */
public class UserSearchCallback extends DefaultSearchCallback {

    public UserSearchCallback() {
    }

    @Override
    public void prepareQL(StringBuilder ql, Searchable search) {
        super.prepareQL(ql, search);

        boolean hasDept = search.containsSearchKey("dept");
        boolean hasJob = search.containsSearchKey("job");
        if (hasDept || hasJob) {
            ql.append(" and exists(select 1 from UserDeptJob oj");
            if (hasDept) {
                ql.append(" left join oj.dept o ");

            }
            if (hasJob) {
                ql.append(" left join oj.job j ");

            }

            ql.append(" where oj.user=x ");
            if (hasDept) {
                ql.append(" and (o.id=:deptId or o.parentIds like :deptParentIds)");
            }
            if (hasJob) {
                ql.append(" and (j.id=:jobId or j.parentIds like :jobParentIds)");
            }

            ql.append(")");
        }

    }

    @Override
    public void setValues(Query query, Searchable search) {
        super.setValues(query, search);

        if (search.containsSearchKey("dept")) {
            DeptDomain dept = (DeptDomain) search.getValue("dept");
            query.setParameter("deptId", dept.getId());
            query.setParameter("deptParentIds", dept.makeSelfAsNewParentIds() + "%");
        }

        if (search.containsSearchKey("job")) {
            JobDomain job = (JobDomain) search.getValue("job");
            query.setParameter("jobId", job.getId());
            query.setParameter("jobParentIds", job.makeSelfAsNewParentIds() + "%");
        }
    }

}
