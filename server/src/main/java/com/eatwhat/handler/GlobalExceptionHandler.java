package com.eatwhat.handler;

import com.eatwhat.constant.MessageConstant;
import com.eatwhat.exception.BaseException;
import com.eatwhat.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 业务异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex) {
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * SQL 异常
     */
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException e) {
        String message = e.getMessage();
        if (message.contains("Duplicate entry")) {
            String[] s = message.split(" ");
            String msg = s[2] + " " + MessageConstant.ALREADY_EXIST;
            return Result.error(msg);
        } else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }
}