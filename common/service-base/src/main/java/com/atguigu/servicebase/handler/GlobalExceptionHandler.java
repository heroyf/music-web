package com.atguigu.servicebase.handler;

import com.atguigu.CommonUtils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 制定出现什么异常会执行这个handler
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R errorHandler(Exception e) {
        e.printStackTrace();
        return R.error().message("全局异常处理..");
    }
}
