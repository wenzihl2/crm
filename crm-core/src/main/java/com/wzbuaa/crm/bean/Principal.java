package com.wzbuaa.crm.bean;

import java.io.Serializable;


@SuppressWarnings("serial")
public class Principal implements Serializable {

	private Long id;
	private String username;

	public Principal() {
		super();
	}

	public Principal(Long id, String username) {
		super();
		this.id = id;
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
