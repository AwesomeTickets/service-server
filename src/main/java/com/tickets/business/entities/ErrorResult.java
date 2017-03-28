package com.tickets.business.entities;

/**
 * Error result of RESTFul web service.
 */
public class ErrorResult extends RestResult {
    private static final String KEY_MSG = "msg";

    public ErrorResult() {
        this("");
    }

    public ErrorResult(String msg) {
        super();
        setMsg(msg);
    }

    public void setMsg(String msg) {
        put(KEY_MSG, msg);
    }

    public String getMsg() {
        return (String) get(KEY_MSG);
    }
}
