package com.awesome_tickets.web.controller;

import com.awesome_tickets.business.entities.MovieOnShow;
import com.awesome_tickets.business.services.MovieOnShowService;
import com.awesome_tickets.web.controller.response.RestResponse;
import com.awesome_tickets.util.DateUtil;
import com.awesome_tickets.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.Time;
import java.util.*;

import com.awesome_tickets.web.controller.response.CollectionResponse;
import com.awesome_tickets.web.controller.response.ErrorResponse;


/**
 * RESTFul API of on-show movie resources.
 */
@RestController
@RequestMapping("/resource/movie-on-show")
public class MovieOnShowController {

    @Autowired
    private MovieOnShowService movieOnShowService;

    private static final Logger LOG = LoggerFactory.getLogger(MovieOnShowController.class);

    @RequestMapping(path = "",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse getMovieOnShowByDetails(
        @RequestParam("movieId") Integer movieId,
        @RequestParam("cinemaHallId") Integer cinemaHallId,
        @RequestParam("showDate") Date showDate,
        @RequestParam("showTime") Time showTime,
        HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(LOG, request);
        RestResponse result = new RestResponse();
        MovieOnShow movieOnShow = movieOnShowService.getMovieOnShow(movieId, cinemaHallId, showDate, showTime);
        if (movieOnShow == null) {
            response.setStatus(404);
            return new ErrorResponse("Resource not found");
        }
        result.put("movieOnShowId", movieOnShow.getMovieOnShowID());
        result.put("movieId", movieOnShow.getMovie().getMovieID());
        result.put("cinemaHallId", movieOnShow.getCinemaHall().getCinemaHallID());
        result.put("lang", movieOnShow.getLang());
        result.put("showDate", movieOnShow.getShowDate().toString());
        result.put("showTime", movieOnShow.getShowTime().toString());
        result.put("price", movieOnShow.getPrice());
        response.setStatus(200);
        return result;
    }

    @RequestMapping(path = "/{movieOnShowId}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse getMovieOnShowByID(@PathVariable Integer movieOnShowId,
                                           HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(LOG, request);
        RestResponse result = new RestResponse();
        MovieOnShow movieOnShow = movieOnShowService.getMovieOnShow(movieOnShowId);
        if (movieOnShow == null) {
            response.setStatus(404);
            return new ErrorResponse("Resource not found");
        }
        result.put("movieOnShowId", movieOnShow.getMovieOnShowID());
        result.put("movieId", movieOnShow.getMovie().getMovieID());
        result.put("cinemaHallId", movieOnShow.getCinemaHall().getCinemaHallID());
        result.put("lang", movieOnShow.getLang());
        result.put("showDate", movieOnShow.getShowDate().toString());
        result.put("showTime", movieOnShow.getShowTime().toString());
        result.put("price", movieOnShow.getPrice());
        response.setStatus(200);
        return result;
    }

    @RequestMapping(path = "/recent",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse getRecentMovieOnShow(@RequestParam("movieId") Integer movieId,
                                             HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(LOG, request);
        final int range = 3;
        // Date date = Calendar.getInstance().getTime();
        Date date = Date.valueOf("2017-05-01");
        List<Date> dates = new ArrayList<Date>();
        for (int i = 0; i < range; i++) {
            dates.add(date);
            date = DateUtil.getNextDate(date);
        }
        Map<Date, List<Integer>> resultMap = movieOnShowService.getCinemaByDates(movieId, dates);
        List<LinkedHashMap<String, Object>> dataList = new LinkedList<LinkedHashMap<String, Object>>();
        for (int i = 0; i < dates.size(); i++) {
            if (resultMap.get(dates.get(i)).size() != 0) {
                RestResponse data = new RestResponse();
                data.put("showDate", dates.get(i).toString());
                data.put("cinemaId", resultMap.get(dates.get(i)));
                dataList.add(data);
            }
        }
        CollectionResponse result = new CollectionResponse(dataList);
        response.setStatus(200);
        return result;
    }

    @RequestMapping(path = "/day",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse getMovieOnShowByDay(
        @RequestParam("movieId") Integer movieId,
        @RequestParam("cinemaId") Integer cinemaId,
        @RequestParam("showDate") Date showDate,
        HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(LOG, request);
        List<Integer> idsList = movieOnShowService.getMovieOnShowByDate(movieId, showDate, cinemaId);
        CollectionResponse result = new CollectionResponse(idsList);
        response.setStatus(200);
        return result;
    }

    @RequestMapping(path = "/day/brief",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse getBriefMovieOnShowByDay(
        @RequestParam("movieId") Integer movieId,
        @RequestParam("cinemaId") Integer cinemaId,
        @RequestParam("showDate") Date showDate,
        HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(LOG, request);
        RestResponse result = new RestResponse();
        Object[] objs = movieOnShowService.getBriefMovieOnShowByDate(movieId, showDate, cinemaId);
        List<String> timeList = (List<String>)objs[1];
        Float minPrice = (Float)objs[0];
        if (timeList.size() == 0) minPrice = 0.00F;
        result.put("minPrice", minPrice);
        result.put("showTime", timeList);
        response.setStatus(200);
        return result;
    }
}
