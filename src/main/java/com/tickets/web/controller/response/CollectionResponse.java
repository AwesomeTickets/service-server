package com.tickets.web.controller.response;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Collection response of RESTFul API.
 */
public class CollectionResponse extends RestResponse {

    public <T> CollectionResponse() {
        this(new ArrayList<T>());
    }

    public <T> CollectionResponse(Collection<T> data) {
        put("count", data.size());
        put("data", data);
    }

}
