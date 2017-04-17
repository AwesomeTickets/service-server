package com.tickets.business.entities.repositories;

import com.tickets.business.entities.Seat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by CrazeWong on 2017/4/16.
 */
public interface SeatRepository extends CrudRepository<Seat, Integer> {

    @Query("select s from Seat s where s.movieOnShow.movieOnShowID = ?1 AND s.available = ?2")
    List<Seat> findByMovieOnShowIDAndAvailable(Integer movieOnShowID, Boolean available);
}
