package com.tickets.business.services;

import com.tickets.business.entities.Seat;
import com.tickets.business.entities.repositories.SeatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.LinkedList;

@Service
public class SeatService {
    private static final Logger LOG = LoggerFactory.getLogger(SeatService.class);

    @Autowired
    private SeatRepository seatRepo;

    public SeatService() {
        super();
    }

    /**
     * Get the list of Seat those are unavailable
     * @param movieOnShowID, the movieOnShow's ID
     * @return list of the Seat entities
     */
    public List<Seat> getUnavailable(Integer movieOnShowID) {
        List<Seat> seatList = new LinkedList<Seat>();
        List<Object[]> seats =  seatRepo.findByMovieOnShowIDAndAvailable(movieOnShowID, false);
        for (Object[] seat : seats) {
            Integer row = (Integer)seat[0];
            Integer col = (Integer)seat[1];
            Seat s = new Seat();
            s.setRow(row);
            s.setCol(col);
            seatList.add(s);
        }
        return seatList;
    }
}
