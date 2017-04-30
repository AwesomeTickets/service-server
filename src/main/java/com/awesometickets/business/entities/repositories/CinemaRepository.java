package com.awesometickets.business.entities.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.awesometickets.business.entities.Cinema;


@Repository
public interface CinemaRepository extends CrudRepository<Cinema, Integer> {
}
