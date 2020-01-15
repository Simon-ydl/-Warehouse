package com.tensquare.base.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理类
 */
@ControllerAdvice
public class BaseExceptionHandler {


    /**
     * 定义异常处理方法
     */
    @ExceptionHandler(value = Exception.class )  //  @ExceptionHandler:定义需要捕获哪种异常
    @ResponseBody  // 这里必须加上@ResponseBody
    public Result handlerError(Exception e){
        return new Result(false, StatusCode.ERROR,"出现异常："+e.getMessage());
    }



}
