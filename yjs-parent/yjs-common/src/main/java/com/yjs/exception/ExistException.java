package com.yjs.exception;


/**
 * 数据已存在异常
 */
public class ExistException extends BaseException {

	private static final long serialVersionUID = 1L;
	public static final String CODE = "3001";
	private static final String MSG = "已存在相同数据";
	
	/**
	 * 数据已存在异常
	 * @author 田红兵
	 * @since 2015年11月18日
	 */
	public ExistException(Throwable args,String appendMsg){		
		super(CODE, MSG +appendMsg, args);
	}

}
