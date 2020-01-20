package com.yjs.bean.siteReceipt;

import lombok.Data;

import javax.persistence.*;

@Table(name = "ro_site_receipt")
@Data
public class SiteReceipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;//序列号
    private String businessCode;//业务信息
    private String itemId;//材料对应的事项id信息
    private String materialName;//材料名称
    private String materialId;//材料id信息
    private String num;//收件份数


}
