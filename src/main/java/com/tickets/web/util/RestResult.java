package com.tickets.web.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Resource result of RESTFul web service.
 */
public class RestResult extends LinkedHashMap<String, Object> {

    public RestResult(Map<? extends String, ?> m) {
        super(m);
    }

    public RestResult() {
        super();
    }
}
