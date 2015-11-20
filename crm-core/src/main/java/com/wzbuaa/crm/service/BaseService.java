package com.wzbuaa.crm.service;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.wzbuaa.crm.component.entity.Companyable;
import com.wzbuaa.crm.domain.AbstractEntity;
import com.wzbuaa.crm.repository.BaseRepository;
import com.wzbuaa.crm.util.ShopUtil;
import com.wzbuaa.crm.util.SysUtil;

import framework.entity.search.SearchOperator;
import framework.entity.search.Searchable;
import framework.util.Collections3;
import framework.util.Reflections;

/**
 * <p>抽象service层基类 提供一些简便方法
 * <p/>
 * <p>泛型 ： M 表示实体类型；ID表示主键类型
 * <p/>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-12 下午4:43
 * <p>Version: 1.0
 */
@SuppressWarnings("rawtypes")
public abstract class BaseService<M extends AbstractEntity, ID extends Serializable> {

    protected BaseRepository<M, ID> baseRepository;

    @Autowired
    public void setBaseRepository(BaseRepository<M, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    /**
     * 保存单个实体
     *
     * @param m 实体
     * @return 返回保存的实体
     */
    public M save(M m) {
    	if (m instanceof Companyable && SysUtil.getUser() != null && !SysUtil.isAdmin()) {
    		((Companyable)m).setCompany(SysUtil.getUser().companyId);
    	} else if(m instanceof Companyable && ShopUtil.getUser() != null){
    		((Companyable)m).setCompany(ShopUtil.getUser().companyId);
    	}
        return baseRepository.save(m);
    }

    public M saveAndFlush(M m) {
        m = save(m);
        baseRepository.flush();
        return m;
    }

    /**
     * 更新单个实体
     *
     * @param m 实体
     * @return 返回更新的实体
     */
    public M update(M m) {
    	if (m instanceof Companyable && SysUtil.getUser() != null && !SysUtil.isAdmin()) {
    		((Companyable)m).setCompany(SysUtil.getUser().companyId);
    	} else if(m instanceof Companyable && ShopUtil.getUser() != null){
    		((Companyable)m).setCompany(ShopUtil.getUser().companyId);
    	}
        return baseRepository.save(m);
    }

    /**
     * 根据主键删除相应实体
     *
     * @param id 主键
     */
    public void delete(ID id) {
        baseRepository.delete(id);
    }

    /**
     * 删除实体
     *
     * @param m 实体
     */
    public void delete(M m) {
        baseRepository.delete(m);
    }

    /**
     * 根据主键删除相应实体
     *
     * @param ids 实体
     */
    public void delete(ID[] ids) {
        baseRepository.delete(ids);
    }

    /**
     * 按照主键查询
     *
     * @param id 主键
     * @return 返回id对应的实体
     */
    public M findOne(ID id) {
        return baseRepository.findOne(id);
    }
    
    public boolean isExist(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		Searchable searchable = Searchable.newSearchable()
				.addSearchFilter(propertyName, SearchOperator.eq, value);
		// 如果带有企业过滤字段，则增加企业过滤
		Class entityClass = Reflections.getClassGenricType(getClass(), 0);
		if(entityClass.isAssignableFrom(Companyable.class) && !SysUtil.isAdmin()) {
			searchable.addSearchFilter("company", SearchOperator.eq, SysUtil.getUser().companyId);
		}
		List<M> list = findAllWithNoPageNoSort(searchable);
		return !list.isEmpty();
	}

    /**
     * 实体是否存在
     *
     * @param id 主键
     * @return 存在 返回true，否则false
     */
    public boolean exists(ID id) {
        return baseRepository.exists(id);
    }

    /**
     * 统计实体总数
     *
     * @return 实体总数
     */
    public long count() {
        return baseRepository.count();
    }

    /**
     * 查询所有实体
     *
     * @return
     */
    public List<M> findAll() {
        return baseRepository.findAll();
    }
    

    public List<M> findAll(Iterable<ID> ids) {
    	return baseRepository.findAll(ids);
    }

    /**
     * 按照顺序查询所有实体
     *
     * @param sort
     * @return
     */
    public List<M> findAll(Sort sort) {
        return baseRepository.findAll(sort);
    }
    
    public boolean isUnique(String propertyName, Object oldValue, Object newValue) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(newValue, "newValue is required");
		if (newValue == oldValue || newValue.equals(oldValue)) {
			return true;
		}
		if (newValue instanceof String) {
			if (oldValue != null && StringUtils.equalsIgnoreCase((String) oldValue, (String) newValue)) {
				return true;
			}
		}
		Searchable searchable = Searchable.newSearchable()
					.addSearchFilter(propertyName, SearchOperator.eq, newValue);
		// 如果带有企业过滤字段，则增加企业过滤
		Class entityClass = Reflections.getClassGenricType(getClass(), 0);
		if(entityClass.isAssignableFrom(Companyable.class) && !SysUtil.isAdmin()) {
			searchable.addSearchFilter("company", SearchOperator.eq, SysUtil.getUser().companyId);
		}
		
		List<M> list = findAllWithNoPageNoSort(searchable);
		return Collections3.isEmpty(list);
	}

    /**
     * 分页及排序查询实体
     *
     * @param pageable 分页及排序数据
     * @return
     */
    public Page<M> findAll(Pageable pageable) {
        return baseRepository.findAll(pageable);
    }

    /**
     * 按条件分页并排序查询实体
     *
     * @param searchable 条件
     * @return
     */
    public Page<M> findAll(Searchable searchable) {
        return baseRepository.findAll(searchable);
    }

    /**
     * 按条件不分页不排序查询实体
     *
     * @param searchable 条件
     * @return
     */
    public List<M> findAllWithNoPageNoSort(Searchable searchable) {
        searchable.removePageable();
        searchable.removeSort();
        return Lists.newArrayList(baseRepository.findAll(searchable).getContent());
    }

    /**
     * 按条件排序查询实体(不分页)
     *
     * @param searchable 条件
     * @return
     */
    public List<M> findAllWithSort(Searchable searchable) {
        searchable.removePageable();
        return Lists.newArrayList(baseRepository.findAll(searchable).getContent());
    }


    /**
     * 按条件分页并排序统计实体数量
     *
     * @param searchable 条件
     * @return
     */
    public Long count(Searchable searchable) {
        return baseRepository.count(searchable);
    }

    public String getSql(Searchable searchable) {
    	return baseRepository.getSql(searchable);
    }
    
}