package com.tickets.web.controller;

import com.tickets.business.entities.CinemaHall;
import com.tickets.business.services.CinemaHallService;
import com.tickets.web.controller.response.ErrorResponse;
import com.tickets.web.controller.response.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CinemaHallResourceController {

    @Autowired
    private CinemaHallService cinemaHallService;

    private static final Logger LOG = LoggerFactory.getLogger(CinemaHallResourceController.class);

    @RequestMapping(path = "/{cinemaHallID}", method = RequestMethod.GET)
    public RestResponse getCinemaHall(@PathVariable Integer cinemaHallID,
                                      HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        RestResponse result = new RestResponse();

        // TODO Construct CinemaHall without 'seatLayout' attribute
        CinemaHall cinemaHall = cinemaHallService.getCinemaHall(cinemaHallID);

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

    @RequestMapping(path = "/{cinemaHallID}/seat_layout", method = RequestMethod.GET)
    public RestResponse getCinemaHallSeatLayout(@PathVariable Integer cinemaHallID,
                                                HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        RestResponse result = new RestResponse();

        // TODO Construct CinemaHall with only 'cinemaHallID' and 'seatLayout' attributes
        CinemaHall cinemaHall = cinemaHallService.getCinemaHall(cinemaHallID);

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
