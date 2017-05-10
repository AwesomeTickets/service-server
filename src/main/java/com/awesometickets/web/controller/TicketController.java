package com.awesometickets.web.controller;

import com.awesometickets.business.entities.Seat;
import com.awesometickets.business.entities.User;
import com.awesometickets.business.services.SeatService;
import com.awesometickets.business.services.TicketService;
import com.awesometickets.business.services.UserService;
import com.awesometickets.util.LogUtil;
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
import java.util.*;

/**
 * RESTFul API of ticket resources.
 */
@RestController
@RequestMapping("/resource/ticket")
public class TicketController {

    private static final Logger LOG = LoggerFactory.getLogger(TicketController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private Validator validator;

    @RequestMapping(path = "",
                    method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse buyTicket(
        @RequestParam("movieOnShowId") Integer movieOnShowId,
        @RequestParam("phoneNum") String phoneNum,
        @RequestParam("seats") Integer[] seats,
        HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(LOG, request);

        if (!validator.checkPhoneNum(phoneNum)) {
            return new ErrorResponse(response, ErrorStatus.PHONE_INVALID_FORMAT);
        } else if (!validator.checkSeatsFormat(seats)) {
            return new ErrorResponse(response, ErrorStatus.SEAT_NOT_FOUND);
        }

        User user = userService.getUserByPhoneNum(phoneNum);
        if (user == null) {
            return new ErrorResponse(response, ErrorStatus.PHONE_NOT_VERIFIED);
        } else if (user.getRemainPurchase() == 0) {
            return new ErrorResponse(response, ErrorStatus.PURCHASE_UNAVAILABLE);
        }

        List<Seat> seatList = new ArrayList<Seat>();
        if (!seatService.checkSeatsAvailable(seatList, seats, movieOnShowId)) {
            return new ErrorResponse(response, ErrorStatus.SEAT_UNAVAILABLE);
        }

        String code = ticketService.buyTicketAndGenerateCode(seatList, user);
        List<Integer[]> dataList = new LinkedList<Integer[]>();
        for (Seat seat : seatList) {
            dataList.add(new Integer[] {seat.getRow(), seat.getCol()});
        }
        RestResponse re = new RestResponse();
        re.put("movieOnShowId", movieOnShowId);
        re.put("seats", dataList);
        re.put("phoneNum", phoneNum);
        re.put("ticketCode", code);
        return re;
    }

//    @RequestMapping(path = "/check",
//                    method = RequestMethod.POST,
//                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public RestResponse ticketCheck(
//        @RequestParam("ticketCode") String ticketCode,
//        @RequestParam("phoneNum") String phoneNum,
//        HttpServletRequest request, HttpServletResponse response) {
//        LogUtil.logReq(LOG, request);
//
//        if (!PhoneNumUtil.isPhone(phoneNum)) {
//            return new ErrorResponse(response, ErrorStatus.PHONE_INVALID_FORMAT);
//        }
//        User user = userService.getUserByPhoneNum(phoneNum);
//        if (user == null) {
//            return new ErrorResponse(response, ErrorStatus.RESOURCE_NOT_FOUND);
//        }
//        List<Seat> seatList = ticketService.checkTicket(ticketCode, user);
//        if (seatList == null) {
//            return new ErrorResponse(response, ErrorStatus.RESOURCE_NOT_FOUND);
//        }
//
//        List<Integer[]> dataList = new LinkedList<Integer[]>();
//        for (Seat seat : seatList) {
//            dataList.add(new Integer[] {seat.getRow(), seat.getCol()});
//        }
//        RestResponse re = new RestResponse();
//        re.put("movieOnShowId", seatList.get(0).getMovieOnShow().getMovieOnShowId());
//        re.put("seats", dataList);
//        re.put("phoneNum", phoneNum);
//        return re;
//    }
//
//    @RequestMapping(path = "/info",
//                    method = RequestMethod.POST,
//                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public RestResponse ticketInfo(
//        @RequestParam("ticketCode") String ticketCode,
//        @RequestParam("phoneNum") String phoneNum,
//        HttpServletRequest request, HttpServletResponse response) {
//        LogUtil.logReq(LOG, request);
//
//        if (!PhoneNumUtil.isPhone(phoneNum)) {
//            return new ErrorResponse(response, ErrorStatus.PHONE_INVALID_FORMAT);
//        }
//        User user = userService.getUserByPhoneNum(phoneNum);
//        if (user == null) {
//            return new ErrorResponse(response, ErrorStatus.RESOURCE_NOT_FOUND);
//        }
//        List<Seat> seatList = ticketService.ticketInfo(ticketCode, user);
//        if (seatList == null) {
//            return new ErrorResponse(response, ErrorStatus.RESOURCE_NOT_FOUND);
//        }
//
//        List<Integer[]> dataList = new LinkedList<Integer[]>();
//        for (Seat seat : seatList) {
//            dataList.add(new Integer[] {seat.getRow(), seat.getCol()});
//        }
//        RestResponse re = new RestResponse();
//        re.put("movieOnShowId", seatList.get(0).getMovieOnShow().getMovieOnShowId());
//        re.put("seats", dataList);
//        re.put("valid", seatList.get(0).getTicket().getValid());
//        re.put("phoneNum", phoneNum);
//        return re;
//    }
}
