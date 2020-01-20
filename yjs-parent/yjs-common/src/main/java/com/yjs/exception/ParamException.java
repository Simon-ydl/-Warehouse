package com.yjs.exception;


import com.yjs.bean.system.User;

/**
 * 参数异常
 *
 */
public class ParamException extends BaseException{
	/**
	 * 参数错误
	 */
	public final static String CODE = "1001";
	private static final long serialVersionUID = -8861366463346740047L;
	public ParamException() {
		super(CODE);
	}
	
	public ParamException(String msg) {		
		super(CODE,"参数错误:" + msg);
	}
	public ParamException(User user) {
		super(CODE,user);
	}
	public ParamException(User user,Object obj) {
		super(CODE,"参数错误:" + obj.toString(),user);
	}
}
