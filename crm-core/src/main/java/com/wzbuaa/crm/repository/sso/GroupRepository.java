package com.wzbuaa.crm.repository.sso;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.wzbuaa.crm.domain.sso.user.GroupDomain;
import com.wzbuaa.crm.repository.BaseRepository;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:00
 * <p>Version: 1.0
 */
public interface GroupRepository extends BaseRepository<GroupDomain, Long> {

    @Query("select id from GroupDomain where defaultGroup=true and show=true")
    List<Long> findDefaultGroupIds();

}
