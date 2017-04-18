package com.tickets.web.controller;

import com.tickets.business.entities.Seat;
import com.tickets.business.services.SeatService;
import com.tickets.web.controller.response.CollectionResponse;
import com.tickets.web.controller.response.RestResponse;
import com.tickets.web.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;

import java.util.List;


/**
 * RESTFul API of seat resources.
 */
@RestController
@RequestMapping("/resource/seat")
public class SeatController {

    @Autowired
    private SeatService seatService;

    private static final Logger LOG = LoggerFactory.getLogger(SeatController.class);

    @RequestMapping(path = "/unavailable",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse getUnavailableSeat(@RequestParam("movieOnShowID") Integer movieOnShowID,
                                           HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(LOG, request);

        List<Integer[]> dataList = new LinkedList<Integer[]>();

        // TODO Construct seats with only 'row' and 'col' attributes

        List<Seat> seats = seatService.getUnavailable(movieOnShowID);

        for (Seat seat : seats) {
            dataList.add(new Integer[] {seat.getRow(), seat.getCol()});
        }

        CollectionResponse result = new CollectionResponse(dataList);
        response.setStatus(200);
        return result;
    }

}