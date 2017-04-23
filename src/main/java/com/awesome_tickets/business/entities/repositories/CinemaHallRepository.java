package com.awesome_tickets.business.entities.repositories;

import com.awesome_tickets.business.entities.CinemaHall;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CinemaHallRepository extends CrudRepository<CinemaHall, Integer> {

	@Query("select c.cinemaHallID, c.cinema.cinemaID, c.name from CinemaHall c where c.cinemaHallID = ?1")
    List<Object[]> findWithoutSeatLayout(Integer cinemaHallID);

    @Query("select c.cinemaHallID, c.seatLayout from CinemaHall c where c.cinemaHallID = ?1")
    List<Object[]> findWithSeatLayout(Integer cinemaHallID);

}
