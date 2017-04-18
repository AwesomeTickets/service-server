package com.tickets.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tickets.web.controller.response.ErrorResponse;
import com.tickets.web.controller.response.RestResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tickets.business.entities.Cinema;
import com.tickets.business.services.CinemaService;


/**
 * RESTFul API of cinema resources.
 */
@RestController
@RequestMapping("/resource/cinema")
public class CinemaController {

    private static final Logger LOG = LoggerFactory.getLogger(CinemaController.class);

    @Autowired
    private CinemaService cinemaService;

    @RequestMapping(path = "/{cinemaID}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse getCinemaByID(@PathVariable Integer cinemaID,
                                      HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        Cinema cinema = cinemaService.getCinema(cinemaID);
        if (cinema == null) {
            response.setStatus(404);
            return new ErrorResponse("Resource not found");
        }
        RestResponse res = new RestResponse();
        res.put("cinemaID", cinemaID);
        res.put("name", cinema.getName());
        res.put("location", cinema.getLocation());
        return res;
    }

}