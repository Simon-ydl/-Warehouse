package com.yjs.dataSource;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Table(name="t_sys_database")
@Getter
@Setter
public class Database {

	/**
	 * 
	 */

	private static final long serialVersionUID = 2959567867207055588L;

	@Id
	@Column(name = "id")
	protected Integer id;
	private String code;
	private String name;
	private String type;
	private String dc;
	private String url;
	private String userName;
	private String passWord;

	private String creator;
	private Object creatorId;
	private Date createDate;
	private Boolean deleted;
		
}
