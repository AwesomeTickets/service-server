package com.tickets.web.controller;

import com.tickets.business.entities.CinemaHall;
import com.tickets.business.services.CinemaHallService;
import com.tickets.web.controller.response.ErrorResponse;
import com.tickets.web.controller.response.RestResponse;
import com.tickets.web.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * RESTFul API of cinema hall resources.
 */
@RestController
@RequestMapping("/resource/cinema_hall")
public class CinemaHallController {

    @Autowired
    private CinemaHallService cinemaHallService;

    private static final Logger LOG = LoggerFactory.getLogger(CinemaHallController.class);

    @RequestMapping(path = "/{cinemaHallID}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse getCinemaHallWithoutSeatByID(@PathVariable Integer cinemaHallID,
                                                     HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(LOG, request);
        RestResponse result = new RestResponse();

        // TODO Construct CinemaHall without 'seatLayout' attribute
        CinemaHall cinemaHall = cinemaHallService.getWithoutSeatLayout(cinemaHallID);

        if (cinemaHall == null) {
            response.setStatus(404);
            return new ErrorResponse("Resource not found");
        }

        result.put("cinemaHallID", cinemaHall.getCinemaHallID());
        result.put("cinemaID", cinemaHall.getCinema().getCinemaID());
        result.put("name", cinemaHall.getName());

        response.setStatus(200);
        return result;
    }

    @RequestMapping(path = "/{cinemaHallID}/seat_layout",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse getCinemaHallWithSeatByID(@PathVariable Integer cinemaHallID,
                                                  HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(LOG, request);
        RestResponse result = new RestResponse();

        // TODO Construct CinemaHall with only 'cinemaHallID' and 'seatLayout' attributes
        CinemaHall cinemaHall = cinemaHallService.getWithSeatLayout(cinemaHallID);

        if (cinemaHall == null) {
            response.setStatus(404);
            return new ErrorResponse("Resource not found");
        }

        result.put("cinemaHallID", cinemaHall.getCinemaHallID());
        result.put("seatLayout", cinemaHall.getSeatLayout());

        response.setStatus(200);
        return result;
    }

}
