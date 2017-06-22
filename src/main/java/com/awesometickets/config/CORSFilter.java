package com.awesometickets.config;

import com.awesometickets.web.controller.CinemaController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Cross-Origin Resource Sharing filter.
 */
public class CORSFilter implements Filter {
    private static final Logger Log = LoggerFactory.getLogger(CORSFilter.class);
    private static Set<String> allowedOrigins = new HashSet<String>(Arrays.asList(
        "http://localhost", "http://localhost:8080", "http://stevennl.cn"));

    public void init(FilterConfig filterConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        String ori = req.getHeader("Origin");
        if (ori != null && allowedOrigins.contains(ori)) {
            HttpServletResponse res = (HttpServletResponse)response;
            res.setHeader("Access-Control-Allow-Origin", ori);
            res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            res.setHeader("Access-Control-Max-Age", "3600");
            res.setHeader("Access-Control-Allow-Headers", "x-requested-with");
            res.setHeader("Access-Control-Allow-Credentials", "true");
        }
        chain.doFilter(request, response);
    }

    public void destroy() {}

}