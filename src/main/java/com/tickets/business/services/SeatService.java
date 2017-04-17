package com.tickets.business.services;

import com.tickets.business.entities.Seat;
import com.tickets.business.entities.repositories.SeatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by CrazeWong on 2017/4/16.
 */
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
        return seatRepo.findByMovieOnShowIDAndAvailable(movieOnShowID, false);
    }
}
