package com.awesometickets.web.controller;

import com.awesometickets.business.entities.User;
import com.awesometickets.business.services.SmsService;
import com.awesometickets.business.services.TicketService;
import com.awesometickets.business.services.UserService;
import com.awesometickets.util.LogUtil;
import com.awesometickets.web.SessionManager;
import com.awesometickets.web.Validator;
import com.awesometickets.web.controller.response.CollectionResponse;
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
import java.util.Collection;


/**
 * RESTFul API of user resources.
 */
@RestController
@RequestMapping("/resource")
public class UserController {
    private static final Logger Log = LoggerFactory.getLogger(UserController.class);
    private SmsService smsService = SmsService.getInstance();

    @Autowired
    private UserService userService;

    @Autowired
    private Validator validator;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private SessionManager sessionManager;


    @RequestMapping(path = "/session/check",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse checkSession(HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(Log, request);
        RestResponse res = new RestResponse();
        res.put("expire", !sessionManager.isSessionExists(request));
        return res;
    }

    @RequestMapping(path = "/user",
                    method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse register(
        @RequestParam("phoneNum") String phoneNum,
        @RequestParam("password") String password,
        @RequestParam("smsCode") String smsCode,
        HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(Log, request);
        if (!validator.isValidPhone(phoneNum)) {
            return new ErrorResponse(response, ErrorStatus.PHONE_INVALID_FORMAT);
        }
        if (!validator.isValidPassword(password)) {
            return new ErrorResponse(response, ErrorStatus.PASSWORD_INVALID_FORMAT);
        }
        if (!smsService.verifySmsCode(phoneNum, smsCode)) {
            return new ErrorResponse(response, ErrorStatus.SMS_MISMATCH);
        }
        if (userService.hasUser(phoneNum)) {
            return new ErrorResponse(response, ErrorStatus.PHONE_REGISTERED);
        }
        User user = userService.addUser(phoneNum, password);
        sessionManager.addUser(user, request);
        RestResponse res = new RestResponse();
        res.put("phoneNum", phoneNum);
        return res;
    }

    @RequestMapping(path = "/user/{phoneNum}/ticket/history",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse userTickets(
        @PathVariable("phoneNum") String phoneNum,
        HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(Log, request);
        if (!validator.isValidPhone(phoneNum)) {
            return new ErrorResponse(response, ErrorStatus.PHONE_INVALID_FORMAT);
        }
        String sessionPhone = sessionManager.getUserPhone(request);
        if (sessionPhone == null || !phoneNum.equals(sessionPhone)) {
            return new ErrorResponse(response, ErrorStatus.SESSION_NOT_FOUND);
        }
        return new CollectionResponse(ticketService.getAllTicketsByPhoneNum(phoneNum));
    }

    @RequestMapping(path = "/session",
                    method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse login(
        @RequestParam("phoneNum") String phoneNum,
        @RequestParam("password") String password,
        HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(Log, request);
        if (!validator.isValidPhone(phoneNum)) {
            return new ErrorResponse(response, ErrorStatus.PHONE_INVALID_FORMAT);
        }
        if (!validator.isValidPassword(password)) {
            return new ErrorResponse(response, ErrorStatus.PASSWORD_INVALID_FORMAT);
        }
        User user = userService.findUser(phoneNum);
        if (user == null) {
            return new ErrorResponse(response, ErrorStatus.USER_NOT_FOUND);
        }
        if (!userService.checkPassword(password, user.getPassword())) {
            return new ErrorResponse(response, ErrorStatus.PASSWORD_MISMATCH);
        }
        sessionManager.addUser(user, request);
        RestResponse res = new RestResponse();
        res.put("phoneNum", phoneNum);
        return res;
    }

    @RequestMapping(path = "/session/drop",
                    method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse logout(
        @RequestParam("phoneNum") String phoneNum,
        HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(Log, request);
        if (!validator.isValidPhone(phoneNum)) {
            return new ErrorResponse(response, ErrorStatus.PHONE_INVALID_FORMAT);
        }
        String sessionPhone = sessionManager.getUserPhone(request);
        if (sessionPhone == null || !phoneNum.equals(sessionPhone)) {
            return new ErrorResponse(response, ErrorStatus.SESSION_NOT_FOUND);
        }
        sessionManager.remove(request);
        RestResponse res = new RestResponse();
        res.put("phoneNum", phoneNum);
        return res;
    }
}
