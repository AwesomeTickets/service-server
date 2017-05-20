package com.awesometickets.web.controller;

import com.awesometickets.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
public class ViewController {

    private static final Logger Log = LoggerFactory.getLogger(ViewController.class);
    
    public ViewController() {
        super();
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(Log, request);
        return "Welcome to AwesomeTickets";
    }
}
