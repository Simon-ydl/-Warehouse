package com.yjs.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ids extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	protected Object id;
	
	@Transient
	private String ids;//通用ids	
	
}
