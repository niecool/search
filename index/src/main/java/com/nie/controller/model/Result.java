package com.nie.controller.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaochengye
 * @date 2019-05-06 17:40
 */
public class Result<D> implements Serializable {
    private static final long serialVersionUID = -8064087525287821119L;
    private boolean success;

    private int code;

    private String msg;

    private D data;

    private List<String> segments;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    public List<String> getSegments() {
        return segments;
    }

    public void setSegments(List<String> segments) {
        this.segments = segments;
    }
}
