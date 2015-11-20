package com.wzbuaa.crm.bean;

public class KeyValue {
	
	private String id;
	private String text;

	public KeyValue(String id, String text) {
		super();
		this.id = id;
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}