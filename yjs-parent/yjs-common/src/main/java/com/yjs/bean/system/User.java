package com.yjs.bean.system;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.yjs.bean.Ids;
import org.apache.commons.codec.binary.Base64;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User extends Ids {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Id
		protected Object id;
		//账号
	    private String account;
	    //应用名称
	    private String name;
	    //申请时间
	    private Date applyDate;
	    //密码
	    private String password;
	    //访问接口时携带token
	    private String token;
	    //联系电话
	    private String phone;
	    //类型
	    private String type;
	    //申请人
	    private String applicant;
	    //营业执照
	    private byte[] businessLicense;
	    @Transient
	  	private String businessLicenseBase64;
	    //应用图标
	    private byte[] applicationIcon;
	    @Transient
	  	private String applicationIconBase64;
	    //状态
	    private Integer status;
	    //是否启用
	    private Boolean isEnable;
	    //是否开启印章审核
	    private Boolean isSealAudit;
	    //是否开启调用接口传签名值
	    private Boolean isNeedSignval;
	    
	    //公钥
	    private String publicKey;
	    //私钥
	    private String privateKey;
	    
	    
	    //角色ids(设置角色用)
	  	@Transient
	  	private String rids;
	    //服务接口授权用到
		@Transient
		private Boolean isAuthorize;
	  	
		
		public byte[] getBusinessLicense() {
			return businessLicense;
		}
		public void setBusinessLicense(byte[] businessLicense) {
			this.businessLicense = businessLicense;
			if(null != businessLicense){
				this.businessLicenseBase64 = Base64.encodeBase64String(businessLicense);
			}
			
			
		}
		public byte[] getApplicationIcon() {
			return applicationIcon;
		}
		public void setApplicationIcon(byte[] applicationIcon) {
			this.applicationIcon = applicationIcon;
			if(null != applicationIcon){
				this.applicationIconBase64 = Base64.encodeBase64String(applicationIcon);
			}
		}
		
}
