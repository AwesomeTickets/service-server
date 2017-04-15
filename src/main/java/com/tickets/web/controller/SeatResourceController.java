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
 * RESTFul API of seat resources.
 */
@RestController
@RequestMapping("/resource/seat")
public class SeatResourceController {

    private static final Logger LOG = LoggerFactory.getLogger(SeatResourceController.class);

    @RequestMapping(path = "/unavailable", method = RequestMethod.GET)
    public RestResponse getSeat(
        @RequestParam(value="movieOnShowID",defaultValue="1") int movieOnShowID,
        HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        LOG.info("movieOnShowID "+movieOnShowID);
		ArrayList<Integer> data1 = new ArrayList<Integer>();
		data1.add(4);
		data1.add(1);
		ArrayList<Integer> data2 = new ArrayList<Integer>();
		data2.add(4);
		data2.add(2);
		ArrayList<Integer> data3 = new ArrayList<Integer>();
		data3.add(4);
		data3.add(3);
        List<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
        list.add(data1);
        list.add(data2);
        list.add(data3);
        return new CollectionResponse(list);
    }
}