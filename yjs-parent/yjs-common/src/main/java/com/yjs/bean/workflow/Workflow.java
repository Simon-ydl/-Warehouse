package com.yjs.bean.workflow;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "ro_workflow")
public class Workflow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "department_id")
    private Integer departmentId;//部门id
    @Column(name = "workflow_order")
    private Integer workflowOrder;//工作流顺序
    @Column(name = "matter_id")
    private Integer matterId;//主题外键
    @Column(name = "act_name")
    private String actName;
    @Column(name = "approve_step")
    private String approveStep;
}
