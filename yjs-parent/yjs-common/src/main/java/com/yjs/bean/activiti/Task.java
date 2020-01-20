package com.yjs.bean.activiti;

import lombok.Data;

import javax.persistence.*;

/**
 * @author summer
 */

@Table(name = "act_ru_task")
@Data
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_")
    private String id;

    @Column(name = "PROC_INST_ID_")
    private String procinstId;

    @Column(name = "ASSIGNEE_")
    private String assignee;

    @Column(name = "SUSPENSION_STATE_")
    private String suspensionState;

}
