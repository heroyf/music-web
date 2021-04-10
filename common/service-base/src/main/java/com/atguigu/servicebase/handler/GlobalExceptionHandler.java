package com.atguigu.servicebase.handler;

import com.atguigu.CommonUtils.R;
import com.atguigu.servicebase.customException.GuliException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 指定出现什么异常会执行这个handler
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R errorHandler(Exception e) {
        e.printStackTrace();
        return R.error().message("全局异常处理..");
    }

    // 指定出现什么异常会执行这个handler
    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R errorHandler(GuliException e) {
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
