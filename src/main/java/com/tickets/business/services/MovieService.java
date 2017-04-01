package com.tickets.business.services;

import com.tickets.business.entities.*;
import com.tickets.business.entities.repositories.*;
import com.tickets.web.util.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepo;

    @Autowired
    private CountryRepository countryRepo;

    @Autowired
    private MovieStyleRepository movieStyleRepo;

    @Autowired
    private MovieTypeRepository movieTypeRepo;

    @Autowired
    private MovieStatusRepository movieStatusRepo;


    public MovieService() {
        super();
    }

    public List<Integer> getMovieByStatus(String status) {
        if (!status.equals("soon") && !status.equals("on")) return null;
        return  movieRepo.findByStatus(movieStatusRepo.findByStatus(status).get(0));
    }

    public Movie getMovie(Integer movieID) {
        return movieRepo.findOne(movieID);
    }
}
