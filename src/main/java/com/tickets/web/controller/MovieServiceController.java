package com.tickets.web.controller;

import com.tickets.business.entities.Movie;
import com.tickets.business.entities.MovieStyle;
import com.tickets.business.services.MovieService;
import com.tickets.web.util.ErrorResult;
import com.tickets.web.util.RestResult;
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

        Movie movie = movieService.getMovie(id);
        if (movie == null) {
            response.setStatus(404);
            return new ErrorResult("请求资源不存在");
        }

        List<String> stylesStrings = new ArrayList<String>();
        for (MovieStyle ms : movie.getMovieStyleSet()) {
            stylesStrings.add(ms.getStyle());
        }

        RestResult re = new RestResult();
        re.put("id", movie.getMovieID());
        re.put("title", movie.getTitle());
        re.put("pubdate", movie.getPubdate());
        re.put("length", movie.getLength());
        re.put("rating", movie.getRating());
        re.put("posterSmall", movie.getPosterSmall());
        re.put("posterLarge", movie.getPosterLarge());
        re.put("country", movie.getCountry().getName());
        re.put("movieStatus", movie.getMovieStatus().getStatus());
        re.put("movieType", movie.getMovieType().getType());
        re.put("movieStyle", stylesStrings);
        return re;
    }

    @RequestMapping(path = "/on_show", method = RequestMethod.GET)
    public RestResult getOnShow(HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        List<Integer> list = movieService.getMovieByStatus("on");
        RestResult re = new RestResult();
        re.put("count", list.size());
        re.put("movies", list);
        return re;
    }

    @RequestMapping(path = "/coming_soon", method = RequestMethod.GET)
    public RestResult getComingSoon(HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        List<Integer> list = movieService.getMovieByStatus("soon");
        RestResult re = new RestResult();
        re.put("count", list.size());
        re.put("movies", list);
        return re;
    }

    @RequestMapping(path = "/popular", method = RequestMethod.GET)
    public RestResult getPopular(@RequestParam(value="count", defaultValue="3") int count,
                                 HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        // TODO GET /resource/movie/popular?count=XXX
        RestResult result = new RestResult();
        result.put("count", count);
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
        result.put("subjects",data);
        return result;
    }
}
