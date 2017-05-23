package com.awesometickets.web;

import com.awesometickets.business.entities.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Manage the session.
 */
@Component
public class SessionService {

    public void setSessionUser(HttpServletRequest request, User user) {
        if (user.getPhoneNum() == null || user.getPassword() == null) return;
        request.getSession().setAttribute("phoneNum", user.getPhoneNum());
        request.getSession().setAttribute("password", user.getPassword());
    }

    public User getSessionUser(HttpServletRequest request) {
        String phoneNum = (String) request.getSession().getAttribute("phoneNum");
        String password = (String) request.getSession().getAttribute("password");
        if (phoneNum == null || password == null) return null;
        User user = new User();
        user.setPhoneNum(phoneNum);
        user.setPassword(password);
        return user;
    }

    public void clearSessionUser(HttpServletRequest request) {
        request.getSession().removeAttribute("phoneNum");
        request.getSession().removeAttribute("password");
    }
}
