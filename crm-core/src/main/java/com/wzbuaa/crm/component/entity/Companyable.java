package com.wzbuaa.crm.component.entity;


/**
 * <p>实体实现该接口表示想要逻辑删除
 * 为了简化开发 约定删除标识列名为close，如果想自定义删除的标识列名：
 * 1、使用注解元数据
 * 2、写一个 getColumn() 方法 返回列名
 * <p/>
 * <p>User: zhenglong
 * <p>Date: 2015年6月4日
 * <p>Version: 1.0
 */
public interface Companyable {

	public abstract void setCompany(Long company_id);
	
	public abstract Long getCompany();
}
