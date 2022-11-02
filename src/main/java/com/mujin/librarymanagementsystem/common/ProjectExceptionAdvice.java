package com.mujin.librarymanagementsystem.common;

import com.mujin.librarymanagementsystem.common.constant.Code;
import com.mujin.librarymanagementsystem.common.entity.Result;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@ResponseBody
public class ProjectExceptionAdvice {
    /**
     * 所有异常
     */
    @ExceptionHandler(Exception.class)
    public Result doException(Exception exception) {
        return new Result(Code.Exception_ERROR, null, exception.getMessage());
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public Result pageLost() {
        return new Result(Code.PAGE_LOST, null, "页面数据丢失,检查连接是否正常");
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result methodError() {
        return new Result(Code.METHOD_ERROR, null, "访问方法异常,请检查访问方法是否正确");
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, HttpMessageNotReadableException.class})
    public Result parameterException() {
        return new Result(Code.PARAMETER_EXCEPTION, null, "参数异常,检查参数传递是否正确或数据是否为空");
    }

    /**
     * 处理空指针的异常
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = NullPointerException.class)
    public Result nullPointerException() {
        return new Result(Code.Exception_ERROR, null, "空指针异常");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Result databaseException() {
        return new Result(Code.Exception_ERROR, null, "数据库异常,请检查数据是否存在或检查提交的数据类型是否正常");
    }
}