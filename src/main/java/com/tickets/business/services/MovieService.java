package com.tickets.business.services;

import com.tickets.business.entities.*;
import com.tickets.business.entities.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class MovieService {

    private static final Logger LOG = LoggerFactory.getLogger(MovieService.class);

    @Autowired
    private MovieRepository movieRepo;


    public MovieService() {
        super();
    }

    /**
     * Return the movie entity whose ID equals to @param movieID.
     */
    public Movie getMovie(Integer movieID) {
        return movieRepo.findOne(movieID);
    }

    /**
     * Return the list of ID of movie whose status equals to @param status.
     */
    public List<Integer> getMovieByStatus(String status) {
        return movieRepo.findByMovieStatus(status);
    }

    /**
     * Return the list of (ID, URI of large poster) tuple.
     *
     * @param maxCount Maximum size of the result list
     */
    public List<Object[]> getLargePoster(int maxCount) {
        // TODO
        ArrayList<Object[]> posters = new ArrayList<Object[]>();
        posters.add(new Object[]{1, "https://raw.githubusercontent.com/AwesomeTickets/Dashboard/master/img/poster/large/1.png"});
        posters.add(new Object[]{2, "https://raw.githubusercontent.com/AwesomeTickets/Dashboard/master/img/poster/large/2.png"});
        posters.add(new Object[]{3, "https://raw.githubusercontent.com/AwesomeTickets/Dashboard/master/img/poster/large/3.png"});
        return posters;
    }
}
