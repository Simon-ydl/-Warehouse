package com.yjs.bean.Situation;

import lombok.Data;

/**
 * 情形表
 */
@Data
public class Situation {

    private int id;

    /**
     * 情形姓名
     */
    private String siName;

    /**
     * 情形内容
     */
    private String siContent;

    /**
     * 业务编号
     */
    private String businessCode;
}
