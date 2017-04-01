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

    public void createMovie(Movie movie) {
        movieRepo.save(movie);
    }

    public void createCountry(Country country) {
        countryRepo.save(country);
    }

    public void createMovieStatus(MovieStatus movieStatus) {
        movieStatusRepo.save(movieStatus);
    }

    public void createMovieStyle(MovieStyle movieStyle) {
        movieStyleRepo.save(movieStyle);
    }

    public void createMovieType(MovieType movieType) {
        movieTypeRepo.save(movieType);
    }

    public List<Integer> getMovieByStatus(String status) {
        if (!status.equals("soon") && !status.equals("on")) return null;

        return  movieRepo.findByStatus(movieStatusRepo.findByStatus(status).get(0));
//        return movieIDs;
    }

    public Movie getMovie(Integer movieID) {
        Movie movie = movieRepo.findOne(movieID);
        if (movie == null) return null;
        else return movie;
    }

    // TODO MovieService
}
