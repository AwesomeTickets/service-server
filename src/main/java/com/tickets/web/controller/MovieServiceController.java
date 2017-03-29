package com.tickets.web.controller;


import com.tickets.web.util.RestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Movie RESTFul web service controller.
 */
@RestController
@RequestMapping("/resource/movie")
public class MovieServiceController {
    private static final Logger LOG = LoggerFactory.getLogger(MovieServiceController.class);

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public RestResult getMovie(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        RestResult result = new RestResult();
        result.put("id", id);
        // TODO GET /resource/movie/:id
        return result;
    }

    @RequestMapping(path = "/on_show", method = RequestMethod.GET)
    public RestResult getOnShow(HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        RestResult result = new RestResult();
        // TODO GET /resource/movie/on_show
        return result;
    }

    @RequestMapping(path = "/coming_soon", method = RequestMethod.GET)
    public RestResult getComingSoon(HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        RestResult result = new RestResult();
        // TODO GET /resource/movie/coming_soon
        return result;
    }

    @RequestMapping(path = "/popular", method = RequestMethod.GET)
    public RestResult getPopular(@RequestParam(value="count", defaultValue="3") int count,
                                 HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        RestResult result = new RestResult();
        result.put("count", count);
        // TODO GET /resource/movie/popular?count=XXX
        return result;
    }
}
