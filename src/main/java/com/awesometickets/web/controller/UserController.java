package com.awesometickets.web.controller;

import com.awesometickets.business.entities.User;
import com.awesometickets.business.services.SmsService;
import com.awesometickets.business.services.UserService;
import com.awesometickets.util.LogUtil;
import com.awesometickets.web.SessionService;
import com.awesometickets.web.Validator;
import com.awesometickets.web.controller.response.ErrorResponse;
import com.awesometickets.web.controller.response.ErrorStatus;
import com.awesometickets.web.controller.response.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * RESTFul API of user resources.
 */
@RestController
@RequestMapping("/resource")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Validator validator;

    @Autowired
    private SessionService sessionService;

    private SmsService smsService = SmsService.getInstance();

    private static final Logger Log = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(path = "/user",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse register(@RequestParam("phoneNum") String phoneNum,
                                 @RequestParam("password") String password,
                                 @RequestParam("smsCode") String smsCode,
                                     HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(Log, request);
        if (!validator.isValidPhone(phoneNum)) {
            return new ErrorResponse(response, ErrorStatus.PHONE_INVALID_FORMAT);
        }
        if (smsService.verifySmsCode(phoneNum, smsCode)) {
            User user = userService.registerUserWithRawPassword(phoneNum, password);
            if (user == null) {
                return new ErrorResponse(response, ErrorStatus.PHONE_REGISTERED);
            }

            sessionService.setSessionUser(request, user);
            RestResponse res = new RestResponse();
            res.put("phoneNum", phoneNum);
            return res;
        } else {
            return new ErrorResponse(response, ErrorStatus.SMS_MISMATCH);
        }
    }

    @RequestMapping(path = "/session",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse doLogin(@RequestParam("phoneNum") String phoneNum,
                                 @RequestParam("password") String password,
                                 HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(Log, request);
        if (!validator.isValidPhone(phoneNum)) {
            return new ErrorResponse(response, ErrorStatus.PHONE_INVALID_FORMAT);
        }

        User user = userService.findUser(phoneNum, password);
        if (user == null) {
            return new ErrorResponse(response, ErrorStatus.PASSWORD_MISMATCH);
        }

        sessionService.setSessionUser(request, user);
        RestResponse res = new RestResponse();
        res.put("phoneNum", phoneNum);
        return res;
    }

    @RequestMapping(path = "/session",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse expireSession(HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(Log, request);

        RestResponse res = new RestResponse();
        User user = sessionService.getSessionUser(request);
        if (user == null) {
            res.put("expire", Boolean.TRUE);
        } else {
            res.put("expire", Boolean.FALSE);
        }

        return res;
    }

    @RequestMapping(path = "/session/drop",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse doLogout(@RequestParam("phoneNum") String phoneNum,
                                HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(Log, request);
        if (!validator.isValidPhone(phoneNum)) {
            return new ErrorResponse(response, ErrorStatus.PHONE_INVALID_FORMAT);
        }

        User user = sessionService.getSessionUser(request);
        if (user == null || !user.getPhoneNum().equals(phoneNum)) {
            return new ErrorResponse(response, ErrorStatus.SESSION_NOT_FOUND);
        }

        sessionService.clearSessionUser(request);
        RestResponse res = new RestResponse();
        res.put("phoneNum", phoneNum);
        return res;
    }



}
