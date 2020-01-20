package com.yjs.bean.materials;

import lombok.Data;

import javax.persistence.*;

@Table(name = "ro_materials")
@Data
public class Materials  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;//序列号
    private String businessCode;//业务信息
    private String material;//材料id，多个以,隔开
    private String materialInItem;//材料对应的事项id信息

}
