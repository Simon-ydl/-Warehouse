package com.yjs.bean.dto;

import lombok.Data;

@Data
public class UpdateStatusData {
    private String itemId;//要更新事项id
    private String msg;//更新事项的信息
    private int code;
}
