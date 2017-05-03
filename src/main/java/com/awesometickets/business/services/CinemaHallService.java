package com.awesometickets.business.services;

import com.awesometickets.business.entities.CinemaHall;
import com.awesometickets.business.entities.Cinema;
import com.awesometickets.business.entities.repositories.CinemaHallRepository;
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
        if (list.size() == 0) return null;

        Object[] o = list.get(0);
        Integer cinemaID = (Integer)o[1];
        String name = (String )o[2];
        Cinema cinema = new Cinema();
        cinema.setCinemaId(cinemaID);
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCinemaHallId(cinemaHallId);
        cinemaHall.setCinema(cinema);
        cinemaHall.setHallName(name);
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
