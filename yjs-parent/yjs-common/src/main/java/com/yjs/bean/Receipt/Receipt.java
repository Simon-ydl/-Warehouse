package com.yjs.bean.Receipt;

import lombok.Data;

@Data
public class Receipt {
    /**
     * id
     *
     */
    private int id;

    /**
     * 收件人姓名
     *
     */
    private String rName;

    /**
     * 收件方式
     *
     */
    private String rWay;

    /**
     * 联系方式
     */
    private String rMobile;

    /**
     *所属区域
     *
     */
    private String rArea;

    /**
     * 详细地址
     *
     */
    private String rAddress;

    /**
     * 取件大厅
     *
     */
    private String rHall;

    /**
     * 业务编号
     *
     */
    private String businessCode;
}
