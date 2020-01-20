package com.yjs.bean.department;

import lombok.Data;

import javax.persistence.*;

//部门表数据库表对应实体类
@Table(name = "ro_department")
@Data
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id ")
    private Integer id; //部门id
    @Column(name = "department_name")
    private String departmentName;//部门名称
}
