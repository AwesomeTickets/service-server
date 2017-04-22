package com.awesome_tickets.business.services;

import com.awesome_tickets.business.entities.*;
import com.awesome_tickets.business.entities.repositories.*;
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
     * Return the cinema entity whose ID equals to @param cinemaID.
     */
    public Cinema getCinema(Integer cinemaID) {
        return cinemaRepo.findOne(cinemaID);
    }

}