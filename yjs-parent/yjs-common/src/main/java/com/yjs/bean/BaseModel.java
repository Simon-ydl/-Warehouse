package com.yjs.bean;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
/**
 * 基础模型
 * @author thb
 *
 */
@Getter
@Setter
public class BaseModel extends BaseBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	protected String creator;
	protected Object creatorId;
	protected Date createDate;
	protected Boolean deleted;	
	
//	@Transient
//	public String ids;
	/**
	 * 设置部分属性为null
	 */
	public void buildSimple(){
		this.createDate = null;
		this.creator = null;
		this.creatorId = null;
		this.deleted = null;
	}
}
