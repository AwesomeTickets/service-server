package com.awesometickets.web.controller;

import com.awesometickets.web.controller.response.ErrorStatus;
import com.awesometickets.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.awesometickets.web.controller.response.ErrorResponse;
import com.awesometickets.web.controller.response.RestResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awesometickets.business.entities.Cinema;
import com.awesometickets.business.services.CinemaService;


/**
 * RESTFul API of cinema resources.
 */
@RestController
@RequestMapping("/resource/cinema")
public class CinemaController {

    private static final Logger LOG = LoggerFactory.getLogger(CinemaController.class);

    @Autowired
    private CinemaService cinemaService;

    public CinemaController() {
        super();
    }

    @RequestMapping(path = "/{cinemaId}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse getCinemaByID(@PathVariable Integer cinemaId,
                                      HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(LOG, request);
        Cinema cinema = cinemaService.getCinema(cinemaId);
        if (cinema == null) {
            return new ErrorResponse(response, ErrorStatus.RESOURCE_NOT_FOUND);
        }
        RestResponse res = new RestResponse();
        res.put("cinemaId", cinemaId);
        res.put("cinemaName", cinema.getCinemaName());
        res.put("cinemaAddr", cinema.getCinemaAddr());
        return res;
    }

}