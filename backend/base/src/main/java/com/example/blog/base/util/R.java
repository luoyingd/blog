package com.example.blog.base.util;

import org.apache.http.HttpStatus;


public class R {
    private int code;
    private String msg;
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public R() {
        this.code = 200;
        this.msg = "success";
    }

    public static R ok() {
        return new R();
    }

//    public static R ok(String msg) {
//        R r = new R();
//        r.setMsg(msg);
//        return r;
//    }

    public static R ok(Object data) {
        R r = R.ok();
        r.setData(data);
        return r;
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static R okRpc() {
        R r = new R();
        r.setCode(1);
        return r;
    }

    public static R okRpc(Object data) {
        R r = new R();
        r.setCode(1);
        r.setData(data);
        return r;
    }

    public static R errorRpc(String msg) {
        return R.error(0, msg);
    }

    public static R error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static R error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Unknown Error");
    }
}