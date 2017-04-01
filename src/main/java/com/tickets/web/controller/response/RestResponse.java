package com.tickets.web.controller.response;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Response of RESTFul API.
 */
public class RestResponse extends LinkedHashMap<String, Object> {

    public RestResponse() {
        super();
    }

    public RestResponse(LinkedHashMap<String, Object> m) {
        super(m);
    }

}
