package com.wzbuaa.crm.repository.base;

import org.springframework.data.jpa.repository.Query;

import com.wzbuaa.crm.domain.SnCreateType;
import com.wzbuaa.crm.domain.base.SnCreaterDomain;
import com.wzbuaa.crm.repository.BaseRepository;

/**
 * <p>User: zhenglong
 * <p>Date: 2015年6月10日
 * <p>Version: 1.0
 */
public interface SnCreaterRepository extends BaseRepository<SnCreaterDomain, Long> {

	@Query("from SnCreaterDomain bean where bean.code=?1 and bean.company=?2")
	SnCreaterDomain findByCode(SnCreateType code, Long company);
	
}
