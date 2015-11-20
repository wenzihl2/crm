package com.wzbuaa.crm.bean;

/**
 * 提现状态
 * @author fangchen
 *
 */
public enum DrawState {
	
	apply("申请提现"), 
	approve("审核通过"), 
	finish("提现成功"), 
	refuse("拒绝提现");

	private String name;
	private DrawState(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
}
