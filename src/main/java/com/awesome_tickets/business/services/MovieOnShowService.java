package com.awesome_tickets.business.services;

import com.awesome_tickets.business.entities.MovieOnShow;
import com.awesome_tickets.business.entities.repositories.MovieOnShowRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.*;


@Service
public class MovieOnShowService {

    @Autowired
    private MovieOnShowRepository movieOnShowRepo;

    public MovieOnShowService() {
        super();
    }

    /**
     * Find one MovieOnShow object with its details.
     */
    public MovieOnShow getMovieOnShow(Integer movieId, Integer cinemaHallId, Date showDate, Time showTime) {
        List<MovieOnShow> movieOnShows = movieOnShowRepo.findOne(movieId, cinemaHallId, showDate, showTime);
        if (movieOnShows.size() == 0) return null;
        return movieOnShows.get(0);
    }

    /**
     * Find one MovieOnShow object with its id.
     */
    public MovieOnShow getMovieOnShow(Integer movieOnShowId) {
        return movieOnShowRepo.findOne(movieOnShowId);
    }

    /**
     * Get the map of cinemaID for a movie at some dates
     *
     * @param movieId, the movie's ID
     * @param showDates, the show dates
     * @return map of list of the cinemas' ID, with date keys
     */
    public Map<Date, List<Integer>> getCinemaByDates(Integer movieId, List<Date> showDates) {
        Map<Date, List<Integer>> dataMap = new LinkedHashMap<Date, List<Integer>>();
        for (int i = 0; i < showDates.size(); i++) {
            dataMap.put(showDates.get(i), new ArrayList<Integer>());
        }
        List<Object[]> results = movieOnShowRepo.findCinemaByDate(movieId, showDates);
        for (int i = 0; i < results.size(); i++) {
            dataMap.get((Date) results.get(i)[1]).add((Integer) results.get(i)[0]);
        }
        return dataMap;
    }

    /**
     * Get the list of movieOnShow id for a movie at date in the cinema
     * @param movieId, the movie's ID
     * @param showDate, the show date
     * @param cinemaID, the show cinema's ID
     * @return list of the movieOnShowIDs
     */
    public List<Integer> getMovieOnShowByDate(Integer movieId, Date showDate, Integer cinemaID) {
        return movieOnShowRepo.findByDate(movieId, showDate, cinemaID);
    }

    /**
     * Get the  movieOnShow's price and showTime for a movie at date in the cinema
     * @param movieId, the movie's ID
     * @param showDate, the show date
     * @param cinemaId, the show cinema's ID
     * @return object[0], the min price in a day
     *         object[1], the showTime list
     */
    public Object[] getBriefMovieOnShowByDate(Integer movieId, Date showDate, Integer cinemaId) {
        List<Object[]> objsList = movieOnShowRepo.findBriefByDate(movieId, showDate, cinemaId);
        Object[] result = new Object[2];
        result[0] = Float.MAX_VALUE;
        result[1] = new ArrayList<String>();
        for (Object[] objs: objsList) {
            if ((Float)objs[0] < (Float)result[0]) result[0] = objs[0];
            ((ArrayList<String>)result[1]).add(objs[1].toString());
        }
        return result;
    }
}
