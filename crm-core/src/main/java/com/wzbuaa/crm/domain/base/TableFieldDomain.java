package com.wzbuaa.crm.domain.base;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.alibaba.fastjson.annotation.JSONField;
import com.wzbuaa.crm.domain.BaseEntity;
import com.wzbuaa.crm.repository.support.annotation.EnableQueryCache;

import framework.entity.search.SearchOperator;

@SuppressWarnings("serial")
@Entity
@Table(name = "base_maintain_table_field")
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TableFieldDomain extends BaseEntity<Long> {

	public enum FieldType {
		text, textarea, date, dateRange, select, enumSelect, categorySelect, productType, autocomplete, digits, number, hidden;
	}

	private String name; // 字段名
	private String display; // 显示名
	private boolean allownull = false; // 是否为空
	private FieldType type = FieldType.text; // 控件类型
	private SearchOperator operator = SearchOperator.eq; // 查询操作符
	private int labelWidth = 80; // 标签宽度
	private int inputWidth = 80; // 控件宽度
	private int width = 150; // 控件宽度
	private int space = 10; // 间隔宽度
	private boolean newline = false; // 是否新行
	private boolean advanced = false; // 是否高级
	private boolean used = false; // 是否使用
	private int priority = 0;
	private TableDomain table; // 对应的表单
	private String options; // 配置项
	private String data;
	private Long company; // 所属企业
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public boolean isAllownull() {
		return allownull;
	}

	public void setAllownull(boolean allownull) {
		this.allownull = allownull;
	}

	@Enumerated(EnumType.STRING)
	public FieldType getType() {
		return type;
	}

	public void setType(FieldType type) {
		this.type = type;
	}

	@Enumerated(EnumType.STRING)
	public SearchOperator getOperator() {
		return operator;
	}

	public void setOperator(SearchOperator operator) {
		this.operator = operator;
	}

	public int getSpace() {
		return space;
	}

	public void setSpace(int space) {
		this.space = space;
	}

	public boolean isNewline() {
		return newline;
	}

	public boolean isAdvanced() {
		return advanced;
	}

	public void setAdvanced(boolean advanced) {
		this.advanced = advanced;
	}

	public void setNewline(boolean newline) {
		this.newline = newline;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JSONField(serialize = false)
	public TableDomain getTable() {
		return table;
	}

	public void setTable(TableDomain table) {
		this.table = table;
	}

	public boolean getUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public int getLabelWidth() {
		return labelWidth;
	}

	public void setLabelWidth(int labelWidth) {
		this.labelWidth = labelWidth;
	}

	public int getInputWidth() {
		return inputWidth;
	}

	public void setInputWidth(int inputWidth) {
		this.inputWidth = inputWidth;
	}

	@Transient
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public Long getCompany() {
		return company;
	}

	public void setCompany(Long company) {
		this.company = company;
	}

}