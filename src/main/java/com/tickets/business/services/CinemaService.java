package com.tickets.business.services;

import com.tickets.business.entities.*;
import com.tickets.business.entities.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


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