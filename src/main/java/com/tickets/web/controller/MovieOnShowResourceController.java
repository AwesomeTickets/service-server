package com.tickets.web.controller;

import com.tickets.business.entities.MovieOnShow;
import com.tickets.business.services.MovieOnShowService;
import com.tickets.web.controller.response.RestResponse;
import com.tickets.web.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.Time;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tickets.web.controller.response.CollectionResponse;
import com.tickets.web.controller.response.ErrorResponse;
import com.tickets.web.controller.response.RestResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * RESTFul API of on-show movie resources.
 */
@RestController
@RequestMapping("/resource/movie_on_show")
public class MovieOnShowResourceController {

    @Autowired
    private MovieOnShowService movieOnShowService;

    private static final Logger LOG = LoggerFactory.getLogger(MovieOnShowResourceController.class);

    @RequestMapping(path = "", method = RequestMethod.GET)
    public RestResponse getMovieOnShow(
        @RequestParam("movieID") Integer movieID,
        @RequestParam("cinemaHallID") Integer cinemaHallID,
        @RequestParam("showDate") Date showDate,
        @RequestParam("showTime") Time showTime,
        HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        RestResponse result = new RestResponse();
        MovieOnShow movieOnShow = movieOnShowService.getMovieOnShow(movieID, cinemaHallID, showDate, showTime);

        if (movieOnShow == null) {
            response.setStatus(404);
            return new ErrorResponse("Resource not found");
        }

        result.put("movieOnShowID", movieOnShow.getMovieOnShowID());
        result.put("movieID", movieOnShow.getMovie().getMovieID());
        result.put("cinemaHallID", movieOnShow.getCinemaHall().getCinemaHallID());
        result.put("lang", movieOnShow.getLang());
        result.put("showDate", movieOnShow.getShowDate().toString());
        result.put("showTime", movieOnShow.getShowTime().toString());
        result.put("price", movieOnShow.getPrice());

        response.setStatus(200);
        return result;
    }

    @RequestMapping(path = "/{movieOnShowID}", method = RequestMethod.GET)
    public RestResponse getCinema(@PathVariable Integer movieOnShowID,
                                  HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        RestResponse result = new RestResponse();
        MovieOnShow movieOnShow = movieOnShowService.getMovieOnShow(movieOnShowID);

        if (movieOnShow == null) {
            response.setStatus(404);
            return new ErrorResponse("Resource not found");
        }

        result.put("movieOnShowID", movieOnShow.getMovieOnShowID());
        result.put("movieID", movieOnShow.getMovie().getMovieID());
        result.put("cinemaHallID", movieOnShow.getCinemaHall().getCinemaHallID());
        result.put("lang", movieOnShow.getLang());
        result.put("showDate", movieOnShow.getShowDate().toString());
        result.put("showTime", movieOnShow.getShowTime().toString());
        result.put("price", movieOnShow.getPrice());

        response.setStatus(200);
        return result;
    }

    @RequestMapping(path = "/recent", method = RequestMethod.GET)
    public CollectionResponse getRecent(@RequestParam("movieID") Integer movieID,
                                  HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        int range = 3;

        List<RestResponse> dataList = new LinkedList<RestResponse>();

        // TODO Query only one time to get the results

        // Date date = Calendar.getInstance().getTime();
        Date date = Date.valueOf("2017-04-04");
        for (int i = 0; i < range; i++) {
            List<Integer> idList = movieOnShowService.getCinemaIDsShowAtDate(movieID, date);
            if (idList.size() == 0) continue;

            RestResponse data = new RestResponse();
            data.put("date", date.toString());
            data.put("cinemaID", idList);

            dataList.add(data);
            date = DateUtil.getNextDate(date);
        }

        CollectionResponse result = new CollectionResponse(dataList);
        response.setStatus(200);
        return result;
    }

    @RequestMapping(path = "/day", method = RequestMethod.GET)
    public CollectionResponse getMovieOnShowDay(
        @RequestParam("movieID") Integer movieID,
        @RequestParam("cinemaID") Integer cinemaID,
        @RequestParam("date") Date date,
        HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());

        // TODO Construct MovieOnShow only with 'movieOnShowID' attribute

        List<MovieOnShow> showList = movieOnShowService.getShowsADay(movieID, date, cinemaID);
        List<Integer> idsList = new LinkedList<Integer>();

        for (MovieOnShow show : showList) {
            idsList.add(show.getMovieOnShowID());
        }

        CollectionResponse result = new CollectionResponse(idsList);
        response.setStatus(200);

        return result;
    }

    @RequestMapping(path = "/day/brief", method = RequestMethod.GET)
    public RestResponse getMovieOnShowDayBrief(
        @RequestParam("movieID") Integer movieID,
        @RequestParam("cinemaID") Integer cinemaID,
        @RequestParam("date") Date date,
        HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        RestResponse result = new RestResponse();

        // TODO Construct MovieOnShow only with 'movieOnShowID' attribute
        // TODO Using MIN() of SQL to get the minimum price

        List<MovieOnShow> showList = movieOnShowService.getShowsADay(movieID, date, cinemaID);

        List<String> timeList = new LinkedList<String>();
        float minPrice = Float.MAX_VALUE;

        for (MovieOnShow show : showList) {
            if (show.getPrice() < minPrice) minPrice = show.getPrice();
            timeList.add(show.getShowTime().toString());
        }

        if (timeList.size() == 0) minPrice = 0.00F;
        result.put("minPrice", minPrice);
        result.put("showTime", timeList);

        response.setStatus(200);
        return result;
    }
}
