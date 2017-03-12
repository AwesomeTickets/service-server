package com.tickets.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tickets.business.entities.User;
import com.tickets.business.services.UserService;


@Controller
public class MainController {

    @Autowired
    private UserService userService;
    
    
    public MainController() {
        super();
    }

  
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }
    
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(final User user, final Model model) {
    	if (userService.hasUsername(user.getUsername())) {
            model.addAttribute("msg", "Register failed. Username exists.");
    	} else {
            userService.create(user);
            model.addAttribute("msg", "Register " + user.toString() + " succeed.");
    	}
        return "response";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(final User user, final Model model) {
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
