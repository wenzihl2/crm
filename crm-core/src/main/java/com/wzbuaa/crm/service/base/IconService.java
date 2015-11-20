package com.wzbuaa.crm.service.base;

import org.springframework.stereotype.Service;

import com.wzbuaa.crm.domain.base.IconDomain;
import com.wzbuaa.crm.repository.base.IconRepository;
import com.wzbuaa.crm.service.BaseService;

/**
 * 
 * <p>User: zhenglong
 * <p>Date: 2015年5月27日
 * <p>Version: 1.0
 */
@Service
public class IconService extends BaseService<IconDomain, Long> {

    private IconRepository getIconRepository() {
        return (IconRepository) baseRepository;
    }

    public IconDomain findByIdentity(String identity) {
        return getIconRepository().findByIdentity(identity);
    }
}
