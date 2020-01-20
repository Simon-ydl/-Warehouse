package com.yjs.bean.application;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "ro_applicant")
public class Applicat{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "ap_name")
    private String apName;
    @Column(name = "ap_numberType")
    private String apNumberType;
    @Column(name = "ap_number")
    private String apNumber;
    @Column(name = "business_code")
    private String businessCode;

}
