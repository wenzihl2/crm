package com.wzbuaa.crm.repository.base;

import java.util.List;

import com.wzbuaa.crm.domain.DictionaryType;
import com.wzbuaa.crm.domain.base.DictionaryDomain;
import com.wzbuaa.crm.repository.BaseRepository;

/**
 * <p>User: zhenglong
 * <p>Date: 2015年6月10日
 * <p>Version: 1.0
 */
public interface DictionaryRepository extends BaseRepository<DictionaryDomain, Long> {
	
	public int findMaxPriority(DictionaryType type, Long parentId);
	
	public List<DictionaryDomain> findByTypeOrderByPriorityAsc(DictionaryType type);
	
	/**
	 * 查询公共的数据字典
	 * @param type
	 * @param name
	 * @return
	 */
	public DictionaryDomain findByTypeAndName(DictionaryType type, String name);
	
	/**
	 * 查询公共的数据字典
	 * @param type
	 * @param code
	 * @return
	 */
	public DictionaryDomain findByTypeAndCode(DictionaryType type, String code);
	
	/**
	 * 查询各自企业的字典
	 * @param type
	 * @param name
	 * @param company
	 * @return
	 */
	public DictionaryDomain findByTypeAndNameAndCompany(DictionaryType type, String name, Long company);
	
	/**
	 * 查询各自企业的字典
	 * @param type
	 * @param name
	 * @param company
	 * @return
	 */
	public DictionaryDomain findByTypeAndCodeAndCompany(DictionaryType type, String code, Long company);
}
