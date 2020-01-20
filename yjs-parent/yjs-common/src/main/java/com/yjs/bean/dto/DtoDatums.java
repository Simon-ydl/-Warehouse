package com.yjs.bean.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DtoDatums {
    private String applicationName;//申请人姓名
    private String itemName;//事项名称
    private Date createTime;//创建时间
    private String  businessCode;//业务流水号

}
