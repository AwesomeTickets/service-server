package com.awesome_tickets.business.entities.repositories;

import com.awesome_tickets.business.entities.MovieOnShow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Repository
public interface MovieOnShowRepository extends CrudRepository<MovieOnShow, Integer> {

    @Query("select m from MovieOnShow m where " +
            "m.movie.movieId = ?1 AND " +
            "m.cinemaHall.cinemaHallId = ?2 AND " +
            "m.showDate = ?3 AND " +
            "m.showTime = ?4")
    List<MovieOnShow> findOne(Integer movieId, Integer cinemaHallId, Date showDate, Time showTime);

    @Query("select DISTINCT(m.cinemaHall.cinema.cinemaId), m.showDate from MovieOnShow m where " +
            "m.movie.movieId = ?1 AND m.showDate IN (?2)")
    List<Object[]> findCinemaByDate(Integer movieId, List<Date> showDates);

    @Query("select m.movieOnShowId from MovieOnShow m where " +
            "m.movie.movieId = ?1 AND m.showDate = ?2 AND m.cinemaHall.cinema.cinemaId = ?3")
    List<Integer> findByDate(Integer movieId, Date showDate, Integer cinemaId);

    @Query("select m.price, m.showTime from MovieOnShow m where " +
            "m.movie.movieId = ?1 AND m.showDate = ?2 AND m.cinemaHall.cinema.cinemaId = ?3 " +
            "order by m.showTime asc")
    List<Object[]> findBriefByDate(Integer movieId, Date showDate, Integer cinemaId);
}
