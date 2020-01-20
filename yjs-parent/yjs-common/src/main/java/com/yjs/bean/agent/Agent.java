package com.yjs.bean.agent;

import lombok.Data;

import javax.persistence.*;

/**
 * 经办人实体类
 */
@Table(name="ro_agent")
@Data
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;//经办人id
    @Column(name = "aName")
    private String aName;//经办人姓名
    @Column(name = "aNumber")
    private String aNumber;//经办人编号
    @Column(name = "aNumberType")
    private String aNumberType;//证件类型
    @Column(name = "aMobile")
    private String aMobile;//经办人电话
    @Column(name = "aEmail")
    private String aEmail;//经办人邮箱
    @Column(name = "business_code")
    private String businessCode;//业务编号
}
