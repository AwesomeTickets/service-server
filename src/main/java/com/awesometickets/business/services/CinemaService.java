package com.awesometickets.business.services;

import com.awesometickets.business.entities.*;
import com.awesometickets.business.entities.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CinemaService {

    @Autowired
    private CinemaRepository cinemaRepo;

    public CinemaService() {
        super();
    }

    /**
     * Return the cinema entity whose ID equals to @param cinemaId.
     */
    public Cinema getCinema(Integer cinemaId) {
        return cinemaRepo.findOne(cinemaId);
    }
}