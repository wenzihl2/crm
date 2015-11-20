package com.wzbuaa.crm.domain.base;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.wzbuaa.crm.domain.BaseEntity;
/**
 * 
 * @author wjl
 *
 */
@Entity
@Table(name="base_search_record")
public class SearchRecordDomain extends BaseEntity<Long> {
	private static final long serialVersionUID = -6671295737003100088L;
	
	private String name;
	private Long ownerId;
	private String tabURL;
	private String searchJson;
	
	public String getSearchJson() {
		return searchJson;
	}
	public void setSearchJson(String searchJson) {
		this.searchJson = searchJson;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
	public String getTabURL() {
		return tabURL;
	}
	public void setTabURL(String tabURL) {
		this.tabURL = tabURL;
	}
}
