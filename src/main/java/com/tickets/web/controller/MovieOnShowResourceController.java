package com.tickets.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private static final Logger LOG = LoggerFactory.getLogger(MovieOnShowResourceController.class);

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public RestResponse getMovieOnShow(@RequestParam(value="movieID",defaultValue="1") int movieID,@RequestParam(value="cinemaHallID",defaultValue="333") int cinemaHallID,
        @RequestParam(value="showDate",defaultValue="2017-04-04") String showDate,
        @RequestParam(value="showTime",defaultValue="12:35:00") String showTime,
        HttpServletRequest request, HttpServletResponse response) {
        // TODO 获取 GET 参数 
        // DONE
        LOG.info("movieID "+movieID+" cinemaHallID "+cinemaHallID+" showDate "+showDate+" showTime "+showTime);

        LOG.info(request.getMethod() + " " + request.getRequestURI());
        RestResponse res = new RestResponse();
        res.put("movieOnShowID", 222);
        res.put("movieID", 444);
        res.put("cinemaHallID", 333);
        res.put("lang", "国语");
        res.put("showDate", "2017-04-04");
        res.put("showTime", "12:35:00");
        res.put("price", 35.0);
        return res;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public RestResponse getMovieOnShowWithId(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        RestResponse res = new RestResponse();
        res.put("movieOnShowID", 222);
        res.put("movieID", 444);
        res.put("cinemaHallID", 333);
        res.put("lang", "国语");
        res.put("showDate", "2017-04-04");
        res.put("showTime", "12:35:00");
        res.put("price", 35.0);
        return res;
    }

    @RequestMapping(path = "/recent", method = RequestMethod.GET)
    public RestResponse getRecentMovie(@RequestParam(value="movieID",defaultValue="1") int movieID, HttpServletRequest request, HttpServletResponse response) {
        // TODO 获取 GET 参数
        // TODO 使用 CollectionResponse 返回
        // DONE

        LOG.info("movieID "+movieID);

        LOG.info(request.getMethod() + " " + request.getRequestURI());
        RestResponse res = new RestResponse();

		List<LinkedHashMap<String, Object>> subjects = new ArrayList<LinkedHashMap<String, Object>>();
        LinkedHashMap<String, Object> movie1 = new LinkedHashMap<String, Object>();
        List<Integer> list1 = new ArrayList<Integer>();
        list1.add(111);
        list1.add(222);
        list1.add(333);
        movie1.put("date", "2017-04-04");
        movie1.put("cinemaID", list1);
        subjects.add(movie1);

        LinkedHashMap<String, Object> movie2 = new LinkedHashMap<String, Object>();
        List<Integer> list2 = new ArrayList<Integer>();
        list2.add(444);
        list2.add(555);
        list2.add(666);
        movie2.put("date", "2017-04-05");
        movie2.put("cinemaID", list2);
        subjects.add(movie2);

        return new CollectionResponse(subjects);
    }

    @RequestMapping(path = "/day", method = RequestMethod.GET)
    public RestResponse getMovieDay(@RequestParam(value="date",defaultValue="2017-04-05") String date, 
        @RequestParam(value="cinemaID",defaultValue="1") int cinemaID,
        @RequestParam(value="movieID",defaultValue="1") int movieID,
        HttpServletRequest request, HttpServletResponse response) {
        // TODO 获取 GET 参数
        // TODO 使用 CollectionResponse 返回
        // DONE
        LOG.info("data "+date+" cinemaID "+cinemaID+" movieID "+movieID);
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        RestResponse res = new RestResponse();
	
        List<Integer> list1 = new ArrayList<Integer>();
        list1.add(111);
        list1.add(222);
        list1.add(333);

        return new CollectionResponse(list1);
    }

    @RequestMapping(path = "/day/brief", method = RequestMethod.GET)
    public RestResponse getMovieBrief(@RequestParam(value="date",defaultValue="2017-04-05") String date, 
        @RequestParam(value="cinemaID",defaultValue="1") int cinemaID,
        @RequestParam(value="movieID",defaultValue="1") int movieID,
        HttpServletRequest request, HttpServletResponse response) {
        // TODO 获取 GET 参数
        // DONE
        LOG.info("data "+date+" cinemaID "+cinemaID+" movieID "+movieID);
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        RestResponse res = new RestResponse();
	
        List<String> list1 = new ArrayList<String>();
        list1.add("14:55:00");
        list1.add("18:20:00");
        list1.add("21:25:00");

        res.put("min_price", 38.0);
        res.put("time", list1);

        return res;
    }
}
