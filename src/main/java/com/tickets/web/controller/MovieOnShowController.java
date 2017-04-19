package com.tickets.web.controller;

import com.tickets.business.entities.MovieOnShow;
import com.tickets.business.services.MovieOnShowService;
import com.tickets.web.controller.response.RestResponse;
import com.tickets.web.util.DateUtil;
import com.tickets.web.util.LogUtil;
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

import com.tickets.web.controller.response.CollectionResponse;
import com.tickets.web.controller.response.ErrorResponse;


/**
 * RESTFul API of on-show movie resources.
 */
@RestController
@RequestMapping("/resource/movie_on_show")
public class MovieOnShowController {

    @Autowired
    private MovieOnShowService movieOnShowService;

    private static final Logger LOG = LoggerFactory.getLogger(MovieOnShowController.class);

    @RequestMapping(path = "",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse getMovieOnShowByDetails(
        @RequestParam("movieID") Integer movieID,
        @RequestParam("cinemaHallID") Integer cinemaHallID,
        @RequestParam("showDate") Date showDate,
        @RequestParam("showTime") Time showTime,
        HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(LOG, request);
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

    @RequestMapping(path = "/{movieOnShowID}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse getMovieOnShowByID(@PathVariable Integer movieOnShowID,
                                           HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(LOG, request);
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

    @RequestMapping(path = "/recent",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse getRecentMovieOnShow(@RequestParam("movieID") Integer movieID,
                                             HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(LOG, request);
        int range = 3;
        // Date date = Calendar.getInstance().getTime();
        Date date = Date.valueOf("2017-04-04");
        List<Date> dates = new ArrayList<Date>();
        for (int i = 0; i < range; i++) {
            dates.add(date);
            date = DateUtil.getNextDate(date);
        }
        Map<Date, List<Integer>> resultMap = movieOnShowService.getCinemaIDsShowAtDates(movieID, dates);
        List<LinkedHashMap<String, Object>> dataList = new LinkedList<LinkedHashMap<String, Object>>();
        for (int i = 0; i < dates.size(); i++) {
            if (resultMap.get(dates.get(i)).size() != 0) {
                RestResponse data = new RestResponse();
                data.put("date", dates.get(i).toString());
                data.put("cinemaID", resultMap.get(dates.get(i)));
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
        @RequestParam("movieID") Integer movieID,
        @RequestParam("cinemaID") Integer cinemaID,
        @RequestParam("date") Date date,
        HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(LOG, request);
        List<Integer> idsList = movieOnShowService.getShowsIDADay(movieID, date, cinemaID);
        CollectionResponse result = new CollectionResponse(idsList);
        response.setStatus(200);
        return result;
    }

    @RequestMapping(path = "/day/brief",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse getBriefMovieOnShowByDay(
        @RequestParam("movieID") Integer movieID,
        @RequestParam("cinemaID") Integer cinemaID,
        @RequestParam("date") Date date,
        HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(LOG, request);
        RestResponse result = new RestResponse();

        // TODO Construct MovieOnShow only with 'showTime' attribute
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
