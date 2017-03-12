package com.tickets.web.controller;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tickets.business.entities.User;
import com.tickets.business.services.UserService;


@Controller
public class MainController {

    
    @Autowired
    private UserService userService;
    
    
    
    public MainController() {
        super();
    }

    
    @ModelAttribute("allSeedStarters")
    public List<User> populateUsers() {
        return this.userService.findAll();
    }
    
    
    @RequestMapping({"/"})
    public String showSeedstarters(final User seedStarter) {
        seedStarter.setDatePlanted(Calendar.getInstance().getTime());
        return "seedstartermng";
    }
    
    @RequestMapping(value="/seedstartermng", params={"save"})
    public String saveSeedstarter(final User user, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "seedstartermng";
        }
        this.userService.add(user);
        model.clear();
        return "redirect:/index";
    }

}
