package com.tickets.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tickets.business.entities.User;
import com.tickets.business.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller that returns views.
 */
@Controller
public class ViewController {

	private static final Logger LOG = LoggerFactory.getLogger(ViewController.class);

    @Autowired
    private UserService userService;
    
    
    public ViewController() {
        super();
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        return "index";
    }

    @RequestMapping(path = "/tickets", method = RequestMethod.GET)
    public String tickets(HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        return "tickets";
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public String user(HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        return "user";
    }
    
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(final User user, final Model model, HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
    	if (userService.hasUsername(user.getUsername())) {
            model.addAttribute("msg", "Register failed. Username exists.");
    	} else {
            userService.create(user);
            model.addAttribute("msg", "Register " + user.toString() + " succeed.");
    	}
        return "response";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(final User user, final Model model, HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
    	if (!userService.hasUsername(user.getUsername())) {
            model.addAttribute("msg", "Login failed. Username doesn't exist.");
    	} else if (userService.permitLogin(user.getUsername(), user.getPassword())) {
            model.addAttribute("msg", "Login " + user.toString() + " succeed.");
    	} else {
    		model.addAttribute("msg", "Login failed. Incorrect password.");
    	}
        return "response";
    }
}
