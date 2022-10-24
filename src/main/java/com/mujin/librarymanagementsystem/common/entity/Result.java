package com.mujin.librarymanagementsystem.common.entity;

public class Result {
    private Object data;
    private Integer code;
    private Object msg;

    public Result(Object data) {
        this.data = data;
    }

    public Result(Integer code, Object data) {
        this.data = data;
        this.code = code;
    }

    public Result(Integer code, Object data, Object msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public Result() {
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
