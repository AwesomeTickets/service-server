package com.awesometickets.web;

import com.awesometickets.business.entities.User;
import com.awesometickets.util.LogUtil;
import com.awesometickets.web.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Manage the session.
 */
@Component
public class SessionManager {
    private static final Logger Log = LoggerFactory.getLogger(SessionManager.class);
    private static final String ATTR_PHONE = "phoneNum";

    @Value("${session.maxAge}")
    private int SESSION_MAX_AGE;

    /**
     * Add a user to current session.
     *
     * @param user The user object
     * @param req The http request
     */
    public void addUser(User user, HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.setMaxInactiveInterval(SESSION_MAX_AGE);
        session.setAttribute(ATTR_PHONE, user.getPhoneNum());
    }

    /**
     * Return the phone number store in the session, or
     * null if session does not exist.
     *
     * @param req The hee
     */
    public String getUserPhone(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null) {
            return null;
        }
        return (String) req.getSession().getAttribute(ATTR_PHONE);
    }

    /**
     * Return true if session exists.
     *
     * @param req The http request
     */
    public boolean isSessionExists(HttpServletRequest req) {
        return getUserPhone(req) != null;
    }

    /**
     * Remove current session.
     *
     * @param req The http request
     */
    public void remove(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
