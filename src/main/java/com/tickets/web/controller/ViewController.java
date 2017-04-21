package com.tickets.web.controller;

import com.tickets.web.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Controller that returns views.
 */
@Controller
public class ViewController {

	private static final Logger LOG = LoggerFactory.getLogger(ViewController.class);
    
    public ViewController() {
        super();
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(LOG, request);
        return "index";
    }

    @RequestMapping(path = "/tickets", method = RequestMethod.GET)
    public String tickets(HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(LOG, request);
        return "tickets";
    }

}
