package com.awesometickets.web.controller;

import com.awesometickets.business.services.SmsService;
import com.awesometickets.util.LogUtil;
import com.awesometickets.util.PhoneNumUtil;
import com.awesometickets.web.controller.response.ErrorResponse;
import com.awesometickets.web.controller.response.ErrorStatus;
import com.awesometickets.web.controller.response.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * RESTFul API of sms resources.
 */
@RestController
@RequestMapping("/resource/sms")
public class SmsController {
    private static final Logger LOG = LoggerFactory.getLogger(SmsController.class);
    private SmsService smsService = SmsService.getInstance();

    @RequestMapping(path = "/{phoneNum}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse sendSms(@PathVariable String phoneNum,
                                HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(LOG, request);
        if (!PhoneNumUtil.isPhone(phoneNum)) {
            return new ErrorResponse(response, ErrorStatus.PHONE_INVALID_FORMAT);
        }
        if (smsService.sendSmsCode(phoneNum)) {
            RestResponse res = new RestResponse();
            res.put("phoneNum", phoneNum);
            return res;
        } else {
            return new ErrorResponse(response, ErrorStatus.SMS_SEND_FAIL);
        }
    }

    @RequestMapping(path = "/{phoneNum}/check",
                    method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse checkSmsCode(@PathVariable String phoneNum,
                                     @RequestParam("code") String code,
                                     HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(LOG, request);
        if (!PhoneNumUtil.isPhone(phoneNum)) {
            return new ErrorResponse(response, ErrorStatus.PHONE_INVALID_FORMAT);
        }
        if (smsService.verifySmsCode(phoneNum, code)) {
            RestResponse res = new RestResponse();
            res.put("phoneNum", phoneNum);
            return res;
        } else {
            return new ErrorResponse(response, ErrorStatus.SMS_MISMATCH);
        }
    }
}
