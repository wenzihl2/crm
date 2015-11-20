package com.wzbuaa.crm.service.base;

import org.springframework.stereotype.Service;

import com.wzbuaa.crm.domain.base.TableDomain;
import com.wzbuaa.crm.repository.base.TableRepository;
import com.wzbuaa.crm.service.BaseService;

@Service
public class TableService extends BaseService<TableDomain, Long> {

	private TableRepository getTableRepository() {
        return (TableRepository) baseRepository;
    }
	
	public TableDomain getByClassPath(String path) {
		return getTableRepository().findByKeywordLike(path);
	}
}
