package com.yjs.bean;

public class UserBean {
    private  String useridcode;//省统一账户唯ID
    private  String  cn;//用户名
    private  String idcardtype;//证件类型
    private  String idcardnumber;//证件号码
    private  String telephonenumber; //电话
    private  String code;
    private  String msg;
	public String getUseridcode() {
		return useridcode;
	}
	public void setUseridcode(String useridcode) {
		this.useridcode = useridcode;
	}
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public String getIdcardtype() {
		return idcardtype;
	}
	public void setIdcardtype(String idcardtype) {
		this.idcardtype = idcardtype;
	}
	public String getIdcardnumber() {
		return idcardnumber;
	}
	public void setIdcardnumber(String idcardnumber) {
		this.idcardnumber = idcardnumber;
	}
	public String getTelephonenumber() {
		return telephonenumber;
	}
	public void setTelephonenumber(String telephonenumber) {
		this.telephonenumber = telephonenumber;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Override
	public String toString() {
		return "UserBean [useridcode=" + useridcode + ", cn=" + cn + ", idcardtype=" + idcardtype + ", idcardnumber="
				+ idcardnumber + ", telephonenumber=" + telephonenumber + ", code=" + code + ", msg=" + msg + "]";
	}
	public UserBean(String useridcode, String cn, String idcardtype, String idcardnumber, String telephonenumber,
                    String code, String msg) {
		super();
		this.useridcode = useridcode;
		this.cn = cn;
		this.idcardtype = idcardtype;
		this.idcardnumber = idcardnumber;
		this.telephonenumber = telephonenumber;
		this.code = code;
		this.msg = msg;
	}
	public UserBean() {
		super();
		// TODO Auto-generated constructor stub
	}

   
}
