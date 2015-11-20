package com.wzbuaa.crm.repository.sso;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.wzbuaa.crm.domain.sso.user.S_userDomain;
import com.wzbuaa.crm.domain.sso.user.UserDeptJob;
import com.wzbuaa.crm.repository.BaseRepository;
import com.wzbuaa.crm.repository.support.annotation.SearchableQuery;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:00
 * <p>Version: 1.0
 */
@SearchableQuery(callbackClass = UserSearchCallback.class)
public interface UserRepository extends BaseRepository<S_userDomain, Long> {

	S_userDomain findByUsername(String username);

	S_userDomain findByMobilePhoneNumber(String mobilePhoneNumber);

	S_userDomain findByEmail(String email);

    @Query("from UserDeptJob where user=?1 and deptId=?2 and jobId=?3")
    UserDeptJob findUserDept(S_userDomain user, Long deotId, Long jobId);

//    @Query("from S_userDomain user where user.dept.id in (?1) and user.company=?2")
//    List<S_userDomain> findUserDept(List<Long> deptIds, Long company);
    
    @Query("select uoj from UserDeptJob uoj where not exists(select 1 from JobDomain j where uoj.jobId=j.id) or not exists(select 1 from DeptDomain o where uoj.deptId=o.id)")
    Page<UserDeptJob> findUserDeptJobOnNotExistsOrganizationOrJob(Pageable pageable);

    @Modifying
    @Query("delete from UserDeptJob uoj where not exists(select 1 from S_userDomain u where uoj.user=u)")
    void deleteUserOrganizationJobOnNotExistsUser();
}
