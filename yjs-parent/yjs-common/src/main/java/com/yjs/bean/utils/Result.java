package com.yjs.bean.utils;

import java.io.Serializable;
import java.util.List;

/**
 *
 * 设计前后端响应结果集
 *
 */
public class Result implements Serializable {

    //响应的状态码
    private Integer code;

    //响应是否成功
    private Boolean flag;

    //响应的信息
    private String message;

    //响应给前端的数据
    private Object data;


    public Result(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(Boolean flag, Integer code, String message, Object data) {
        this.code = code;
        this.flag = flag;
        this.message = message;
        this.data = data;
    }

    public Result(Boolean flag,Integer code,  String message) {
        this.code = code;
        this.flag = flag;
        this.message = message;
    }

    public Result() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
