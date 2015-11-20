package com.wzbuaa.crm.service.base;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wzbuaa.crm.component.service.BaseTreeableService;
import com.wzbuaa.crm.domain.DictionaryType;
import com.wzbuaa.crm.domain.base.DictionaryDomain;
import com.wzbuaa.crm.repository.base.DictionaryRepository;
import com.wzbuaa.crm.util.SysUtil;

@Service
public class DictionaryService extends BaseTreeableService<DictionaryDomain, Long> {

	private DictionaryRepository getDictionaryRepository() {
        return (DictionaryRepository) baseRepository;
    }
	
	public int findMaxPriority(DictionaryType type, Long parentId) {
		return getDictionaryRepository().findMaxPriority(type, parentId);
	}
	
	public List<DictionaryDomain> findByTypeOrderByPriority(DictionaryType type) {
		return getDictionaryRepository().findByTypeOrderByPriorityAsc(type);
	}
	
	public DictionaryDomain findByTypeAndName(DictionaryType type, String name) {
		return getDictionaryRepository().findByTypeAndNameAndCompany(type, name, SysUtil.getCurrentCompany());
	}
	
	public DictionaryDomain findByTypeAndCode(DictionaryType type, String code) {
		return getDictionaryRepository().findByTypeAndCodeAndCompany(type, code, SysUtil.getCurrentCompany());
	}
	
	/**
	 * 查询公共的字典
	 * @param type
	 * @param name
	 * @return
	 */
	public DictionaryDomain findByTypeAndNameShare(DictionaryType type, String name) {
		return getDictionaryRepository().findByTypeAndName(type, name);
	}
	
	/**
	 * 查询公共的字典
	 * @param type
	 * @param code
	 * @return
	 */
	public DictionaryDomain findByTypeAndCodeShare(DictionaryType type, String code) {
		return getDictionaryRepository().findByTypeAndCode(type, code);
	}
}
