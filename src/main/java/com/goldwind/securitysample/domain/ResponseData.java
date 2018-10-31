package com.goldwind.securitysample.domain;

import org.springframework.context.annotation.Configuration;

/**
 * 
 *<p>Title:	ResponseData</p>
 *<p>Description:	TODO-</p>
 *<p>Company:	com.goldwind</p>
 * @author	macbook37349
 * @date	4:16:13 PM,Oct 29, 2018
 */
@Configuration
public class ResponseData<T> {

	// 成功
	public final static int SUCCESS = 0;

	// 失败
	public final static int ERROR = -1;

	private Integer code;

	private String message;

	private T data;

	public ResponseData() {
	}
	
	public ResponseData(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public ResponseData(Integer code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}