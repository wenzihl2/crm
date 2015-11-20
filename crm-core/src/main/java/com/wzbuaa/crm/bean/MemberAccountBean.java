package com.wzbuaa.crm.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MemberAccountBean")
public class MemberAccountBean {
	private List<AccountBean> accountList;//会员银行卡列表
	private Integer accountLimit;//银行卡数量限制
	
	public Boolean canAdd(){
		if(accountList == null || accountLimit == null){
			return true;
		}
		return accountList.size() < accountLimit;
	}

	public List<AccountBean> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<AccountBean> accountList) {
		this.accountList = accountList;
	}

	public Integer getAccountLimit() {
		return accountLimit;
	}

	public void setAccountLimit(Integer accountLimit) {
		this.accountLimit = accountLimit;
	}

}
