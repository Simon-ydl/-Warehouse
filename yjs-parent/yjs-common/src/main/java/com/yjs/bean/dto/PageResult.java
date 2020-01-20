package com.yjs.bean.dto;

import lombok.Data;

/**
 * 返回数据实体类
 */
@Data
public class PageResult<T> {
    private int code;//1(成功)/0(失败)
    private T data;//返回调用方的数据
    private String msg;//调用成功或失败的提示信息
}
