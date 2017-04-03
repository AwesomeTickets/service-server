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
        ArrayList<Object[]> posters = new ArrayList<Object[]>();
        List<Object[]> movies = movieRepo.findByMoviePoster();
        int num = movies.size();
        int min = (maxCount<num) ? maxCount : num;
        for(int i = 0; i < min;i ++)
        {
            posters.add(movies.get(i));
        }
        return posters;
    }
}
