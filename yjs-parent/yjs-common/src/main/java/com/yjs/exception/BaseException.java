package com.yjs.exception;


import com.yjs.bean.system.User;

/**
 * 基础异常类
 */
public class BaseException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	protected String code;
	protected User user;
	
	
	public BaseException(String code) {
		this.code = code;
	}
	public BaseException(String code , User user) {
		this.code = code;
		this.user = user;
	}
	

	public BaseException(Throwable args) {
		super(args);

	}

	public BaseException(String code, String msg) {
		super(msg);
		this.code = code;
	}
	public BaseException(String code, String msg , User user) {
		super(msg);
		this.code = code;
		this.user = user;
	}

	public BaseException(String code, String msg, Throwable args) {
		super(msg, args);
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	public User getUser() {
		return this.user;
	}
}
