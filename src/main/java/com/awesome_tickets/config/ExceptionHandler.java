package com.awesome_tickets.config;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * An ExceptionHandler to return a html for all exception.
 */
public class ExceptionHandler implements HandlerExceptionResolver {

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception ex) {
        response.setStatus(500);
        ex.printStackTrace();
        return new ModelAndView("exception");
    }

}
