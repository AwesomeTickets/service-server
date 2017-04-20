package com.tickets.business.services;

import com.tickets.business.entities.CinemaHall;
import com.tickets.business.entities.Cinema;
import com.tickets.business.entities.repositories.CinemaHallRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CinemaHallService {
    private static final Logger LOG = LoggerFactory.getLogger(CinemaHallService.class);

    @Autowired
    private CinemaHallRepository cinemaHallRepo;

    public CinemaHallService() {
        super();
    }

    /**
     * Find the CinemaHall with a given ID and
     * project without 'seatLayout' attribute.
     *
     * @param cinemaHallID The id of the CinemaHall
     */
    public CinemaHall getWithoutSeatLayout(Integer cinemaHallID) {
        List<Object[]> list = cinemaHallRepo.findWithoutSeatLayout(cinemaHallID);

        Object[] o = list.get(0);
        Integer cinemaID = (Integer)o[1];
        String name = (String )o[2];

        Cinema cinema = new Cinema();
        cinema.setCinemaID(cinemaID);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCinemaHallID(cinemaHallID);
        cinemaHall.setCinema(cinema);
        cinemaHall.setName(name);

        return cinemaHall;
    }

    /**
     * Find the CinemaHall with a given ID and
     * project with only 'seatLayout' attribute.
     *
     * @param cinemaHallID The id of the CinemaHall
     */
    public CinemaHall getWithSeatLayout(Integer cinemaHallID) {
        
        List<Object[]> list = cinemaHallRepo.findWithSeatLayout(cinemaHallID);

        Object[] o = list.get(0);
        Integer cinemaID = (Integer)o[0];
        String seatLayout = (String)o[1];

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCinemaHallID(cinemaHallID);
        cinemaHall.setSeatLayout(seatLayout);

        return cinemaHall;
    }

}
