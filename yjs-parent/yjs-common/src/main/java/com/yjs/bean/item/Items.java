package com.yjs.bean.item;

import lombok.Data;

import javax.persistence.*;

//事项数据库表对应实体类
@Table(name = "ro_items")
@Data
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;//编号
    @Column(name = "item_name")
    private String itemName;//事项名称
    @Column(name = "department_id")
    private Integer departmentId;//主管部门
    @Column(name = "item_code")
    private String itemCode; //事项编号


}
