package com.wzbuaa.crm.repository.support;

import org.apache.commons.lang3.StringUtils;

public class JoinTable {

	private String table;
	private String field;
	private String searchProperty;
	
	public JoinTable(String searchProperty) {
		this.searchProperty = searchProperty;
		if(this.searchProperty.startsWith("$$")) {
			this.searchProperty = StringUtils.removeStart(this.searchProperty, "$$");
		}
		this.table = StringUtils.substringBefore(this.searchProperty, ".");
		this.field = StringUtils.substringAfter(this.searchProperty, ".");
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getSearchProperty() {
		return searchProperty;
	}

	public void setSearchProperty(String searchProperty) {
		this.searchProperty = searchProperty;
	}
	
	
}
