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
    public RestResult getMovie(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) {
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
        return new RestResult(movieService.getMovieByStatus("on"));
    }

    @RequestMapping(path = "/coming_soon", method = RequestMethod.GET)
    public RestResult getComingSoon(HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        return new RestResult(movieService.getMovieByStatus("soon"));
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
