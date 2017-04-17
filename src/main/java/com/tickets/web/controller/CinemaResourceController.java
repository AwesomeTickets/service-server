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
 * RESTFul API of cinema resources.
 */
@RestController
@RequestMapping("/resource/cinema")
public class CinemaResourceController {

    private static final Logger LOG = LoggerFactory.getLogger(CinemaResourceController.class);

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public RestResponse getCinema(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        RestResponse res = new RestResponse();
        res.put("cinemaID", 3);
        res.put("name", "金逸珠江国际影城（大学城店）");
        res.put("location", "番禺区大学城XXX铺");
        return res;
    }
}