package com.awesometickets.util;

import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;


public class LogUtil {

    /**
     * Print logs of http request.
     */
    public static void logReq(Logger logger, HttpServletRequest req) {
        String queryStr = req.getQueryString();
        if (queryStr == null || queryStr.length() == 0) {
            logger.info(req.getMethod() + " " + req.getRequestURI());
        } else {
            logger.info(req.getMethod() + " " + req.getRequestURI() + "?" + queryStr);
        }
    }

}
