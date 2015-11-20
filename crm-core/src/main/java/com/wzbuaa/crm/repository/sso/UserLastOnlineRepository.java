package com.wzbuaa.crm.repository.sso;

import com.wzbuaa.crm.domain.sso.user.UserLastOnline;
import com.wzbuaa.crm.repository.BaseRepository;


/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:00
 * <p>Version: 1.0
 */
public interface UserLastOnlineRepository extends BaseRepository<UserLastOnline, Long> {

    UserLastOnline findByUserId(Long userId);
}
