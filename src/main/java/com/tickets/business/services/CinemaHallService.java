package com.tickets.business.services;

import com.tickets.business.entities.CinemaHall;
import com.tickets.business.entities.repositories.CinemaHallRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
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

    public List<Object[]> getWithoutSeatLayout(Integer cinemaHallID)
    {
        return cinemaHallRepo.findWithoutSeatLayout(cinemaHallID);
    }

    public List<Object[]> getWithSeatLayout(Integer cinemaHallID)
    {
        return cinemaHallRepo.findWithSeatLayout(cinemaHallID);
    }
}
