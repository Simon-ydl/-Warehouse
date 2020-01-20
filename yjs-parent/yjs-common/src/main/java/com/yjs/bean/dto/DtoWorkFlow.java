package com.yjs.bean.dto;

import lombok.Data;

@Data
public class DtoWorkFlow {
    private String departmentName;
    private String actName; //流程名称
    private Integer order;
    private Integer departmentId;
    private String approveStep;
}
