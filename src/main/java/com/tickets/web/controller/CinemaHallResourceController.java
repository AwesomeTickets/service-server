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


/**
 * RESTFul API of cinema hall resources.
 */
@RestController
@RequestMapping("/resource/cinema_hall")
public class CinemaHallResourceController {

    private static final Logger LOG = LoggerFactory.getLogger(CinemaResourceController.class);
    
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public RestResponse getCinemaHall(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        RestResponse res = new RestResponse();
        res.put("cinemaHallID", 12);
        res.put("cinemaID", 3);
        res.put("name", "2号厅");
        return res;
    }

    @RequestMapping(path = "/{id}/seat_layout", method = RequestMethod.GET)
    public RestResponse getSeatLayout(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        RestResponse res = new RestResponse();
        res.put("cinemaHallID", 11);
        res.put("seatLayout", "01110,01110,11111,11111,11111");
        return res;
    }
}
