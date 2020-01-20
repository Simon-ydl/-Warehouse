package com.yjs.bean.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ApproveData {
    private String approver;
    private String approveOpinion;
    private Date createTime;
    private Integer departmentId;
    private String departmentName;
    private String state;
    private Integer enclosureId;
}
