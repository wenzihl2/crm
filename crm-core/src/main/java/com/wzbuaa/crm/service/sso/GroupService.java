package com.wzbuaa.crm.service.sso;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.wzbuaa.crm.domain.sso.user.GroupDomain;
import com.wzbuaa.crm.repository.sso.GroupRepository;
import com.wzbuaa.crm.service.BaseService;

import framework.entity.search.SearchOperator;
import framework.entity.search.Searchable;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:01
 * <p>Version: 1.0
 */
@Service
public class GroupService extends BaseService<GroupDomain, Long> {


    @Autowired
    private GroupRelationService groupRelationService;

    private GroupRepository getGroupRepository() {
        return (GroupRepository) baseRepository;
    }

    public Set<Map<String, Object>> findIdAndNames(Searchable searchable, String groupName) {

        searchable.addSearchFilter("name", SearchOperator.like, groupName);

        return Sets.newHashSet(
                Lists.transform(
                        findAll(searchable).getContent(),
                        new Function<GroupDomain, Map<String, Object>>() {
                            @Override
                            public Map<String, Object> apply(GroupDomain input) {
                                Map<String, Object> data = Maps.newHashMap();
                                data.put("label", input.getName());
                                data.put("value", input.getId());
                                return data;
                            }
                        }
                )
        );
    }

    /**
     * 获取可用的的分组编号列表
     *
     * @param userId
     * @param organizationIds
     * @return
     */
    public Set<Long> findShowGroupIds(Long userId, Set<Long> organizationIds) {
        Set<Long> groupIds = Sets.newHashSet();
        groupIds.addAll(getGroupRepository().findDefaultGroupIds());
        groupIds.addAll(groupRelationService.findGroupIds(userId, organizationIds));


        //TODO 如果分组数量很多 建议此处查询时直接带着是否可用的标识去查
        for (GroupDomain group : findAll()) {
            if (Boolean.FALSE.equals(group.getShow())) {
                groupIds.remove(group.getId());
            }
        }

        return groupIds;
    }
}
