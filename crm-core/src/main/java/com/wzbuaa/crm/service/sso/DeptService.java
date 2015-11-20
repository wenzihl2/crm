package com.wzbuaa.crm.service.sso;

import java.util.Iterator;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.wzbuaa.crm.component.service.BaseTreeableService;
import com.wzbuaa.crm.domain.sso.user.DeptDomain;
/**
 * Service接口 - 部门管理
 */
@Service
public class DeptService extends BaseTreeableService<DeptDomain, Long> {

	/**
     * 过滤仅获取可显示的数据
     *
     * @param organizationIds
     * @param organizationJobIds
     */
    public void filterForCanShow(Set<Long> deptIds, Set<Long[]> organizationJobIds) {

        Iterator<Long> iter1 = deptIds.iterator();

        while (iter1.hasNext()) {
            Long id = iter1.next();
            DeptDomain o = findOne(id);
            if (o == null || Boolean.FALSE.equals(o.getShow())) {
                iter1.remove();
            }
        }

        Iterator<Long[]> iter2 = organizationJobIds.iterator();

        while (iter2.hasNext()) {
            Long id = iter2.next()[0];
            DeptDomain o = findOne(id);
            if (o == null || Boolean.FALSE.equals(o.getShow())) {
                iter2.remove();
            }
        }

    }
}