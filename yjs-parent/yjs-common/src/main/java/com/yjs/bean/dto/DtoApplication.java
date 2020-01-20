package com.yjs.bean.dto;

import lombok.Data;

/**
 * 返回数据中申请人对象实体类
 */
@Data
public class DtoApplication {
    private String applicationName;//申请人姓名
    private String applicationNummber;//申请人身份证号(在页面中字段名称为number)
}
