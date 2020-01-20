package com.yjs.bean.page;

import lombok.Data;

/**
 * 申请表地址储存
 */
@Data
public class Page {

    private int id;

    /**
     * 表单名
     */
    private String pgName;
    /**
     * 表单
     */
    private String pgInfo;

    /**
     * 表单英文名
     */
//    private String tableName;

    /**
     * 主题id
     */
    private int matterId;

    private  String itemId;

    private  String paType;
}
