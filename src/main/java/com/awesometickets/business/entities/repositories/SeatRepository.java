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

    // TODO No need to join 'MovieOnShow' table
    @Query("from Seat s " +
            "left outer join fetch s.movieOnShow " +
            "left outer join fetch s.ticket " +
            "where s.ticket.ticketId= ?1")
    List<Seat> findByTicketIdWithMovieOnShowAndTicket(Integer ticketId);

    @Query("from Seat s where s.row = ?1 " +
            "AND s.col = ?2 " +
            "AND s.movieOnShow.movieOnShowId = ?3")
    Seat findByRowAndColAndMovieOnShowId(Integer row, Integer col, Integer movieOnShowId);
}
