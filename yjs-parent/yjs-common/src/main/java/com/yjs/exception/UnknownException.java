package com.yjs.exception;


/**
 * 未知异常
 * @author 田红兵
 * @since 2015年11月18日
 */
public class UnknownException extends BaseException {
	private static final long serialVersionUID = 1L;
	public static final String CODE = "2001";
	private static final String MSG = "未知错误:";
	
	/**
	 * 未知异常
	 * @author 田红兵
	 * @since 2015年11月18日
	 */
	public UnknownException(Throwable args,String appendMsg){
		super(CODE, MSG + appendMsg, args);
	}
}
