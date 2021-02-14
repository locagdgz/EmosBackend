package com.emos.wx.exception;

import lombok.Data;

@Data
public class EmosException extends RuntimeException {

    private String msg;
    private int code = 500;

    public EmosException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public EmosException(String msg, Throwable t) {
        super(msg, t);
        this.msg = msg;
    }

    public EmosException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public EmosException(String msg, int code, Throwable t) {
        super(msg, t);
        this.msg = msg;
        this.code = code;
    }
}
