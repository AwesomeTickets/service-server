package com.tickets.business.services;

import com.tickets.business.entities.MovieOnShow;
import com.tickets.business.entities.repositories.MovieOnShowRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;


@Service
public class MovieOnShowService {
    private static final Logger LOG = LoggerFactory.getLogger(MovieOnShowService.class);

    @Autowired
    private MovieOnShowRepository movieOnShowRepo;

    public MovieOnShowService() {
        super();
    }

    /**
     * Get the movieOnShow entity whose movieID, cinemaHallID, showDate, showTime equals to
     * @param movieID, the show movie's ID
     * @param cinemaHallID, the show cinemaHall's ID
     * @param showDate, the show date
     * @param showTime, the show time
     * @return the movieOnShow entity
     */
    public MovieOnShow getMovieOnShow(Integer movieID, Integer cinemaHallID, Date showDate, Time showTime) {
        List<MovieOnShow> movieOnShows = movieOnShowRepo.findOne(movieID, cinemaHallID, showDate, showTime);
        if (movieOnShows.size() == 0) return null;
        return movieOnShows.get(0);
    }

    /**
     * Get the movieOnShow entity whose ID equals to
     * @param movieOnShowID
     * @return the movieOnShow entity
     */
    public MovieOnShow getMovieOnShow(Integer movieOnShowID) {
        return movieOnShowRepo.findOne(movieOnShowID);
    }

    /**
     * Get the list of cinemaID for a movie at date
     * @param movieID, the movie's ID
     * @param date, the show date
     * @return list of the cinemas' ID
     */
    public List<Integer> getCinemaIDsShowAtDate(Integer movieID, Date date) {
        return movieOnShowRepo.findCinemaIDsByMovieAndShowDate(movieID, date);
    }

    /**
     * Get the list of movieOnShow entity for a movie at date in the cinema
     * @param movieID, the movie's ID
     * @param showDate, the show date
     * @param cinemaID, the show cinema's ID
     * @return list of the movieOnShow entities
     */
    public List<MovieOnShow> getShowsADay(Integer movieID, Date showDate, Integer cinemaID) {
        return movieOnShowRepo.findShowsADay(movieID, showDate, cinemaID);
    }
}
