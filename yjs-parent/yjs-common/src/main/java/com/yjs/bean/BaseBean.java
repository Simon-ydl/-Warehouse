package com.yjs.bean;

import java.io.Serializable;

import com.yjs.utils.FastJsonUtil;

/**
 * 基础bean
 * @author thb
 *
 */
public class BaseBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return FastJsonUtil.toJson(this);
	}
	/**
	 * 拷贝当前实体
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T copy(){
		String json = FastJsonUtil.toJson(this);
		return (T) FastJsonUtil.fromJson(json, this.getClass());
	}

}
