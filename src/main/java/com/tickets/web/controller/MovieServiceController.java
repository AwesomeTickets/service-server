package com.tickets.web.controller;


import com.tickets.business.services.MovieService;
import com.tickets.web.util.ErrorResult;
import com.tickets.web.util.RestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.ArrayList;


/**
 * Movie RESTFul web service controller.
 */
@RestController
@RequestMapping("/resource/movie")
public class MovieServiceController {
    private static final Logger LOG = LoggerFactory.getLogger(MovieServiceController.class);

    @Autowired
    private MovieService movieService;

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public RestResult getMovie(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        LinkedHashMap<String, Object> re = movieService.getMovie(id);
        if (re == null) {
            response.setStatus(404);
            return new ErrorResult("请求资源不存在");
        }
        return new RestResult(re);
    }

    @RequestMapping(path = "/on_show", method = RequestMethod.GET)
    public RestResult getOnShow(HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        //return new RestResult(movieService.getMovieByStatus("on"));
        RestResult result = new RestResult();
        // TODO GET /resource/movie/on_show
        result.put("count",9);
        int[] array = {1, 4, 5, 8, 9, 10, 11, 15, 16};
        result.put("movies", array);
        return result;
    }

    @RequestMapping(path = "/coming_soon", method = RequestMethod.GET)
    public RestResult getComingSoon(HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        //return new RestResult(movieService.getMovieByStatus("soon"));
        RestResult result = new RestResult();
        result.put("count",9);
        int[] array = {2, 3, 6, 7, 12, 13, 14, 17, 18};
        result.put("movies", array);
        return result;
    }

    @RequestMapping(path = "/popular", method = RequestMethod.GET)
    public RestResult getPopular(@RequestParam(value="count", defaultValue="3") int count,
                                 HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        RestResult result = new RestResult();
        result.put("count", count);
        // TODO GET /resource/movie/popular?count=XXX
        ArrayList<Object> data = new ArrayList<Object>();
        LinkedHashMap<String, Object> map1 = new LinkedHashMap<String, Object>();
        map1.put("id",1);
        map1.put("posterURL","http://120.25.76.106:8080/poster/large/1.png");
        data.add(map1);
        LinkedHashMap<String, Object> map2 = new LinkedHashMap<String, Object>();
        map2.put("id",2);
        map2.put("posterURL", "http://120.25.76.106:8080/poster/large/2.png");
        data.add(map2);
        LinkedHashMap<String, Object> map3 = new LinkedHashMap<String, Object>();
        map3.put("id",3);
        map3.put("posterURL", "http://120.25.76.106:8080/poster/large/3.png");
        data.add(map3);
        result.put("subjects",data);
        return result;
    }
}
