package com.yjs.bean.approver;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Table(name = "ro_approver")
@Data
public class Approver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;//id
    @Column(name = "approver")
    private String approver;//审批人
    @Column(name = "approveOpinion")
    private String  approveOpinion;//审批意见
    @Column(name = "department_id")
    private Integer departmentId;//审批人所在部门id
    @Column(name = "item_id")
    private Integer itemId;//事项id
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;//创建时间
    @Column(name = "business_code")
    private String businessCode;//业务流水号
    @Column(name = "lack_materials")
    private String lackMaterials;//需要重新上传的材料id数组
    @Column(name = "enclosure_id")
    private Integer enclosureId;//附件id
    @Column(name = "approve_state")
    private String approveState;
}
