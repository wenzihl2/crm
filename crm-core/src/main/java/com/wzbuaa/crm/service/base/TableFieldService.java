package com.wzbuaa.crm.service.base;

import org.springframework.stereotype.Service;

import com.wzbuaa.crm.domain.base.TableFieldDomain;
import com.wzbuaa.crm.repository.base.TableFieldRepository;
import com.wzbuaa.crm.service.BaseService;

@Service
public class TableFieldService extends BaseService<TableFieldDomain, Long> {
	
	private TableFieldRepository getTableFieldRepository() {
        return (TableFieldRepository) baseRepository;
    }
}
