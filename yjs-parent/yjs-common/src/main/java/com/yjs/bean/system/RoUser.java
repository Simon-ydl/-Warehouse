package com.yjs.bean.system;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "ro_user")
@Getter
@Setter
public class RoUser {

    @Id
    private Integer id;
    private String account;
    private String userName;
    private String userTel;
    private String userOrg;
    private String userRole;
    private String dtId;
    private String dtName;
    private String ckId;
    private String ckName;
    protected String creator;
    protected Object creatorId;
    private Date createDate;
    protected Boolean deleted;


}
