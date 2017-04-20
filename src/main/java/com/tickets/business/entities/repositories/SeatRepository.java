package com.tickets.business.entities.repositories;

import com.tickets.business.entities.Seat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface SeatRepository extends CrudRepository<Seat, Integer> {

    @Query("select s.row, s.col from Seat s where s.movieOnShow.movieOnShowID = ?1 AND s.available = ?2")
    List<Object[]> findByMovieOnShowIDAndAvailable(Integer movieOnShowID, Boolean available);
}
