package com.awesometickets.web.controller.response;

import javax.servlet.http.HttpServletResponse;


/**
 * Error response of RESTFul API.
 */
public class ErrorResponse extends RestResponse {
    private static final String KEY_CODE = "code";
    private static final String KEY_INFO = "info";

    public ErrorResponse(HttpServletResponse res, int code) {
        super();
        setCode(code);
        setInfo(ErrorStatus.info(code));
        res.setStatus(ErrorStatus.status(code));
    }

    public void setCode(int code) {
        put(KEY_CODE, code);
    }

    public int getCode() {
        return (Integer) get(KEY_CODE);
    }

    public void setInfo(String info) {
        put(KEY_INFO, info);
    }

    public String getInfo() {
        return (String) get(KEY_INFO);
    }
}
