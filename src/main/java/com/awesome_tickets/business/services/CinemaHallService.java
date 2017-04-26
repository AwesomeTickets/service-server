package com.awesome_tickets.business.services;

import com.awesome_tickets.business.entities.CinemaHall;
import com.awesome_tickets.business.entities.Cinema;
import com.awesome_tickets.business.entities.repositories.CinemaHallRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CinemaHallService {

    @Autowired
    private CinemaHallRepository cinemaHallRepo;

    public CinemaHallService() {
        super();
    }

    /**
     * Find the CinemaHall with a given ID and
     * project without 'seatLayout' attribute.
     *
     * @param cinemaHallId The id of the CinemaHall
     */
    public CinemaHall getWithoutSeatLayout(Integer cinemaHallId) {
        List<Object[]> list = cinemaHallRepo.findWithoutSeatLayout(cinemaHallId);
        Object[] o = list.get(0);
        Integer cinemaID = (Integer)o[1];
        String name = (String )o[2];
        Cinema cinema = new Cinema();
        cinema.setCinemaId(cinemaID);
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCinemaHallId(cinemaHallId);
        cinemaHall.setCinema(cinema);
        cinemaHall.setName(name);
        return cinemaHall;
    }

    /**
     * Find the CinemaHall with a given ID and
     * project with only 'seatLayout' attribute.
     *
     * @param cinemaHallId The id of the CinemaHall
     */
    public CinemaHall getWithSeatLayout(Integer cinemaHallId) {
        List<Object[]> list = cinemaHallRepo.findWithSeatLayout(cinemaHallId);
        Object[] o = list.get(0);
        Integer cinemaID = (Integer)o[0];
        String seatLayout = (String)o[1];
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCinemaHallId(cinemaHallId);
        cinemaHall.setSeatLayout(seatLayout);
        return cinemaHall;
    }
}
