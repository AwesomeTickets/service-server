package com.tickets.web.controller;

import com.tickets.business.entities.Movie;
import com.tickets.business.entities.MovieStyle;
import com.tickets.business.services.MovieService;
import com.tickets.web.controller.response.ErrorResponse;
import com.tickets.web.controller.response.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * RESTFul API of movie resources.
 */
@RestController
@RequestMapping("/resource/movie")
public class MovieServiceController {

    private static final Logger LOG = LoggerFactory.getLogger(MovieServiceController.class);

    @Autowired
    private MovieService movieService;

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public RestResponse getMovie(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        Movie movie = movieService.getMovie(id);
        if (movie == null) {
            response.setStatus(404);
            return new ErrorResponse("Resource not found");
        }
        List<String> movieStyles = new ArrayList<String>();
        for (MovieStyle ms : movie.getMovieStyleSet()) {
            movieStyles.add(ms.getStyle());
        }
        RestResponse res = new RestResponse();
        res.put("id", movie.getMovieID());
        res.put("title", movie.getTitle());
        res.put("pubdate", movie.getPubdate());
        res.put("length", movie.getLength());
        res.put("rating", movie.getRating());
        res.put("country", movie.getCountry().getName());
        res.put("movieStatus", movie.getMovieStatus().getStatus());
        res.put("movieType", movie.getMovieType().getType());
        res.put("movieStyle", movieStyles);
        res.put("posterSmall", movie.getPosterSmall());
        res.put("posterLarge", movie.getPosterLarge());
        return res;
    }

    @RequestMapping(path = "/on_show", method = RequestMethod.GET)
    public RestResponse getOnShow(HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        List<Integer> movieIDs = movieService.getMovieByStatus("on");
        RestResponse res = new RestResponse();
        res.put("count", movieIDs.size());
        res.put("movies", movieIDs);
        return res;
    }

    @RequestMapping(path = "/coming_soon", method = RequestMethod.GET)
    public RestResponse getComingSoon(HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        List<Integer> movieIDs = movieService.getMovieByStatus("soon");
        RestResponse res = new RestResponse();
        res.put("count", movieIDs.size());
        res.put("movies", movieIDs);
        return res;
    }

    @RequestMapping(path = "/popular", method = RequestMethod.GET)
    public RestResponse getPopular(@RequestParam(value="count", defaultValue="3") int count,
                                   HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        // TODO GET /resource/movie/popular?count=XXX
        RestResponse res = new RestResponse();
        res.put("count", count);
        ArrayList<Object> data = new ArrayList<Object>();
        LinkedHashMap<String, Object> map1 = new LinkedHashMap<String, Object>();
        map1.put("id",1);
        map1.put("posterURL","https://raw.githubusercontent.com/AwesomeTickets/Dashboard/master/img/poster/large/1.png");
        data.add(map1);
        LinkedHashMap<String, Object> map2 = new LinkedHashMap<String, Object>();
        map2.put("id",2);
        map2.put("posterURL", "https://raw.githubusercontent.com/AwesomeTickets/Dashboard/master/img/poster/large/2.png");
        data.add(map2);
        LinkedHashMap<String, Object> map3 = new LinkedHashMap<String, Object>();
        map3.put("id",3);
        map3.put("posterURL", "https://raw.githubusercontent.com/AwesomeTickets/Dashboard/master/img/poster/large/3.png");
        data.add(map3);
        res.put("subjects",data);
        return res;
    }
}
