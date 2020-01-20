package com.yjs.bean.oracle;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "ora_approver")
public class OraApprover {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" )
    private Integer id;
    @Column(name = "approver")
    private String approver;
    @Column(name = "approveOpinion")
    private String approveOpinion;
    @Column(name = "department_id")
    private Integer departmentId;
    @Column(name = "item_id")
    private Integer itemId;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "business_code")
    private String businessCode;
    @Column(name = "lack_materials")
    private String lackMaterials;
    @Column(name = "approve_state")
    private String approveState;
    @Column(name = "enclosure_name")
    private String enclosureName;
    @Column(name = "enclosure_content")
    private String enclosureContent;
    @Column(name = "status")
    private String status;

}
