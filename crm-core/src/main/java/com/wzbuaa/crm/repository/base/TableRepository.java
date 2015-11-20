package com.wzbuaa.crm.repository.base;

import com.wzbuaa.crm.domain.base.TableDomain;
import com.wzbuaa.crm.repository.BaseRepository;

/**
 * 
 * <p>User: zhenglong
 * <p>Date: 2015年6月11日
 * <p>Version: 1.0
 */
public interface TableRepository extends BaseRepository<TableDomain, Long> {

	TableDomain findByKeywordLike(String path);
}