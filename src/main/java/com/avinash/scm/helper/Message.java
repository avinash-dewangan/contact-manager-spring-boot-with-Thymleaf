package com.avinash.scm.helper;


public class Message {

	private String content;
	private String type;


	public Message() {

	}
	public Message(java.lang.String content, java.lang.String type) {
		this.content = content;
		this.type = type;
	}

	public java.lang.String getContent() {
		return content;
	}

	public void setContent(java.lang.String content) {
		this.content = content;
	}

	public java.lang.String getType() {
		return type;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}

}
