package com.tickets.business.services;

import com.tickets.business.entities.MovieOnShow;
import com.tickets.business.entities.repositories.MovieOnShowRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


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
     *
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
     * Get the movieOnShow entity by id.
     */
    public MovieOnShow getMovieOnShow(Integer movieOnShowID) {
        return movieOnShowRepo.findOne(movieOnShowID);
    }

    /**
     * Get the map of cinemaID for a movie at some dates
     *
     * @param movieID, the movie's ID
     * @param showDates, the show dates
     * @return map of list of the cinemas' ID, with date keys
     */
    public Map<Date, List<Integer>> getCinemaByDates(Integer movieID, List<Date> showDates) {
        Map<Date, List<Integer>> dataMap = new LinkedHashMap<Date, List<Integer>>();
        for (int i = 0; i < showDates.size(); i++) {
            dataMap.put(showDates.get(i), new ArrayList<Integer>());
        }
        List<Object[]> results = movieOnShowRepo.findCinemaByDate(movieID, showDates);
        for (int i = 0; i < results.size(); i++) {
            dataMap.get((Date) results.get(i)[1]).add((Integer) results.get(i)[0]);
        }
        return dataMap;
    }

    /**
     * Get the list of movieOnShow id for a movie at date in the cinema
     * @param movieID, the movie's ID
     * @param showDate, the show date
     * @param cinemaID, the show cinema's ID
     * @return list of the movieOnShowIDs
     */
    public List<Integer> getMovieOnShowByDate(Integer movieID, Date showDate, Integer cinemaID) {
        return movieOnShowRepo.findByDate(movieID, showDate, cinemaID);
    }

    /**
     * Get the list of movieOnShow entity for a movie at date in the cinema
     * @param movieID, the movie's ID
     * @param showDate, the show date
     * @param cinemaID, the show cinema's ID
     * @return list of the movieOnShow entities
     */
    public List<MovieOnShow> getBriefMovieOnShowByDate(Integer movieID, Date showDate, Integer cinemaID) {
        return movieOnShowRepo.findBriefByDate(movieID, showDate, cinemaID);
    }
}
