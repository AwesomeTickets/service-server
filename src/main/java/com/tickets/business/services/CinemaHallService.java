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
     * Return the cinemaHall entity whose ID equals to @param cinemaHallID.
     */
    public CinemaHall getCinemaHall(Integer cinemaHallID) {
        return cinemaHallRepo.findOne(cinemaHallID);
    }

    public CinemaHall getWithoutSeatLayout(Integer cinemaHallID)
    {
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

    public CinemaHall getWithSeatLayout(Integer cinemaHallID)
    {
        
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
