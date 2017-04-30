package com.awesome_tickets.business.entities.repositories;

import com.awesome_tickets.business.entities.CinemaHall;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CinemaHallRepository extends CrudRepository<CinemaHall, Integer> {

	@Query("select c.cinemaHallId, c.cinema.cinemaId, c.hallName from CinemaHall c where c.cinemaHallId = ?1")
    List<Object[]> findWithoutSeatLayout(Integer cinemaHallId);

    @Query("select c.cinemaHallId, c.seatLayout from CinemaHall c where c.cinemaHallId = ?1")
    List<Object[]> findWithSeatLayout(Integer cinemaHallId);

}
