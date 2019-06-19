package com.beemall.manager.exception;

import com.beemall.entity.ResponseData;
import com.beemall.entity.ResponseDataUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author ：bee
 * @date ：Created in 2019/6/19 15:52
 * @description：统一异常处理
 * @modified By：
 */
@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseData handleException(Exception e){
        e.printStackTrace();
        return ResponseDataUtil.buildError();
    }
}
