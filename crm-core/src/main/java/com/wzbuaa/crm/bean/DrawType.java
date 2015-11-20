package com.wzbuaa.crm.bean;

/**
 * 提现类型
 * @author fangchen
 *
 */
public enum DrawType{
	deposit("余额提现");

	private String name;
	private DrawType(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
}
