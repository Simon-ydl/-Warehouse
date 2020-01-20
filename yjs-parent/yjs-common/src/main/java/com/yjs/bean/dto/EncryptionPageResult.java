package com.yjs.bean.dto;

import lombok.Data;

/**
 * 加密后的返回实体类
 */
@Data
public class EncryptionPageResult {
    private int code;//1(成功)/0(失败)
    private String data;//返回调用方的数据
    private String msg;//调用成功或失败的提示信息
}
