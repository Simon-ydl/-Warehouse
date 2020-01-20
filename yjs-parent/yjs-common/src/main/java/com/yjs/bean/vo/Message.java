package com.yjs.bean.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 消息vo
 * @author thb
 *
 */
@Getter
@Setter
public class Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8949370160805092848L;
		
	private String errmsg ;
	
	private String errcode ;
	
	private Boolean flag;
	
	private Object data;
	/**
	 * 消息vo
	 */
	public Message() {
	}
	/**
	 * 消息vo
	 * @param code
	 * @param flag
	 */
	public Message(String code) {
		super();
		this.errcode = code;
	}
	/**
	 * 消息vo
	 * @param code
	 * @param flag
	 */
	public Message(String code, boolean flag) {
		super();
		this.errcode = code;
		this.flag = flag;
	}
	/**
	 * 消息vo
	 * @param code
	 * @param flag
	 * @param data
	 */
	public Message(String code, boolean flag, Object data) {
		super();
		this.errcode = code;
		this.flag = flag;
		this.data = data;
	}
	/**
	 * 消息vo
	 * @param msg
	 * @param code
	 * @param flag
	 * @param data
	 */
	public Message(String msg, String code, boolean flag, Object data) {
		super();
		this.errmsg = msg;
		this.errcode = code;
		this.flag = flag;
		this.data = data;
	}
	
	
}
