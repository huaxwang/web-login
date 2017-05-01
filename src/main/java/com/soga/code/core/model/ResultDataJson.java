package com.soga.code.core.model;


public class ResultDataJson {
	private String mac;
	private Head head;
	private ResultJsonObject body;
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public Head getHead() {
		return head;
	}
	public void setHead(Head head) {
		this.head = head;
	}
	public ResultJsonObject getBody() {
		return body;
	}
	public void setBody(ResultJsonObject body) {
		this.body = body;
	}
}
