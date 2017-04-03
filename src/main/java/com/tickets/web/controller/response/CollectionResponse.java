package com.tickets.web.controller.response;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Collection response of RESTFul API.
 */
public class CollectionResponse<T> extends RestResponse {

    public CollectionResponse() {
        this(new ArrayList<T>());
    }

    public CollectionResponse(Collection<T> data) {
        put("count", data.size());
        put("data", data);
    }

}
