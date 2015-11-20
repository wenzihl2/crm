package com.wzbuaa.crm.service.sso;

import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;
import com.wzbuaa.crm.domain.sso.user.GroupRelationDomain;
import com.wzbuaa.crm.repository.sso.GroupRelationRepository;
import com.wzbuaa.crm.service.BaseService;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:01
 * <p>Version: 1.0
 */
@Service
public class GroupRelationService extends BaseService<GroupRelationDomain, Long> {

    private GroupRelationRepository getGroupRelationRepository() {
        return (GroupRelationRepository) baseRepository;
    }


    public void appendRelation(Long groupId, Long[] deptIds) {
        if (ArrayUtils.isEmpty(deptIds)) {
            return;
        }
        for (Long deptId : deptIds) {
            if (deptId == null) {
                continue;
            }
            GroupRelationDomain r = getGroupRelationRepository().findByGroupIdAndDeptId(groupId, deptId);
            if (r == null) {
                r = new GroupRelationDomain();
                r.setGroupId(groupId);
                r.setDeptId(deptId);
                save(r);
            }
        }
    }

    public void appendRelation(Long groupId, Long[] userIds, Long[] startUserIds, Long[] endUserIds) {
        if (ArrayUtils.isEmpty(userIds) && ArrayUtils.isEmpty(startUserIds)) {
            return;
        }
        if (!ArrayUtils.isEmpty(userIds)) {
            for (Long userId : userIds) {
                if (userId == null) {
                    continue;
                }
                GroupRelationDomain r = getGroupRelationRepository().findByGroupIdAndUserId(groupId, userId);
                if (r == null) {
                    r = new GroupRelationDomain();
                    r.setGroupId(groupId);
                    r.setUserId(userId);
                    save(r);
                }
            }
        }

        if (!ArrayUtils.isEmpty(startUserIds)) {
            for (int i = 0, l = startUserIds.length; i < l; i++) {
                Long startUserId = startUserIds[i];
                Long endUserId = endUserIds[i];
                //范围查 如果在指定范围内 就没必要再新增一个 如当前是[10,20] 如果数据库有[9,21]
                GroupRelationDomain r = getGroupRelationRepository().findByGroupIdAndStartUserIdLessThanEqualAndEndUserIdGreaterThanEqual(groupId, startUserId, endUserId);

                if (r == null) {
                    //删除范围内的
                    getGroupRelationRepository().deleteInRange(startUserId, endUserId);
                    r = new GroupRelationDomain();
                    r.setGroupId(groupId);
                    r.setStartUserId(startUserId);
                    r.setEndUserId(endUserId);
                    save(r);
                }

            }
        }
    }

    public Set<Long> findGroupIds(Long userId, Set<Long> organizationIds) {
        if (organizationIds.isEmpty()) {
            return Sets.newHashSet(getGroupRelationRepository().findGroupIds(userId));
        }

        return Sets.newHashSet(getGroupRelationRepository().findGroupIds(userId, organizationIds));
    }

}
