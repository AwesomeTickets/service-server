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
    private MovieHasStyleRepository movieHasStyleRepo;

    @Autowired
    private MovieStyleRepository movieStyleRepo;

    @Autowired
    private MovieTypeRepository movieTypeRepo;

    @Autowired
    private MovieStatusRepository movieStatusRepo;

    public MovieService() {
        super();
    }

    public void createMovie(Movie movie, List<MovieStyle> movieStyles) {
        movieRepo.save(movie);
        for (MovieStyle ms : movieStyles) {
            movieHasStyleRepo.save(new MovieHasStyle(movie, ms));
        }
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

    public LinkedHashMap<String, Object> getMovieByStatus(String status) {
        if (!status.equals("soon") && !status.equals("on")) return null;

        List<Long> movieIDs = (List<Long>)(List) movieRepo.findByStatus(movieStatusRepo.findByStatus(status).get(0));
        LinkedHashMap<String, Object> re = new LinkedHashMap<String, Object>();

        re.put("count", movieIDs.size());
        re.put("movies", movieIDs);
        return re;
    }

    public LinkedHashMap<String, Object> getMovie(Long movieID) {
        Movie movie = movieRepo.findOne(movieID);
        if (movie == null) return null;

        List<MovieHasStyle> styles = movieHasStyleRepo.findByMovie(movie);
        List<String> stylesStrings = new ArrayList<String>();
        for (MovieHasStyle ms : styles) {
            stylesStrings.add(ms.getMovieStyle().getStyle());
        }

        LinkedHashMap<String, Object> re = new LinkedHashMap<String, Object>();

        re.put("id", movie.getMovieID());
        re.put("title", movie.getTitle());
        re.put("pubdate", movie.getPubdate());
        re.put("length", movie.getLength());
        re.put("rating", movie.getRating());
        re.put("posterSmall", movie.getPosterSmall());
        re.put("posterLarge", movie.getPosterLarge());
        re.put("country", movie.getCountry().getName());
        re.put("movieStatus", movie.getMovieStatus().getStatus());
        re.put("movieType", movie.getMovieType().getType());
        re.put("movieStyle", stylesStrings);
        return re;
    }

    // TODO MovieService
}
