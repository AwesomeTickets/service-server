package com.tickets.business.entities.repositories;

import com.tickets.business.entities.MovieOnShow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;


public interface MovieOnShowRepository extends CrudRepository<MovieOnShow, Integer> {

    @Query("select m from MovieOnShow m where " +
            "m.movie.movieID = ?1 AND " +
            "m.cinemaHall.cinemaHallID = ?2 AND " +
            "m.showDate = ?3 AND " +
            "m.showTime = ?4")
    List<MovieOnShow> findOne(Integer movieID, Integer cinemaHallID, Date showDate, Time showTime);

    @Query("select DISTINCT(m.cinemaHall.cinema.cinemaID) from MovieOnShow m where " +
            "m.movie.movieID = ?1 AND m.showDate = ?2")
    List<Integer> findCinemaIDsByMovieAndShowDate(Integer movieID, Date showDate);

    @Query("select m from MovieOnShow m where " +
            "m.movie.movieID = ?1 AND m.showDate = ?2 AND m.cinemaHall.cinema.cinemaID = ?3 " +
            "order by m.showTime asc")
    List<MovieOnShow> findShowsADay(Integer movieID, Date showDate, Integer cinemaID);
}
