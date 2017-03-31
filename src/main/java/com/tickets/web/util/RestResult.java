package com.tickets.web.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Resource result of RESTFul web service.
 */
public class RestResult extends LinkedHashMap<String, Object> {

    // Just use interfaces of LinkedHashMap
    public RestResult(Map<? extends String, ?> m) {
        super(m);
    }

    public RestResult() {
        super();
    }
}
