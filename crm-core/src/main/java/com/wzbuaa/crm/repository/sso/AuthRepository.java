package com.wzbuaa.crm.repository.sso;

import java.util.Set;

import com.wzbuaa.crm.domain.sso.Auth;
import com.wzbuaa.crm.repository.BaseRepository;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:00
 * <p>Version: 1.0
 */
public interface AuthRepository extends BaseRepository<Auth, Long> {

    Auth findByUserId(Long userId);

    Auth findByGroupId(Long groupId);

    Auth findByDeptIdAndJobId(Long deptId, Long jobId);

    ///////////委托给AuthRepositoryImpl实现
    public Set<Long> findRoleIds(Long userId, Set<Long> groupIds, Set<Long> deptIds, Set<Long> jobIds, Set<Long[]> organizationJobIds);

}
