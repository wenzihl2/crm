package com.wzbuaa.crm.service.sso;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import com.wzbuaa.crm.domain.sso.Resource;
import com.wzbuaa.crm.domain.sso.Role;
import com.wzbuaa.crm.domain.sso.RoleResourcePermission;
import com.wzbuaa.crm.domain.sso.user.S_userDomain;
import com.wzbuaa.crm.domain.sso.user.UserDeptJob;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * 分组、组织机构、用户、新增、修改、删除时evict缓存
 * <p/>
 * 获取用户授权的角色及组装好的权限
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-1 下午2:38
 * <p>Version: 1.0
 */
@Service
public class UserAuthService {

    @Autowired private GroupService groupService;
    @Autowired private DeptService deptService;
    @Autowired private JobService jobService;
    @Autowired private AuthService authService;
    @Autowired private RoleService roleService;
    @Autowired private ResourceService resourceService;


    public Set<Role> findRoles(S_userDomain user) {

        if (user == null) {
            return Sets.newHashSet();
        }

        Long userId = user.getId();

        Set<Long[]> organizationJobIds = Sets.newHashSet();
        Set<Long> deptIds = Sets.newHashSet();
        Set<Long> jobIds = Sets.newHashSet();

        for (UserDeptJob o : user.getDeptJobs()) {
            Long organizationId = o.getDeptId();
            Long jobId = o.getJobId();

            if (organizationId != null && jobId != null && organizationId != 0L && jobId != 0L) {
                organizationJobIds.add(new Long[]{organizationId, jobId});
            }
            deptIds.add(organizationId);
            jobIds.add(jobId);
        }

        //TODO 目前默认子会继承父 后续实现添加flag控制是否继承

        //找组织机构祖先
        deptIds.addAll(deptService.findAncestorIds(deptIds));
        //找工作职务的祖先
        jobIds.addAll(jobService.findAncestorIds(jobIds));

        //过滤组织机构 仅获取目前可用的组织机构数据
        deptService.filterForCanShow(deptIds, organizationJobIds);
        jobService.filterForCanShow(jobIds, organizationJobIds);

        //过滤工作职务 仅获取目前可用的工作职务数据

        //默认分组 + 根据用户编号 和 组织编号 找 分组
        Set<Long> groupIds = groupService.findShowGroupIds(userId, deptIds);

        //获取权限
        //1.1、获取用户角色
        //1.2、获取组织机构角色
        //1.3、获取工作职务角色
        //1.4、获取组织机构和工作职务组合的角色
        //1.5、获取组角色
        Set<Long> roleIds = authService.findRoleIds(userId, groupIds, deptIds, jobIds, organizationJobIds);

        Set<Role> roles = roleService.findShowRoles(roleIds);

        return roles;

    }

    public Set<String> findStringRoles(S_userDomain user) {
        Set<Role> roles = ((UserAuthService) AopContext.currentProxy()).findRoles(user);
        return Sets.newHashSet(Collections2.transform(roles, new Function<Role, String>() {
            @Override
            public String apply(Role input) {
                return input.getValue();
            }
        }));
    }

    /**
     * 根据角色获取 权限字符串 如sys:admin
     *
     * @param user
     * @return
     */
    public Set<String> findStringPermissions(S_userDomain user) {
        Set<String> permissions = Sets.newHashSet();

        Set<Role> roles = ((UserAuthService) AopContext.currentProxy()).findRoles(user);
        for (Role role : roles) {
            for (RoleResourcePermission rrp : role.getResourcePermissions()) {
                Resource resource = resourceService.findOne(rrp.getResourceId());

                String actualResourceIdentity = resourceService.findActualResourceIdentity(resource);

                //不可用 即没查到 或者标识字符串不存在
                if (resource == null || StringUtils.isEmpty(actualResourceIdentity) || Boolean.FALSE.equals(resource.getShow())) {
                    continue;
                }

                for (Long permissionId : rrp.getPermissionIds()) {
                	Resource permission = resourceService.findOne(permissionId);

                    //不可用
                    if (permission == null || Boolean.FALSE.equals(permission.getShow())) {
                        continue;
                    }
                    permissions.add(actualResourceIdentity + ":" + permission.getIdentity());

                }
            }

        }

        return permissions;
    }

}
