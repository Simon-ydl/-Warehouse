package com.yjs.bean.dto;

import lombok.Data;

import java.util.List;

@Data
public class ItemStatusData {
    private String departmentName ;//处理的部门名称
    private String departmentId ;//处理的部门的id
    private String departApprover ;//部门处理人
    private String itemDealTime ;//事项处理时间,默认为空串
    private String itemResult;//事项的处理结果默认为空串
    private String approveOpinion;//事项的审批意见
    private List<DtoApproveFile> materials;//事项的提交的材料
}
