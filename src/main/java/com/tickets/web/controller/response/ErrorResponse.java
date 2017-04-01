package com.tickets.web.controller.response;

/**
 * Error response of RESTFul API.
 */
public class ErrorResponse extends RestResponse {
    private static final String KEY_MSG = "msg";

    public ErrorResponse() {
        this("");
    }

    public ErrorResponse(String msg) {
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
