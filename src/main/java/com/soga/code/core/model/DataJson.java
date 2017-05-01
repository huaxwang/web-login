package com.soga.code.core.model;

/**
 * 
 * 版权：融贯资讯 <br/>
 * 作者：kai.gao@rogrand.com <br/>
 * 生成日期：2013-10-24 <br/>
 * 描述：用户后台向前台返回的JSON对象
 */
public class DataJson<T>{
	
	private String mac;
	private Head head;
	private T body;
	
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
	
	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}
}
