/** 
 * @Title:常量工具类
 * @Desription:常量工具类
 * @file: Constans.java
 * @Author: 田红兵
 *    
 */ 

package com.yjs.bean;


public final class Constans {
	private Constans() {
		super();
	}
	/**
	 * 人脸识别语音验证码sessionId
	 */
	public static final  String VOICECODE_SESSIONID="voiceCodeSessionId";
	
	/**
	 * 密码服务
	 */
	public static final  String PASSWORD_SERVICE="MM";
	/**
	 * 文档转换
	 */
	public static final  String DOCUMENT_CONVERSION="WD";
	/**
	 * 签名生成
	 */
	public static final  String SIGNATURE_GENERATION="QM";
	/**
	 * 印章生成
	 */
	public static final  String SEAL_GENERATION="YZ";
	/**
	 * 盖章
	 */
	public static final  String SEAL="GZ";
	
	
	public static final  String VALID="valid";
	/**
	 * 数据库名前缀
	 */
	public static final String DBNAME_PREFIX = "cms_";
	
	/**
	 * 默认超级管理员手机号码
	 */
	public static final String ADMIN = "13246435626";	
	/**
	 * 匿名客户
	 */
	public static final String ANONYMITY = "anonymity";
	/**
	 * 系统用户
	 */
	public static final String SYSTEM = "system";	
	/**
	 * 会话中webSocket用户名
	 */
	public static final String WEBSOCKET_USERNAME="webSocketUserName";
	/**
	 * 会话中用户名
	 */
	public static final String SESSION_USERNAME="sessionUserName";
	/**
	 * 密码
	 */
	public static final String PW_DEFAULT = "888";
	

	/**
	 * 消息key
	 */
	public static final String MSG = "msg";
	/**
	 * 逗号
	 */
	public static final String COMMA=",";
	
	/**
	 * 分号
	 */
	public static final String SEMICOLON=";";

	/**
	 * 下划线
	 */
	public static final String UNDERLINE="_";
	/**
	 * 中横线
	 */
	public static final String HYPHEN="-";
	
	
	public final static String LOGIN_VALIDATE_CODE = "valiCode";
	/**
	 * 是否需要启用验证码
	 */
	public static final Boolean IS_VALI_CODE = false;
  	/**
  	 * 数据库字符窜最大长度
  	 */
  	public static final int DB_STRING_LENGTH_MAX = 5000;
  	
  	/**
  	 * 登录类型key
  	 */
  	public static final String LOGIN_TYPE = "loginType";

  	/**
  	 * 手机验证码登录
  	 */
  	public static final String LOGIN_TYPE_PHONE_CODE = "LTPC";
  	/**
  	 * session中的key,手机验证码
  	 */
  	public static final String SESSION_PHONE_CODE_KEY = "mobilePhoneCode";
  	/**
  	 * session中的key,微信小程序手机验证码
  	 */
  	public static final String WX_SESSION_PHONE_CODE_KEY = "wxmobilePhoneCode";
  	/**
  	 * session中的key,获取key手机验证码
  	 */
  	public static final String SESSION_GETKEY_CODE_KEY = "getKetCode";
  	
  	/**
  	 * session中的key,微信网页授权，获取用户openid用
  	 */
  	public static final String SESSION_WEIXIN_SIGN = "WEIXIN_SIGN";
  	/**
  	 * 用户数据
  	 */
  	public static final String SESSION_USER = "SESSION_USER";
  	
  	/**
  	 * 用户未找到(未绑定微信号)
  	 */
  	public static final String SECURITY_NOT_FOND_USER = "NOT_FOND_USER";
  	

	/**
	 * 必须填写用户名和密码
	 */
	public static final String SECURITY_MUST_USERNAME_AND_PW = "必须填写用户名和密码";
	/**
     * 默认当前页码
     */
	public static final int DEFAULT_CURRENT_PAGE = 0;

    /**
     * 默认分页大小
     */
	public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 最大分页大小100
     */
	public static final int DEFAULT_MAX_PAGE_SIZE = 100;
	
	/**
	 * 商户系统管理员角色id
	 */
	public static final String ROLE_SH_SYSTEM_MANAGER_ID = "1";
	/**
	 * 商户系统普通用户角色id
	 */
	public static final String ROLE_SH__AVERAGEUSER_ID = "2";
	
  	
  	
}
