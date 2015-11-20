package com.wzbuaa.crm.service.sso;

import com.wzbuaa.crm.domain.sso.Auth;
import com.wzbuaa.crm.domain.sso.user.GroupDomain;
import com.wzbuaa.crm.domain.sso.user.S_userDomain;
import com.wzbuaa.crm.repository.sso.AuthRepository;
import com.wzbuaa.crm.service.BaseService;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:01
 * <p>Version: 1.0
 */
@Service
public class AuthService extends BaseService<Auth, Long> {

    @Autowired private S_userService userService;
    @Autowired private GroupService groupService;

    private AuthRepository getAuthRepository() {
        return (AuthRepository) baseRepository;
    }

    public void addUserAuth(Long[] userIds, Auth m) {

        if (ArrayUtils.isEmpty(userIds)) {
            return;
        }

        for (Long userId : userIds) {

            S_userDomain user = userService.findOne(userId);
            if (user == null) {
                continue;
            }

            Auth auth = getAuthRepository().findByUserId(userId);
            if (auth != null) {
                auth.addRoleIds(m.getRoleIds());
                continue;
            }
            auth = new Auth();
            auth.setUserId(userId);
            auth.setType(m.getType());
            auth.setRoleIds(m.getRoleIds());
            save(auth);
        }
    }

    public void addGroupAuth(Long[] groupIds, Auth m) {
        if (ArrayUtils.isEmpty(groupIds)) {
            return;
        }

        for (Long groupId : groupIds) {
        	GroupDomain group = groupService.findOne(groupId);
            if (group == null) {
                continue;
            }

            Auth auth = getAuthRepository().findByGroupId(groupId);
            if (auth != null) {
                auth.addRoleIds(m.getRoleIds());
                continue;
            }
            auth = new Auth();
            auth.setGroupId(groupId);
            auth.setType(m.getType());
            auth.setRoleIds(m.getRoleIds());
            save(auth);
        }
    }

    public void addDeptJobAuth(Long[] deptIds, Long[][] jobIds, Auth m) {

        if (ArrayUtils.isEmpty(deptIds)) {
            return;
        }
        for (int i = 0, l = deptIds.length; i < l; i++) {
            Long deptId = deptIds[i];
            if (jobIds[i].length == 0) {
                addDeptJobAuth(deptId, null, m);
                continue;
            }

            //仅新增/修改一个 spring会自动split（“，”）--->给数组
            if (l == 1) {
                for (int j = 0, l2 = jobIds.length; j < l2; j++) {
                    Long jobId = jobIds[i][0];
                    addDeptJobAuth(deptId, jobId, m);
                }
            } else {
                for (int j = 0, l2 = jobIds[i].length; j < l2; j++) {
                    Long jobId = jobIds[i][0];
                    addDeptJobAuth(deptId, jobId, m);
                }
            }

        }
    }

    private void addDeptJobAuth(Long deptId, Long jobId, Auth m) {
        if (deptId == null) {
            deptId = 0L;
        }
        if (jobId == null) {
            jobId = 0L;
        }


        Auth auth = getAuthRepository().findByDeptIdAndJobId(deptId, jobId);
        if (auth != null) {
            auth.addRoleIds(m.getRoleIds());
            return;
        }

        auth = new Auth();
        auth.setDeptId(deptId);
        auth.setJobId(jobId);
        auth.setType(m.getType());
        auth.setRoleIds(m.getRoleIds());
        save(auth);


    }

    /**
     * 根据用户信息获取 角色
     * 1.1、用户  根据用户绝对匹配
     * 1.2、组织机构 根据组织机构绝对匹配 此处需要注意 祖先需要自己获取
     * 1.3、工作职务 根据工作职务绝对匹配 此处需要注意 祖先需要自己获取
     * 1.4、组织机构和工作职务  根据组织机构和工作职务绝对匹配 此处不匹配祖先
     * 1.5、组  根据组绝对匹配
     *
     * @param userId             必须有
     * @param groupIds           可选
     * @param deptIds    可选
     * @param jobIds             可选
     * @param organizationJobIds 可选
     * @return
     */
    public Set<Long> findRoleIds(Long userId, Set<Long> groupIds, Set<Long> deptIds, Set<Long> jobIds, Set<Long[]> organizationJobIds) {
        return getAuthRepository().findRoleIds(userId, groupIds, deptIds, jobIds, organizationJobIds);
    }
}
