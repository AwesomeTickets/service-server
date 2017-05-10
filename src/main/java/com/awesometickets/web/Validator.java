package com.awesometickets.web;

import com.awesometickets.business.entities.User;
import com.awesometickets.business.services.UserService;
import com.awesometickets.util.PhoneNumUtil;
import com.awesometickets.web.controller.response.ErrorResponse;
import com.awesometickets.web.controller.response.ErrorStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Validation of all request parameters.
 */
@Component
public class Validator {

    @Autowired
    private UserService userService;

    public int validateTicketParams(User user, String phoneNum, Integer[] seats) {
        if (!PhoneNumUtil.isPhone(phoneNum)) {
            return ErrorStatus.PHONE_INVALID_FORMAT;
        }
        if (seats.length > 8 || seats.length < 2 || seats.length % 2 != 0) {
            return ErrorStatus.BAD_REQUEST;
        }
        Integer userId = userService.getUserByPhoneNum(phoneNum);
        if (user == null) {
            return ErrorStatus.;
        }

        user.setUserId(userId);
        return -1;
    }

    public int validateTicketParams(String phoneNum, String ticketCode)
}
