package com.awesometickets.web.controller;

import com.awesometickets.business.entities.Seat;
import com.awesometickets.business.entities.Ticket;
import com.awesometickets.business.entities.User;
import com.awesometickets.business.services.SeatService;
import com.awesometickets.business.services.TicketService;
import com.awesometickets.business.services.UserService;
import com.awesometickets.util.LogUtil;
import com.awesometickets.web.SessionManager;
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

    @Autowired
    private SessionManager sessionManager;

    @RequestMapping(path = "",
                    method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse buyTicket(
        @RequestParam("movieOnShowId") Integer movieOnShowId,
        @RequestParam("phoneNum") String phoneNum,
        @RequestParam("seats") Integer[] seats,
        HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(LOG, request);
        if (!validator.isValidPhone(phoneNum)) {
            return new ErrorResponse(response, ErrorStatus.PHONE_INVALID_FORMAT);
        }
        if (!validator.isValidSeats(seats)) {
            return new ErrorResponse(response, ErrorStatus.BAD_REQUEST);
        }
        String sessionPhoneNum = sessionManager.getUserPhone(request);
        if (sessionPhoneNum == null || !phoneNum.equals(sessionPhoneNum)) {
            return new ErrorResponse(response, ErrorStatus.SESSION_NOT_FOUND);
        }
        User user = userService.findUser(sessionPhoneNum);
        if (user.getRemainPurchase() == 0) {
            return new ErrorResponse(response, ErrorStatus.PURCHASE_UNAVAILABLE);
        }
        List<Seat> seatList = new ArrayList<Seat>();
        if (!seatService.checkSeatExist(seatList, seats, movieOnShowId)) {
            return new ErrorResponse(response, ErrorStatus.SEAT_NOT_FOUND);
        } else if (!seatService.checkSeatsAvailable(seatList)) {
            return new ErrorResponse(response, ErrorStatus.SEAT_UNAVAILABLE);
        }
        List<Integer[]> dataList = new LinkedList<Integer[]>();
        for (Seat seat : seatList) {
            dataList.add(new Integer[] {seat.getRow(), seat.getCol()});
        }
        String code = ticketService.buyTicketAndGenerateCode(seatList, user);
        RestResponse re = new RestResponse();
        re.put("movieOnShowId", movieOnShowId);
        re.put("seats", dataList);
        re.put("phoneNum", phoneNum);
        re.put("ticketCode", code);
        return re;
    }

    @RequestMapping(path = "/check",
                    method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse ticketCheck(
        @RequestParam("ticketCode") String ticketCode,
        @RequestParam("phoneNum") String phoneNum,
        HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(LOG, request);
        if (!validator.isValidPhone(phoneNum)) {
            return new ErrorResponse(response, ErrorStatus.PHONE_INVALID_FORMAT);
        }
        Ticket ticket = ticketService.findByCode(ticketCode);
        if (ticket == null) {
            return new ErrorResponse(response, ErrorStatus.TICKET_CODE_NOT_FOUND);
        }
        User user = userService.findUser(phoneNum);
        if (user == null) {
            return new ErrorResponse(response, ErrorStatus.USER_NOT_FOUND);
        } else if (!user.getUserId().equals(ticket.getUser().getUserId())) {
            return new ErrorResponse(response, ErrorStatus.PHONE_MISMATCH);
        }
        if (!ticketService.checkTicket(ticket)) {
            return new ErrorResponse(response, ErrorStatus.TICKET_CHECKED);
        }
        List<Seat> seatList = ticketService.findTicketSeats(ticket);
        List<Integer[]> dataList = new LinkedList<Integer[]>();
        for (Seat seat : seatList) {
            dataList.add(new Integer[] {seat.getRow(), seat.getCol()});
        }
        RestResponse res = new RestResponse();
        res.put("movieOnShowId", seatList.get(0).getMovieOnShow().getMovieOnShowId());
        res.put("seats", dataList);
        res.put("phoneNum", phoneNum);
        return res;
    }

    @RequestMapping(path = "/info",
                    method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse ticketInfo(
        @RequestParam("ticketCode") String ticketCode,
        @RequestParam("phoneNum") String phoneNum,
        HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(LOG, request);
        if (!validator.isValidPhone(phoneNum)) {
            return new ErrorResponse(response, ErrorStatus.PHONE_INVALID_FORMAT);
        }
        Ticket ticket = ticketService.findByCode(ticketCode);
        if (ticket == null) {
            return new ErrorResponse(response, ErrorStatus.TICKET_CODE_NOT_FOUND);
        }
        User user = userService.findUser(phoneNum);
        if (user == null) {
            return new ErrorResponse(response, ErrorStatus.USER_NOT_FOUND);
        } else if (!user.getUserId().equals(ticket.getUser().getUserId())) {
            return new ErrorResponse(response, ErrorStatus.PHONE_MISMATCH);
        }
        List<Seat> seatList = ticketService.findTicketSeats(ticket);
        List<Integer[]> dataList = new LinkedList<Integer[]>();
        for (Seat seat : seatList) {
            dataList.add(new Integer[] {seat.getRow(), seat.getCol()});
        }
        RestResponse res = new RestResponse();
        res.put("movieOnShowId", seatList.get(0).getMovieOnShow().getMovieOnShowId());
        res.put("seats", dataList);
        res.put("valid", seatList.get(0).getTicket().getValid());
        res.put("phoneNum", phoneNum);
        return res;
    }
}
