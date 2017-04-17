package com.tickets.business.services;

import com.tickets.business.entities.Cinema;
import com.tickets.business.entities.repositories.CinemaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by CrazeWong on 2017/4/16.
 */
@Service
public class CinemaService {
    private static final Logger LOG = LoggerFactory.getLogger(CinemaService.class);

    @Autowired
    private CinemaRepository cinemaRepo;

    public CinemaService() {
        super();
    }

    /**
     * Get the cinema entity whose ID equals to
     * @param cinemaID
     * @return the cinema entity
     */
    public Cinema getCinema(Integer cinemaID) {
        return cinemaRepo.findOne(cinemaID);
    }
}
