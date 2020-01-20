package com.yjs.bean.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableData {

	private String msg ;
	
	private String code ;
	
	private Long count;
	
	private Object data;

	public TableData(String msg, String code, Long count, Object data) {
		super();
		this.msg = msg;
		this.code = code;
		this.count = count;
		this.data = data;
	}
	
	
}
