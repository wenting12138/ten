package com.wen.common.handler;

import com.wen.common.exception.TenException;
import com.wen.common.result.Result;
import com.wen.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class TenExceptionHandler {

    @ExceptionHandler(TenException.class)
    @ResponseBody
    public Result handler(TenException e){
        log.error("出现异常, e: {}", e);
        Result result = new Result();
        result.setMessage(e.getMessage());
        result.setCode(e.getCode());
        result.setFlag(false);
        return result;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handlerException(Exception e){
        log.error("出现异常, e: {}", e);
        Result result = new Result();
        result.setMessage(ResultCode.FAIL.getMessage());
        result.setCode(ResultCode.FAIL.getCode());
        result.setFlag(false);
        return result;
    }
}
