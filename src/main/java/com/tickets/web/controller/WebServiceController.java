package com.tickets.web.controller;

import com.tickets.web.util.ErrorResult;
import com.tickets.web.util.RestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Controller of RESTFul web service.
 */
@RestController
@RequestMapping("/resource")
public class WebServiceController {
    private static final Logger LOG = LoggerFactory.getLogger(WebServiceController.class);

    @RequestMapping(path = "/testRest", method = RequestMethod.GET)
    public RestResult testRest(HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        response.setStatus(200);
        RestResult result = new RestResult();
        result.put("username:", "tickets");
        result.put("password:", "123456");
        ArrayList<Object> data = new ArrayList<Object>();
        data.add("string");
        data.add(666);
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("key", "value");
        data.add(map);
        result.put("list_of_data", data);
        return result;
    }

    @RequestMapping(path = "/testError", method = RequestMethod.GET)
    public RestResult testError(HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        response.setStatus(404);
        return new ErrorResult("Oops! An error occurs.");
    }

}