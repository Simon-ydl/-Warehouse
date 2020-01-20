package com.yjs.bean.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "ro_business")
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "useridcode")
    private String userIdCode;
    @Column(name = "business_name")
    private String businessName;
    @Column(name = "business_code")
    private String businessCode ;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_at")
    private Date createAt;
    @Column(name = "matter_id")
    private Integer matterId;
    @Column(name = "state")
    private String state;
    @Column(name = "business_tag")
    private  String businessTag;

    public void setCreate() {
        long d=System.currentTimeMillis()+30*60*1000;
        Date date=new Date(d);
        createAt=date;
    }

}
