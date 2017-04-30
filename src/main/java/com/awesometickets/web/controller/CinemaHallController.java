package com.awesometickets.web.controller;

import com.awesometickets.business.entities.CinemaHall;
import com.awesometickets.business.services.CinemaHallService;
import com.awesometickets.web.controller.response.ErrorResponse;
import com.awesometickets.web.controller.response.RestResponse;
import com.awesometickets.util.LogUtil;
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
@RequestMapping("/resource/cinema-hall")
public class CinemaHallController {

    private static final Logger LOG = LoggerFactory.getLogger(CinemaHallController.class);

    @Autowired
    private CinemaHallService cinemaHallService;

    public CinemaHallController() {
        super();
    }

    @RequestMapping(path = "/{cinemaHallId}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse getCinemaHallWithoutSeatByID(@PathVariable Integer cinemaHallId,
                                                     HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(LOG, request);
        RestResponse result = new RestResponse();
        CinemaHall cinemaHall = cinemaHallService.getWithoutSeatLayout(cinemaHallId);
        if (cinemaHall == null) {
            response.setStatus(404);
            return new ErrorResponse("Resource not found");
        }
        result.put("cinemaHallId", cinemaHall.getCinemaHallId());
        result.put("cinemaId", cinemaHall.getCinema().getCinemaId());
        result.put("hallName", cinemaHall.getHallName());
        response.setStatus(200);
        return result;
    }

    @RequestMapping(path = "/{cinemaHallId}/seat-layout",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse getCinemaHallWithSeatByID(@PathVariable Integer cinemaHallId,
                                                  HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(LOG, request);
        RestResponse result = new RestResponse();
        CinemaHall cinemaHall = cinemaHallService.getWithSeatLayout(cinemaHallId);
        if (cinemaHall == null) {
            response.setStatus(404);
            return new ErrorResponse("Resource not found");
        }
        result.put("cinemaHallId", cinemaHall.getCinemaHallId());
        result.put("seatLayout", cinemaHall.getSeatLayout());
        response.setStatus(200);
        return result;
    }

}
