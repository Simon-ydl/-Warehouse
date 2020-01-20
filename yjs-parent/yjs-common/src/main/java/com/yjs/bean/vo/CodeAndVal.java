package com.yjs.bean.vo;


import com.yjs.bean.BaseBean;
import lombok.Getter;
import lombok.Setter;

/**
 * 编码值VO
 * 
 * @author thb
 *
 */
@Getter
@Setter
public class CodeAndVal extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4863419098557521421L;
	/**
	 * 编码
	 */
	private String code;
	//显示文本
	private String text;
	//值
	private Object val;
	//范围(0:表示通用，1：平台用，2:表示商户用)
	private String scope;
	
	private String time;
	public CodeAndVal() {
	}
	public CodeAndVal(String text , String val) {
		this.text = text;
		this.val = val;
	}
	
}
