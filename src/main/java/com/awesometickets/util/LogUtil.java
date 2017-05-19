package com.awesometickets.util;

import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


public class LogUtil {

    /**
     * Print logs of http request.
     */
    public static void logReq(Logger logger, HttpServletRequest req) {
        String queryStr = req.getQueryString();
        if (queryStr == null || queryStr.length() == 0) {
            logger.info(req.getMethod() + " " + req.getRequestURI());
        } else {
            try {
                queryStr = URLDecoder.decode(queryStr, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.warn(e.getMessage());
            }
            logger.info(req.getMethod() + " " + req.getRequestURI() + "?" + queryStr);
        }
    }

}
