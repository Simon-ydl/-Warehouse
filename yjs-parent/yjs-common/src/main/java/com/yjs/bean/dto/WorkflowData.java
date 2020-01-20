package com.yjs.bean.dto;

import lombok.Data;

import java.util.List;

@Data
public class WorkflowData {
    private String businessCode;//业务编号
    private List<DtoWorkFlow> workFlows;//工作流的部门信息（departmentId,departmentName,部门的工作流的顺序）
}
