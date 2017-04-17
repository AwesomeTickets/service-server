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

import com.tickets.business.entities.Cinema;
import com.tickets.business.services.CinemaService;

/**
 * RESTFul API of cinema resources.
 */
@RestController
@RequestMapping("/resource/cinema")
public class CinemaResourceController {

    private static final Logger LOG = LoggerFactory.getLogger(CinemaResourceController.class);
    
    @Autowired
    private CinemaService cinemaService;

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public RestResponse getCinema(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        Cinema cinema = cinemaService.getCinema(id);
        if (cinema == null) {
            response.setStatus(404);
            return new ErrorResponse("Resource not found");
        }
        RestResponse res = new RestResponse();
        res.put("cinemaID", id);
        res.put("name", cinema.getName());
        res.put("location", cinema.getLocation());
        return res;
    }

}