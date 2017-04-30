package com.awesometickets.business.entities.repositories;

import com.awesometickets.business.entities.Seat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends CrudRepository<Seat, Integer> {

    @Query("select s.row, s.col from Seat s where s.movieOnShow.movieOnShowId = ?1 AND s.available = ?2")
    List<Object[]> findByMovieOnShowIDAndAvailable(Integer movieOnShowId, Boolean available);

}
