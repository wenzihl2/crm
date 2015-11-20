package com.wzbuaa.crm.plugin.SysDataConvert;

public enum DataType {

	user, //用户
	group, //用户组
	dept, //组织机构
	deptTree, //组织机构含上级节点
	job, //工作职务
	jobTree, //工作职务含上级几点
	role, // 角色转换，可以是逗号表达式
	dept_group; //组织机构组
}
