package com.tickets.web.controller;

import com.tickets.business.entities.Cinema;
import com.tickets.business.services.CinemaService;
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
 * RESTFul API of cinema resources.
 */
@RestController
@RequestMapping("/resource/cinema")
public class CinemaResourceController {

    @Autowired
    private CinemaService cinemaService;

    private static final Logger LOG = LoggerFactory.getLogger(CinemaResourceController.class);

    @RequestMapping(path = "/{cinemaID}", method = RequestMethod.GET)
    public RestResponse getCinema(@PathVariable Integer cinemaID, HttpServletRequest request, HttpServletResponse response) {
        RestResponse result = new RestResponse();
        Cinema cinema = cinemaService.getCinema(cinemaID);

        if (cinema == null) {
            response.setStatus(404);
            return null;
        }

        result.put("cinemaID", cinema.getCinemaID());
        result.put("name", cinema.getName());
        result.put("location", cinema.getLocation());

        response.setStatus(200);
        return result;
    }

}